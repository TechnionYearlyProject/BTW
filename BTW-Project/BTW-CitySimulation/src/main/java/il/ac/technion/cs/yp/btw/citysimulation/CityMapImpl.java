package il.ac.technion.cs.yp.btw.citysimulation;

import java.util.Set;

/**
 * @author Adam Elgressy and Guy Rephaeli
 * @Date 20-1-2018
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
     * @author Adam Elgressy and Guy Rephaeli
     * @Date 20-1-2018
     * @return all CityRoads on the map
     */
    @Override
    public Set<CityRoad> getAllRoads() {
        return this.roads;
    }

    /**
     * @author Adam Elgressy and Guy Rephaeli
     * @Date 20-1-2018
     * @return all CityTrafficLights on the map
     */
    @Override
    public Set<CityTrafficLight> getAllTrafficLights() {
        return this.trafficLights;
    }

    /**
     * @author Adam Elgressy and Guy Rephaeli
     * @Date 20-1-2018
     * @return all CityCrossroads on the map
     */
    @Override
    public Set<CityCrossroad> getAllCrossroads() {
        return this.crossroads;
    }
}
