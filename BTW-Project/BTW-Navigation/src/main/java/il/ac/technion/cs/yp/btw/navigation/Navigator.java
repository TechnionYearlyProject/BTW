package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.Road;

import java.util.List;

/**
 * Navigator layer interface for the BTW project
 */
public interface Navigator {

    /**
     * @return the next Road for the Vehicle currently
     *         using this Navigator
     */
    Road getNextRoad();

    Road getDestination();

    boolean hasArrived();
}
