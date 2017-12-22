package il.ac.technion.cs.yp.btw.classes;

import java.sql.Time;

/**
 * Point on a road map which represents
 * transition between two different Roads
 */
public class TrafficLightImpl extends PointAbstract implements TrafficLight {
    private String sourceRoadId;
    private String destinationRoadId;
    private String overload;
    public TrafficLightImpl(Point pos, String sourceRoadId, String destinationRoadId, String overload) {
        super(pos);
        this.sourceRoadId = sourceRoadId;
        this.destinationRoadId = destinationRoadId;
        this.overload = overload;
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

    //@Override
    public String toStringTrafficLightFull() {
        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"Point\","+"\"coordinates\""+":"+
                "[["+this.getCoordinateX()+","+this.getCoordinateY()+"]},"+"\"properties\":{"+"\"name\":"+"\""+this.getName()+"\","+
                "\"overload\":"+"\""+this.getCurrentWeight()+"\"},";
    }

    //@Override
    public String toStringTrafficLight() {
        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"Point\","+"\"coordinates\""+":"+
                "[["+this.getCoordinateX()+","+this.getCoordinateY()+"]},"+"\"properties\":{"+"\"name\":"+"\""+this.getName()+"\"},";
    }
}


