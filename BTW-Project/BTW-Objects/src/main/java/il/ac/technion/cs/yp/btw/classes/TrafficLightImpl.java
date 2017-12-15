package il.ac.technion.cs.yp.btw.classes;

import java.sql.Time;

/**
 * Point on a road map which represents
 * transition between two different Roads
 */
public class TrafficLightImpl extends PointAbstract implements TrafficLight {
    private Road sourceRoad;
    private Road destinationRoad;
    public TrafficLightImpl(Point pos, Road sourceRoad, Road destinationRoad) {
        super(pos);
        this.sourceRoad = sourceRoad;
        this.destinationRoad = destinationRoad;
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
    public Road getSourceRoad(){return this.sourceRoad;}

    /**
     * @return the destination Road you
     *         go to from this TrafficLightImpl
     */
    @Override
    public Road getDestinationRoad(){return this.destinationRoad;}

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
}
