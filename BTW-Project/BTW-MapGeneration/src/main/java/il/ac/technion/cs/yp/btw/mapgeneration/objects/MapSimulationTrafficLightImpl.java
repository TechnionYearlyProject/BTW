package il.ac.technion.cs.yp.btw.mapgeneration.objects;

import il.ac.technion.cs.yp.btw.classes.*;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * Point on a road map which represents
 * transition between two different Roads
 */
public class MapSimulationTrafficLightImpl extends PointAbstract implements TrafficLight {
    private Road sourceRoad;
    private Road destinationRoad;
    public MapSimulationTrafficLightImpl(Point pos, Road sourceRoad, Road destinationRoad) {
        super(pos);
        this.sourceRoad = sourceRoad;
        this.destinationRoad = destinationRoad;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the MapSimulationTrafficLightImpl's unique name
     * in the format:
     * from:@getSourceRoad() to:@getDestinationRoad()
     */
    @Override
    public String getName(){return
            "from:"+getSourceRoad().getRoadName()
                    +" to:"+getDestinationRoad().getRoadName();
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the Road you got from to this
     *         MapSimulationTrafficLightImpl
     */
    @Override
    public Road getSourceRoad(){return this.sourceRoad;}

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the destination Road you
     *         go to from this MapSimulationTrafficLightImpl
     */
    @Override
    public Road getDestinationRoad(){return this.destinationRoad;}

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * returns the right Weight for the given Time
     * @param time - Time we want to check the load
     *             on the traffic light at
     * @return Weight of this MapSimulationTrafficLightImpl according
     *         to the given Time
     */
    @Override
    public BTWWeight getWeightByTime(BTWTime time){return null;}

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return minimum possible Weight of MapSimulationTrafficLightImpl
     */
    @Override
    public BTWWeight getMinimumWeight(){return null;}
}
