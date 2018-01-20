package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.navigation.PathNotFoundException;

import java.util.List;

/**
 * @author Adam Elgressy and Guy Rephaeli
 * @Date 20-1-2018
 * Interface to describe City Simulator with live
 * Vehicles and TrafficLights
 */
public interface CitySimulator {
    /**
     * @param  road - the road to find in the real city
     * @return - the actual road corresponding to the input road
     * */
    CityRoad getRealRoad(Road road);

    /**
     * @param  trafficLight - the traffic-light to find in the real city
     * @return - the actual traffic-light corresponding to the input traffic-light
     * */
    CityTrafficLight getRealTrafficLight(TrafficLight trafficLight);

    /**
     * @param  crossroad - the crossroad to find in the real city
     * @return - the actual crossroad corresponding to the input crossroad
     * */
    CityCrossroad getRealCrossroad(Crossroad crossroad);

    /**
     * @author Adam Elgressy and Guy Rephaeli
     * @Date 20-1-2018
     * @param vehicleDescriptor - technical properties
     *                          of the Vehicle
     * @param source - source road
     * @param sourceRoadRatio - place on source road to start from
     * @param destination - destination road
     * @param destinationRoadRatio - place on destination road to finish
     * @return a Vehicle with technical properties
     *         as described in the VehicleDescriptor, which
     *         will driven from the given source address
     *         to the given destination address
     */
    Vehicle addVehicle(VehicleDescriptor vehicleDescriptor, Road source, double sourceRoadRatio,
                       Road destination, double destinationRoadRatio) throws PathNotFoundException;

    /**
     * @author Adam Elgressy and Guy Rephaeli
     * @Date 20-1-2018
     * @param vehicleDescriptors - List of technical properties
     *                             of the Vehicles
     * @param source - source road
     * @param sourceRoadRatio - place on source road to start from
     * @param destination - destination road
     * @param destinationRoadRatio - place on destination road to finish
     * @param interval - time interval between Vehicle generation,
     *                   in seconds
     * @return a Vehicle List with technical properties
     *         as described in the VehicleDescriptors, which
     *         will drive from the given source address
     *         to the given destination address.
     *         new Vehicles will be generated in the
     *         given interval
     */
    List<Vehicle> addSeveralVehicles(List<VehicleDescriptor> vehicleDescriptors, Road source, double sourceRoadRatio,
                                     Road destination, double destinationRoadRatio, int interval) throws PathNotFoundException;

    /**
     * @param numOfVehicles - number of vehicles to enter
     * @return a Vehicle List all the randomly generated vehicles
     */
    List<Vehicle> addRandomVehicles(int numOfVehicles) throws PathNotFoundException;

    /**
     * @return CityMap to be saved for graphic uses
     */
    CityMap saveMap();

    /**
     * @author Adam Elgressy and Guy Rephaeli
     * @Date 20-1-2018
     * progress everything by a clock tick,
     * a clock tick is considered to be
     * an advancement of 1 second
     * @return self
     */
    CitySimulator tick();

    /**
     * @author Adam Elgressy and Guy Rephaeli
     * @Date 20-1-2018
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
