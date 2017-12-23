package il.ac.technion.cs.yp.btw.db.DataObjects;

import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointAbstract;
import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.db.TrafficLightsDataBase;
/**
 * Point on a road map which represents
 * all TrafficLights located at the same point
 */
public class DataCrossRoad extends PointAbstract implements Crossroad {

    private String mapName;
    private Set<TrafficLight> trafficLights;
    public DataCrossRoad(Point position, String mapName) {
        super(position);
        TrafficLightsDataBase trafficLightsDataBase = new TrafficLightsDataBase(mapName);
        this.trafficLights = trafficLightsDataBase.getAllTrafficLights(position);
        this.mapName = mapName;
    }
    public DataCrossRoad(Point position, Set<TrafficLight> trafficLights, String mapName) {
        super(position);
        this.trafficLights = trafficLights;
        this.mapName = mapName;
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

        String crossRoad = new String("");
        crossRoad += "cross road: ";
        crossRoad += "position = (" + super.getCoordinateX() + "," + super.getCoordinateY() + ")\n";
        Iterator<TrafficLight> iterator = trafficLights.iterator();
        while(iterator.hasNext()){
            crossRoad+= "\t" + iterator.next().toString() + "\n";
        }
        return crossRoad;
    }

}
