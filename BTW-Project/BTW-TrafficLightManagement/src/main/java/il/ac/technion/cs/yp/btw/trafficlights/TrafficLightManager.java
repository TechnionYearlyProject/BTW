package il.ac.technion.cs.yp.btw.trafficlights;


/**
 * @author Guy Rephaeli and Adam Elgressy
 *
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
