package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.VehicleDescriptor;

/**
 * Navigation Manager interface
 */
public interface NavigationManager {

    Navigator getNavigator(VehicleDescriptor vehicleDescriptor,
                           Road source,double ratioSource,
                           Road destination,double ratioDestination);
}
