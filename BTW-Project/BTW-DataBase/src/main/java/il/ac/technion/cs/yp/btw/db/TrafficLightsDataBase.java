package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataTrafficLight;
import il.ac.technion.cs.yp.btw.db.queries.Query;
import il.ac.technion.cs.yp.btw.db.queries.QueryAllTrafficLights;
import il.ac.technion.cs.yp.btw.db.queries.QueryOverloadOfTrafficLight;
import il.ac.technion.cs.yp.btw.db.queries.QueryTrafficLight;
import il.ac.technion.cs.yp.btw.classes.Point;

import java.util.Set;

/*
* this class handles the fetching of traffic lights from the data base
* */
public class TrafficLightsDataBase {

    /*
    * get all the traffic lights of a map from the data base
    * @author Sharon Hadar
    * @Date 21/01/2018
    * */
    public static Set<TrafficLight>  getAllTrafficLights(String mapName){
        Query query = new QueryAllTrafficLights(mapName);
        return (Set<TrafficLight>) MainDataBase.queryDataBase(query);
    }


    /*
    * get from data base all traffic lights in a specific position
    * @author Sharon Hadar
    * @Date 21/01/2018
    * */
    public static Set<TrafficLight> getAllTrafficLights(Point position, String mapName){
        Query query = new QueryAllTrafficLights(mapName, position);
        return (Set<TrafficLight>) MainDataBase.queryDataBase(query);
    }
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * get from the data base a specific traffic light by its name
     */
    public static TrafficLight getTrafficLight(String nameID, String mapName){

        Query query = new QueryTrafficLight(mapName, nameID);
        return (TrafficLight) MainDataBase.queryDataBase(query);
    }
//
//    public static long getOverload(String nameID, String mapName) {
//        Query query = new QueryOverloadOfTrafficLight(mapName, nameID);
//        return (Long) MainDataBase.queryDataBase(query);
//    }

}