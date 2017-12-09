package il.ac.technion.cs.yp.btw.mapsimulation;

import il.ac.technion.cs.yp.btw.classes.*;

import java.util.Set;

/**
 * Map simulation layer interface for the BTW project
 */
public interface MapSimulator {
    /**
     *
     * @return Set of the simulated map's
     *          traffic Lights
     */
    Set<il.ac.technion.cs.yp.TrafficLight> getTrafficLights();

    /**
     *
     * @return Set of the simulated map's
     *          roads
     */
    Set<il.ac.technion.cs.yp.Road> getRoads();

    /**
     *
     * @return Set of the simulated map's
     *          cross roads
     */
    Set<Crossroad> getCrossRoads();

    /**
     *
     * @return Set of the simulated map's
     *          central locations
     */
    Set<il.ac.technion.cs.yp.CentralLocation> getCentralLocations();

    /**
     *
     * @return Set of the simulated map's
     *          central locations
     */
    Set<Street> getStreets();
}
