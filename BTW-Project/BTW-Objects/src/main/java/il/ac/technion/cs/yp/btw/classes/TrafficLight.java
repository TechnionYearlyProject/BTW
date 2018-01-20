package il.ac.technion.cs.yp.btw.classes;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * TrafficLight Interface
 */
public interface TrafficLight extends Point {
    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the TrafficLight's unique name
     * in the format:
     * from:@getSourceRoad() to:@getDestinationRoad()
     */
    String getName();

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the Road you got from to this
     *         TrafficLightImpl
     */
    Road getSourceRoad();

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the destination Road you
     *         go to from this TrafficLight
     */
    Road getDestinationRoad();
    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * returns the right Weight for the given Time
     * @param time - Time we want to check the load
     *             on the traffic light at
     * @return Weight of this TrafficLight according
     *         to the given Time
     */
    BTWWeight getWeightByTime(BTWTime time);

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return minimum possible Weight of TrafficLight
     */
    BTWWeight getMinimumWeight();
}
