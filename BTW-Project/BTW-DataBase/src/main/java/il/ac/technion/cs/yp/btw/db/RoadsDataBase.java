package il.ac.technion.cs.yp.btw.db;


import java.util.Set;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.db.queries.QueryAllRoads;
import il.ac.technion.cs.yp.btw.db.queries.QueryRoad;
import il.ac.technion.cs.yp.btw.db.queries.Query;


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

}