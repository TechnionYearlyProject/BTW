package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.citysimulation.VehicleDescriptor;

import il.ac.technion.cs.yp.btw.classes.BTWTime;
import il.ac.technion.cs.yp.btw.classes.Road;

/**
 * @author Guy Rephaeli
 *
 * Navigation Manager interface
 */
public interface NavigationManager {

    /**
     * @author Guy Rephaeli
     *
     * A method to create navigator objects
     *
     * @param vehicleDescriptor - the descriptor of the driving vehicle
     * @param source - the source road
     * @param sourceRoadRatio - the place on the source road from which to start
     * @param destination - the destination road
     * @param destinationRoadRatio - the place on the destination road to stop at
     * @param time - the time to start driving
     * @return - Navigator object to lead the vehicle during driving
     * @throws PathNotFoundException - if a path between the source and the destination is not found
     */
    Navigator getNavigator(VehicleDescriptor vehicleDescriptor,
                           Road source, double sourceRoadRatio,
                           Road destination, double destinationRoadRatio,
                           BTWTime time) throws PathNotFoundException;
}
