package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.db.queries.Query;
import il.ac.technion.cs.yp.btw.db.queries.QueryAllTrafficLights;
import il.ac.technion.cs.yp.btw.db.queries.QueryTrafficLight;
import il.ac.technion.cs.yp.btw.classes.Point;

import java.util.Set;

public class TrafficLightsDataBase {

    private String mapName;

    public TrafficLightsDataBase(String mapName){
        this.mapName = mapName;
    }

    public Set<TrafficLight>  getAllTrafficLights(){
        Query query = new QueryAllTrafficLights(mapName);
        return (Set<TrafficLight>) MainDataBase.queryDataBase(query);
    }


    public Set<TrafficLight> getAllTrafficLights(Point position){
        Query query = new QueryAllTrafficLights(mapName, position);
        return (Set<TrafficLight>) MainDataBase.queryDataBase(query);
    }

    public TrafficLight getTrafficLight(String nameID){

        Query query = new QueryTrafficLight(mapName, nameID);
        return (TrafficLight) MainDataBase.queryDataBase(query);
    }

}