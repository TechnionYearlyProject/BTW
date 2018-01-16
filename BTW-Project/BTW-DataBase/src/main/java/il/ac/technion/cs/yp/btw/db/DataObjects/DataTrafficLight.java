package il.ac.technion.cs.yp.btw.db.DataObjects;

import java.sql.Time;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.db.RoadsDataBase;
import il.ac.technion.cs.yp.btw.db.TrafficLightsDataBase;


/**
 * Point on a road map which represents
 * transition between two different Roads
 */
public class DataTrafficLight extends PointAbstract implements TrafficLight {

    private String mapName;
    private String sourceRoadId;
    private String destinationRoadId;
    private long overload;
    private String nameID;
    public DataTrafficLight(String nameID, Point pos, String sourceRoadId, String destinationRoadId, long overload, String mapName) {
        super(pos);
        this.nameID = nameID;
        this.sourceRoadId = sourceRoadId;
        this.destinationRoadId = destinationRoadId;
        this.overload = overload;
        this.mapName = mapName;
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
    public Road getSourceRoad() {
        return RoadsDataBase.getRoad(sourceRoadId, mapName);
    }

    /**
     * @return the destination Road you
     *         go to from this TrafficLightImpl
     */
    @Override
    public Road getDestinationRoad(){
        return RoadsDataBase.getRoad(destinationRoadId, mapName);
    }

    /**
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

    @Override
    public String toString(){
        String trafficLight = "";
        trafficLight += "traffic light: ";
        trafficLight += "nameID = " + nameID + " ";
        trafficLight += "sourceRoadId = " + sourceRoadId + " ";
        trafficLight += "destinationRoadId = " + destinationRoadId + " ";
        trafficLight += "overload = " + overload +" ";
        trafficLight += "position = (" + super.getCoordinateX() + "," + super.getCoordinateY() + ")";
        return trafficLight;
    }
}
