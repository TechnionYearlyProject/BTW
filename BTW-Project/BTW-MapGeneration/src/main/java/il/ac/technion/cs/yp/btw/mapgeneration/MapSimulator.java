package il.ac.technion.cs.yp.btw.mapgeneration;

import il.ac.technion.cs.yp.btw.classes.*;

import java.util.Set;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * Map simulation layer interface for the BTW project
 */
public interface MapSimulator {
    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of the simulated map's
     *          traffic Lights
     */
    Set<TrafficLight> getTrafficLights();

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of the simulated map's
     *          roads
     */
    Set<Road> getRoads();

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of the simulated map's
     *          cross roads
     */
    Set<Crossroad> getCrossRoads();

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of the simulated map's
     *          central locations
     */
    Set<CentralLocation> getCentralLocations();

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of the simulated map's
     *          central locations
     */
    Set<Street> getStreets();

    Map build();
}
