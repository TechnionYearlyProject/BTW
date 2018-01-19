package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.Crossroad;

import java.util.Set;

/**
 * Interface for the real Crossroad operating unit - not just a data passing class
 */
public interface CityCrossroad extends Crossroad, StatisticsProviding<CrossroadData> {
    /**
     * add a Vehicle to this Crossroad
     * @param vehicle - the Vehicle to be added
     * @return self
     */
    CityCrossroad addVehicle(Vehicle vehicle);

    /**
     * @return real instances of all traffic-lights in the crossroad
     */
    Set<CityTrafficLight> getRealTrafficLights();

    /**
     * progress everything by a clock all TrafficLights
     * in this Crossroad, and manage the opening and closing
     * of TrafficLights during the tick
     * @return self
     */
    CityCrossroad tick();
}
