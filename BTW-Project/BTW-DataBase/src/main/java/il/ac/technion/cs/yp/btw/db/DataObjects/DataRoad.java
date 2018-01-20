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
    private long DEFAULT_SPEED_LIMIT = 50;
    private String mapName;
    private String name;
    private int roadLength;
    private String myStreet;
    private Point sourceCrossroadPosition;
    private Point destinationCrossroadPosition;
    private Crossroad sourceCrossroad;
    private Crossroad destinationCrossroad;
    private int secStart;
    private int secEnd;
    private long overload;

    public DataRoad(String name, int roadLength,
                    String myStreet,
                    Point sourceCrossroadPosition,
                    Point destinationCrossroadPosition,
                    String mapName){
        this.name = name;
        this.roadLength = roadLength;
        this.myStreet = myStreet;
        this.sourceCrossroadPosition = sourceCrossroadPosition;
        this.destinationCrossroadPosition = destinationCrossroadPosition;
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

//        overload = RoadsDataBase.getOverload(name, mapName);
        overload = Double.valueOf(this.getRoadLength() / (DEFAULT_SPEED_LIMIT / 3.6)).longValue();
        try{
            return BTWWeight.of(overload);
        }catch(BTWIllegalTimeException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public BTWWeight getHeuristicDist(Road road) {
        Long dist = RoadsDataBase.getHeuristicDist(name,road.getRoadName(),mapName);
        BTWWeight roadOverload = null;
        try{
            roadOverload = BTWWeight.of(dist);
        }catch(BTWIllegalTimeException e){

        }
        return roadOverload;
    }

    public void setSourceCrossRoad (Crossroad sourceCrossroad){
        this.sourceCrossroad = sourceCrossroad;
    }

    public void setDestinationCrossRoad (Crossroad destinationCrossroad){
        this.destinationCrossroad = destinationCrossroad;
    }


    /**
     * @return the CrossroadImpl this Road starts in
     */
    @Override
    public Crossroad getSourceCrossroad() {
        return this.sourceCrossroad;
        //return CrossRoadsDataBase.getCrossRoad(sourceCrossroadId, mapName);
    }

    /**
     * @return the CrossroadImpl this Road ends
     */
    @Override
    public Crossroad getDestinationCrossroad() {

        return this.destinationCrossroad;
        //return CrossRoadsDataBase.getCrossRoad(destinationCrossroadId, mapName);
    }

    public Point getSourceCrossroadPosition() {
        return this.sourceCrossroadPosition;
    }

    public Point getDestinationCrossroadPosition(){
        return this.destinationCrossroadPosition;
    }
    @Override
    public String toString(){

        String road = "";
        road += "road: ";
        road += "name = " + name +" ";
        road += "roadLength = " +roadLength + " ";
        road += "myStreet = " + myStreet + " ";
        road += "sourceCrossroadId = (" + sourceCrossroadPosition.getCoordinateX() + "," + sourceCrossroadPosition.getCoordinateY() + ") ";
        road += "destinationCrossroadId = (" + destinationCrossroadPosition.getCoordinateX() + "," + destinationCrossroadPosition.getCoordinateY() + ") ";
        return road;

    }

    @Override
    public int hashCode(){
        return this.getRoadName().hashCode();
    }

    @Override
    public boolean equals(Object o){
        if (! (o instanceof Road)) {
            return false;
        }
        Road r = (Road)o;
        return this.getRoadName().equals(r.getRoadName());
    }
}
