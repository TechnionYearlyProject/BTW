package il.ac.technion.cs.yp.btw.trafficlights;

import il.ac.technion.cs.yp.btw.citysimulation.CityCrossroad;
import il.ac.technion.cs.yp.btw.citysimulation.CityTrafficLight;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Adam Elgressy and Guy Rephaeli
 * @Date 20-1-2018
 * Naive implementation of TrafficLightManager
 */

public class NaiveTrafficLightManager implements TrafficLightManager {

    private Set<CityCrossroad> crossroads;
    private Map<CityCrossroad, List<HashSet<CityTrafficLight>>> trafficLightsOfCrossroadByRoad;
    private Map<CityCrossroad, HashSet<CityTrafficLight>> currentGreenTrafficLightsOfCrossroad;
    private Map<CityCrossroad, Iterator<HashSet<CityTrafficLight>>> currentIteratorOfCrossroad;
    private int minimumOpenTime;
    private int count;

    public NaiveTrafficLightManager() {
        this.crossroads = new HashSet<>();
        this.trafficLightsOfCrossroadByRoad = new HashMap<>();
        this.currentGreenTrafficLightsOfCrossroad = new HashMap<>();
        this.currentIteratorOfCrossroad = new HashMap<>();
        this.minimumOpenTime = -1;
        this.count = 0;
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
        this.currentGreenTrafficLightsOfCrossroad.get(crossroad)
                .forEach(trafficLight -> trafficLight.setTrafficLightState(CityTrafficLight.TrafficLightState.RED));
        Iterator<HashSet<CityTrafficLight>> iter = currentIteratorOfCrossroad.get(crossroad);
        if (! iter.hasNext()) {
            this.currentIteratorOfCrossroad.put(crossroad, this.trafficLightsOfCrossroadByRoad.get(crossroad).iterator());
        }
        this.currentGreenTrafficLightsOfCrossroad.put(crossroad, this.currentIteratorOfCrossroad.get(crossroad).next());
        this.currentGreenTrafficLightsOfCrossroad.get(crossroad)
                .forEach(trafficLight -> trafficLight.setTrafficLightState(CityTrafficLight.TrafficLightState.GREEN));
        return this;
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
                .getAsInt() + 1; // may throw - need to catch

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
     * @author Adam Elgressy and Guy Rephaeli
     * @Date 20-1-2018
     * progress all TrafficLights, and manage
     * the future of execution between Crossroads
     *
     * @return self
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
