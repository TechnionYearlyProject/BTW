package il.ac.technion.cs.yp.btw.db;

import java.lang.String;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.db.queries.Query;
import il.ac.technion.cs.yp.btw.db.queries.QueryAllTrafficLightPositions;
import il.ac.technion.cs.yp.btw.db.queries.QueryCrossRoad;

/*cross roads is an intersection of roads*/
public class CrossRoadsDataBase {


    public CrossRoadsDataBase(){

    }

    public static Set<Crossroad> getAllCrossRoads(String mapName){
        Query query = new QueryAllTrafficLightPositions(mapName);
        Set<Point> positions = (Set<Point>) MainDataBase.queryDataBase(query);
        Set<Crossroad> crossRoads = new HashSet();
        Iterator<Point> iterator = positions.iterator();
        while(iterator.hasNext()){
            crossRoads.add(getCrossRoad(iterator.next(), mapName));
        }
        return crossRoads;
    }

    public static Crossroad getCrossRoad(Point position, String mapName){

        Query query = new QueryCrossRoad(mapName, position);
        return (Crossroad) MainDataBase.queryDataBase(query);
    }

    public static void addCrossRoad(Crossroad crossRoad, String mapName){

    }

}