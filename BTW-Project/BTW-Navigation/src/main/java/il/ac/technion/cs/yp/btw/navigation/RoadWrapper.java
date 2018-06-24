package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.BTWTime;
import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.classes.Road;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Guy Rephaeli
 *
 * A wrapper for the Road class used for Priority Queue
 */
class RoadWrapper implements Comparable<RoadWrapper> {
    double dist;
    private long heuristics;
    private Road road;
    private final Map<Road, Long> distFromNeighbor;
    private RoadWrapper parent;
    private BTWTime time;

    private RoadWrapper(Road road, Road dst, Double dist, RoadWrapper parent, Double sourceRoadRatio, BTWTime time) {
        this.road = road;
        this.dist = dist;
        this.heuristics = road.getHeuristicDist(dst).seconds();
        this.parent = parent;
        this.time = time;
        Crossroad destinationCrossroad = road.getDestinationCrossroad();
        long roadWeight = (long) Math.ceil((1.0 - sourceRoadRatio) * (double)(this.road.getWeightByTime(this.time).seconds()));
        if (destinationCrossroad == null) {
            this.distFromNeighbor = new HashMap<>();
        } else {
            this.distFromNeighbor = destinationCrossroad.getTrafficLightsFromRoad(road)
                    .stream()
                    .map(trafficLight -> new Pair<>(trafficLight.getDestinationRoad(),
                            trafficLight.getWeightByTime(this.time).seconds()))
                    .collect(Collectors.toMap(Pair::getKey,
                            r -> r.getValue() + roadWeight));
        }
    }

    static RoadWrapper buildSourceRoad(Road road, Road dst, Double sourceRoadRatio, BTWTime time){
        return new RoadWrapper(road, dst, 0.0, null, sourceRoadRatio, time);
    }

    static RoadWrapper buildRouteRoad(Road road, Road dst, Double dist, RoadWrapper parent, BTWTime time) {
        return new RoadWrapper(road, dst, dist, parent, 0.0, time);
    }

    Road getRoad() {
        return this.road;
    }

    RoadWrapper getParent() {
        return this.parent;
    }

    Set<Road> getNeighbors() {
        return this.distFromNeighbor.keySet();
    }

    double getDistFromNeighbor(Road neighbor) {
        return this.distFromNeighbor.get(neighbor);
    }

    @Override
    public int compareTo(RoadWrapper o) {
        Double sign = Math.signum((this.dist + this.heuristics) - (o.dist + o.heuristics));
        return sign.intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (! (o instanceof RoadWrapper)) {
            return false;
        }
        RoadWrapper wrapper = (RoadWrapper)o;
        return this.road.equals(wrapper.road);
    }

    @Override
    public int hashCode() {
        return this.road.hashCode();
    }
}
