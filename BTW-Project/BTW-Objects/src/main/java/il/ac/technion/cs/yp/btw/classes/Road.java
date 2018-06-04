package il.ac.technion.cs.yp.btw.classes;

import java.sql.Time;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * Interface which represents road connection
 * between to points on a road map
 */
public interface Road {
    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * returns true if the given street number is part
     * of this road, false otherwise
     * @param streetNumber - the street number we check
     * @return true if it is inside the road's range
     *         false o.w.
     */
    boolean isStreetNumberInRange(int streetNumber);

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the length in meters of the road
     */
    int getRoadLength();

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the unique name of the road
     */
    String getRoadName();

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the Street this Road is in
     */
    Street getStreet();

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * returns the right Weight for the given Time
     * @param time - Time we want to check the load
     *             on the road at
     * @return Weight of this Road according
     *         to the given Time
     */
    BTWWeight getWeightByTime(BTWTime time);

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return minimum possible Weight of Road
     */
    BTWWeight getMinimumWeight();

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return minimum possible Weight of Road
     */
    BTWWeight getHeuristicDist(Road road);

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the CrossroadImpl this Road starts in
     */
    Crossroad getSourceCrossroad();
    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the CrossroadImpl this Road ends
     */
    Crossroad getDestinationCrossroad();

    /*
    * @Author Sharon Hadar
    * @Date 2/6/2018
    * @return the current speed on the road
    * */
    double getSpeed();

    /*
    * @Author Sharon Hadar
    * @Date 2/6/2018
    * @return the current overload on the road
    * */
    Double getOverload();
}
