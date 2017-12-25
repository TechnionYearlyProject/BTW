package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.db.queries.Query;
import il.ac.technion.cs.yp.btw.db.queries.QueryAllTrafficLights;
import il.ac.technion.cs.yp.btw.db.queries.QueryTrafficLight;
import il.ac.technion.cs.yp.btw.classes.Point;

import java.util.Set;

public class TrafficLightsDataBase {

    public TrafficLightsDataBase(String mapName){

    }

    public static Set<TrafficLight>  getAllTrafficLights(String mapName){
        Query query = new QueryAllTrafficLights(mapName);
        return (Set<TrafficLight>) MainDataBase.queryDataBase(query);
    }


    public static Set<TrafficLight> getAllTrafficLights(Point position, String mapName){
        Query query = new QueryAllTrafficLights(mapName, position);
        return (Set<TrafficLight>) MainDataBase.queryDataBase(query);
    }

    public static TrafficLight getTrafficLight(String nameID, String mapName){

        Query query = new QueryTrafficLight(mapName, nameID);
        return (TrafficLight) MainDataBase.queryDataBase(query);
    }

}