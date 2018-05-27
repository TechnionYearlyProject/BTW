package il.ac.technion.cs.yp.btw.db.DataObjects;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.db.RoadsDataBase;
import java.util.Map;
import java.util.HashMap;

/**
 * default implementation for the interface Road
 */
public class DataRoad implements Road {
    private long DEFAULT_SPEED_LIMIT = 50;
    private String mapName;
    private String name;
    private int roadLength;
    private String streetName;
    private Street street;
    private Point sourceCrossroadPosition;
    private Point destinationCrossroadPosition;
    private Crossroad sourceCrossroad;
    private Crossroad destinationCrossroad;
    private int secStart;
    private int secEnd;
    private long overload;
    private Map<String,Long> distances;
    private BTWWeight[] weights;

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    public DataRoad(String name, int roadLength,
                    String streetName,
                    Point sourceCrossroadPosition,
                    Point destinationCrossroadPosition,
                    String mapName){
        this.name = name;
        this.roadLength = roadLength;
        this.streetName = streetName;
        this.street = null;
        this.sourceCrossroadPosition = sourceCrossroadPosition;
        this.destinationCrossroadPosition = destinationCrossroadPosition;
        this.mapName = mapName;
        this.weights = null;
        this.distances = new HashMap<String,Long>();
    }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    public DataRoad(String name, int roadLength,
                    String streetName,
                    Point sourceCrossroadId,
                    Point destinationCrossroadId,
                    int secStart,
                    int secEnd,
                    long overload,
                    String mapName){
        this(name,roadLength, streetName,sourceCrossroadId,destinationCrossroadId,mapName);
        this.secStart = secStart;
        this.secEnd = secEnd;
        this.overload = overload;
        this.weights = null;
        this.distances = new HashMap<String,Long>();

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
     * @author Sharon Hadar
     * @Date 21/01/2018
     * @return the length in meters of the road
     */
    @Override
    public int getRoadLength() {
        return this.roadLength;
    }

    /**
     * @author Sharon Hadar
     * @Date 21/01/2018
     * @return the unique name of the road
     */
    @Override
    public String getRoadName() {
        return this.name;
    }

    /**
     * @author Sharon Hadar
     * @Date 21/01/2018
     * @return the street this Road is in
     */
    @Override
    public Street getStreet() {
        return this.street;
    }

    /*@Author
    *@Date 30/3/2018
    * return the street name of the road as a String
     */
    public String getStreetName(){
        return this.streetName;
    }

    public void setStreet(Street street){
        this.street = street;
    }

    /**
     * @Author: shay
     * @Date: 09/05/18
     * returns the right Weight for the given Time
     *
     * @param time - Time we want to check the load
     *             on the road at
     * @return Weight of this Road according
     * to the given Time
     */
    @Override
    public BTWWeight getWeightByTime(BTWTime time) {
        if (weights != null) {
            long index = time.seconds()/1800 -1;
            if (index >= 0) return weights[(int)index];
            else return weights[0];

        }
        overload = RoadsDataBase.getOverload(name, mapName);
        BTWWeight roadOverload = null;
        try{
            roadOverload = BTWWeight.of(overload);
        }catch(BTWIllegalTimeException e){
        }
        return roadOverload;
    }

    /**
     * @Author: shay
     * @Date: 09/05/18
     * @return minimum possible Weight of Road
     */
    @Override
    public BTWWeight getMinimumWeight() {
        overload = Double.valueOf(this.getRoadLength() / (DEFAULT_SPEED_LIMIT / 3.6)).longValue();
        try{
            return BTWWeight.of(overload);
        }catch(BTWIllegalTimeException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * @Author: shay
     * @Date: 09/05/18
     * @return the distance between this road to the parameter road
     */
    @Override
    public BTWWeight getHeuristicDist(Road road) {
        //Long dist = RoadsDataBase.getHeuristicDist(name,road.getRoadName(),mapName);
        Long dist = this.distances.get(road.getRoadName());
        BTWWeight roadOverload = null;
        try{
            roadOverload = BTWWeight.of(dist);
        }catch(BTWIllegalTimeException e){

        }
        return roadOverload;
    }

    /**
     * @Author: shay
     * @Date: 09/05/18
     * set source cross road
     */
    public void setSourceCrossRoad (Crossroad sourceCrossroad){

        this.sourceCrossroad = sourceCrossroad;
    }

    /**
     * @Author: shay
     * @Date: 09/05/18
     * set destination cross road
     */
    public void setDestinationCrossRoad (Crossroad destinationCrossroad){
        this.destinationCrossroad = destinationCrossroad;
    }


    /**
     * @author Sharon Hadar
     * @Date 21/01/2018
     * @return the CrossroadImpl this Road starts in
     */
    @Override
    public Crossroad getSourceCrossroad() {
        return this.sourceCrossroad;
    }

    /**
     * @author Sharon Hadar
     * @Date 21/01/2018
     * @return the CrossroadImpl this Road ends
     */
    @Override
    public Crossroad getDestinationCrossroad() {

        return this.destinationCrossroad;
    }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    public Point getSourceCrossroadPosition() {
        return this.sourceCrossroadPosition;
    }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    public Point getDestinationCrossroadPosition(){
        return this.destinationCrossroadPosition;
    }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    @Override
    public String toString(){

        String road = "";
        road += "road: ";
        road += "name = " + name +" ";
        road += "roadLength = " +roadLength + " ";
        road += "streetName = " + streetName + " ";
        road += "sourceCrossroadId = (" + sourceCrossroadPosition.getCoordinateX() + "," + sourceCrossroadPosition.getCoordinateY() + ") ";
        road += "destinationCrossroadId = (" + destinationCrossroadPosition.getCoordinateX() + "," + destinationCrossroadPosition.getCoordinateY() + ") ";
        road += "secStart = " + secStart + " ";
        road += "secEnd = " + secEnd + " ";
        road += "overload = " + overload + " ";
        return road;

    }

    /**
     * @Date: 18/05/18
     * @Author: Shay
     * @return int hashcode
     */
    @Override
    public int hashCode(){
        return this.getRoadName().hashCode();
    }

    /**
     * @Date: 18/05/18
     * @Author: Shay
     * equals function for comparing roads
     */
    @Override
    public boolean equals(Object o){
        if (! (o instanceof Road)) {
            return false;
        }
        Road r = (Road)o;
        return this.getRoadName().equals(r.getRoadName());
    }

    /**
     * @Date: 18/05/18
     * @Author: Shay
     * set updated weights array to the DataRoad
     * Pay Attention: Only For DataRoads!
     * @return this DataRoad
     */
    public DataRoad setWeights(BTWWeight[] roadWeights) {
        this.weights = roadWeights;
        return this;
    }

    /**
     * @Date: 18/05/18
     * @Author: Shay
     * get updated weights array of this DataRoad
     * Pay Attention: Only For DataRoads!
     * @return BTWWeight array of the road
     */
    public BTWWeight[] getWeights() {
        return this.weights;
    }

    /**
     * @Date: 18/05/18
     * @Author: Shay
     * set updated distances Map of this DataRoad
     * Pay Attention: Only For DataRoads!
     * @return this DataRoad
     */
    public DataRoad setDistances(Map<String,Long> map) {
        this.distances = map;
        return this;
    }

    /**
     * @Date: 18/05/18
     * @Author: Shay
     * get updated distances Map of this DataRoad
     * Pay Attention: Only For DataRoads!
     * @return B Map<String,Long> distances of the road
     */
    public Map<String,Long> getDistances() {
        return this.distances;
    }

}
