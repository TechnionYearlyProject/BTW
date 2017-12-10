package il.ac.technion.cs.yp.btw.classes;

import java.sql.Time;

/**
 * default implementation for the interface Road
 */
public class RoadImpl implements Road {
    private String name;
    private int roadLength;
    private Street myStreet;
    private Crossroad sourceCrossroad;
    private Crossroad destinationCrossroad;

    public RoadImpl(String name, int roadLength,
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
     * returns true if the given street number is part
     * of this road, false otherwise
     *
     * @param streetNumber - the street number we check
     * @return true if it is inside the road's range
     * false o.w.
     */
    @Override
    public boolean isStreetNumberInRange(int streetNumber) {
        return true;//TODO
    }

    /**
     * @return the length in meters of the road
     */
    @Override
    public int getRoadLength() {
        return this.roadLength;
    }

    /**
     * @return the unique name of the road
     */
    @Override
    public String getRoadName() {
        return this.name;
    }

    /**
     * @return the Street this Road is in
     */
    @Override
    public Street getStreet() {
        return this.myStreet;
    }

    /**
     * returns the right Weight for the given Time
     *
     * @param time - Time we want to check the load
     *             on the road at
     * @return Weight of this Road according
     * to the given Time
     */
    @Override
    public Weight getWeightByTime(Time time) {
        return null;//TODO
    }

    /**
     * @return the Crossroad this Road starts in
     */
    @Override
    public Crossroad getSourceCrossroad() {
        return this.sourceCrossroad;
    }

    /**
     * @return the Crossroad this Road ends
     */
    @Override
    public Crossroad getDestinationCrossroad() {
        return this.destinationCrossroad;
    }
}