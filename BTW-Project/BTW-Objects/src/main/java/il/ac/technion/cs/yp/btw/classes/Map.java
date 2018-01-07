package il.ac.technion.cs.yp.btw.classes;

import java.util.Set;

public interface Map {
    /**
     *
     * @return Set of the map's
     *          traffic Lights
     */
    Set<TrafficLight> getTrafficLights();

    /**
     *
     * @return Set of the map's
     *          roads
     */
    Set<Road> getRoads();

    /**
     *
     * @return Set of the map's
     *          cross roads
     */
    Set<Crossroad> getCrossRoads();

    /**
     *
     * @return Set of the map's
     *          central locations
     */
    Set<CentralLocation> getCentralLocations();

    /**
     *
     * @return Set of the map's
     *          central locations
     */
    Set<Street> getStreets();
}
