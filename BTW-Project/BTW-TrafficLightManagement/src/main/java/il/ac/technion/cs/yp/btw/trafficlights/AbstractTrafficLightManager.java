package il.ac.technion.cs.yp.btw.trafficlights;

import il.ac.technion.cs.yp.btw.citysimulation.CityCrossroad;
import il.ac.technion.cs.yp.btw.citysimulation.CityTrafficLight;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Adam Elgressy
 * @Date 01-6-2018
 */
public abstract class AbstractTrafficLightManager implements TrafficLightManager{
    final static Logger logger = Logger.getLogger(AbstractTrafficLightManager.class);

    protected Set<CityCrossroad> crossroads;
    protected Map<CityCrossroad, List<HashSet<CityTrafficLight>>> trafficLightsOfCrossroadByRoad;
    protected Map<CityCrossroad, HashSet<CityTrafficLight>> currentGreenTrafficLightsOfCrossroad;
    protected Map<CityCrossroad, Iterator<HashSet<CityTrafficLight>>> currentIteratorOfCrossroad;
    protected int minimumOpenTime;
    protected int count;

    /**
     * @author Adam Elgressy
     * @Date 25-4-2018
     */
    public AbstractTrafficLightManager() {
        this.crossroads = new HashSet<>();
        this.trafficLightsOfCrossroadByRoad = new HashMap<>();
        this.currentGreenTrafficLightsOfCrossroad = new HashMap<>();
        this.currentIteratorOfCrossroad = new HashMap<>();
        this.minimumOpenTime = 10;
        this.count = 0;
    }
    /**
     * @author Adam Elgressy and Guy Rephaeli
     * @Date 25-4-2018
     *
     * Insert the crossroads to manage
     * @param crossroads - the crossroads to manage
     * @return self
     */
    @Override
    public TrafficLightManager insertCrossroads(Set<CityCrossroad> crossroads) {
        logger.debug("Start initializing manager with crossroads");
        this.crossroads = crossroads;

        logger.debug("\t1. Initializing traffic-lights");
        this.trafficLightsOfCrossroadByRoad = crossroads
                .stream()
                .map(crossroad -> new Pair<>(crossroad, crossroad.getRealTrafficLights()
                        .stream()
                        .filter(trafficLight -> trafficLight.getSourceRoad() != null)
                        .collect(Collectors.groupingBy(TrafficLight::getSourceRoad))
                        .values()
                        .stream()
                        .map(HashSet::new)
                        .collect(Collectors.toList())
                ))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

        logger.debug("\t2. Initializing traffic-light iterators per crossroad");
        this.currentIteratorOfCrossroad = trafficLightsOfCrossroadByRoad.entrySet()
                .stream()
                .map(e -> new Pair<>(e.getKey(), e.getValue().iterator()))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

        logger.debug("\t3. Initializing minimum-open-time");
        this.minimumOpenTime = crossroads.stream()
                .flatMap(crossroad -> crossroad.getRealTrafficLights().stream())
                .mapToInt(CityTrafficLight::getMinimumOpenTime)
                .max()
                .getAsInt() + 1; // may throw - need to catch

        logger.debug("\t4. Initializing green traffic-lights");
        this.currentGreenTrafficLightsOfCrossroad = this.currentIteratorOfCrossroad.entrySet()
                .stream()
                .map(e -> new Pair<>(e.getKey(), trafficLightsOfCrossroadByRoad.get(e.getKey()).isEmpty()
                        ? new HashSet<CityTrafficLight>()
                        : e.getValue().next()))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

        logger.debug("\t5. Setting green traffic-lights");
        this.crossroads.forEach(crossroad ->
                this.currentGreenTrafficLightsOfCrossroad.get(crossroad)
                        .forEach(trafficLight ->
                                trafficLight.setTrafficLightState(CityTrafficLight.TrafficLightState.GREEN)));
        logger.debug("Manager initialized successfully");
        return this;
    }


}
