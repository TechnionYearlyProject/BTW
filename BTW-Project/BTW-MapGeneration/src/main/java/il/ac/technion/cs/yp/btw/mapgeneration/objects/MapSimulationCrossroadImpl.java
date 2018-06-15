package il.ac.technion.cs.yp.btw.mapgeneration.objects;

import il.ac.technion.cs.yp.btw.classes.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * Point on a road map which represents
 * all TrafficLights located at the same point
 */
public class MapSimulationCrossroadImpl extends PointAbstract implements Crossroad {
    private Set<TrafficLight> trafficLights;
    private String name;
    public MapSimulationCrossroadImpl(Point position) {
        this(position,new HashSet<>());
    }
    public MapSimulationCrossroadImpl(Point position,
                                      Set<TrafficLight> trafficLights) {
        super(position);
        this.trafficLights = trafficLights;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of all TrafficLights in this MapSimulationCrossroadImpl
     */
    @Override
    public Set<TrafficLight> getTrafficLights() {
        return this.trafficLights;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @param road - the Road from which the TrafficLights are going
     * @return Set of all TrafficLights from a specific road
     */
    @Override
    public Set<TrafficLight> getTrafficLightsFromRoad(Road road) {
        return null;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * Adds the given MapSimulationTrafficLightImpl to this MapSimulationCrossroadImpl
     * @param tl - the MapSimulationTrafficLightImpl being added
     * @return self
     */
    @Override
    public Crossroad addTrafficLight(TrafficLight tl){
        this.trafficLights.add(tl);
        StringBuilder stb = new StringBuilder();
        this.trafficLights.stream().forEachOrdered(trafficLight -> stb.append(trafficLight.getName()));
        this.name = stb.toString();
        return this;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return this Crossroad's name
     */
    public String getName(){
    return this.name;
    }

    @Override
    public Double getOverload(){
        return 0.0;
    }
}
