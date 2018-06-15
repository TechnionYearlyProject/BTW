package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.BTWWeight;
import il.ac.technion.cs.yp.btw.classes.Road;

import java.util.Set;

/**
 * Interface for the real Road operating unit - not just a data passing class
 */
public interface CityRoad extends Road, StatisticsProviding<RoadData> {
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

    /**
     * @author Adam Elgressy
     * @date 30-5-2018
     * returns all the vehicles currently driving
     * on this road, not including those which wait
     * on the traffic lights
     * @return - Set of above mentioned vehicles
     */
    Set<Vehicle> getVehiclesOnRoad();
}
