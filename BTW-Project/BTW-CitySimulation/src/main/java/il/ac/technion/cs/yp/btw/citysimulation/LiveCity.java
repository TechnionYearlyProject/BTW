package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.*;

import java.util.*;
import java.util.Map;

/**
 * managing the city
 */
public class LiveCity {
    private static Map<String, LiveRoad> roads = new HashMap<>();
    private static Map<String, LiveTrafficLight> trafficLights = new HashMap<>();

    static LiveRoad getRealRoad(Road road) {
        String roadName = road.getRoadName();
        if (! roads.containsKey(roadName)) {
            LiveRoad newLiveRoad = new LiveRoad(road);
            roads.put(roadName, newLiveRoad);
        }
        return roads.get(roadName);
    }

    static void reset() {
        roads = new HashMap<>();
        trafficLights = new HashMap<>();
    }

    static LiveTrafficLight getRealTrafficLight(TrafficLight trafficLight) {
        String trafficLightName = trafficLight.getName();
        if (! trafficLights.containsKey(trafficLightName)) {
            LiveTrafficLight newLiveTrafficLight = new LiveTrafficLight(trafficLight);
            trafficLights.put(trafficLightName, newLiveTrafficLight);
        }
        return trafficLights.get(trafficLightName);
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
        private final String name;
        private final int length;
        private final Street street;
        private final Crossroad source;
        private final Crossroad destination;
        private Set<Vehicle> vehicles;
        private BTWWeight minWeight;


        private LiveRoad(Road road) {
            this.name = road.getRoadName();
            this.length = road.getRoadLength();
            this.street = road.getStreet();
            this.source = road.getSourceCrossroad();
            this.destination = road.getDestinationCrossroad();
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
                vehicle.progressRoad();
            }
            return this;
        }

        @Override
        public BTWWeight getCurrentWeight() {
            // TODO: better
            Double dWeight = (((vehicles.size() - 1) / length) + 1) * (double)minWeight.seconds();
            try {
                return BTWWeight.of(dWeight.longValue());
            } catch (BTWIllegalTimeException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class LiveTrafficLight implements CityTrafficLight {

        private final double xCoord;
        private final double yCoord;
        private final String name;
        private final Road source;
        private final Road destination;
        private Queue<Vehicle> vehicles;
        private boolean isOpen;

        private LiveTrafficLight(TrafficLight trafficLight) {
            this.xCoord = trafficLight.getCoordinateX();
            this.yCoord = trafficLight.getCoordinateY();
            this.name = trafficLight.getName();
            this.source = trafficLight.getSourceRoad();
            this.destination = trafficLight.getDestinationRoad();
            this.vehicles = new LinkedList<>();
            this.isOpen = false;
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

        @Override
        public CityTrafficLight open() {
            isOpen = true;
            return this;
        }

        @Override
        public CityTrafficLight close() {
            isOpen = false;
            return this;
        }

        @Override
        public CityTrafficLight addVehicle(Vehicle vehicle) {
            vehicles.add(vehicle);
            return this;
        }

        @Override
        public CityTrafficLight tick() {
            // TODO: something more flexible
            if (isOpen && (! vehicles.isEmpty())) {
                Vehicle vehicle = vehicles.poll();
                vehicle.progressRoad();
            }
            return this;
        }
    }
}
