package il.ac.technion.cs.yp.btw.evaluation;

import il.ac.technion.cs.yp.btw.citysimulation.Vehicle;
import il.ac.technion.cs.yp.btw.citysimulation.VehicleDescriptor;
import il.ac.technion.cs.yp.btw.citysimulation.VehicleEntry;
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
    private Map<String, Pair<BTWWeight, Integer> > waitingTimeOnTrafficLight;
//    private Map<TrafficLight, Pair<BTWWeight, Integer> > waitingTimeOnTrafficLight;
    private Map<String, Pair<BTWWeight, Integer> > drivingTimeOnRoad;
//    private Map<Road, Pair<BTWWeight, Integer> > drivingTimeOnRoad;

    public EvaluatorImpl(List<VehicleEntry> vehicles, BTWDataBase db){
        this(db, vehicles.stream().map(VehicleEntry::getDescriptor).map(Optional::get).collect(Collectors.toList()));
    }

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
                        .toMap(TrafficObject::getName,
                                tl -> new Pair<>(tl.getMinimumWeight(), 0)));
        this.drivingTimeOnRoad = db
                .getAllRoads()
                .stream()
                .collect(Collectors
                        .toMap(TrafficObject::getName,
                                rd -> new Pair<>(rd.getMinimumWeight(), 0)));
    }

    private static BTWWeight averageCounts(Map<String, Pair<BTWWeight, Integer> > weightMap) {
        Pair<Long, Integer> total = weightMap
                .values()
                .stream()
                .map(p -> new Pair<>(p.getKey().seconds() * p.getValue(), p.getValue()))
                .reduce(new Pair<>(0L, 0),
                        (p1, p2) -> new Pair<>(p1.getKey() + p2.getKey(), p1.getValue() + p2.getValue()));
        if (total.getValue() != 0) {
            return BTWWeight.of(total.getKey() / total.getValue());
        } else {
            return BTWWeight.of(0);
        }
    }

//    private static <T extends TrafficObject> BTWWeight averageCounts(Map<T, Pair<BTWWeight, Integer> > weightMap) {
//        Pair<Long, Integer> total = weightMap
//                .values()
//                .stream()
//                .map(p -> new Pair<>(p.getKey().seconds() * p.getValue(), p.getValue()))
//                .reduce(new Pair<>(0L, 0),
//                        (p1, p2) -> new Pair<>(p1.getKey() + p2.getKey(), p1.getValue() + p2.getValue()));
//        if (total.getValue() != 0) {
//            return BTWWeight.of(total.getKey() / total.getValue());
//        } else {
//            return BTWWeight.of(0);
//        }
//    }

    private static <T extends TrafficObject> Pair<BTWWeight, Integer> pairFromReport(T element, Map<T, StatisticalReport> reportMap) {
        StatisticalReport report = reportMap.get(element);
        BTWWeight weight = report.timeTaken();
        Integer numReporters = report.getNumOfReporters();
        return new Pair<>(weight, numReporters);
    }

//    private static Pair<BTWWeight, Integer> pairFromReport(String elementName, Map<String, StatisticalReport> reportMap) {
//        StatisticalReport report = reportMap.get(elementName);
//        BTWWeight weight = report.timeTaken();
//        Integer numReporters = report.getNumOfReporters();
//        return new Pair<>(weight, numReporters);
//    }

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
        if (! this.waitingTimeOnTrafficLight.containsKey(tl.getName())) {
            throw new NoSuchTrafficLightException(tl.getName());
        }
        return this.waitingTimeOnTrafficLight.get(tl.getName()).getKey();
    }

    @Override
    public BTWWeight averageWaitingTimeOnAllTrafficLights() {
        return averageCounts(this.waitingTimeOnTrafficLight);
    }

    @Override
    public BTWWeight averageDrivingTimeOnRoad(Road rd) {
        if (! this.drivingTimeOnRoad.containsKey(rd.getName())) {
            throw new NoSuchRoadException(rd.getName());
        }
        return this.drivingTimeOnRoad.get(rd.getName()).getKey();
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
        Set<String> reportedRoadNames = reportOfRoad
                .keySet()
                .stream()
                .map(Road::getName)
                .collect(Collectors.toSet());
        if (! this.drivingTimeOnRoad.keySet().containsAll(reportedRoadNames)) {
            logger.error("Roads not recognized");
            throw new NoSuchRoadException("Some roads");
        }
        reportOfRoad
                .keySet()
                .forEach(rd ->
                        this.drivingTimeOnRoad.put(rd.getName(), pairFromReport(rd, reportOfRoad)));
        logger.debug("Road reports added successfully");
        return this;
    }

    @Override
    public Evaluator addTrafficLightReports(Map<TrafficLight, StatisticalReport> reportOfTrafficLight) {
        logger.debug("Adding traffic-light reports");
        Set<String> reportedTrafficLightNames = reportOfTrafficLight
                .keySet()
                .stream()
                .map(TrafficLight::getName)
                .collect(Collectors.toSet());
        if (! this.waitingTimeOnTrafficLight.keySet().containsAll(reportedTrafficLightNames)) {
            logger.error("Traffic-lights not recognized");
            throw new NoSuchTrafficLightException("Some traffic-lights");
        }
        reportOfTrafficLight
                .keySet()
                .forEach(tl ->
                        this.waitingTimeOnTrafficLight.put(tl.getName(), pairFromReport(tl, reportOfTrafficLight)));
        logger.debug("Traffic-lights reports added successfully");
        return this;
    }
}
