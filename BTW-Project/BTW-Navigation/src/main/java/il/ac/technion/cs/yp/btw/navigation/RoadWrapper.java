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

    RoadWrapper(Road road, Road dst, Double dist, RoadWrapper parent) {
        this.road = road;
        this.dist = dist;
        this.heuristics = road.getHeuristicDist(dst).seconds();
        this.parent = parent;
        Crossroad destinationCrossroad = road.getDestinationCrossroad();
        if (destinationCrossroad == null) {
            this.distFromNeighbor = new HashMap<>();
        } else {
            this.distFromNeighbor = destinationCrossroad.getTrafficLightsFromRoad(road)
                    .stream()
                    .map(trafficLight -> new Pair<>(trafficLight.getDestinationRoad(),
                            trafficLight.getMinimumWeight().seconds()))
                    .collect(Collectors.toMap(Pair::getKey,
                            r -> r.getKey().getMinimumWeight().seconds() + r.getValue()));
        }
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
}
