package il.ac.technion.cs.yp.btw.db;


import java.util.Set;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.db.queries.*;


public class RoadsDataBase {


    public RoadsDataBase(){

    }

    public static Set<Road> getAllRoads(String mapName){

        Query query = new QueryAllRoads(mapName);
        return (Set<Road>) MainDataBase.queryDataBase(query);
    }

    public static Road getRoad(String nameID, String mapName){

        Query query = new QueryRoad(mapName, nameID);
        return (Road) MainDataBase.queryDataBase(query);
    }

    public static long getOverload(String nameID, String mapName){
        Query query = new QueryOverloadOfRoad(mapName, nameID);
        return (Long) MainDataBase.queryDataBase(query);
    }

    public static long getHeuristicDist(String srcID, String dstID, String mapName) {
        Query query = new QueryHeuristicDist(mapName,srcID, dstID);
        return (long) MainDataBase.queryDataBase(query);
    }

    public static void updateOverload(long newOverload, String nameID, String mapName){
        Query query = new UpdateOverloadOfRoad(mapName, nameID, newOverload);
        MainDataBase.queryDataBase(query);
    }

}