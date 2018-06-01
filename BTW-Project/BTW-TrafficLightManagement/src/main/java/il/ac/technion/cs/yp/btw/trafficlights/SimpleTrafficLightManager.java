package il.ac.technion.cs.yp.btw.trafficlights;

import il.ac.technion.cs.yp.btw.citysimulation.CityCrossroad;
import il.ac.technion.cs.yp.btw.citysimulation.CityTrafficLight;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Adam Elgressy
 * @Date 21-4-2018
 * Simple implementation of TrafficLightManager:
 * a simple 'smart' algorithm to control traffic lights in our city, per crossroad, as follows:
 * If there are no cars in the crossroad, all traffic lights are red.
 * if only one traffic light has cars waiting, it will be green non-stop.
 * if multiple traffic lights have cars waiting, use constant intervals between them
 */
public class SimpleTrafficLightManager implements TrafficLightManager {
    private Set<CityCrossroad> crossroads;
    private Map<CityCrossroad, List<HashSet<CityTrafficLight>>> trafficLightsOfCrossroadByRoad;
    private Map<CityCrossroad, HashSet<CityTrafficLight>> currentGreenTrafficLightsOfCrossroad;
    private Map<CityCrossroad, Iterator<HashSet<CityTrafficLight>>> currentIteratorOfCrossroad;
    private int minimumOpenTime;
    private int count;
    /**
     * @author Adam Elgressy
     * @Date 25-4-2018
     */
    public SimpleTrafficLightManager() {
        this.crossroads = new HashSet<>();
        this.trafficLightsOfCrossroadByRoad = new HashMap<>();
        this.currentGreenTrafficLightsOfCrossroad = new HashMap<>();
        this.currentIteratorOfCrossroad = new HashMap<>();
        this.minimumOpenTime = 10;
        this.count = 0;
    }
    /**
     * @author Adam Elgressy
     * @Date 21-4-2018
     *
     * @param crossroads - the crossroads in the live map
     */
    public SimpleTrafficLightManager(Set<CityCrossroad> crossroads) {
        this();
        this.insertCrossroads(crossroads);
    }
    /**
     * @author Adam Elgressy
     * @Date 21-4-2018
     * @param crossroad - the crossroad its traffic-lights are converted in a cyclic way: each set of traffic-lights is
     *                  turned to green for the minimal possible time, and then turns red so the next set could turn green
     * @return self
     */
    private SimpleTrafficLightManager turnToNextGreen(CityCrossroad crossroad) {
        this.currentGreenTrafficLightsOfCrossroad.get(crossroad)
                .forEach(trafficLight -> trafficLight.setTrafficLightState(CityTrafficLight.TrafficLightState.RED));
        Iterator<HashSet<CityTrafficLight>> iter = currentIteratorOfCrossroad.get(crossroad);
        if (!iter.hasNext()) {
            this.currentIteratorOfCrossroad.put(crossroad, this.trafficLightsOfCrossroadByRoad.get(crossroad).iterator());
        }
        HashSet<CityTrafficLight> k = this.currentIteratorOfCrossroad.get(crossroad).next();
        if(k.stream().anyMatch(cityTrafficLight -> cityTrafficLight.getNumOfWaitingVehiclesInTrafficLights()>0)){
            this.currentGreenTrafficLightsOfCrossroad.put(crossroad,k);
        }
        this.currentGreenTrafficLightsOfCrossroad.get(crossroad)
                .forEach(trafficLight -> trafficLight.setTrafficLightState(CityTrafficLight.TrafficLightState.GREEN));
        return this;
    }

    /**
     * @param crossroads - the crossroads to manage
     * @return self
     * @author Adam Elgressy
     * @Date 25-4-2018
     * Insert the crossroads to manage
     */
    @Override
    public TrafficLightManager insertCrossroads(Set<CityCrossroad> crossroads) {
        this.crossroads = crossroads;

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

        this.currentIteratorOfCrossroad = trafficLightsOfCrossroadByRoad.entrySet()
                .stream()
                .map(e -> new Pair<>(e.getKey(), e.getValue().iterator()))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

        this.minimumOpenTime = crossroads.stream()
                .flatMap(crossroad -> crossroad.getRealTrafficLights().stream())
                .mapToInt(CityTrafficLight::getMinimumOpenTime)
                .max()
                .getAsInt() + 2 - 1; // may throw - need to catch

        this.currentGreenTrafficLightsOfCrossroad = this.currentIteratorOfCrossroad.entrySet()
                .stream()
                .map(e -> new Pair<>(e.getKey(), trafficLightsOfCrossroadByRoad.get(e.getKey()).isEmpty()
                        ? new HashSet<CityTrafficLight>()
                        : e.getValue().next()))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

        this.crossroads.forEach(crossroad ->
                this.currentGreenTrafficLightsOfCrossroad.get(crossroad)
                        .forEach(trafficLight ->
                                trafficLight.setTrafficLightState(CityTrafficLight.TrafficLightState.GREEN)));
        return this;
    }

    /**
     * @return self
     * @author Adam Elgressy
     * @Date 21-4-2018
     * progress all TrafficLights, and manage
     * the future of execution between Crossroads
     */
    @Override
    public TrafficLightManager tick() {
        count++;
        if (this.count >= this.minimumOpenTime) {
            this.crossroads.forEach(this::turnToNextGreen);
            count = 0;
        }
        this.crossroads.forEach(CityCrossroad::tick);
        return this;
    }
}
