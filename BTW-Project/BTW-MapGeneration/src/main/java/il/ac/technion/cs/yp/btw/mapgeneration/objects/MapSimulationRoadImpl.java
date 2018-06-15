package il.ac.technion.cs.yp.btw.mapgeneration.objects;

import il.ac.technion.cs.yp.btw.classes.*;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * default implementation for the interface Road
 */
public class MapSimulationRoadImpl implements Road {
    private String name;
    private int roadLength;
    private Street myStreet;
    private Crossroad sourceCrossroad;
    private Crossroad destinationCrossroad;

    public MapSimulationRoadImpl(String name, int roadLength,
                                 Street myStreet,
                                 Crossroad sourceCrossroad,
                                 Crossroad destinationCrossroad){
        this.name = name;
        this.roadLength = roadLength;
        this.myStreet = myStreet;
        this.sourceCrossroad = sourceCrossroad;
        this.destinationCrossroad = destinationCrossroad;
    }
    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * returns true if the given street number is part
     * of this road, false otherwise
     * @param streetNumber - the street number we check
     * @return true if it is inside the road's range
     * false o.w.
     */
    @Override
    public boolean isStreetNumberInRange(int streetNumber) {
        return true;//TODO
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the length in meters of the road
     */
    @Override
    public int getRoadLength() {
        return this.roadLength;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the unique name of the road
     */
    @Override
    public String getRoadName() {
        return this.name;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the Street this Road is in
     */
    @Override
    public Street getStreet() {
        return this.myStreet;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * returns the right Weight for the given Time
     *
     * @param time - Time we want to check the load
     *             on the road at
     * @return Weight of this Road according
     * to the given Time
     */
    @Override
    public BTWWeight getWeightByTime(BTWTime time) {
        return null;//TODO
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return minimum possible Weight of Road
     */
    @Override
    public BTWWeight getMinimumWeight() {
        return null;//TODO
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @param road - the road to which the heuristic distance is calculated
     * @return minimum possible Weight of Road
     */
    @Override
    public BTWWeight getHeuristicDist(Road road) {
        return null;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the MapSimulationCrossroadImpl this Road starts in
     */
    @Override
    public Crossroad getSourceCrossroad() {
        return this.sourceCrossroad;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the MapSimulationCrossroadImpl this Road ends
     */
    @Override
    public Crossroad getDestinationCrossroad() {
        return this.destinationCrossroad;
    }

    /*
    * @Author Sharon Hadar
    * @Date 2/6/2018
    * @return the current speed on the road
    * */
    @Override
    public double getSpeed(){
        return 0.0;
    }

    /*
     * @Author Sharon Hadar
     * @Date 2/6/2018
     * @return the current overload on the road
     * */
    @Override
    public Double getOverload(){
        return 0.0;
    }
}

