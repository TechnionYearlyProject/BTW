package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.*;

import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * managing the city
 */
public class LiveCity {
    private static Map<String, LiveRoad> roads = new HashMap<>();
    private static Map<String, LiveTrafficLight> trafficLights = new HashMap<>();
    private static Map<String, LiveCrossroad> crossroads = new HashMap<>();

    static LiveRoad getRealRoad(Road road) {
        String roadName = road.getRoadName();
        if (!roads.containsKey(roadName)) {
            LiveRoad newLiveRoad = new LiveRoad(road);
            roads.put(roadName, newLiveRoad);
        }
        return roads.get(roadName);
    }

    static void reset() {
        roads = new HashMap<>();
        trafficLights = new HashMap<>();
        crossroads = new HashMap<>();
    }

    static LiveTrafficLight getRealTrafficLight(TrafficLight trafficLight) {
        String trafficLightName = trafficLight.getName();
        if (!trafficLights.containsKey(trafficLightName)) {
            LiveTrafficLight newLiveTrafficLight = new LiveTrafficLight(trafficLight);
            trafficLights.put(trafficLightName, newLiveTrafficLight);
        }
        return trafficLights.get(trafficLightName);
    }

    static LiveCrossroad getRealCrossroad(Crossroad crossroad) {
        String crossroadName = crossroad.getName();
        if (!crossroads.containsKey(crossroadName)) {
            LiveCrossroad newLiveCrossroad = new LiveCrossroad(crossroad);
            crossroads.put(crossroadName, newLiveCrossroad);
        }
        return crossroads.get(crossroadName);
    }

    public void tick() {
        // TODO: handle traffic-light opening and closing using the manager
        for (CityRoad road : roads.values()) {
            road.tick();
        }
        for (CityTrafficLight trafficLight : trafficLights.values()) {
            trafficLight.tick();
        }
    }

    static class LiveRoad implements CityRoad {
        private static final double DEFAULT_CAPACITY_PER_METER = 0.45;
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


        private LiveRoad(Road road) {
            this.name = road.getRoadName();
            this.length = road.getRoadLength();
            this.street = road.getStreet();
            this.source = road.getSourceCrossroad();
            this.destination = road.getDestinationCrossroad();
            this.capacity = Double.valueOf(DEFAULT_CAPACITY_PER_METER*length).intValue();
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

        @Override
        public CityRoad addVehicle(Vehicle vehicle) {
            this.vehicles.add(vehicle);
            vehicle.setRemainingTimeOnRoad(getCurrentWeight());
            return this;
        }

        @Override
        public CityRoad removeVehicle(Vehicle vehicle) {
            this.vehicles.remove(vehicle);
            return this;
        }

        @Override
        public CityRoad tick() {
            for (Vehicle vehicle : vehicles) {
                vehicle.driveToNextRoad();
            }
            return this;
        }

        @Override
        public BTWWeight getCurrentWeight() {
            // TODO: better
            double currSpeed = getSpeed(this.speedLimit,this.capacity,this.vehicles.size());
            Double time = this.length/currSpeed;
            Double dWeight = (((vehicles.size() - 1) / length) + 1) * (double) minWeight.seconds();
            try {
                return BTWWeight.of(time.longValue());
            } catch (BTWIllegalTimeException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         *
         * @param speedLimit in km/h on road
         * @param capacity of vehicles on road
         * @param numberOfVehiclesOnRoad -
         * @return actual speed on the road, in m/s
         */
        private static double getSpeed(int speedLimit, int capacity, int numberOfVehiclesOnRoad) {
            //TODO
            return 1;
        }

        /**
         * @return StatisticalData of current object
         */
        @Override
        public StatisticalData getStatisticalData() {
            return null;
        }
    }

    static class LiveTrafficLight implements CityTrafficLight {

        private static final int DEFAULT_MINIMUM_OPEN_TIME = 20;
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

        private LiveTrafficLight(TrafficLight trafficLight) {
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
         * sets this Traffic light to the given state
         *
         * @param state - TrafficLightState, such as GREEN/RED
         * @return self
         */
        @Override
        public CityTrafficLight setTrafficLightState(TrafficLightState state) {
            if (state.equals(TrafficLightState.RED)) {
                if (timeOpen < minimumOpenTime) {
                    throw new IllegalStateException();//TODO better exception
                } else {
                    this.timeOpen = this.state.equals(TrafficLightState.RED) ? 0 : this.timeOpen;
                }
            }
            this.state = state;
            return this;
        }

        @Override
        public CityTrafficLight addVehicle(Vehicle vehicle) {
            vehicles.add(vehicle);
            return this;
        }

        @Override
        public CityTrafficLight tick() {
            if (state.equals(TrafficLightState.GREEN)) {
                this.timeOpen++;
                if ((!vehicles.isEmpty())) {
                    while (this.totalThroughputInCurrentGreen > 1.0) {
                        Vehicle vehicle = vehicles.poll();
                        vehicle.driveToNextRoad();
                        this.totalThroughputInCurrentGreen -= 1.0;
                    }
                }
            }
            return this;
        }

        /**
         * @return minimum amount in seconds the TrafficLight
         * needs to be open
         */
        @Override
        public int getMinimumOpenTime() {
            return this.minimumOpenTime;
        }

        /**
         * @return StatisticalData of current object
         */
        @Override
        public StatisticalData getStatisticalData() {
            return null;
        }
    }

    static class LiveCrossroad implements CityCrossroad {
        private final double xCoord;
        private final double yCoord;
        private final String name;
        private Set<TrafficLight> trafficLights;


        private LiveCrossroad(Crossroad crossroad) {
            this.name = crossroad.getName();
            this.xCoord = crossroad.getCoordinateX();
            this.yCoord = crossroad.getCoordinateY();
            this.trafficLights = new HashSet<>();
            this.trafficLights.addAll(crossroad.getTrafficLights());
        }

        /**
         * add a Vehicle to this Crossroad
         *
         * @param vehicle - the Vehicle to be added
         * @return self
         */
        @Override
        public CityCrossroad addVehicle(Vehicle vehicle) {
            Set<TrafficLight> possibleTrafficLights = this.getTrafficLightsFromRoad(vehicle.getCurrentRoad());
            TrafficLight toWaitOn = null;
            for (TrafficLight trafficLight : possibleTrafficLights) {
                if (trafficLight.getDestinationRoad().equals(vehicle.getNextRoad())) {
                    toWaitOn = trafficLight;
                    break;
                }
            }
            CityTrafficLight realTL = LiveCity.getRealTrafficLight(toWaitOn);
            realTL.addVehicle(vehicle);
            return this;
        }

        /**
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
                            -> LiveCity.getRealTrafficLight(trafficLight).tick());
            return this;
        }

        /**
         * @return the name of the crossroad
         */
        @Override
        public String getName() {
            return this.name;
        }

        /**
         * @return Set of all TrafficLights in this CrossroadImpl
         */
        @Override
        public Set<TrafficLight> getTrafficLights() {
            return this.trafficLights;
        }

        /**
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
         * @return the x coordinate of this Point
         */
        @Override
        public double getCoordinateX() {
            return this.xCoord;
        }

        /**
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
        public StatisticalData getStatisticalData() {
            return null;
        }
    }
}
