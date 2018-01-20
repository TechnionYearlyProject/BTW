package il.ac.technion.cs.yp.btw.mapgeneration;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.mapgeneration.objects.MapImpl;
import il.ac.technion.cs.yp.btw.mapgeneration.objects.MapSimulationTrafficLightImpl;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * abstract class for all mutual code of map simulators
 */
abstract class AbstractMapSimulator implements MapSimulator {
    protected Set<TrafficLight> trafficLights;
    protected Set<Road> roads;
    protected Set<Crossroad> crossRoads;
    protected Set<CentralLocation> centralLocations;
    protected Set<Street> streets;

    protected AbstractMapSimulator() {
        initializeAllSets();
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     * <p>
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     *
     * @returns Distance in Meters
     */
    protected static double distanceBetween2PointsOnEarth(double lat1, double lat2, double lon1,
                                                        double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    /**
     * adds all possible TrafficLights to this MapSimulator's map
     */
    protected void addTrafficLights() {
        for(Crossroad cr : getCrossRoads()){
            Set<Road> roadsEndsInCurrCrossroad =
                    getRoads().stream()
                            .filter(road -> road.getDestinationCrossroad().equals(cr))
                            .collect(Collectors.toSet());
            Set<Road> roadsStartInCurrCrossroad =
                    getRoads().stream()
                            .filter(road -> road.getSourceCrossroad().equals(cr))
                            .collect(Collectors.toSet());
            for (Road sourceRoad : roadsEndsInCurrCrossroad){
                for (Road destRoad : roadsStartInCurrCrossroad){
                    TrafficLight tl = new MapSimulationTrafficLightImpl(cr, sourceRoad, destRoad);
                    cr.addTrafficLight(tl);
                    this.getTrafficLights().add(tl);
                }
            }
        }
    }

    /**
     * @return Map with the corresponding Sets
     *         of the current MapSimulator's state
     *         returns empty map if no build was made
     *         in this MapSimulator
     */
    protected Map extractMap() {
        return new MapImpl(this.getTrafficLights(),this.getRoads(),this.getCrossRoads()
        ,this.getCentralLocations(),this.getStreets());
    }

    protected void initializeAllSets() {
        this.roads = new HashSet<>();
        this.crossRoads = new HashSet<>();
        this.trafficLights = new HashSet<>();
        this.centralLocations = new HashSet<>();
        this.streets = new HashSet<>();
    }

    static double metersToDegrees(int meters){
        return meters/111111.0;
    }
}
