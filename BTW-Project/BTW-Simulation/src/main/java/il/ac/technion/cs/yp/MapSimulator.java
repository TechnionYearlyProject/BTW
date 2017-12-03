package il.ac.technion.cs.yp;

import java.util.Set;

/**
 * map simulation interface
 */
public interface MapSimulator {
    /**
     *
     * @return Set of the simulated map's
     *          traffic Lights
     */
    Set<TrafficLight> getTrafficLights();

    /**
     *
     * @return Set of the simulated map's
     *          roads
     */
    Set<Road> getRoads();

    /**
     *
     * @return Set of the simulated map's
     *          cross roads
     */
    Set<CrossRoad> getCrossRoads();

    /**
     *
     * @return Set of the simulated map's
     *          central locations
     */
    Set<CentralLocation> getCentralLocations();
}
