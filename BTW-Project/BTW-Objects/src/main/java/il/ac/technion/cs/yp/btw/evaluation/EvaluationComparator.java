package il.ac.technion.cs.yp.btw.evaluation;

import il.ac.technion.cs.yp.btw.citysimulation.VehicleDescriptor;
import il.ac.technion.cs.yp.btw.classes.BTWWeight;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import org.apache.log4j.Logger;

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

    public Long compareDrivingTimeOfVehicle(VehicleDescriptor descriptor) {
        return compareWeight(e -> e.totalDrivingTime(descriptor));
    }

    public Long compareAverageDrivingTimeOfVehicles() {
        return compareWeight(Evaluator::averageTotalDrivingTime);
    }

    public Long compareDrivingTimeOnRoad(Road rd) {
        return compareWeight(e -> e.averageDrivingTimeOnRoad(rd));
    }

    public Long compareAverageDrivingTimeOnRoads() {
        return compareWeight(Evaluator::averageDrivingTimeOnAllRoads);
    }

    public Long compareWaintingTimeOnTarfficLight(TrafficLight tl) {
        return compareWeight(e -> e.averageWaitingTimeOnTrafficLight(tl));
    }

    public Long compareAverageWaintingTimeOnTarfficLights() {
        return compareWeight(Evaluator::averageWaitingTimeOnAllTrafficLights);
    }

    public Integer compareUnaccomplishedDrives() {
        return this.e1.unaccomplishedDrives() - this.e2.unaccomplishedDrives();
    }
}
