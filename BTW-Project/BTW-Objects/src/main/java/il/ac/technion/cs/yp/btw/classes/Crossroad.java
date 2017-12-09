package il.ac.technion.cs.yp.btw.classes;

import java.util.HashSet;
import java.util.Set;

/**
 * Point on a road map which represents
 * all TrafficLights located at the same point
 */
public class Crossroad extends PointAbstract {
    private Set<TrafficLight> trafficLights;
    public Crossroad(Point position) {
        this(position,new HashSet<TrafficLight>());
    }
    public Crossroad(Point position,
              Set<TrafficLight> trafficLights) {
        super(position);
        this.trafficLights = trafficLights;
    }

    /**
     * @return Set of all TrafficLights in this Crossroad
     */
    public Set<TrafficLight> getTrafficLights() {
        return this.trafficLights;
    }

    /**
     * Adds the given TrafficLight to this Crossroad
     * @param tl - the TrafficLight being added
     */
    public void addTrafficLight(TrafficLight tl){
        this.trafficLights.add(tl);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
