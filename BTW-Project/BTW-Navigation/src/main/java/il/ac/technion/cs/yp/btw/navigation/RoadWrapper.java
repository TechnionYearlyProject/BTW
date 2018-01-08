package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.classes.Road;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A wrapper for the Road class used for Priority Queue
 */
class RoadWrapper implements Comparable<RoadWrapper>{
    double dist;
    private long heuristics;
    private Road road;
    private final Map<Road, Long> distFromNeighbor;
    private RoadWrapper parent;

    private RoadWrapper(Road road, Road dst, Double dist, RoadWrapper parent, Double sourceRoadRatio) {
        this.road = road;
        this.dist = dist;
        this.heuristics = road.getHeuristicDist(dst).seconds();
        this.parent = parent;
        Crossroad destinationCrossroad = road.getDestinationCrossroad();
        long roadWeight = (long) Math.ceil((1.0 - sourceRoadRatio) * (double)(this.road.getMinimumWeight().seconds()));
        if (destinationCrossroad == null) {
            this.distFromNeighbor = new HashMap<>();
        } else {
            this.distFromNeighbor = destinationCrossroad.getTrafficLightsFromRoad(road)
                    .stream()
                    .map(trafficLight -> new Pair<>(trafficLight.getDestinationRoad(),
                            trafficLight.getMinimumWeight().seconds()))
                    .collect(Collectors.toMap(Pair::getKey,
                            r -> r.getValue() + roadWeight));
        }
    }

    static RoadWrapper buildSourceRoad(Road road, Road dst, Double sourceRoadRatio){
        return new RoadWrapper(road, dst, 0.0, null, sourceRoadRatio);
    }

    static RoadWrapper buildRouteRoad(Road road, Road dst, Double dist, RoadWrapper parent) {
        return new RoadWrapper(road, dst, dist, parent, 0.0);
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
