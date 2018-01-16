package il.ac.technion.cs.yp.btw.trafficlights;

public interface TrafficLightManager {
    /**
     * progress all TrafficLights, and manage
     * the future of execution between Crossroads
     * @return self
     */
    TrafficLightManager tick();

}
