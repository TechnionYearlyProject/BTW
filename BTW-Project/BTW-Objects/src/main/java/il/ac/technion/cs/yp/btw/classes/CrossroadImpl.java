package il.ac.technion.cs.yp.btw.classes;

import java.util.HashSet;
import java.util.Set;
import java.lang.Long;
/**
 * Point on a road map which represents
 * all TrafficLights located at the same point
 */
public class CrossroadImpl extends PointAbstract implements Crossroad {

    private String id;
    private Set<TrafficLight> trafficLights;
    public CrossroadImpl(Point position) {
        this(position,new HashSet<TrafficLight>());
        this.id = "(" + position.getCoordinateX() + "," + position.getCoordinateY() + ")" ;
    }
    public CrossroadImpl(Point position,
                         Set<TrafficLight> trafficLights) {
        super(position);
        this.trafficLights = trafficLights;
        this.id = "(" + position.getCoordinateX() + "," + position.getCoordinateY() + ")" ;
    }

    /**
     * @return Set of all TrafficLights in this CrossroadImpl
     */
    @Override
    public Set<TrafficLight> getTrafficLights() {
        return this.trafficLights;
    }

    /**
     * Adds the given TrafficLightImpl to this CrossroadImpl
     * @param tl - the TrafficLightImpl being added
     */
    @Override
    public void addTrafficLight(TrafficLight tl){
        this.trafficLights.add(tl);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
