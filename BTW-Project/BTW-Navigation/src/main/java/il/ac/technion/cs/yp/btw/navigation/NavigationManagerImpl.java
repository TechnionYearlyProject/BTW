package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.VehicleDescriptor;


/**
 * Implementation of the navigation manager
 */
public class NavigationManagerImpl implements NavigationManager {
    private final BTWDataBase database;

    public NavigationManagerImpl(BTWDataBase db) {
        this.database = db.updateHeuristics();
    }


    @Override
    public Navigator getNavigator(VehicleDescriptor vehicleDescriptor, Road source, double ratioSource, Road destination, double ratioDestination) {
        //TODO change to use the given source and destination
        return new NavigatorImpl(database);
    }
}
