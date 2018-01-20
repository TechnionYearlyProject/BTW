package il.ac.technion.cs.yp.btw.db;


import java.util.Set;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataRoad;
import il.ac.technion.cs.yp.btw.db.queries.*;


public class RoadsDataBase {


    public RoadsDataBase(){

    }

    /**
     * @author: sharon
     * @date: 21/1/18
     * get all roads in data base
     * @param mapName
     * @return set of roads
     */
    public static Set<Road> getAllRoads(String mapName){

        Query query = new QueryAllRoads(mapName);
        return (Set<Road>) MainDataBase.queryDataBase(query);
    }

    /**
     * @author: sharon
     * @date: 21/1/18
     * get specific road by name from data base
     * @param mapName, nameID
     * @return road
     */
    public static Road getRoad(String nameID, String mapName){

        Query query = new QueryRoad(mapName, nameID);
        return (Road) MainDataBase.queryDataBase(query);
    }

    /**
     * @author: sharon
     * @date: 21/1/18
     * get overload of the road
     * @param mapName, nameID
     * @return long value of overload
     */
    public static long getOverload(String nameID, String mapName){
        Query query = new QueryOverloadOfRoad(mapName, nameID);
        return (Long) MainDataBase.queryDataBase(query);
    }

    /**
     * @author: Shay
     * @date: 21/1/18
     * get the heuristic distance between roads
     * @param mapName, srcID, dstID
     * @return long value of overload
     */
    public static long getHeuristicDist(String srcID, String dstID, String mapName) {
        Query query = new QueryHeuristicDist(mapName,srcID, dstID);
        return (long) MainDataBase.queryDataBase(query);
    }

    /**
     * @author: Shay
     * @date: 21/1/18
     * update the overload on the map
     * @param mapName, nameID, newOverload
     *
     */
    public static void updateOverload(long newOverload, String nameID, String mapName){
        Query query = new UpdateOverloadOfRoad(mapName, nameID, newOverload);
        MainDataBase.queryDataBase(query);
    }

}