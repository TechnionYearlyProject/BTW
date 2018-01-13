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

    static LiveTrafficLight getRealTrafficLight(TrafficLight trafficLight) {
        String trafficLightName = trafficLight.getName();
        if (! trafficLights.containsKey(trafficLightName)) {
            LiveTrafficLight newLiveTrafficLight = new LiveTrafficLight(trafficLight);
            trafficLights.put(trafficLightName, newLiveTrafficLight);
        }
        return trafficLights.get(trafficLightName);
    }

    static class LiveRoad implements CityRoad {
        private final String name;
        private final int length;
        private final Street street;
        private final Crossroad source;
        private final Crossroad destination;
        private Set<Vehicle> vehicles;


        private LiveRoad(Road road) {
            this.name = road.getRoadName();
            this.length = road.getRoadLength();
            this.street = road.getStreet();
            this.source = road.getSourceCrossroad();
            this.destination = road.getDestinationCrossroad();
            this.vehicles = new HashSet<>();
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
            return null;
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
        public synchronized BTWWeight addVehicle(Vehicle vehicle) {
            this.vehicles.add(vehicle);
            return getCurrentWeight();
        }


        @Override
        public synchronized CityRoad removeVehicle(Vehicle vehicle) {
            this.vehicles.remove(vehicle);
            return this;
        }

        @Override
        public BTWWeight getCurrentWeight() {
            // TODO
            return null;
        }
    }

    static class LiveTrafficLight implements CityTrafficLight {

        private final double xCoord;
        private final double yCoord;
        private final String name;
        private final Road source;
        private final Road destination;
        private Queue<Vehicle> vehicles;

        private LiveTrafficLight(TrafficLight trafficLight) {
            this.xCoord = trafficLight.getCoordinateX();
            this.yCoord = trafficLight.getCoordinateY();
            this.name = trafficLight.getName();
            this.source = trafficLight.getSourceRoad();
            this.destination = trafficLight.getDestinationRoad();
            this.vehicles = new LinkedList<>();
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
            // TODO
            return this;
        }

        @Override
        public CityTrafficLight close() {
            // TODO
            return this;
        }

        @Override
        public CityTrafficLight addVehicle(Vehicle vehicle) {
            // TODO
            return this;
        }
    }
}
