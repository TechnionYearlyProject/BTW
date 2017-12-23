package il.ac.technion.cs.yp;

import il.ac.technion.cs.yp.btw.db.TrafficLightsDataBase;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import junit.framework.TestCase;
import java.util.Iterator;
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

    public void testGetAllTrafficLightsInPosition(){
        TrafficLightsDataBase trafficLightsDataBase = new TrafficLightsDataBase("first");
        Point position = new PointImpl(1,1);
        Set<TrafficLight> allTrafficLights = trafficLightsDataBase.getAllTrafficLights(position);
        Iterator<TrafficLight> iterator = allTrafficLights.iterator();
        System.out.println("\n\n\nthe result set is:");
        while(iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
    }

    public void testGetRoad(){

        TrafficLightsDataBase trafficLightsDataBase = new TrafficLightsDataBase("first");
        TrafficLight trafficLight = trafficLightsDataBase.getTrafficLight("T1-Road4-Road6");
        System.out.println(trafficLight.toString());
    }
}