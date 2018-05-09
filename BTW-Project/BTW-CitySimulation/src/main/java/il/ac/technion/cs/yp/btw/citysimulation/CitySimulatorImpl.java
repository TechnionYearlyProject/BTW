package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.navigation.NaiveNavigationManager;
import il.ac.technion.cs.yp.btw.navigation.NavigationManager;
import il.ac.technion.cs.yp.btw.navigation.PathNotFoundException;
import il.ac.technion.cs.yp.btw.statistics.NaiveStatisticsCalculator;
import il.ac.technion.cs.yp.btw.statistics.StatisticalReport;
import il.ac.technion.cs.yp.btw.statistics.StatisticsCalculator;
import il.ac.technion.cs.yp.btw.trafficlights.NaiveTrafficLightManager;
import il.ac.technion.cs.yp.btw.trafficlights.TrafficLightManager;

import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Adam Elgressy and Guy Rephaeli
 * @Date 20-1-2018
 * Implement CitySimulation
 */

public class CitySimulatorImpl implements CitySimulator {
    private Map<String, CityRoad> roads;
    private Set<Road> fakeRoads;
    private Map<String, CityTrafficLight> trafficLights;
    private Map<String, CityCrossroad> crossroads;
    private TrafficLightManager trafficLightManager;
    private NavigationManager navigationManager;
    private Set<Vehicle> vehicles; //all vehicles that drove/drive
    private List<Vehicle> vehiclesToEnter;
    private long clock;
    private Map<Road, StatisticalReport> currentReportOfRoad;
    private Map<TrafficLight, StatisticalReport> currentReportOfTrafficLight;
    private long timeWindow;
    private StatisticsCalculator calculator; // maybe should be singleton

    private class CityRoadImpl implements CityRoad {
        private static final double DEFAULT_CAPACITY_PER_METER = 0.4;
        private static final int DEFAULT_SPEED_LIMIT = 50;
        private final String name;
        private final int length;
        private final Street street;
        private final Crossroad source;
        private final Crossroad destination;
        private final int capacity;
        private final int speedLimit;
        private Set<Vehicle> vehicles;
        private BTWWeight minWeight;


        private CityRoadImpl(Road road) {
            this.name = road.getRoadName();
            this.length = road.getRoadLength();
            this.street = road.getStreet();
            this.source = road.getSourceCrossroad();
            this.destination = road.getDestinationCrossroad();
            this.capacity = Double.valueOf(DEFAULT_CAPACITY_PER_METER * length).intValue();
            this.speedLimit = DEFAULT_SPEED_LIMIT;
            this.vehicles = new HashSet<>();
            this.minWeight = road.getMinimumWeight();
        }

        @Override
        public boolean isStreetNumberInRange(int streetNumber) {
            return false;
        }

        @Override
        public int getRoadLength() {
            return this.length;
        }

        @Override
        public String getRoadName() {
            return this.name;
        }

        @Override
        public Street getStreet() {
            return this.street;
        }

        @Override
        public BTWWeight getWeightByTime(BTWTime time) {
            return null;
        }

        @Override
        public BTWWeight getMinimumWeight() {
            return minWeight;
        }

        @Override
        public BTWWeight getHeuristicDist(Road road) {
            return null;
        }

        @Override
        public Crossroad getSourceCrossroad() {
            return this.source;
        }

        @Override
        public Crossroad getDestinationCrossroad() {
            return this.destination;
        }

        /**
         * @author Guy Rephaeli
         * @Date 20-1-2018
         *
         * @param vehicle - vehicle to enter the road
         * @return self
         */
        @Override
        public CityRoad addVehicle(Vehicle vehicle) {
            this.vehicles.add(vehicle);
            return this;
        }

        /**
         * @author Guy Rephaeli
         * @Date 20-1-2018
         *
         * @param vehicle - vehicle to leave the road
         * @return self
         */
        @Override
        public CityRoad removeVehicle(Vehicle vehicle) {
            this.vehicles.remove(vehicle);
            return this;
        }

        /**
         * @author Guy Rephaeli
         * @Date 20-1-2018
         *
         * @return self
         */
        @Override
        public CityRoad tick() {
            vehicles.forEach(Vehicle::progressOnRoad);
            return this;
        }

        /**
         * @author Adam Elgressy and Guy Rephaeli
         * @Date 20-1-2018
         *
         * @return
         */
        @Override
        public BTWWeight getCurrentWeight() {
            double currSpeed = getSpeed();
            Double time = this.length/currSpeed;
            try {
                return BTWWeight.of(time.longValue());
            } catch (BTWIllegalTimeException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * @author Adam Elgressy and Guy Rephaeli
         * @Date 20-1-2018
         * speedLimit in km/h on road
         * capacity - discrete
         * number of vehicles on road - discrete
         * @return actual speed on the road, in m/s
         */
        private double getSpeed() {
            return (this.speedLimit * (1.0 - (((double) this.vehicles.size()) / ((double) this.capacity)))) / 3.6;
        }

        /**
         * @author Guy Rephaeli
         * @Date 20-1-2018
         *
         * @return StatisticalData of current object
         */
        @Override
        public RoadData getStatisticalData() {
            return new RoadData(this.length, this.getSpeed() * 3.6, this.vehicles.size());
        }
    }

    private class CityTrafficLightImpl implements CityTrafficLight {
        private static final int DEFAULT_MINIMUM_OPEN_TIME = 19;
        private static final double DEFAULT_THROUGHPUT = 0.5;
        private final double xCoord;
        private final double yCoord;
        private final String name;
        private final Road source;
        private final Road destination;
        private Queue<Vehicle> vehicles;
        private TrafficLightState state;
        private int timeOpen;
        private int minimumOpenTime;
        private double throughput;
        private double totalThroughputInCurrentGreen;

        private CityTrafficLightImpl(TrafficLight trafficLight) {
            this.xCoord = trafficLight.getCoordinateX();
            this.yCoord = trafficLight.getCoordinateY();
            this.name = trafficLight.getName();
            this.source = trafficLight.getSourceRoad();
            this.destination = trafficLight.getDestinationRoad();
            this.vehicles = new LinkedList<>();
            this.state = TrafficLightState.RED;
            this.minimumOpenTime = DEFAULT_MINIMUM_OPEN_TIME;
            this.throughput = DEFAULT_THROUGHPUT;
            this.totalThroughputInCurrentGreen = 0.0;
            this.timeOpen = 0;
        }

        @Override
        public double getCoordinateX() {
            return xCoord;
        }

        @Override
        public double getCoordinateY() {
            return yCoord;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Road getSourceRoad() {
            return source;
        }

        @Override
        public Road getDestinationRoad() {
            return destination;
        }

        @Override
        public BTWWeight getWeightByTime(BTWTime time) {
            return null;
        }

        @Override
        public BTWWeight getMinimumWeight() {
            return null;
        }

        /**
         * @author Adam Elgressy and Guy Rephaeli
         * @Date 20-1-2018
         *
         * sets this Traffic light to the given state
         *
         * @param state - TrafficLightState, such as GREEN/RED
         * @return self
         */
        @Override
        public CityTrafficLight setTrafficLightState(TrafficLightState state) {
            if (state.equals(TrafficLightState.RED)) {
                if (timeOpen < minimumOpenTime && this.state.equals(TrafficLightState.GREEN)) {
                    throw new IllegalStateException();//TODO better exception
                } else {
                    this.timeOpen = 0;
//                    this.timeOpen = this.state.equals(TrafficLightState.RED) ? 0 : this.timeOpen;
                    this.totalThroughputInCurrentGreen = this.throughput;
                }
            }
            this.state = state;
            return this;
        }

        /**
         * @author Guy Rephaeli
         * @Date 20-1-2018
         *
         * @param vehicle - vehicle to wait on traffic-light
         * @return self
         */
        @Override
        public CityTrafficLight addVehicle(Vehicle vehicle) {
            vehicles.add(vehicle);
            return this;
        }

        /**
         * @author Guy Rephaeli
         * @Date 20-1-2018
         *
         * progress the trafficLight vy 1 tick. Release waiting vehicles if exists and the trafficLight is GREEN
         *
         * @return self
         */
        @Override
        public CityTrafficLight tick() {
            if (state.equals(TrafficLightState.GREEN)) {
                this.timeOpen++;
                if ((!vehicles.isEmpty())) {
                    this.totalThroughputInCurrentGreen += this.throughput;
                    while (this.totalThroughputInCurrentGreen >= 1.0) {
                        Vehicle vehicle = vehicles.poll();
                        vehicle.driveToNextRoad();
                        this.totalThroughputInCurrentGreen -= 1.0;
                    }
                }
            }
            return this;
        }

        /**
         * @author Adam Elgressy and Guy Rephaeli
         * @Date 20-1-2018
         * @return minimum amount in seconds the TrafficLight
         * needs to be open
         */
        @Override
        public int getMinimumOpenTime() {
            return this.minimumOpenTime;
        }

        /**
         * @author Guy Rephaeli
         * @Date 20-1-2018
         *
         * @return the state of the trafficLight
         */
        @Override
        public TrafficLightState getState() {
            return this.state;
        }

        /**
         * @return StatisticalData of current object
         */
        @Override
        public TrafficLightData getStatisticalData() {
            // TODO
            return null;
        }
    }

    private class CityCrossroadImpl implements CityCrossroad {
        private final double xCoord;
        private final double yCoord;
        private final String name;
        private Set<TrafficLight> trafficLights;


        private CityCrossroadImpl(Crossroad crossroad) {
            this.name = crossroad.getName();
            this.xCoord = crossroad.getCoordinateX();
            this.yCoord = crossroad.getCoordinateY();
            this.trafficLights = new HashSet<>();
            this.trafficLights.addAll(crossroad.getTrafficLights());
        }

        /**
         * @author Guy Rephaeli
         * @Date 20-1-2018
         * add a Vehicle to this Crossroad
         *
         * @param vehicle - the Vehicle to be added
         * @return self
         */
        @Override
        public CityTrafficLight addVehicleOnTrafficLight(Vehicle vehicle) {
            Set<TrafficLight> possibleTrafficLights = this.getTrafficLightsFromRoad(vehicle.getCurrentRoad());
            TrafficLight toWaitOn = null;
            for (TrafficLight trafficLight : possibleTrafficLights) {
                if (trafficLight.getDestinationRoad().equals(vehicle.getNextRoad())) {
                    toWaitOn = trafficLight;
                    break;
                }
            }
            CityTrafficLight realTL = getRealTrafficLight(toWaitOn);
            realTL.addVehicle(vehicle);
            return realTL;
        }

        /**
         * @author Guy Rephaeli
         * @Date 20-1-2018
         *
         * realTrafficLight is the actual trafficLight instance that exists in the live map
         *
         * @return the set of all real trafficLights
         */
        @Override
        public Set<CityTrafficLight> getRealTrafficLights() {
            return this.trafficLights.stream()
                    .map(CitySimulatorImpl.this::getRealTrafficLight)
                    .collect(Collectors.toSet());
        }

        /**
         * @author Adam Elgressy and Guy Rephaeli
         * @Date 20-1-2018
         * progress everything by a clock all TrafficLights
         * in this Crossroad, and manage the opening and closing
         * of TrafficLights during the tick
         *
         * @return self
         */
        @Override
        public CityCrossroad tick() {
            this.trafficLights
                    .forEach(trafficLight
                            -> getRealTrafficLight(trafficLight).tick());
            return this;
        }

        /**
         * @author Guy Rephaeli
         * @Date 20-1-2018
         *
         * @return the name of the crossroad
         */
        @Override
        public String getName() {
            return this.name;
        }

        /**
         * @author Guy Rephaeli
         * @Date 20-1-2018
         *
         * @return Set of all TrafficLights in this Crossroad
         */
        @Override
        public Set<TrafficLight> getTrafficLights() {
            return this.trafficLights;
        }

        /**
         * @author Guy Rephaeli
         * @Date 20-1-2018
         *
         * @param road - the Road from which the TrafficLights are going
         * @return Set of all TrafficLights from a specific road
         */
        @Override
        public Set<TrafficLight> getTrafficLightsFromRoad(Road road) {
            return this.trafficLights
                    .stream()
                    .filter(trafficLight -> trafficLight.getSourceRoad().equals(road))
                    .collect(Collectors.toSet());
        }

        /**
         * @author Adam Elgressy and Guy Rephaeli
         * @Date 20-1-2018
         * Adds the given TrafficLightImpl to this CrossroadImpl
         *
         * @param tl - the TrafficLightImpl being added
         */
        @Override
        public Crossroad addTrafficLight(TrafficLight tl) {
            return null;
            //TODO better exception
            //throw new IllegalAccessException();
        }

        /**
         * @author Guy Rephaeli
         * @Date 20-1-2018
         *
         * @return the x coordinate of this Point
         */
        @Override
        public double getCoordinateX() {
            return this.xCoord;
        }

        /**
         * * @author Guy Rephaeli
         * @Date 20-1-2018
         *
         * @return the y coordinate of this Point
         */
        @Override
        public double getCoordinateY() {
            return this.yCoord;
        }

        /**
         * @return StatisticalData of current object
         */
        @Override
        public CrossroadData getStatisticalData() {
            // TODO
            return null;
        }
    }

    /**
     * * @author Guy Rephaeli
     * @Date 20-1-2018
     *
     * @param roads - the roads in the map
     * @param trafficLights - the traffic-lights in the map
     * @param crossroads - the crossroads in the map
     * @param navigationManager - the navigation manager that creates the navigators
     */
    CitySimulatorImpl(Set<Road> roads, Set<TrafficLight> trafficLights, Set<Crossroad> crossroads,
                      NavigationManager navigationManager, TrafficLightManager trafficLightManager, StatisticsCalculator calculator, long timeWindow){
        this.roads = new HashMap<>();
        this.trafficLights = new HashMap<>();
        this.crossroads = new HashMap<>();
        this.fakeRoads = roads;
        this.currentReportOfRoad = new HashMap<>();
        this.currentReportOfTrafficLight = new HashMap<>();
        this.calculator = calculator;

        roads.forEach(this::getRealRoad);
        trafficLights.forEach(this::getRealTrafficLight);
        crossroads.forEach(this::getRealCrossroad);

        this.vehicles = new HashSet<>();
        this.vehiclesToEnter = new ArrayList<>();
        this.navigationManager = navigationManager;
        this.trafficLightManager = trafficLightManager;
        this.clock = 0;
        this.timeWindow = timeWindow;
    }

    /**
     * @author Guy Rephaeli
     * @Date 20-1-2018
     *
     * @param db - the db containing the map
     */
    public CitySimulatorImpl(BTWDataBase db, NavigationManager navigationManager, TrafficLightManager trafficLightManager, StatisticsCalculator calculator) {
        this(db.getAllRoads(), db.getAllTrafficLights(), db.getAllCrossroads(), navigationManager, trafficLightManager, calculator, db.getStatisticsPeriod());
    }

    /**
     * @author Guy Rephaeli
     * @Date 20-1-2018
     *
     * @param  road - the road to find in the real city
     * @return the real road
     */
    @Override
    public CityRoad getRealRoad(Road road) {
        String roadName = road.getRoadName();
        if (!this.roads.containsKey(roadName)) {
            CityRoad newLiveRoad = new CityRoadImpl(road);
            this.roads.put(roadName, newLiveRoad);
        }
        return this.roads.get(roadName);
    }

    /**
     * @author Guy Rephaeli
     * @Date 20-1-2018
     *
     * @param  trafficLight - the traffic-light to find in the real city
     * @return the real trafficLight
     */
    @Override
    public CityTrafficLight getRealTrafficLight(TrafficLight trafficLight) {
        String trafficLightName = trafficLight.getName();
        if (!this.trafficLights.containsKey(trafficLightName)) {
            CityTrafficLight newLiveTrafficLight = new CityTrafficLightImpl(trafficLight);
            this.trafficLights.put(trafficLightName, newLiveTrafficLight);
        }
        return this.trafficLights.get(trafficLightName);
    }

    /**
     * @author Guy Rephaeli
     * @Date 20-1-2018
     *
     * @param  crossroad - the crossroad to find in the real city
     * @return the real crossroad
     */
    @Override
    public CityCrossroad getRealCrossroad(Crossroad crossroad) {
        String crossroadName = crossroad.getName();
        if (!this.crossroads.containsKey(crossroadName)) {
            CityCrossroad newLiveCrossroad = new CityCrossroadImpl(crossroad);
            this.crossroads.put(crossroadName, newLiveCrossroad);
        }
        return this.crossroads.get(crossroadName);
    }

    @Override
    public CitySimulator addVehiclesFromList(/* TODO:ADAM */) {
        // Don't forget to initialize the navigator. See addVehicleOnTime
        return this;
    }

    /**
     * @author Guy Rephaeli and Adam Elgressy
     * @date 25.4.2018
     *
     * @param vehicle - the vehicle to be added to the waiting vehicles list
     * @return self
     */
    /**
     * @author Guy Rephaeli and Adam Elgressy
     * @date 25.4.2018
     *
     * Find the index to enter the vehicle at using binary search
     *
     * @param time - the time to be serched fo
     * @param low - low index for binary search
     * @param middle - middle index for binary search
     * @param high - high index for binary search
     * @return - the index to enter the vehicle in
     */
    private int getVehicleIndex(long time, int low, int middle, int high) {
        if (low >= high - 1) {
            return high;
        }
        if (this.vehiclesToEnter.get(middle).getStartingTime().seconds() == time) {
            return middle;
        }
        if (this.vehiclesToEnter.get(middle).getStartingTime().seconds() < time) {
            int newLow = middle;
            return this.getVehicleIndex(time, newLow, (newLow + high) / 2, high);
        }
        if (this.vehiclesToEnter.get(middle).getStartingTime().seconds() > time) {
            int newHigh = middle;
            return this.getVehicleIndex(time, low, (low + newHigh) / 2, newHigh);
        }
        return -1;
    }

    /**
     * @author Guy Rephaeli and Adam Elgressy
     * @date 25.4.2018
     *
     * Add the vehicle to the list while maintaining order
     *
     * @param vehicle - the vehicle to be added to the waiting vehicles list
     * @return self
     */
    private CitySimulator addVehicleSorted(Vehicle vehicle) {
        int low = 0;
        int high = this.vehiclesToEnter.size();
        int middle = (low + high) / 2;
        int indexToEnter = this.getVehicleIndex(vehicle.getStartingTime().seconds(), low, middle, high);
        this.vehiclesToEnter.add(indexToEnter, vehicle);
        return this;
    }

    /**
     * @author Guy Rephaeli
     * @Date 20-1-2018
     *
     * @param vehicleDescriptor - the descriptor of the vehicle we wish to create and add
     * @param source - the source road from which the vehicle should start driving
     * @param sourceRoadRatio - the place on the source road from which the vehicle should start driving
     * @param destination - the destination road to which the vehicle should eventually arrive
     * @param destinationRoadRatio - the place on the destination road to which the vehicle should eventually arrive
     * @param time - the global time to start driving
     * @return the added vehicle
     * @throws PathNotFoundException
     */
    private Vehicle addVehicleOnTime(VehicleDescriptor vehicleDescriptor, Road source, double sourceRoadRatio,
                              Road destination, double destinationRoadRatio, long time) throws PathNotFoundException {
        Vehicle newVehicle = new VehicleImpl(vehicleDescriptor,
                source, sourceRoadRatio, destination, destinationRoadRatio,
                this.navigationManager.getNavigator(vehicleDescriptor, source, sourceRoadRatio, destination, destinationRoadRatio),
                this, time);
        this.addVehicleSorted(newVehicle);
        return newVehicle;
    }

    /**
     * @author Adam Elgressy and Guy Rephaeli
     * @Date 20-1-2018
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
    public Vehicle addVehicle(VehicleDescriptor vehicleDescriptor, Road source, double sourceRoadRatio,
                              Road destination, double destinationRoadRatio) throws PathNotFoundException {
        return this.addVehicleOnTime(vehicleDescriptor, source, sourceRoadRatio, destination, destinationRoadRatio, this.clock + 1);
    }

    /**
     * @author Adam Elgressy and Guy Rephaeli
     * @Date 20-1-2018
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
    public List<Vehicle> addSeveralVehicles(List<VehicleDescriptor> vehicleDescriptors,  Road source, double sourceRoadRatio,
                                            Road destination, double destinationRoadRatio, int interval) throws PathNotFoundException{
        List<Vehicle> added = new ArrayList<>();
        long startFromNow = 1;
        for (VehicleDescriptor descriptor : vehicleDescriptors) {
            added.add(addVehicleOnTime(descriptor, source, sourceRoadRatio, destination, destinationRoadRatio, this.clock + startFromNow));
            startFromNow += interval;
        }
        return added;
    }

    /**
     * @author Guy Rephaeli
     * @Date 20-1-2018
     *
     * @param numOfVehicles - number of vehicles to enter
     * @return the list of added vehicles
     * @throws PathNotFoundException
     */
    @Override
    public List<Vehicle> addRandomVehicles(int numOfVehicles) throws PathNotFoundException {
        List<VehicleDescriptor> descriptors = new ArrayList<>();
        for (int i = 0; i < numOfVehicles; i++) {
            descriptors.add(null);
        }
        List<Vehicle> currVehicles = new ArrayList<>();
        boolean pathNotFound = true;
        while (pathNotFound) {
            Random rnd = new Random();
            int rndInt1 = rnd.nextInt(this.fakeRoads.size());
            int rndInt2 = rnd.nextInt(this.fakeRoads.size());
            Road source = (Road) this.fakeRoads.toArray()[rndInt1];
            Road destination = (Road) this.fakeRoads.toArray()[rndInt2];
            try {
                currVehicles = addSeveralVehicles(descriptors,  source, 0.0, destination, 1.0, 5);
                pathNotFound = false;
            } catch (PathNotFoundException e) {
                pathNotFound = true;
            }
        }
        return currVehicles;
    }

    /**
     * @author Adam Elgressy and Guy Rephaeli
     * @Date 20-1-2018
     * @return CityMap to be saved for graphic uses
     */
    @Override
    public CityMap saveMap() {
        return new CityMapImpl(new HashSet<>(this.roads.values())
                ,new HashSet<>(this.trafficLights.values())
                ,new HashSet<>(this.crossroads.values()));
    }

    /**
     * @author Adam Elgressy and Guy Rephaeli
     * @Date 20-1-2018
     * progress everything by a clock tick,
     * a clock tick is considered to be
     * an advancement of 1 second
     *
     * @return self
     */
    @Override
    public CitySimulator tick() {
        this.clock++;
        if (this.clock % this.timeWindow == 0) {
            this.currentReportOfRoad
                    .keySet()
                    .forEach(rd -> this.calculator.adRoadReport(rd, this.currentReportOfRoad.get(rd)));
            this.currentReportOfRoad = new HashMap<>();

            this.currentReportOfTrafficLight
                    .keySet()
                    .forEach(tl -> this.calculator.adTrafficLightReport(tl, this.currentReportOfTrafficLight.get(tl)));
            this.currentReportOfTrafficLight = new HashMap<>();
        }
        roads.values().forEach(CityRoad::tick);
        this.trafficLightManager.tick();
        Set<Vehicle> drivingVehicles = new HashSet<>();

        boolean readyToDrive = (this.vehiclesToEnter.size() > 0);
        while (readyToDrive) {
            Vehicle currentWaitingVehicle = this.vehiclesToEnter.get(0);
            readyToDrive = currentWaitingVehicle.driveOnTime(this.clock);
            if (readyToDrive) {
                drivingVehicles.add(currentWaitingVehicle);
                this.vehiclesToEnter.remove(0);
            }
            readyToDrive &= (this.vehiclesToEnter.size() > 0);
        }
//        this.vehiclesToEnter.forEach(vehicle -> {
//            if (vehicle.driveOnTime(this.clock)) {
//                drivingVehicles.add(vehicle);
//            }
//        });
        this.vehiclesToEnter.removeAll(drivingVehicles);
        this.vehicles.addAll(drivingVehicles);
        return this;
    }

    @Override
    public Long getCurrentTime() {
        return this.clock;
    }

    @Override
    public CitySimulator reportOnRoad(Road rd, Long time) {
        if (! this.currentReportOfRoad.containsKey(rd)) {
            StatisticalReport report = new StatisticalReport(BTWTime.of(this.clock - (this.clock % this.timeWindow)));
            this.currentReportOfRoad.put(rd, report);
        }
//        this.currentReportOfRoad.get(rd).update(BTWWeight.of(time - (time % this.timeWindow)));
        this.currentReportOfRoad.get(rd).update(BTWWeight.of(time));
        return this;
    }

    @Override
    public CitySimulator reportOnTrafficLight(TrafficLight tl, Long time) {
        if (! this.currentReportOfTrafficLight.containsKey(tl)) {
            StatisticalReport report = new StatisticalReport(BTWTime.of(this.clock - (this.clock % this.timeWindow)));
            this.currentReportOfTrafficLight.put(tl, report);
        }
//        this.currentReportOfTrafficLight.get(tl).update(BTWWeight.of(time - (time % this.timeWindow)));
        this.currentReportOfTrafficLight.get(tl).update(BTWWeight.of(time));
        return this;
    }

}