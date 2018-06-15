package il.ac.technion.cs.yp.btw.evaluation;

import il.ac.technion.cs.yp.btw.citysimulation.VehicleDescriptor;
import il.ac.technion.cs.yp.btw.classes.BTWWeight;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.statistics.StatisticalReport;

import java.util.Map;

/**
 * @author Guy Rephaeli
 * @date 05-Jun-18.
 */
public interface Evaluator {
    /**
     * @author Guy Rephaeli
     * @date 05-Jun-18.
     *
     * @param desc The descriptor of the subject vehicle
     * @return The time it took for vehicle with descriptor "desc" to finish its route
     */
    BTWWeight totalDrivingTime(VehicleDescriptor desc);

    /**
     * @author Guy Rephaeli
     * @date 05-Jun-18.
     *
     * @return The average time it takes for a vehicle to finish its route
     */
    BTWWeight averageTotalDrivingTime();

    /**
     * @author Guy Rephaeli
     * @date 05-Jun-18.
     *
     * @param tl The subject traffic-light
     * @return The average waiting time on traffic-light "tl"
     */
    BTWWeight averageWaitingTimeOnTrafficLight(TrafficLight tl);

    /**
     * @author Guy Rephaeli
     * @date 05-Jun-18.
     *
     * @return The average waiting time on a traffic-light
     */
    BTWWeight averageWaitingTimeOnAllTrafficLights();

    /**
     * @author Guy Rephaeli
     * @date 05-Jun-18.
     *
     * @param rd The subject road
     * @return The average time it took to drive on road "rd"
     */
    BTWWeight averageDrivingTimeOnRoad(Road rd);

    /**
     * @author Guy Rephaeli
     * @date 05-Jun-18.
     *
     * @return The average driving time on a road
     */
    BTWWeight averageDrivingTimeOnAllRoads();

    /**
     * @author Guy Rephaeli
     * @date 08-Jun-18.
     *
     * @return the number of cars that didn't finish their routes
     */
    Integer unaccomplishedDrives();

    /**
     * @author Guy Rephaeli
     * @date 08-Jun-18.
     *
     * When a vehicle with descriptor "desc" finishes driving, it tells the evaluator hoe much time it took
     *
     * @param desc The descriptor of the reporting vehicle
     * @param weight The total time it took the vehicle
     * @return self
     */
    Evaluator addVehicleInfo(VehicleDescriptor desc, BTWWeight weight);

//    /**
//     * @author Guy Rephaeli
//     * @date 08-Jun-18.
//     *
//     * Getting report on how much time it took to pass the road "rd"
//     *
//     * @param rd The road being reported
//     * @param report The report of how much time it took to drive on the road
//     * @return self
//     */
//    Evaluator addRoadReport(Road rd, StatisticalReport report);
//
//    /**
//     * @author Guy Rephaeli
//     * @date 08-Jun-18.
//     *
//     * Getting report on how much time it took to wait on traffic-light "tl"
//     *
//     * @param tl The traffic-light being reported
//     * @param report The report of how much time it took to wait on the traffic-light
//     * @return self
//     */
//    Evaluator addTrafficLightReport(TrafficLight tl, StatisticalReport report);

    /**
     * @author Guy Rephaeli
     * @date 09-Jun-18.
     *
     * Getting reports on how much time it took to drive on each road
     *
     * @param reportOfRoad The reports of how much time it took to drive on each road
     * @return self
     */
    Evaluator addRoadReports(Map<Road, StatisticalReport> reportOfRoad);

    /**
     * @author Guy Rephaeli
     * @date 09-Jun-18.
     *
     * Getting reports on how much time it took to wait on each traffic-light
     *
     * @param reportOfTrafficLight The reports of how much time it took to wait on each traffic-light
     * @return self
     */
    Evaluator addTrafficLightReports(Map<TrafficLight, StatisticalReport> reportOfTrafficLight);
}
