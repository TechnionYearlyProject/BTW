package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.BTWWeight;
import il.ac.technion.cs.yp.btw.classes.Crossroad;
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

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the CrossroadImpl this Road starts in
     */
    @Override
    CityCrossroad getSourceCrossroad();

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the CrossroadImpl this Road ends
     */
    @Override
    CityCrossroad getDestinationCrossroad();

    /**
    * @Author Sharon Hadar
    * @Date 2/6/2018
    * @return the current overload on the road
    */
    Double getOverload();

    /**
    * @Author Sharon Hadar
    * @Date 2/6/2018
    * @return the current speed on the road
    */
    double getSpeed();
}
