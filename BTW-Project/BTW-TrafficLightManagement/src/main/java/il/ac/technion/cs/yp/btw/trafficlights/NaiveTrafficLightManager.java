package il.ac.technion.cs.yp.btw.trafficlights;

import il.ac.technion.cs.yp.btw.citysimulation.CityCrossroad;

import java.util.Set;

public class NaiveTrafficLightManager implements TrafficLightManager {

    private Set<CityCrossroad> crossroads;

    public NaiveTrafficLightManager(Set<CityCrossroad> crossroads) {
        this.crossroads = crossroads;
    }

    /**
     * progress all TrafficLights, and manage
     * the future of execution between Crossroads
     *
     * @return self
     */
    @Override
    public TrafficLightManager tick() {
        return null;
    }
}
