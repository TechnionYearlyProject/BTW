package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.citysimulation.VehicleDescriptor;

import il.ac.technion.cs.yp.btw.classes.Road;

/**
 * Navigation Manager interface
 */
public interface NavigationManager {

    Navigator getNavigator(VehicleDescriptor vehicleDescriptor,
                           Road source,double sourceRoadRatio,
                           Road destination,double destinationRoadRatio) throws PathNotFoundException;
}
