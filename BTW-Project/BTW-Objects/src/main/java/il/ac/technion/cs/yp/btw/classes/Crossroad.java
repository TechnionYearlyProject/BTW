package il.ac.technion.cs.yp.btw.classes;

import java.util.Set;

/**
 * Crossroad Interface
 */
public interface Crossroad extends Point {
     /**
     * @return Set of all TrafficLights in this CrossroadImpl
     */
    Set<TrafficLight> getTrafficLights();

    /**
     * Adds the given TrafficLightImpl to this CrossroadImpl
     * @param tl - the TrafficLightImpl being added
     */
    void addTrafficLight(TrafficLight tl);

}
