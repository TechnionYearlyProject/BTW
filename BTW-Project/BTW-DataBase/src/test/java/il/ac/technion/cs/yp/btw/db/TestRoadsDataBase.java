package il.ac.technion.cs.yp.btw.db;


import il.ac.technion.cs.yp.btw.classes.BTWWeight;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataRoad;
import junit.framework.TestCase;
import java.util.Iterator;
import java.util.Set;


public class TestRoadsDataBase  extends TestCase {

    public void testGetAllRoads(){
        MainDataBase.openConnection();
        Set<Road> allRoads = RoadsDataBase.getAllRoads("first");
        Iterator<Road> iterator = allRoads.iterator();
        System.out.println("\n\n\nthe result set is:");
        while(iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
        MainDataBase.closeConnection();
    }

    public void testGetRoad(){
        String mapName = "first";
        MainDataBase.openConnection();
        Road ayalonn = RoadsDataBase.getRoad("Ayalonn", mapName);
        System.out.println(ayalonn.toString());
        Road road1 = RoadsDataBase.getRoad("Road1", mapName);
        System.out.println(road1.toString());
        Road road4 = RoadsDataBase.getRoad("Road4", mapName);
        System.out.println(road4.toString());
        Road road6 = RoadsDataBase.getRoad("Road6", mapName);
        System.out.println(road6.toString());
        MainDataBase.closeConnection();

    }

    public void testGetOverload(){
        String mapName = "first";
        MainDataBase.openConnection();
        long ayalonnOverload = RoadsDataBase.getOverload("Ayalonn", mapName);
        System.out.println("ayalonnOverload = " + ayalonnOverload);
        long road1Overload = RoadsDataBase.getOverload("Road1", mapName);
        System.out.println("road1Overload = " + road1Overload);
        long road4Overload = RoadsDataBase.getOverload("Road4", mapName);
        System.out.println("road4Overload = " + road4Overload);
        long road6Overload = RoadsDataBase.getOverload("Road6", mapName);
        System.out.println("road6Overload = " + road6Overload);
        MainDataBase.closeConnection();
    }

    public void testGetHeuristicDist(){
        String mapName = "test";
        MainDataBase.openConnection();
        Road aaRoad = RoadsDataBase.getRoad("aa",mapName);
        Road bbRoad = RoadsDataBase.getRoad("bb",mapName);
        BTWWeight dist = aaRoad.getHeuristicDist(bbRoad);
        assert(dist.seconds() == 123123);
        MainDataBase.closeConnection();
    }

}