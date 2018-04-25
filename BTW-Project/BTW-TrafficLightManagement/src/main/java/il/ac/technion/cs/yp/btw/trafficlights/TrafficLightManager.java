package il.ac.technion.cs.yp.btw.trafficlights;

import il.ac.technion.cs.yp.btw.citysimulation.CityCrossroad;

import java.util.Set;

/**
 * @author Adam Elgressy and Guy Rephaeli
 * @Date 20-1-2018
 * interface for TrafficLight management
 */
public interface TrafficLightManager {
    /**
     * @author Adam Elgressy and Guy Rephaeli
     * @Date 25-4-2018
     *
     * Insert the crossroads to manage
     * @param crossroads - the crossroads to manage
     * @return self
     */
    TrafficLightManager insertCrossroads(Set<CityCrossroad> crossroads);

    /**
     * @author Adam Elgressy and Guy Rephaeli
     * @Date 20-1-2018
     * progress all TrafficLights, and manage
     * the future of execution between Crossroads
     * @return self
     */
    TrafficLightManager tick();

}
