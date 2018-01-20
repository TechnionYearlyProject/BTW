package il.ac.technion.cs.yp.btw.mapgeneration.objects;

import il.ac.technion.cs.yp.btw.classes.*;

import java.util.Set;

public class MapImpl implements Map {
    private Set<TrafficLight> trafficLights;
    private Set<Road> roads;
    private Set<Crossroad> crossRoads;
    private Set<CentralLocation> centralLocations;
    private Set<Street> streets;

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
     * @return Set of the map's
     * traffic Lights
     */
    @Override
    public Set<TrafficLight> getTrafficLights() {
        return this.trafficLights;
    }

    /**
     * @return Set of the map's
     * roads
     */
    @Override
    public Set<Road> getRoads() {
        return this.roads;
    }

    /**
     * @return Set of the map's
     * cross roads
     */
    @Override
    public Set<Crossroad> getCrossRoads() {
        return this.crossRoads;
    }

    /**
     * @return Set of the map's
     * central locations
     */
    @Override
    public Set<CentralLocation> getCentralLocations() {
        return this.centralLocations;
    }

    /**
     * @return Set of the map's
     * central locations
     */
    @Override
    public Set<Street> getStreets() {
        return this.streets;
    }
}
