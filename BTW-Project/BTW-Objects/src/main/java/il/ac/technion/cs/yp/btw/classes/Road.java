package il.ac.technion.cs.yp.btw.classes;

import java.sql.Time;

/**
 * Interface which represents road connection
 * between to points on a road map
 */
public interface Road {
    /**
     * returns true if the given street number is part
     * of this road, false otherwise
     * @param streetNumber - the street number we check
     * @return true if it is inside the road's range
     *         false o.w.
     */
    boolean isStreetNumberInRange(int streetNumber);

    /**
     * @return the length in meters of the road
     */
    int getRoadLength();

    /**
     * @return the unique name of the road
     */
    String getRoadName();

    /**
     * @return the street this Road is in
     */
    Street getStreet();
    
    /**
     * returns the right Weight for the given Time
     * @param time - Time we want to check the load
     *             on the road at
     * @return Weight of this Road according
     *         to the given Time
     */
    Weight getWeightByTime(Time time);

    /**
     * @return minimum possible Weight of Road
     */
    Weight getMinimumWeight();

    /**
     * @return the CrossroadImpl this Road starts in
     */
    Crossroad getSourceCrossroad();
    /**
     * @return the CrossroadImpl this Road ends
     */
    Crossroad getDestinationCrossroad();

    /**
     * @return the Range of this Road
     */
    Range getRoadRange();
}
