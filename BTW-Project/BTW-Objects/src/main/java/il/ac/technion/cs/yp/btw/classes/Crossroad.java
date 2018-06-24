package il.ac.technion.cs.yp.btw.classes;

import java.util.Set;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * Crossroad Interface
 */
public interface Crossroad extends Point {
    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the name of the crossroad
     */
    String getName();

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of all TrafficLights in this CrossroadImpl
     */
    Set<TrafficLight> getTrafficLights();

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of all TrafficLights from a specific road
     * @param road - the Road from which the TrafficLights are going
     */
    Set<TrafficLight> getTrafficLightsFromRoad(Road road);

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * Adds the given TrafficLightImpl to this CrossroadImpl
     * @param tl - the TrafficLightImpl being added
     */
    Crossroad addTrafficLight(TrafficLight tl);

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
