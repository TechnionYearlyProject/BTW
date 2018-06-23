package il.ac.technion.cs.yp.btw.evaluation;

import il.ac.technion.cs.yp.btw.citysimulation.VehicleDescriptor;
import il.ac.technion.cs.yp.btw.classes.BTWWeight;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import org.apache.log4j.Logger;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Guy Rephaeli
 * @date 09-Jun-18.
 */
public class EvaluationComparator {
    private final Evaluator e1;
    private final Evaluator e2;
    public EvaluationComparator(Evaluator e1, Evaluator e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    private Long compareWeight(Function<Evaluator, BTWWeight> f) {
        return f.apply(this.e1).seconds() - f.apply(this.e2).seconds();
    }

    private <N extends Number> Double compareWeightPercent(Function<Evaluator, N> f) {
        Double referenceValue = f.apply(this.e1).doubleValue();
        if (referenceValue <= 0) {
            return 0.0;
        }
        return 100 * (referenceValue - f.apply(this.e2).doubleValue()) / referenceValue;
    }

    public Long compareDrivingTimeOfVehicle(VehicleDescriptor descriptor) {
        return compareWeight(e -> e.totalDrivingTime(descriptor));
    }

    public Double compareDrivingTimeOfVehiclePercent(VehicleDescriptor descriptor) {
        return this.compareWeightPercent(e -> e.totalDrivingTime(descriptor).seconds());
    }

    public Long compareAverageDrivingTimeOfVehicles() {
        return compareWeight(Evaluator::averageTotalDrivingTime);
    }

    public Double compareAverageDrivingTimeOfVehiclesPercent() {
        return this.compareWeightPercent(e -> e.averageTotalDrivingTime().seconds());
    }

    public Long compareDrivingTimeOnRoad(Road rd) {
        return compareWeight(e -> e.averageDrivingTimeOnRoad(rd));
    }

    public Double compareDrivingTimeOnRoadPercent(Road rd) {
        return this.compareWeightPercent(e -> e.averageDrivingTimeOnRoad(rd).seconds());
    }

    public Long compareAverageDrivingTimeOnRoads() {
        return compareWeight(Evaluator::averageDrivingTimeOnAllRoads);
    }

    public Double compareAverageDrivingTimeOnRoadsPercent() {
        return this.compareWeightPercent(e -> e.averageDrivingTimeOnAllRoads().seconds());
    }

    public Long compareWaitingTimeOnTrafficLight(TrafficLight tl) {
        return compareWeight(e -> e.averageWaitingTimeOnTrafficLight(tl));
    }

    public Double compareWaitingTimeOnTrafficLightPercent(TrafficLight tl) {
        return this.compareWeightPercent(e -> e.averageWaitingTimeOnTrafficLight(tl).seconds());
    }

    public Long compareAverageWaitingTimeOnTrafficLights() {
        return compareWeight(Evaluator::averageWaitingTimeOnAllTrafficLights);
    }

    public Double compareAverageWaitingTimeOnTrafficLightsPercent() {
        return this.compareWeightPercent(e -> e.averageWaitingTimeOnAllTrafficLights().seconds());
    }

    public Integer compareUnaccomplishedDrives() {
        return this.e1.unaccomplishedDrives() - this.e2.unaccomplishedDrives();
    }

    public Double compareUnaccomplishedDrivesPercent() {
        return this.compareWeightPercent(Evaluator::unaccomplishedDrives);
    }
}
