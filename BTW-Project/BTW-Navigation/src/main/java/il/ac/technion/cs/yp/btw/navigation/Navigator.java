package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.Road;

import java.util.List;

/**
 * @author Guy Rephaeli
 *
 * Navigator layer interface for the BTW project
 */
public interface Navigator {

    /**
     * @author Guy Rephaeli
     *
     * @return the next Road for the Vehicle currently
     *         using this Navigator
     */
    Road getNextRoad();

    /**
     * @author Guy Rephaeli
     *
     * @return The destination road
     */
    Road getDestination();

    /**
     * @author Guy Rephaeli
     *
     * @return Indication if the naigator has finished navigating
     */
    boolean hasArrived();

    /**
     * @author Guy Rephaeli
     * @date 19-Jun-18.
     *
     * Return the next 'roadsNumber' roads in the route, NOT INCLUDING (!!!) the current road
     *
     * @param roadsNumber The number of the requested next roads
     * @return The 'roadsNumber' next roads in the route
     */
    List<Road> showNextRoads(Integer roadsNumber);
}
