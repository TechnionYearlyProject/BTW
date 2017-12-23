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
        Set<TrafficLight> allTrafficLights = TrafficLightsDataBase.getAllTrafficLights("first");
        Iterator<TrafficLight> iterator = allTrafficLights.iterator();
        System.out.println("\n\n\nthe result set is:");
        while(iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
    }

    public void testGetAllTrafficLightsInPosition(){
        Point position = new PointImpl(1,1);
        Set<TrafficLight> allTrafficLights = TrafficLightsDataBase.getAllTrafficLights(position, "first");
        Iterator<TrafficLight> iterator = allTrafficLights.iterator();
        System.out.println("\n\n\nthe result set is:");
        while(iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
    }

    public void testGetTrafficLight(){

        TrafficLight trafficLight = TrafficLightsDataBase.getTrafficLight("T1-Road4-Road6", "first");
        System.out.println(trafficLight.toString());
    }
}