package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.navigation.NaiveNavigationManager;
import il.ac.technion.cs.yp.btw.navigation.NavigationManager;
import il.ac.technion.cs.yp.btw.trafficlights.TrafficLightManager;

import java.util.*;
import java.util.stream.Collectors;

public class CitySimulatorImpl implements CitySimulator {
    private Map<String, CityRoad> roads;
    private Map<String, CityTrafficLight> trafficLights;
    private Map<String, CityCrossroad> crossroads;
    private TrafficLightManager trafficLightManager;
    private NavigationManager navigationManager;
    private Set<Vehicle> vehicles;//all vehicles that drove/drive
    private Set<Vehicle> vehiclesToEnter;
    private long clock;

    public CitySimulatorImpl(TrafficLightManager tlm, NavigationManager nm){
        this.roads = new HashMap<>();
        this.trafficLights = new HashMap<>();
        this.crossroads = new HashMap<>();
        this.vehicles = new HashSet<>();
        this.vehiclesToEnter = new HashSet<>();
        this.navigationManager = nm;
        this.trafficLightManager = tlm;
        this.clock = 0;
    }
    /**
     * @param vehicleDescriptor - technical properties
     *                          of the Vehicle
     * @param source            - source address
     * @param destination       - destination address
     * @return a Vehicle with technical properties
     * as described in the VehicleDescriptor, which
     * will driven from the given source address
     * to the given destination address
     */
    @Override
    public Vehicle addVehicle(VehicleDescriptor vehicleDescriptor, String source, String destination) {
        return null;
    }

    /**
     * @param vehicleDescriptors - List of technical properties
     *                           of the Vehicles
     * @param source             - source address
     * @param destination        - destination address
     * @param interval           - time interval between Vehicle generation,
     *                           in seconds
     * @return a Vehicle List with technical properties
     * as described in the VehicleDescriptors, which
     * will drive from the given source address
     * to the given destination address.
     * new Vehicles will be generated in the
     * given interval
     */
    @Override
    public List<Vehicle> addSeveralVehicles(List<VehicleDescriptor> vehicleDescriptors, String source, String destination, int interval) {
//        int c = 0;
//        while(c > vehicleDescriptors.size()*interval)
//        {
//            createEntry(c=time needed)
//        }
        return null;
    }

    /**
     * @return CityMap to be saved for graphic uses
     */
    @Override
    public CityMap saveMap() {
        return new CityMapImpl(new HashSet<CityRoad>(this.roads.values())
                ,new HashSet<CityTrafficLight>(this.trafficLights.values())
                ,new HashSet<CityCrossroad>(this.crossroads.values()));
    }

    /**
     * progress everything by a clock tick,
     * a clock tick is considered to be
     * an advancement of 1 second
     *
     * @return self
     */
    @Override
    public CitySimulator tick() {
        for (CityRoad road : roads.values()) {
            road.tick();
        }
        this.trafficLightManager.tick();
        this.vehiclesToEnter.forEach(vehicle -> {
            //  if(vehicle.getStartingTime() == this.clock) {
            //      this.vehicles.add(vehicle);
            //      vehicle.startDriving();
            //  }
        });
        return this;
    }

}
