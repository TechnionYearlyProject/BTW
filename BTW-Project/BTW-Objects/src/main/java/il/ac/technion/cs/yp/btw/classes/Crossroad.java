package il.ac.technion.cs.yp.btw.classes;

import java.util.Set;

/**
 * Crossroad Interface
 */
public interface Crossroad extends Point {
    /**
     * @return the name of the crossroad
     */
    String getName();

    /**
     * @return Set of all TrafficLights in this CrossroadImpl
     */
    Set<TrafficLight> getTrafficLights();

    /**
     * @return Set of all TrafficLights from a specific road
     * @param road - the Road from which the TrafficLights are going
     */
    Set<TrafficLight> getTrafficLightsFromRoad(Road road);

    /**
     * Adds the given TrafficLightImpl to this CrossroadImpl
     * @param tl - the TrafficLightImpl being added
     */
    Crossroad addTrafficLight(TrafficLight tl);
}
