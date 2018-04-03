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
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * construct cross road with an empty set of traffic lights*/
    public DataCrossRoad(Point position, String mapName) {
        super(position);
        this.trafficLights = new HashSet<>();
        this.mapName = mapName;
    }

    /*@Author Sharon Hadar
     *@Date 02/04/2018
     * construct cross road with an empty set of traffic lights and a received name
     */
    public DataCrossRoad(Point position, String name, String mapName){
        this(position, mapName);
        this.name = name;
    }


    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * construct the cross road with a pre given set of traffic lights but no name*/
    public DataCrossRoad(Point position, Set<TrafficLight> trafficLights, String mapName) {
        super(position);
        this.trafficLights = trafficLights;
        this.mapName = mapName;
    }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * construct the cross roads with a given set of traffic light and a name*/
    public DataCrossRoad(Point position, Set<TrafficLight> trafficLights, String crossname, String mapName) {
        super(position);
        this.trafficLights = trafficLights;
        this.mapName = mapName;
        this.name = crossname;
    }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * construct a cross road with an empty set of traffic lights*/
    public DataCrossRoad(Point position, boolean noTrafficLights, String mapName) {
        super(position);
        this.trafficLights = new HashSet<>();
        this.mapName = mapName;
    }


    /* @author Sharon Hadar
     * @Date 21/01/2018
     * get the cross road name*/
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @author Sharon Hadar
     * @Date 21/01/2018
     * @return Set of all TrafficLights in this CrossroadImpl
     */
    @Override
    public Set<TrafficLight> getTrafficLights() {
        return this.trafficLights;
    }

    /**
     *
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

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * a string representing the cross road*/
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

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * compare cross roads by coordinates*/
    @Override
    public boolean equals (Object o){
        if(!(o instanceof DataCrossRoad))
            return false;
        DataCrossRoad otherCrossRoad = (DataCrossRoad)o;
        return (this.getCoordinateX() == otherCrossRoad.getCoordinateX())&&(this.getCoordinateY() == otherCrossRoad.getCoordinateY());
    }

    @Override
    public int hashCode() {
        return Double.valueOf(this.getCoordinateX()).hashCode() * 31 + Double.valueOf(this.getCoordinateY()).hashCode() * 17;
    }
}
