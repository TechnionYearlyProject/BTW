package il.ac.technion.cs.yp.btw.db.DataObjects;

import java.sql.Time;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointAbstract;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.Weight;


/**
 * Point on a road map which represents
 * transition between two different Roads
 */
public class DataTrafficLight extends PointAbstract implements TrafficLight {

    private String mapName;
    private String sourceRoadId;
    private String destinationRoadId;
    private String overload;
    public DataTrafficLight(Point pos, String sourceRoadId, String destinationRoadId, String overload, String mapName) {
        super(pos);
        this.sourceRoadId = sourceRoadId;
        this.destinationRoadId = destinationRoadId;
        this.overload = overload;
        this.mapName = mapName;
    }

    /**
     * @return the TrafficLightImpl's unique name
     * in the format:
     * from:@getSourceRoad() to:@getDestinationRoad()
     */
    @Override
    public String getName(){return
            "from:"+getSourceRoad().getRoadName()
                    +" to:"+getDestinationRoad().getRoadName();
    }

    /**
     * @return the Road you got from to this
     *         TrafficLightImpl
     */
    @Override
    public Road getSourceRoad(){return null;}

    /**
     * @return the destination Road you
     *         go to from this TrafficLightImpl
     */
    @Override
    public Road getDestinationRoad(){return null;}

    /**
     * returns the right Weight for the given Time
     * @param time - Time we want to check the load
     *             on the traffic light at
     * @return Weight of this TrafficLightImpl according
     *         to the given Time
     */
    @Override
    public Weight getWeightByTime(Time time){return null;}

    /**
     * @return minimum possible Weight of TrafficLightImpl
     */
    @Override
    public Weight getMinimumWeight(){return null;}

    /**
     * @return current Weight on this TrafficLightImpl
     */
    @Override
    public Weight getCurrentWeight(){return null;}

    @Override
    public String toString(){
        String trafficLight = new String("");
        trafficLight += "traffic light: ";
        trafficLight += "sourceRoadId = " + sourceRoadId + " ";
        trafficLight += "destinationRoadId = " + destinationRoadId + " ";
        trafficLight += "overload = " + overload +" ";
        return trafficLight;
    }
}
