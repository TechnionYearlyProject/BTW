package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.Road;

import java.util.List;

/**
 * Navigator layer interface for the BTW project
 */
public interface BTWNavigator {

    //returns a list of roads that represents the path. the strings are in the correct format
    //allowing to fetch the road from the database
    List<Road> navigate(String source, String target);
}
