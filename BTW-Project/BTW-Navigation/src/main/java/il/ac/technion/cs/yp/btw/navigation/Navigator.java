package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.Road;

import java.util.List;

/**
 * Navigator layer interface for the BTW project
 */
public interface Navigator {

    //returns a list of roads that represents the route.
    //the source and target road are fetched from the database
    List<Road> navigate(Road source, Road target) throws PathNotFoundException;

    //input: the route returned from navigate function, the ratio of the location in road.
    //example: Road is Shalom st 1-10, we want 6, ratio is 0.6
    //output: the time in seconds it takes to travel the road
    long calculateRouteTime(List<Road> route, double ratioSourceRoad, double ratioTargetRoad);
}
