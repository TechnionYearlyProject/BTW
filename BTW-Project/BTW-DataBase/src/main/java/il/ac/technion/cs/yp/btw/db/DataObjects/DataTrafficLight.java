package il.ac.technion.cs.yp.btw.db.DataObjects;

import il.ac.technion.cs.yp.btw.classes.*;


/**
 * Point on a road map which represents
 * transition between two different Roads
 */
public class DataTrafficLight extends PointAbstract implements TrafficLight {

    private String mapName;
    private String sourceRoadName;
    private String destinationRoadName;
    private long overload;
    private String nameID;
    private Road sourceRoad;
    private Road destinationRoad;
    private BTWWeight[] weights;

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    public DataTrafficLight(String nameID, Point pos, String sourceRoadName, String destinationRoadName, long overload, String mapName) {
        super(pos);
        this.nameID = nameID;
        this.sourceRoadName = sourceRoadName;
        this.destinationRoadName = destinationRoadName;
        this.overload = overload;
        this.mapName = mapName;
    }

    /**
     * @author Sharon Hadar
     * @Date 21/01/2018
     * @return the TrafficLightImpl's unique name
     * in the format:
     * from:@getSourceRoad() to:@getDestinationRoad()
     */
    @Override
    public String getName(){return this.nameID;}

    /**
     * @author Sharon Hadar
     * @Date 21/01/2018
     * @return the Road you got from to this
     *         TrafficLightImpl
     */
    @Override
    public Road getSourceRoad() { return this.sourceRoad; }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    public void setSourceRoad(Road sourceRoad) { this.sourceRoad = sourceRoad; }

    /**
     * @author Sharon Hadar
     * @Date 21/01/2018
     * @return the destination Road you
     *         go to from this TrafficLightImpl
     */
    @Override
    public Road getDestinationRoad(){
        return this.destinationRoad;
    }

    /* @author Sharon Hadar
     * @Date 21/01/2018*/
    public void setDestinationRoad(Road destinationRoad){
        this.destinationRoad = destinationRoad;
    }

    /* @author Sharon Hadar
     * @Date 21/01/2018*/
    public String getSourceRoadName() { return this.sourceRoadName; }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    public String getDestinationRoadName(){
        return this.destinationRoadName;
    }

    /**
     * @Date: 18/05/18
     * @Author: Shay
     * returns the right Weight for the given Time
     * @param time - Time we want to check the load
     *             on the traffic light at
     * @return Weight of this TrafficLightImpl according
     *         to the given Time
     */
    @Override
    public BTWWeight getWeightByTime(BTWTime time){
        return getMinimumWeight();
    }

    /**
     * @Date: 18/05/18
     * @Author: Shay
     * @return minimum possible Weight of TrafficLightImpl
     */
    @Override
    public BTWWeight getMinimumWeight(){
        /*overload = TrafficLightsDataBase.getOverload(nameID, mapName);
        BTWWeight roadOverload = null;
        try{
            roadOverload = BTWWeight.of(overload);
        }catch(BTWIllegalTimeException e){

        }
        return roadOverload;*/
        try {
            return BTWWeight.of(0);
        } catch (BTWIllegalTimeException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Date: 18/05/18
     * @Author: Shay
     * set updated weights array to the DataTrafficLight
     * Pay Attention: Only For DataTrafficLight!
     * @return this DataTrafficLight
     */
    public DataTrafficLight setWeights(BTWWeight[] tlWeights) {
        this.weights = tlWeights;
        return this;
    }

    /**
     * @Date: 18/05/18
     * @Author: Shay
     * get updated weights array of this DataTrafficLight
     * Pay Attention: Only For DataTrafficLight!
     * @return the BTWWeight array of the TL
     */
    public BTWWeight[] getWeights() {
        return this.weights;
    }


    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     *return a string representing thr traffic light*/
    @Override
    public String toString(){
        String trafficLight = "";
        trafficLight += "traffic light: ";
        trafficLight += "nameID = " + nameID + " ";
        trafficLight += "sourceRoadName = " + sourceRoadName + " ";
        trafficLight += "destinationRoadName = " + destinationRoadName + " ";
        trafficLight += "overload = " + overload +" ";
        trafficLight += "position = (" + super.getCoordinateX() + "," + super.getCoordinateY() + ")";
        return trafficLight;
    }
}
