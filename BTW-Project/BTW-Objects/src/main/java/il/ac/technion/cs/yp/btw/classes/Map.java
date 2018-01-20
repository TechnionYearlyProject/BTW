package il.ac.technion.cs.yp.btw.classes;

import java.util.Set;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * interface to help different modules to pass
 * a uniform type of Map
 */
public interface Map {
    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of the map's
     *          traffic Lights
     */
    Set<TrafficLight> getTrafficLights();

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of the map's
     *          roads
     */
    Set<Road> getRoads();

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of the map's
     *          cross roads
     */
    Set<Crossroad> getCrossRoads();

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of the map's
     *          central locations
     */
    Set<CentralLocation> getCentralLocations();

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of the map's
     *          central locations
     */
    Set<Street> getStreets();
}
