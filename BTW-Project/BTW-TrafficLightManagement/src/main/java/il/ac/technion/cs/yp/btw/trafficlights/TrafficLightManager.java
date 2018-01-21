package il.ac.technion.cs.yp.btw.trafficlights;

/**
 * @author Adam Elgressy and Guy Rephaeli
 * @Date 20-1-2018
 * interface for TrafficLight management
 */
public interface TrafficLightManager {
    /**
     * @author Guy Rephaeli and Adam Elgressy
     *
     * progress all TrafficLights, and manage
     * the future of execution between Crossroads
     * @return self
     */
    TrafficLightManager tick();

}
