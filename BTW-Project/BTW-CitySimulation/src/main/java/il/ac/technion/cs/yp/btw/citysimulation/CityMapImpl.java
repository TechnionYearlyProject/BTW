package il.ac.technion.cs.yp.btw.citysimulation;

import java.util.Set;

/**
 * implementation of CityMap for graphics use
 */
public class CityMapImpl implements CityMap {
    private Set<CityRoad> roads;
    private Set<CityTrafficLight> trafficLights;
    private Set<CityCrossroad> crossroads;

    CityMapImpl(Set<CityRoad> roads,
                Set<CityTrafficLight> trafficLights,
                Set<CityCrossroad> crossroads) {
        this.roads = roads;
        this.trafficLights = trafficLights;
        this.crossroads = crossroads;
    }

    /**
     * @return all CityRoads on the map
     */
    @Override
    public Set<CityRoad> getAllRoads() {
        return this.roads;
    }

    /**
     * @return all CityTrafficLights on the map
     */
    @Override
    public Set<CityTrafficLight> getAllTrafficLights() {
        return this.trafficLights;
    }

    /**
     * @return all CityCrossroads on the map
     */
    @Override
    public Set<CityCrossroad> getAllCrossroads() {
        return this.crossroads;
    }
}
