package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.db.TrafficLightsDataBase;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertNotNull;


public class TestTrafficLightDataBase {

    @Test
    public void testGetAllTrafficLights(){
        MainDataBase.openConnection();
        Set<TrafficLight> allTrafficLights = TrafficLightsDataBase.getAllTrafficLights("mapName");
        Iterator<TrafficLight> iterator = allTrafficLights.iterator();
        //System.out.println("\n\n\nthe result set is:");
        while(iterator.hasNext()){
            assertNotNull(iterator.next());
            //System.out.println(iterator.next().toString());
        }
        MainDataBase.closeConnection();
    }

    @Test
    public void testGetAllTrafficLightsInPosition(){
        MainDataBase.openConnection();
        Point position = new PointImpl(1,1);
        Set<TrafficLight> allTrafficLights = TrafficLightsDataBase.getAllTrafficLights(position, "mapName");
        Iterator<TrafficLight> iterator = allTrafficLights.iterator();
        //System.out.println("\n\n\nthe result set is:");
        while(iterator.hasNext()){
            assertNotNull(iterator.next());
            //System.out.println(iterator.next().toString());
        }
        MainDataBase.closeConnection();
    }

    @Test
    public void testGetTrafficLight(){
        MainDataBase.openConnection();
        TrafficLight trafficLight = TrafficLightsDataBase.getTrafficLight("from:7 street section 3 to:7 street section 3R", "mapName");
        assertNotNull(trafficLight);
        //System.out.println(trafficLight.toString());
        MainDataBase.closeConnection();
    }
}