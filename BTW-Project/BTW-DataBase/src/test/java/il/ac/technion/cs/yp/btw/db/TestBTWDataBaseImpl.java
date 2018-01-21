package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataRoad;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class TestBTWDataBaseImpl {
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

    @Test
    public void testGetStreetByName() {
        String streetName = "aa";
        BTWDataBaseImpl dataBase = new BTWDataBaseImpl("anat_simulate");
        dataBase.loadMap();
        Street street = dataBase.getStreetByName(streetName);
        Assert.assertNull(street);
    }

    @Test
    public void testGetCentralLocationByName() {

        BTWDataBaseImpl dataBase = new BTWDataBaseImpl("first");
        String centrallLocationName = "MailWay";
        CentralLocation centralLocation = dataBase.getCentralLocationByName(centrallLocationName);
        Assert.assertNull(centralLocation);
    }

    @Test
    public void testGetAllRoads() {
        BTWDataBaseImpl dataBase = new BTWDataBaseImpl("test1");
        Set<Road> roads = dataBase.getAllRoads();
        Assert.assertNotNull(roads);
        roads = dataBase.getAllRoads();
        Assert.assertNotNull(roads);
    }

    @Test
    public void testGetAllCrossroads() {
        BTWDataBaseImpl dataBase = new BTWDataBaseImpl("test1");
        Set<Crossroad> crossRoads = dataBase.getAllCrossroads();
        Assert.assertNotNull(crossRoads);
        crossRoads = dataBase.getAllCrossroads();
        Assert.assertNotNull(crossRoads);
    }

    @Test
    public void testUpdateWeight() {
        BTWDataBase dataBase = new BTWDataBaseImpl("test1");
        dataBase = dataBase.updateWeight();
        Assert.assertNull(dataBase);
    }
}