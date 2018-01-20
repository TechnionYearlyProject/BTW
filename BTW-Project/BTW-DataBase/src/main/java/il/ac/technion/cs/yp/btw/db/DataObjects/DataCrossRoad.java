package il.ac.technion.cs.yp.btw.db.DataObjects;

import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.db.TrafficLightsDataBase;
/**
 * Point on a road map which represents
 * all TrafficLights located at the same point
 */
public class DataCrossRoad extends PointAbstract implements Crossroad {

    private String mapName;
    private Set<TrafficLight> trafficLights;
    private String name;
    public DataCrossRoad(Point position, String mapName) {
        super(position);
        this.trafficLights = new HashSet<>();
        this.mapName = mapName;
    }
    public DataCrossRoad(Point position, Set<TrafficLight> trafficLights, String mapName) {
        super(position);
        this.trafficLights = trafficLights;
        this.mapName = mapName;
    }
    public DataCrossRoad(Point position, Set<TrafficLight> trafficLights, String crossname, String mapName) {
        super(position);
        this.trafficLights = trafficLights;
        this.mapName = mapName;
        this.name = crossname;
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @return Set of all TrafficLights in this CrossroadImpl
     */
    @Override
    public Set<TrafficLight> getTrafficLights() {
        return this.trafficLights;
    }

    /**
     * get all traficlights in the crossroad which are going from road
     * @param road - the Road from which the TrafficLights are going
     * @return all trafic lights from the road
     */
    @Override
    public Set<TrafficLight> getTrafficLightsFromRoad(Road road) {
        Set<TrafficLight> trafficLights = new HashSet<>();
        for (TrafficLight currTraficLight: this.trafficLights) {
            if (currTraficLight.getSourceRoad().getRoadName().equals(road.getRoadName()))
                trafficLights.add(currTraficLight);
        }
        return trafficLights;
    }

    /**
     * Adds the given TrafficLightImpl to this CrossroadImpl
     * @param tl - the TrafficLightImpl being added
     */
    @Override
    public Crossroad addTrafficLight(TrafficLight tl){
        this.trafficLights.add(tl);
        return this;
    }

    @Override
    public String toString() {

        String crossRoad = "";
        crossRoad += "cross road: ";
        crossRoad += "position = (" + super.getCoordinateX() + "," + super.getCoordinateY() + ")\n";
        for (TrafficLight trafficLight : trafficLights) {
            crossRoad += "\t" + trafficLight.toString() + "\n";
        }
        return crossRoad;
    }

    @Override
    public boolean equals (Object o){
        if(!(o instanceof DataCrossRoad))
            return false;
        DataCrossRoad otherCrossRoad = (DataCrossRoad)o;
        return (this.getCoordinateX() == otherCrossRoad.getCoordinateX())&&(this.getCoordinateY() == otherCrossRoad.getCoordinateY());
    }

}
