package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataRoad;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class TestBTWDataBaseImpl {
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    @Test
    public void testGetAllTrafficLights() {
        BTWDataBaseImpl dataBase = BTWDataBaseImpl.getInstance("test1");
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
        BTWDataBaseImpl dataBase = BTWDataBaseImpl.getInstance("anat_simulate");
        dataBase.loadMap();
        Street street = dataBase.getStreetByName(streetName);
        Assert.assertNull(street);
    }
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    @Test
    public void testGetCentralLocationByName() {

        BTWDataBaseImpl dataBase = BTWDataBaseImpl.getInstance("first");
        String centrallLocationName = "MailWay";
        CentralLocation centralLocation = dataBase.getCentralLocationByName(centrallLocationName);
        Assert.assertNull(centralLocation);
    }
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    @Test
    public void testGetAllRoads() {
        BTWDataBaseImpl dataBase = BTWDataBaseImpl.getInstance("test1");
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
        BTWDataBaseImpl dataBase = BTWDataBaseImpl.getInstance("test1");
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
        BTWDataBase dataBase = BTWDataBaseImpl.getInstance("test1");
        dataBase = dataBase.updateWeight();
        Assert.assertNull(dataBase);
    }
}