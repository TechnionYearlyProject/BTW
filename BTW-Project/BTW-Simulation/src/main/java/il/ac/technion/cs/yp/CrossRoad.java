package il.ac.technion.cs.yp;

import java.util.HashSet;
import java.util.Set;

/**
 * cross road object representation in the
 * map simulation
 */
public class CrossRoad extends Point {

    private Set<TrafficLight> trafficLights;
    CrossRoad(int coordinateX, int coordinateY) {
        this(coordinateX,coordinateY,new HashSet<TrafficLight>());
    }
    CrossRoad(int coordinateX, int coordinateY,
              Set<TrafficLight> trafficLights) {
        super(coordinateX, coordinateY);
        this.trafficLights = trafficLights;
    }

    public Set<TrafficLight> getTrafficLights() {
        return trafficLights;
    }

    void addTrafficLight(TrafficLight tl){
        this.trafficLights.add(tl);
    }
}
