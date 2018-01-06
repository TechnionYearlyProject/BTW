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


    //returns a list of roads that represents the route.
    //the source and target road are fetched from the database
    //TODO need to remove from interface
    List<Road> navigate(Road source, Road target) throws PathNotFoundException;

    //input: the route returned from navigate function, the ratio of the location in road.
    //example: Road is Shalom st 1-10, we want 6, ratio is 0.6
    //output: the time in seconds it takes to travel the road
    //TODO need to remove from interface, maybe transfer to the city simulator
    //TODO for future calculation of expected route to show
    long calculateRouteTime(List<Road> route, double ratioSourceRoad, double ratioTargetRoad);
}
