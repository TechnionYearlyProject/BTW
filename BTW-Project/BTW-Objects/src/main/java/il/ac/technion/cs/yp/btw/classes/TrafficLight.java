package il.ac.technion.cs.yp.btw.classes;

import java.sql.Time;

/**
 * TrafficLight Interface
 */
public interface TrafficLight extends Point {
    /**
     * @return the TrafficLight's unique name
     * in the format:
     * from:@getSourceRoad() to:@getDestinationRoad()
     */
    String getName();

    /**
     * @return the Road you got from to this
     *         TrafficLightImpl
     */
    Road getSourceRoad();

    /**
     * @return the destination Road you
     *         go to from this TrafficLight
     */
    Road getDestinationRoad();
    /**
     * returns the right Weight for the given Time
     * @param time - Time we want to check the load
     *             on the traffic light at
     * @return Weight of this TrafficLight according
     *         to the given Time
     */
    BTWWeight getWeightByTime(BTWTime time);

    /**
     * @return minimum possible Weight of TrafficLight
     */
    BTWWeight getMinimumWeight();
}
