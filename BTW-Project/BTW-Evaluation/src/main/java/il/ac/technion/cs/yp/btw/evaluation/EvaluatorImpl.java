package il.ac.technion.cs.yp.btw.evaluation;

import il.ac.technion.cs.yp.btw.citysimulation.VehicleDescriptor;
import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.classes.StatisticalReport;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Guy Rephaeli
 * @date 05-Jun-18.
 */
public class EvaluatorImpl implements Evaluator {
    final static Logger logger = Logger.getLogger(EvaluatorImpl.class);

    private Map<VehicleDescriptor, BTWWeight> timeForVehicle;
    private Map<TrafficLight, Pair<BTWWeight, Integer> > waitingTimeOnTrafficLight;
    private Map<Road, Pair<BTWWeight, Integer> > drivingTimeOnRoad;

    public EvaluatorImpl(BTWDataBase db, List<VehicleDescriptor> descriptors) {
        this.timeForVehicle = descriptors
                .stream()
                .collect(Collectors
                        .toMap(d -> d,
                                d -> BTWWeight.of(0L)));
        this.waitingTimeOnTrafficLight = db
                .getAllTrafficLights()
                .stream()
                .collect(Collectors
                        .toMap(tl -> tl,
                                tl -> new Pair<>(tl.getMinimumWeight(), 0)));
        this.drivingTimeOnRoad = db
                .getAllRoads()
                .stream()
                .collect(Collectors
                        .toMap(rd -> rd,
                                rd -> new Pair<>(rd.getMinimumWeight(), 0)));
    }

    private static <T extends TrafficObject> BTWWeight averageCounts(Map<T, Pair<BTWWeight, Integer> > weightMap) {
        Pair<Long, Integer> total = weightMap
                .values()
                .stream()
                .map(p -> new Pair<>(p.getKey().seconds() * p.getValue(), p.getValue()))
                .reduce(new Pair<>(0L, 0),
                        (p1, p2) -> new Pair<>(p1.getKey() + p2.getKey(), p1.getValue() + p2.getValue()));
        return BTWWeight.of(total.getKey() / total.getValue());
    }

    private static <T extends TrafficObject> Pair<BTWWeight, Integer> pairFromReport(T element, Map<T, StatisticalReport> reportMap) {
        StatisticalReport report = reportMap.get(element);
        BTWWeight weight = report.timeTaken();
        Integer numReporters = report.getNumOfReporters();
        return new Pair<>(weight, numReporters);
    }

    @Override
    public BTWWeight totalDrivingTime(VehicleDescriptor desc) {
        if (! this.timeForVehicle.containsKey(desc)) {
            throw new NoSuchDescriptorException(desc.getID().toString());
        }
        BTWWeight w = this.timeForVehicle.get(desc);
        if (w.seconds() <= 0) {
            throw new UnfinishedVehicleException(desc.getID().toString());
        }
        return this.timeForVehicle.get(desc);
    }

    @Override
    public BTWWeight averageTotalDrivingTime() {
        return BTWWeight.of(
                Double.valueOf(
                        this.timeForVehicle
                                .values()
                                .stream()
                                .mapToDouble(weight -> weight.seconds())
                                .filter(d -> d > 0)
                                .average()
                                .orElse(0.0))
                        .longValue());
    }

    @Override
    public BTWWeight averageWaitingTimeOnTrafficLight(TrafficLight tl) {
        if (! this.waitingTimeOnTrafficLight.containsKey(tl)) {
            throw new NoSuchTrafficLightException(tl.getName());
        }
        return this.waitingTimeOnTrafficLight.get(tl).getKey();
    }

    @Override
    public BTWWeight averageWaitingTimeOnAllTrafficLights() {
        return averageCounts(this.waitingTimeOnTrafficLight);
    }

    @Override
    public BTWWeight averageDrivingTimeOnRoad(Road rd) {
        if (! this.drivingTimeOnRoad.containsKey(rd)) {
            throw new NoSuchRoadException(rd.getName());
        }
        return this.drivingTimeOnRoad.get(rd).getKey();
    }

    @Override
    public BTWWeight averageDrivingTimeOnAllRoads() {
        return averageCounts(this.drivingTimeOnRoad);
    }

    @Override
    public Integer unaccomplishedDrives() {
        return this.timeForVehicle
                .entrySet()
                .stream()
                .filter(entry ->
                        entry.getValue().seconds() <= 0)
                .collect(Collectors.toSet()).size();
    }

    @Override
    public Evaluator addVehicleInfo(VehicleDescriptor desc, BTWWeight weight) {
        logger.debug("Adding vehicle info");
        if (! this.timeForVehicle.containsKey(desc)) {
            logger.error("Vehicle descriptor " + desc.getID().toString() + " not recognized");
            throw new NoSuchDescriptorException(desc.getID().toString());
        }
        if (this.timeForVehicle.get(desc).seconds() > 0) {
            logger.error("Vehicle descriptor " + desc.getID().toString() + " already seen");
            throw new DescriptorAlreadySeenException(desc.getID().toString());
        }
        this.timeForVehicle.put(desc, weight);
        logger.debug("Vehicle info added successfully");
        return this;
    }

    @Override
    public Evaluator addRoadReports(Map<Road, StatisticalReport> reportOfRoad) {
        logger.debug("Adding road reports");
        if (! this.drivingTimeOnRoad.keySet().containsAll(reportOfRoad.keySet())) {
            logger.error("Roads not recognized");
            throw new NoSuchRoadException("Some roads");
        }
        reportOfRoad
                .keySet()
                .forEach(rd ->
                        this.drivingTimeOnRoad.put(rd, pairFromReport(rd, reportOfRoad)));
        logger.debug("Road reports added successfully");
        return this;
    }

    @Override
    public Evaluator addTrafficLightReports(Map<TrafficLight, StatisticalReport> reportOfTrafficLight) {
        logger.debug("Adding traffic-light reports");
        if (! this.waitingTimeOnTrafficLight.keySet().containsAll(reportOfTrafficLight.keySet())) {
            logger.error("Traffic-lights not recognized");
            throw new NoSuchTrafficLightException("Some traffic-lights");
        }
        reportOfTrafficLight
                .keySet()
                .forEach(tl ->
                        this.waitingTimeOnTrafficLight.put(tl, pairFromReport(tl, reportOfTrafficLight)));
        logger.debug("Traffic-lights reports added successfully");
        return this;
    }
}
