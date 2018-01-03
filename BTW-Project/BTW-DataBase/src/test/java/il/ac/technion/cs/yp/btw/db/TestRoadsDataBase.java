package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.db.RoadsDataBase;
import il.ac.technion.cs.yp.btw.classes.Road;
import junit.framework.TestCase;
import java.util.Iterator;
import java.util.Set;


public class TestRoadsDataBase  extends TestCase {

    public void testGetAllRoads(){
        Set<Road> allRoads = RoadsDataBase.getAllRoads("first");
        Iterator<Road> iterator = allRoads.iterator();
        System.out.println("\n\n\nthe result set is:");
        while(iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
    }

    public void testGetRoad(){
        String mapName = "first";
        Road ayalonn = RoadsDataBase.getRoad("Ayalonn", mapName);
        System.out.println(ayalonn.toString());
        Road road1 = RoadsDataBase.getRoad("Road1", mapName);
        System.out.println(road1.toString());
        Road road4 = RoadsDataBase.getRoad("Road4", mapName);
        System.out.println(road4.toString());
        Road road6 = RoadsDataBase.getRoad("Road6", mapName);
        System.out.println(road6.toString());

    }

    public void testGetOverload(){
        String mapName = "first";
        long ayalonnOverload = RoadsDataBase.getOverload("Ayalonn", mapName);
        System.out.println("ayalonnOverload = " + ayalonnOverload);
        long road1Overload = RoadsDataBase.getOverload("Ayalonn", mapName);
        System.out.println("ayalonnOverload = " + road1Overload);
        long road4Overload = RoadsDataBase.getOverload("Ayalonn", mapName);
        System.out.println("ayalonnOverload = " + road4Overload);
        long road6Overload = RoadsDataBase.getOverload("Ayalonn", mapName);
        System.out.println("ayalonnOverload = " + road6Overload);
    }
}