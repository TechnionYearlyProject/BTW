package il.ac.technion.cs.yp.btw.trafficlights;

import il.ac.technion.cs.yp.btw.citysimulation.CityCrossroad;
import il.ac.technion.cs.yp.btw.citysimulation.CityTrafficLight;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Adam Elgressy and Guy Rephaeli
 * @Date 20-1-2018
 * Naive implementation of TrafficLightManager
 */

public class NaiveTrafficLightManager extends AbstractTrafficLightManager {
    final static Logger logger = Logger.getLogger(NaiveTrafficLightManager.class);


    public NaiveTrafficLightManager() {
        super();
    }


    /**
     * @author Guy Rephaeli
     *
     * @param crossroads - the crossroads in the live map
     */
    public NaiveTrafficLightManager(Set<CityCrossroad> crossroads) {
        this();
        this.insertCrossroads(crossroads);
    }

    /**
     * @author Guy Rephaeli
     * @Date 20-1-2018
     * @param crossroad - the crossroad its traffic-lights are converted in a cyclic way: each set of traffic-lights is
     *                  turned to green for the minimal possible time, and then turns red so the next set could turn green
     * @return self
     */
    private NaiveTrafficLightManager turnToNextGreen(CityCrossroad crossroad) {
        logger.debug("Turning traffic-lights to next green light in crossroad: " + crossroad.getName());
        this.currentGreenTrafficLightsOfCrossroad.get(crossroad)
                .forEach(trafficLight -> trafficLight.setTrafficLightState(CityTrafficLight.TrafficLightState.RED));
        Iterator<HashSet<CityTrafficLight>> iter = currentIteratorOfCrossroad.get(crossroad);
        if (! iter.hasNext()) {
            this.currentIteratorOfCrossroad.put(crossroad, this.trafficLightsOfCrossroadByRoad.get(crossroad).iterator());
        }
        this.currentGreenTrafficLightsOfCrossroad.put(crossroad, this.currentIteratorOfCrossroad.get(crossroad).next());
        this.currentGreenTrafficLightsOfCrossroad.get(crossroad)
                .forEach(trafficLight -> trafficLight.setTrafficLightState(CityTrafficLight.TrafficLightState.GREEN));
        logger.debug("Traffic-lights switched successfully");
        return this;
    }

    /**
     * @author Adam Elgressy and Guy Rephaeli
     * @Date 20-1-2018
     * progress all TrafficLights, and manage
     * the future of execution between Crossroads
     *
     * @return self
     */
    @Override
    public TrafficLightManager tick() {
        logger.debug("Tick...");
        count++;
        if (this.count >= this.minimumOpenTime) {
            this.crossroads.forEach(this::turnToNextGreen);
            count = 0;
        }
        this.crossroads.forEach(CityCrossroad::tick);
        return this;
    }
}
