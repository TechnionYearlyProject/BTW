package il.ac.technion.cs.yp.btw.citysimulation;

import java.util.Set;

/**
 * interface which describes data for graphic maps
 */
public interface CityMap {
    /**
     * @return all CityRoads on the map
     */
    Set<CityRoad> getAllRoads();
    /**
     * @return all CityTrafficLights on the map
     */
    Set<CityTrafficLight> getAllTrafficLights();

    /**
     * @return all CityCrossroads on the map
     */
    Set<CityCrossroad> getAllCrossroads();
}
