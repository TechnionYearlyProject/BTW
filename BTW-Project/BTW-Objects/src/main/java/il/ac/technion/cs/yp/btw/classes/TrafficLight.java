package il.ac.technion.cs.yp.btw.classes;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * TrafficLight Interface
 */
public interface TrafficLight extends Point, TrafficObject {
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
}
