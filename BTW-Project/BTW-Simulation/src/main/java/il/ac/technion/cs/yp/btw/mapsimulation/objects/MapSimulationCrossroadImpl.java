package il.ac.technion.cs.yp.btw.mapsimulation.objects;

import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointAbstract;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;

import java.util.HashSet;
import java.util.Set;

/**
 * Point on a road map which represents
 * all TrafficLights located at the same point
 */
public class MapSimulationCrossroadImpl extends PointAbstract implements Crossroad {
    private Set<TrafficLight> trafficLights;
    public MapSimulationCrossroadImpl(Point position) {
        this(position,new HashSet<TrafficLight>());
    }
    public MapSimulationCrossroadImpl(Point position,
                                      Set<TrafficLight> trafficLights) {
        super(position);
        this.trafficLights = trafficLights;
    }

    /**
     * @return Set of all TrafficLights in this MapSimulationCrossroadImpl
     */
    @Override
    public Set<TrafficLight> getTrafficLights() {
        return this.trafficLights;
    }

    /**
     * Adds the given MapSimulationTrafficLightImpl to this MapSimulationCrossroadImpl
     * @param tl - the MapSimulationTrafficLightImpl being added
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
