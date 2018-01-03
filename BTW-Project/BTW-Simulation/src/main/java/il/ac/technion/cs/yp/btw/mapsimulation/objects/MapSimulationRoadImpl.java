package il.ac.technion.cs.yp.btw.mapsimulation.objects;

import il.ac.technion.cs.yp.btw.classes.*;

import java.sql.Time;

/**
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
    public BTWWeight getWeightByTime(BTWTime time) {
        return null;//TODO
    }

    /**
     * @return minimum possible Weight of Road
     */
    @Override
    public BTWWeight getMinimumWeight() {
        return null;//TODO
    }

    /**
     * @param road - the road to which the heuristic distance is calculated
     * @return minimum possible Weight of Road
     */
    @Override
    public BTWWeight getHeuristicDist(Road road) {
        return null;
    }

    /**
     * @return the MapSimulationCrossroadImpl this Road starts in
     */
    @Override
    public Crossroad getSourceCrossroad() {
        return this.sourceCrossroad;
    }

    /**
     * @return the MapSimulationCrossroadImpl this Road ends
     */
    @Override
    public Crossroad getDestinationCrossroad() {
        return this.destinationCrossroad;
    }

    public String toStringRoadFull() {
        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"LineString\","+"\"coordinates\""+":"+
                "[["+this.getSourceCrossroad().getCoordinateX()+","+this.getSourceCrossroad().getCoordinateY()+"],"+
                "["+this.getDestinationCrossroad().getCoordinateX()+","+this.getDestinationCrossroad().getCoordinateY()+"]]},"+
                "\"properties\":{"+"\"name\":"+"\""+this.getRoadName()+"\","+
                "\"length\":"+"\""+this.getRoadLength()+"\","+"\"overload\":"+"\""+this.getMinimumWeight()+"\"}},\n";
    }

    public String toStringRoad() {
        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"LineString\","+"\"coordinates\""+":"+
                "[["+this.getSourceCrossroad().getCoordinateX()+","+this.getSourceCrossroad().getCoordinateY()+"],"+
                "["+this.getDestinationCrossroad().getCoordinateX()+","+this.getDestinationCrossroad().getCoordinateY()+"]]},"+
                "\"properties\":{"+"\"name\":"+"\""+this.getRoadName()+"\"}},\n";
    }

}

//for sector
// "\"sector_start\":"+"\""+given_sector_start+"\","+ "\"sector_end\":"+"\""+given_sector_end+"\","+