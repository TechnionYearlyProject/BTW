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

    /**
     * @author Guy Rephaeli
     * @date 09-Jun-18.
     *
     * @param descriptor - the descriptor of the vehicle
     * @return the difference between the driving time of the vehicle in the first simulation and the second simulation
     */
    public Long compareDrivingTimeOfVehicle(VehicleDescriptor descriptor) {
        return compareWeight(e -> e.totalDrivingTime(descriptor));
    }

    /**
     * @author Guy Rephaeli
     * @date 23-Jun-18.
     *
     * @param descriptor - the descriptor of the vehicle
     * @return the percentage difference between the driving time of the vehicle in the first simulation and the second simulation
     */
    public Double compareDrivingTimeOfVehiclePercent(VehicleDescriptor descriptor) {
        return this.compareWeightPercent(e -> e.totalDrivingTime(descriptor).seconds());
    }

    /**
     * @author Guy Rephaeli
     * @date 09-Jun-18.
     *
     * @return the difference between the average driving time of the vehicles in the first simulation and the second simulation
     */
    public Long compareAverageDrivingTimeOfVehicles() {
        return compareWeight(Evaluator::averageTotalDrivingTime);
    }

    /**
     * @author Guy Rephaeli
     * @date 09-Jun-18.
     *
     * @return the percentage difference between the average driving time of the vehicles in the first simulation and the second simulation
     */
    public Double compareAverageDrivingTimeOfVehiclesPercent() {
        return this.compareWeightPercent(e -> e.averageTotalDrivingTime().seconds());
    }

    /**
     * @author Guy Rephaeli
     * @date 09-Jun-18.
     *
     * @param rd - the road
     * @return the difference between the driving time on the road in the first simulation and the second simulation
     */
    public Long compareDrivingTimeOnRoad(Road rd) {
        return compareWeight(e -> e.averageDrivingTimeOnRoad(rd));
    }

    /**
     * @author Guy Rephaeli
     * @date 23-Jun-18.
     *
     * @param rd - the road
     * @return the percentage difference between the driving time on the road in the first simulation and the second simulation
     */
    public Double compareDrivingTimeOnRoadPercent(Road rd) {
        return this.compareWeightPercent(e -> e.averageDrivingTimeOnRoad(rd).seconds());
    }

    /**
     * @author Guy Rephaeli
     * @date 09-Jun-18.
     *
     * @return the difference between the average driving time on the roads in the first simulation and the second simulation
     */
    public Long compareAverageDrivingTimeOnRoads() {
        return compareWeight(Evaluator::averageDrivingTimeOnAllRoads);
    }

    /**
     * @author Guy Rephaeli
     * @date 23-Jun-18.
     *
     * @return the percentage difference between the average driving time on the roads in the first simulation and the second simulation
     */
    public Double compareAverageDrivingTimeOnRoadsPercent() {
        return this.compareWeightPercent(e -> e.averageDrivingTimeOnAllRoads().seconds());
    }

    /**
     * @author Guy Rephaeli
     * @date 09-Jun-18.
     *
     * @param tl - the traffic-light
     * @return the difference between the waiting time on the traffic-light in the first simulation and the second simulation
     */
    public Long compareWaitingTimeOnTrafficLight(TrafficLight tl) {
        return compareWeight(e -> e.averageWaitingTimeOnTrafficLight(tl));
    }

    /**
     * @author Guy Rephaeli
     * @date 23-Jun-18.
     *
     * @param tl - the traffic-light
     * @return the percentage difference between the waiting time on the traffic-light in the first simulation and the second simulation
     */
    public Double compareWaitingTimeOnTrafficLightPercent(TrafficLight tl) {
        return this.compareWeightPercent(e -> e.averageWaitingTimeOnTrafficLight(tl).seconds());
    }

    /**
     * @author Guy Rephaeli
     * @date 09-Jun-18.
     *
     * @return the difference between the average waiting time on the traffic-lights in the first simulation and the second simulation
     */
    public Long compareAverageWaitingTimeOnTrafficLights() {
        return compareWeight(Evaluator::averageWaitingTimeOnAllTrafficLights);
    }

    /**
     * @author Guy Rephaeli
     * @date 23-Jun-18.
     *
     * @return the percentage difference between the average waiting time on the traffic-lights in the first simulation and the second simulation
     */
    public Double compareAverageWaitingTimeOnTrafficLightsPercent() {
        return this.compareWeightPercent(e -> e.averageWaitingTimeOnAllTrafficLights().seconds());
    }

    /**
     * @author Guy Rephaeli
     * @date 09-Jun-18.
     *
     * @return the difference between the number of vehicles that has not reached their destination in the first simulation and the second simulation
     */
    public Integer compareUnaccomplishedDrives() {
        return this.e1.unaccomplishedDrives() - this.e2.unaccomplishedDrives();
    }

    /**
     * @author Guy Rephaeli
     * @date 23-Jun-18.
     *
     * @return the percentage difference between the number of vehicles that has not reached their destination in the first simulation and the second simulation
     */
    public Double compareUnaccomplishedDrivesPercent() {
        return this.compareWeightPercent(Evaluator::unaccomplishedDrives);
    }
}
