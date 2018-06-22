package il.ac.technion.cs.yp.btw.classes;

import java.sql.Time;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * Interface which represents road connection
 * between to points on a road map
 */
public interface Road extends TrafficObject{
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
     * @return the Street this Road is in
     */
    Street getStreet();

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
}
