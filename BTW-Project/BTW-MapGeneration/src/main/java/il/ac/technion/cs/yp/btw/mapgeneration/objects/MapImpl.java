package il.ac.technion.cs.yp.btw.mapgeneration.objects;

import il.ac.technion.cs.yp.btw.classes.*;

import java.util.Set;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * Map implementation to pass Map information
 * from the MapGeneration to other modules
 */
public class MapImpl implements Map {
    private final Set<TrafficLight> trafficLights;
    private final Set<Road> roads;
    private final Set<Crossroad> crossRoads;
    private final Set<CentralLocation> centralLocations;
    private final Set<Street> streets;

    public MapImpl(Set<TrafficLight> trafficLights, Set<Road> roads
            , Set<Crossroad> crossRoads, Set<CentralLocation> centralLocations
            , Set<Street> streets) {
        this.trafficLights = trafficLights;
        this.roads = roads;
        this.crossRoads = crossRoads;
        this.centralLocations = centralLocations;
        this.streets = streets;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of the map's
     * traffic Lights
     */
    @Override
    public Set<TrafficLight> getTrafficLights() {
        return this.trafficLights;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of the map's
     * roads
     */
    @Override
    public Set<Road> getRoads() {
        return this.roads;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of the map's
     * cross roads
     */
    @Override
    public Set<Crossroad> getCrossRoads() {
        return this.crossRoads;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of the map's
     * central locations
     */
    @Override
    public Set<CentralLocation> getCentralLocations() {
        return this.centralLocations;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of the map's
     * central locations
     */
    @Override
    public Set<Street> getStreets() {
        return this.streets;
    }
}
