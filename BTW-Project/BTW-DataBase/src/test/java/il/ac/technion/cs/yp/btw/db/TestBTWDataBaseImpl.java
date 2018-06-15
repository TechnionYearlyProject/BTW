package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataRoad;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataTrafficLight;
import il.ac.technion.cs.yp.btw.statistics.StatisticsProvider;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;
import java.util.Map;

public class TestBTWDataBaseImpl {

    protected Map<String, Map<String, Long>> heuristics;
    protected BTWDataBase db;
    protected Road road1;
    protected Road road2;
    protected Road road3;
    protected Road road4;
    protected Road road5;
    protected Road road6;
    protected Road road7;
    protected Road road8;
    protected TrafficLight trafficLight1_2;
    protected TrafficLight trafficLight1_3;
    protected TrafficLight trafficLight6_3;
    protected TrafficLight trafficLight7_2;
    protected TrafficLight trafficLight2_4;
    protected TrafficLight trafficLight8_6;
    protected TrafficLight trafficLight4_5;
    protected TrafficLight trafficLight3_5;
    protected TrafficLight trafficLight3_8;
    protected TrafficLight trafficLight4_7;
    protected Crossroad crossroad1;
    protected Crossroad crossroad2;
    protected Crossroad crossroad3;
    protected Set<TrafficLight> trafficLights;
    protected Set<Road> roads;

    public TestBTWDataBaseImpl() {
        // Data Base
        db = Mockito.mock(BTWDataBase.class);

        //Crossroads
        crossroad1 = Mockito.mock(Crossroad.class);
        crossroad2 = Mockito.mock(Crossroad.class);
        crossroad3 = Mockito.mock(Crossroad.class);

        //roads
        road1 = Mockito.mock(DataRoad.class);
        road2 = Mockito.mock(DataRoad.class);
        road3 = Mockito.mock(DataRoad.class);
        road4 = Mockito.mock(DataRoad.class);
        road5 = Mockito.mock(DataRoad.class);
        road6 = Mockito.mock(DataRoad.class);
        road7 = Mockito.mock(DataRoad.class);
        road8 = Mockito.mock(DataRoad.class);

        // traffic lights
        trafficLight1_2 = Mockito.mock(DataTrafficLight.class);
        trafficLight1_3 = Mockito.mock(DataTrafficLight.class);
        trafficLight6_3 = Mockito.mock(DataTrafficLight.class);
        trafficLight7_2 = Mockito.mock(DataTrafficLight.class);
        trafficLight2_4 = Mockito.mock(DataTrafficLight.class);
        trafficLight8_6 = Mockito.mock(DataTrafficLight.class);
        trafficLight4_5 = Mockito.mock(DataTrafficLight.class);
        trafficLight3_5 = Mockito.mock(DataTrafficLight.class);
        trafficLight3_8 = Mockito.mock(DataTrafficLight.class);
        trafficLight4_7 = Mockito.mock(DataTrafficLight.class);

        Road a = new DataRoad("Road6",3,"STR1",new PointImpl(0,0),new PointImpl(6.6,6.6),"try");
        // second constructor
        Road b = new DataRoad("Road4",43346,"STR2",new PointImpl(11.32,77.234),new PointImpl(6.6,6.6),0,342,235,"try");
        DataTrafficLight tl = new DataTrafficLight("tl12009",new PointImpl(2,5.12313),"aa","cc",239732623,"test1");


        trafficLights = new HashSet<>(
                Arrays.asList(
                        tl));

        roads = new HashSet<>(
                Arrays.asList(
                        a,
                        b));

        this.heuristics = new HashMap<>();
    }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    @Test
    public void testGetAllTrafficLights() {
        BTWDataBaseImpl dataBase = new BTWDataBaseImpl("test1");
//        dataBase.loadMap();
        Set<TrafficLight> trafficLights = dataBase.getAllTrafficLights();
        Assert.assertNotNull(trafficLights);
        trafficLights = dataBase.getAllTrafficLights();
        Assert.assertNotNull(trafficLights);
        dataBase.closeDataBaseConnection();
    }
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    @Test
    public void testGetStreetByName() {
        String streetName = "aa";
        BTWDataBaseImpl dataBase = new BTWDataBaseImpl("anat_simulate");
        dataBase.loadMap();
        Street street = dataBase.getStreetByName(streetName);
        Assert.assertNull(street);
    }
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    @Test
    public void testGetCentralLocationByName() {

        BTWDataBaseImpl dataBase = new BTWDataBaseImpl("first");
        String centrallLocationName = "MailWay";
        CentralLocation centralLocation = dataBase.getCentralLocationByName(centrallLocationName);
        Assert.assertNull(centralLocation);
    }
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    @Test
    public void testGetAllRoads() {
        BTWDataBaseImpl dataBase = new BTWDataBaseImpl("test1");
        Set<Road> roads = dataBase.getAllRoads();
        Assert.assertNotNull(roads);
        roads = dataBase.getAllRoads();
        Assert.assertNotNull(roads);
    }
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    @Test
    public void testGetAllCrossroads() {
        BTWDataBaseImpl dataBase = new BTWDataBaseImpl("test1");
        Set<Crossroad> crossRoads = dataBase.getAllCrossroads();
        Assert.assertNotNull(crossRoads);
        crossRoads = dataBase.getAllCrossroads();
        Assert.assertNotNull(crossRoads);
    }
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    @Test
    public void testUpdateWeight() {
        BTWDataBase dataBase = new BTWDataBaseImpl("test1");
        dataBase = dataBase.updateWeight();
        Assert.assertNull(dataBase);
    }

    @Test
    public void testStatistics() {
        BTWDataBase dataBase = new BTWDataBaseImpl("testStats");
        ((BTWDataBaseImpl) dataBase).SetStatisticsModeOn();
        dataBase = dataBase.createStatisticsTables(roads,trafficLights);
        StatisticsProvider SP = dataBase.getStatisticsFromDB();
        dataBase = dataBase.updateStatisticsTables(SP);
        Assert.assertNotNull(dataBase);
    }
}