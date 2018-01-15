package il.ac.technion.cs.yp.btw.citysimulation;

import java.util.List;

/**
 * Interface to describe City Simulator with live
 * Vehicles and TrafficLights
 */
public interface CitySimulator {
    /**
     * @param vehicleDescriptor - technical properties
     *                          of the Vehicle
     * @param source - source address
     * @param destination - destination address
     * @return a Vehicle with technical properties
     *         as described in the VehicleDescriptor, which
     *         will driven from the given source address
     *         to the given destination address
     */
    Vehicle addVehicle(VehicleDescriptor vehicleDescriptor, String source, String destination);

    /**
     * @param vehicleDescriptors - List of technical properties
     *                             of the Vehicles
     * @param source - source address
     * @param destination - destination address
     * @param interval - time interval between Vehicle generation,
     *                   in seconds
     * @return a Vehicle List with technical properties
     *         as described in the VehicleDescriptors, which
     *         will drive from the given source address
     *         to the given destination address.
     *         new Vehicles will be generated in the
     *         given interval
     */
    List<Vehicle> addSeveralVehicles(List<VehicleDescriptor> vehicleDescriptors
            , String source, String destination, int interval);

    /**
     * @return CityMap to be saved for graphic uses
     */
    CityMap saveMap();

    /**
     * progress everything by a clock tick,
     * a clock tick is considered to be
     * an advancement of 1 second
     * @return self
     */
    CitySimulator tick();

    /**
     * progress everything by a numberOfTicks
     * clock ticks,
     * a clock tick is considered to be
     * an advancement of 1 second
     * @param numberOfTicks - number of ticks the
     *                        simulation is advancing
     * @return self
     */
    default CitySimulator tick(int numberOfTicks){
        for (int interval = 0; interval < numberOfTicks ; interval++) {
            this.tick();
        }
        return this;
    }
}
