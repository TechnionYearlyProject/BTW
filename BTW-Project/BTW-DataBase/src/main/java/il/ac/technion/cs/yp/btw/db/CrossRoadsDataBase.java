package il.ac.technion.cs.yp.btw.db;

import java.lang.String;

import java.util.*;

import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataCrossRoad;
import il.ac.technion.cs.yp.btw.db.queries.Query;
import il.ac.technion.cs.yp.btw.db.queries.QueryAllTrafficLightPositions;
import il.ac.technion.cs.yp.btw.db.queries.QueryAllTrafficLights;
import il.ac.technion.cs.yp.btw.db.queries.QueryCrossRoad;

/*cross roads is an intersection of roads*/
public class CrossRoadsDataBase {


    public CrossRoadsDataBase(){

    }
/*
    public static Set<Crossroad> getAllCrossRoads(String mapName) {
        Query query = new QueryAllTrafficLights(mapName);
        Set<TrafficLight> trafficLights = (Set<TrafficLight>) MainDataBase.queryDataBase(query);
        Map<Point, Crossroad> crossRoadsMap = new HashMap<>();
        Iterator<TrafficLight> iterator = trafficLights.iterator();
        while (iterator.hasNext()) {
            Crossroad crossRoad = null;
            TrafficLight trafficLightfromDataBase = iterator.next();
            Point position = new PointImpl(trafficLightfromDataBase.getCoordinateX(), trafficLightfromDataBase.getCoordinateY());
            if (crossRoadsMap.containsKey(position)) {
                crossRoad = crossRoadsMap.get(position);
                crossRoad.addTrafficLight(trafficLightfromDataBase);
            } else {
                crossRoad = new DataCrossRoad(position, mapName);
                crossRoadsMap.put(position, crossRoad);
            }
        }
        Set<Crossroad> crossRoads = new HashSet(crossRoadsMap.values());
        return crossRoads;
    }
    */

    public static Crossroad getCrossRoad(Point position, String mapName){

        Query query = new QueryCrossRoad(mapName, position);
        return (Crossroad) MainDataBase.queryDataBase(query);
    }

    public static void addCrossRoad(Crossroad crossRoad, String mapName){

    }

}