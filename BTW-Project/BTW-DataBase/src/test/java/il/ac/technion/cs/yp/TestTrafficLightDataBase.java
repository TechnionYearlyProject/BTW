package il.ac.technion.cs.yp;

import il.ac.technion.cs.yp.btw.db.TrafficLightsDataBase;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import junit.framework.TestCase;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class TestTrafficLightDataBase  extends TestCase {

    public void testGetAllTrafficLights(){
        TrafficLightsDataBase trafficLightsDataBase = new TrafficLightsDataBase("first");
        Set<TrafficLight> allTrafficLights = trafficLightsDataBase.getAllTrafficLights();
        Iterator<TrafficLight> iterator = allTrafficLights.iterator();
        System.out.println("\n\n\nthe result set is:");
        while(iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
    }

    public void testGetRoad(){
/*
        RoadsDataBase roadsDataBase = new RoadsDataBase("first");
        Road ayalonn = roadsDataBase.getRoad("Ayalonn");
        System.out.println(ayalonn.toString());
        Road road1 = roadsDataBase.getRoad("Road1");
        System.out.println(road1.toString());
        Road road4 = roadsDataBase.getRoad("Road4");
        System.out.println(road4.toString());
        Road road6 = roadsDataBase.getRoad("Road6");
        System.out.println(road6.toString());*/

    }
}