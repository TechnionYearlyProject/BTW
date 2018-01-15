package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.Crossroad;

/**
 * Interface for the real Crossroad operating unit - not just a data passing class
 */
public interface CityCrossroad extends Crossroad, StatisticsProviding {
    /**
     * add a Vehicle to this Crossroad
     * @param vehicle - the Vehicle to be added
     * @return self
     */
    CityCrossroad addVehicle(Vehicle vehicle);
    /**
     * progress everything by a clock all TrafficLights
     * in this Crossroad, and manage the opening and closing
     * of TrafficLights during the tick
     * @return self
     */
    CityCrossroad tick();
}
