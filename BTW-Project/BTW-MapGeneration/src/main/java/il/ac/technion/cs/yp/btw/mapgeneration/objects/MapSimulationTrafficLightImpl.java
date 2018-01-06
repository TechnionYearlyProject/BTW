package il.ac.technion.cs.yp.btw.mapgeneration.objects;

import il.ac.technion.cs.yp.btw.classes.*;

/**
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
     * @return the Road you got from to this
     *         MapSimulationTrafficLightImpl
     */
    @Override
    public Road getSourceRoad(){return this.sourceRoad;}

    /**
     * @return the destination Road you
     *         go to from this MapSimulationTrafficLightImpl
     */
    @Override
    public Road getDestinationRoad(){return this.destinationRoad;}

    /**
     * returns the right Weight for the given Time
     * @param time - Time we want to check the load
     *             on the traffic light at
     * @return Weight of this MapSimulationTrafficLightImpl according
     *         to the given Time
     */
    @Override
    public BTWWeight getWeightByTime(BTWTime time){return null;}

    /**
     * @return minimum possible Weight of MapSimulationTrafficLightImpl
     */
    @Override
    public BTWWeight getMinimumWeight(){return null;}

    /**
     * @return current Weight on this MapSimulationTrafficLightImpl
     */
    @Override
    public BTWWeight getCurrentWeight(){return null;}

}
