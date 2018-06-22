package il.ac.technion.cs.yp.btw.evaluation;

import il.ac.technion.cs.yp.btw.citysimulation.VehicleDescriptor;
import il.ac.technion.cs.yp.btw.classes.BTWWeight;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.classes.StatisticalReport;

import java.util.Map;

/**
 * @author Guy Rephaeli
 * @date 09-Jun-18.
 */
public class DumbEvaluator implements Evaluator {
    @Override
    public BTWWeight totalDrivingTime(VehicleDescriptor desc) {
        return null;
    }

    @Override
    public BTWWeight averageTotalDrivingTime() {
        return null;
    }

    @Override
    public BTWWeight averageWaitingTimeOnTrafficLight(TrafficLight tl) {
        return null;
    }

    @Override
    public BTWWeight averageWaitingTimeOnAllTrafficLights() {
        return null;
    }

    @Override
    public BTWWeight averageDrivingTimeOnRoad(Road rd) {
        return null;
    }

    @Override
    public BTWWeight averageDrivingTimeOnAllRoads() {
        return null;
    }

    @Override
    public Integer unaccomplishedDrives() {
        return null;
    }

    @Override
    public Evaluator addVehicleInfo(VehicleDescriptor desc, BTWWeight weight) {
        return this;
    }

    @Override
    public Evaluator addRoadReports(Map<Road, StatisticalReport> reportOfRoad) {
        return this;
    }

    @Override
    public Evaluator addTrafficLightReports(Map<TrafficLight, StatisticalReport> reportOfTrafficLight) {
        return this;
    }
}
