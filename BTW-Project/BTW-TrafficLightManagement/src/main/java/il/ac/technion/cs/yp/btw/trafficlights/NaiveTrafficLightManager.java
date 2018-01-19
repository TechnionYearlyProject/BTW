package il.ac.technion.cs.yp.btw.trafficlights;

import il.ac.technion.cs.yp.btw.citysimulation.CityCrossroad;
import il.ac.technion.cs.yp.btw.citysimulation.CityTrafficLight;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Naive implementation of TrafficLightManager
 */

public class NaiveTrafficLightManager implements TrafficLightManager {

    private Set<CityCrossroad> crossroads;
    private Map<CityCrossroad, List<Set<CityTrafficLight>>> trafficLightsOfCrossroadByRoad;
    private Map<CityCrossroad, Set<CityTrafficLight>> currentGreenTrafficLightsOfCrossroad;
    private Map<CityCrossroad, Iterator<Set<CityTrafficLight>>> currentIteratorOfCrossroad;
    private int minimumOpenTime;
    private int count;

    public NaiveTrafficLightManager(Set<CityCrossroad> crossroads) {
        this.crossroads = crossroads;

        this.trafficLightsOfCrossroadByRoad = crossroads
                .stream()
                .map(crossroad -> new Pair<>(crossroad, crossroad.getRealTrafficLights()
                        .stream()
                        .collect(Collectors.groupingBy(TrafficLight::getSourceRoad))
                        .values()
                        .stream()
                        .map(l -> l.stream().collect(Collectors.toSet())) // cannot be convert although IntelliJ thinks it can
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
                .getAsInt(); // may throw - need to catch

        this.currentGreenTrafficLightsOfCrossroad = this.currentIteratorOfCrossroad.entrySet()
                .stream()
                .map(e -> new Pair<>(e.getKey(), e.getValue().next()))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

        this.crossroads.forEach(crossroad ->
                this.currentGreenTrafficLightsOfCrossroad.get(crossroad)
                        .forEach(trafficLight ->
                                trafficLight.setTrafficLightState(CityTrafficLight.TrafficLightState.GREEN)));

        this.count = 0;
    }

    /**
     * @param crossroad - the crossroad its traffic-lights are converted in a cyclic way: each set of traffic-lights is
     *                  turned to green for the minimal possible time, and then turns red so the next set could turn green
     * @return self
     */
    private NaiveTrafficLightManager turnToNextGreen(CityCrossroad crossroad) {
        this.currentGreenTrafficLightsOfCrossroad.get(crossroad)
                .forEach(trafficLight -> trafficLight.setTrafficLightState(CityTrafficLight.TrafficLightState.RED));
        Iterator<Set<CityTrafficLight>> iter = currentIteratorOfCrossroad.get(crossroad);
        if (! iter.hasNext()) {
            this.currentIteratorOfCrossroad.put(crossroad, this.trafficLightsOfCrossroadByRoad.get(crossroad).iterator());
        }
        this.currentGreenTrafficLightsOfCrossroad.put(crossroad, this.currentIteratorOfCrossroad.get(crossroad).next());
        this.currentGreenTrafficLightsOfCrossroad.get(crossroad)
                .forEach(trafficLight -> trafficLight.setTrafficLightState(CityTrafficLight.TrafficLightState.GREEN));
        return this;
    }

    /**
     * progress all TrafficLights, and manage
     * the future of execution between Crossroads
     *
     * @return self
     */
    @Override
    public TrafficLightManager tick() {
        if (this.count >= this.minimumOpenTime) {
            this.crossroads.forEach(this::turnToNextGreen);
            count = 0;
        }
        this.crossroads.forEach(CityCrossroad::tick);
        return this;
    }
}
