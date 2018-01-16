package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.BTWWeight;
import il.ac.technion.cs.yp.btw.classes.Road;

/**
 * Interface for the real Road operating unit - not just a data passing class
 */
public interface CityRoad extends Road, StatisticsProviding {
    /**
     * @return current Weight on this Road
     */
    BTWWeight getCurrentWeight();

    /**
     * @param vehicle - vehicle to enter the road
     * @return self
     */
    CityRoad addVehicle(Vehicle vehicle);

    /**
     * @param vehicle - vehicle to leave the road
     * @return self
     */
    CityRoad removeVehicle(Vehicle vehicle);

    /**
     * progress everything by a clock progressOnRoad
     * @return self
     */
    CityRoad tick();
}
