package il.ac.technion.cs.yp.btw.db.DataObjects;

import java.sql.Time;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.db.RoadsDataBase;
import il.ac.technion.cs.yp.btw.db.StreetsDataBase;
import il.ac.technion.cs.yp.btw.db.CrossRoadsDataBase;
import il.ac.technion.cs.yp.btw.classes.BTWTime;
/**
 * default implementation for the interface Road
 */
public class DataRoad implements Road {
    private String mapName;
    private String name;
    private int roadLength;
    private String myStreet;
    private Point sourceCrossroadId;
    private Point destinationCrossroadId;
    private int secStart;
    private int secEnd;
    private long overload;

    public DataRoad(String name, int roadLength,
                    String myStreet,
                    Point sourceCrossroadId,
                    Point destinationCrossroadId,
                    String mapName){
        this.name = name;
        this.roadLength = roadLength;
        this.myStreet = myStreet;
        this.sourceCrossroadId = sourceCrossroadId;
        this.destinationCrossroadId = destinationCrossroadId;
        this.mapName = mapName;
    }

    public DataRoad(String name, int roadLength,
                    String myStreet,
                    Point sourceCrossroadId,
                    Point destinationCrossroadId,
                    int secStart,
                    int secEnd,
                    long overload,
                    String mapName){
        this(name,roadLength, myStreet,sourceCrossroadId,destinationCrossroadId,mapName);
        this.secStart = secStart;
        this.secEnd = secEnd;
        this.overload = overload;

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
        return (streetNumber>=this.secStart && streetNumber<=this.secEnd);
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
     * @return the street this Road is in
     */
    @Override
    public Street getStreet() {
        return StreetsDataBase.getStreet(myStreet, mapName);
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

        overload = RoadsDataBase.getOverload(name, mapName);
        BTWWeight roadOverload = null;
        try{
            roadOverload = BTWWeight.of(overload);
        }catch(BTWIllegalTimeException e){

        }
        return roadOverload;
    }

    /**
     * @return minimum possible Weight of Road
     */
    @Override
    public BTWWeight getMinimumWeight() {

        overload = RoadsDataBase.getOverload(name, mapName);
        BTWWeight roadOverload = null;
        try{
            roadOverload = BTWWeight.of(overload);
        }catch(BTWIllegalTimeException e){

        }
        return roadOverload;
    }

    @Override
    public BTWWeight getHeuristicDist(Road road) {
        // TODO: Implement
        return null;
    }

    /**
     * @return the CrossroadImpl this Road starts in
     */
    @Override
    public Crossroad getSourceCrossroad() {

        return CrossRoadsDataBase.getCrossRoad(sourceCrossroadId, mapName);
    }

    /**
     * @return the CrossroadImpl this Road ends
     */
    @Override
    public Crossroad getDestinationCrossroad() {

        return CrossRoadsDataBase.getCrossRoad(destinationCrossroadId, mapName);
    }

    @Override
    public String toString(){

        String road = new String("");
        road += "road: ";
        road += "name = " + name +" ";
        road += "roadLength = " +roadLength + " ";
        road += "myStreet = " + myStreet + " ";
        road += "sourceCrossroadId = (" + sourceCrossroadId.getCoordinateX() + "," + sourceCrossroadId.getCoordinateY() + ") ";
        road += "destinationCrossroadId = (" + destinationCrossroadId.getCoordinateX() + "," + destinationCrossroadId.getCoordinateY() + ") ";
        return road;

    }

    @Override
    public Weight getHeuristicDist(Road road){
        return null;
    }
}
