package il.ac.technion.cs.yp.btw.db;


import java.util.Set;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.db.queries.QueryAllRoads;
import il.ac.technion.cs.yp.btw.db.queries.QueryRoad;
import il.ac.technion.cs.yp.btw.db.queries.Query;


public class RoadsDataBase {

    private String mapName;

    public RoadsDataBase(String mapName){
        this.mapName = mapName;
    }

    public Set<Road> getAllRoads(){

        Query query = new QueryAllRoads(mapName);
        return (Set<Road>) MainDataBase.queryDataBase(query);
    }

    public Road getRoad(String nameID){

        Query query = new QueryRoad(mapName, nameID);
        return (Road) MainDataBase.queryDataBase(query);
    }

}