package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.BTWTime;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataRoad;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataTrafficLight;
import il.ac.technion.cs.yp.btw.statistics.StatisticsProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

/**
 * Created by shay on 04/05/2018.
 */
public class TestDBStatsiticsProvider {

    Road a;
    Road b;
    Set<TrafficLight> trafficLights;
    Set<Road> roads;

    /**
     * @Autor: Shay
     * @Date: 15/6/18
     * DBStatisticsProvider unit test
     */
    public TestDBStatsiticsProvider() {
        a = new DataRoad("Road6",3,"STR1",new PointImpl(0,0),new PointImpl(6.6,6.6),"try");
        // second constructor
        b = new DataRoad("Road4",43346,"STR2",new PointImpl(11.32,77.234),new PointImpl(6.6,6.6),0,342,235,"try");
        DataTrafficLight tl = new DataTrafficLight("tl12009",new PointImpl(2,5.12313),"aa","cc",239732623,"test1");


        trafficLights = new HashSet<>(
                Arrays.asList(
                        tl));

        roads = new HashSet<>(
                Arrays.asList(
                        a,
                        b));
    }
    /**
     * @Author: Shay
     * @Date: 4/5/18
     * testing DBStatisticsProvider module - getting right results from DB
     */

    @Test
    public void testCreateStatisticsTable() {
        BTWDataBaseImpl db = new BTWDataBaseImpl("ShayTest1");
        db.StatisticsMode = true;
        String s = "{\"type\": \"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0]},\"properties\":{\"name\":\"from:1 avenue section 1R to:1 avenue section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0]},\"properties\":{\"name\":\"from:2 avenue section 1R to:2 avenue section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0]},\"properties\":{\"name\":\"from:2 avenue section 1R to:1 street section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0]},\"properties\":{\"name\":\"from:1 street section 1R to:1 street section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0054000054000054]},\"properties\":{\"name\":\"from:2 street section 1R to:2 street section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0]},\"properties\":{\"name\":\"from:1 street section 1 to:1 street section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0]},\"properties\":{\"name\":\"from:1 avenue section 1R to:1 street section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0054000054000054]},\"properties\":{\"name\":\"from:2 avenue section 1 to:2 street section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0054000054000054]},\"properties\":{\"name\":\"from:2 avenue section 1 to:2 avenue section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0054000054000054]},\"properties\":{\"name\":\"from:1 avenue section 1 to:2 street section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0]},\"properties\":{\"name\":\"from:1 street section 1 to:2 avenue section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0054000054000054]},\"properties\":{\"name\":\"from:2 street section 1 to:2 avenue section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0]},\"properties\":{\"name\":\"from:1 street section 1R to:1 avenue section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0054000054000054]},\"properties\":{\"name\":\"from:2 street section 1 to:2 street section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0054000054000054]},\"properties\":{\"name\":\"from:2 street section 1R to:1 avenue section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0054000054000054]},\"properties\":{\"name\":\"from:1 avenue section 1 to:1 avenue section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0,0.0054000054000054],[0.0,0.0]]},\"properties\":{\"name\":\"1 avenue section 1R\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0054000054000054,0.0054000054000054],[0.0,0.0054000054000054]]},\"properties\":{\"name\":\"2 street section 1R\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0054000054000054,0.0054000054000054],[0.0054000054000054,0.0]]},\"properties\":{\"name\":\"2 avenue section 1R\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0,0.0],[0.0054000054000054,0.0]]},\"properties\":{\"name\":\"1 street section 1\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0,0.0],[0.0,0.0054000054000054]]},\"properties\":{\"name\":\"1 avenue section 1\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0,0.0054000054000054],[0.0054000054000054,0.0054000054000054]]},\"properties\":{\"name\":\"2 street section 1\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0054000054000054,0.0],[0.0054000054000054,0.0054000054000054]]},\"properties\":{\"name\":\"2 avenue section 1\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0054000054000054,0.0],[0.0,0.0]]},\"properties\":{\"name\":\"1 street section 1R\",\"length\":\"600\",\"overload\":0}}]}";
        db.saveMap(s);
        db.createStatisticsTables(db.getAllRoads(),db.getAllTrafficLights());
        assertNotNull(db);

    }

    /**
     * @Author: Shay
     * @Date: 4/5/18
     * testing DBStatisticsProvider module - not from DB... only unit test
     */

    @Test
    public void testStatisticsProviderDummyUnitTest() {
        StatisticsProvider stats = new DBStatisticsProvider(roads,trafficLights);
        assertNotNull(stats);

    }

    /**
     * @Author: Shay
     * @Date: 4/5/18
     * testing DBStatisticsProvider module - with DB
     */

    @Test
    public void testStatisticsProviderConstructorWithDB() {
        BTWDataBaseImpl db = new BTWDataBaseImpl("ShayTest1");
        db.parseMap("{\"type\": \"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0]},\"properties\":{\"name\":\"from:1 street section 1R to:1 avenue section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0054000054000054]},\"properties\":{\"name\":\"from:2 street section 1R to:1 avenue section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0054000054000054]},\"properties\":{\"name\":\"from:2 avenue section 1 to:2 avenue section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0]},\"properties\":{\"name\":\"from:1 street section 1 to:1 street section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0054000054000054]},\"properties\":{\"name\":\"from:2 street section 1 to:2 avenue section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0054000054000054]},\"properties\":{\"name\":\"from:2 avenue section 1 to:2 street section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0054000054000054]},\"properties\":{\"name\":\"from:2 street section 1R to:2 street section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0054000054000054]},\"properties\":{\"name\":\"from:1 avenue section 1 to:2 street section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0054000054000054]},\"properties\":{\"name\":\"from:2 street section 1 to:2 street section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0]},\"properties\":{\"name\":\"from:1 avenue section 1R to:1 street section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0]},\"properties\":{\"name\":\"from:2 avenue section 1R to:1 street section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0]},\"properties\":{\"name\":\"from:1 street section 1 to:2 avenue section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0]},\"properties\":{\"name\":\"from:2 avenue section 1R to:2 avenue section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0]},\"properties\":{\"name\":\"from:1 avenue section 1R to:1 avenue section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0]},\"properties\":{\"name\":\"from:1 street section 1R to:1 street section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0054000054000054]},\"properties\":{\"name\":\"from:1 avenue section 1 to:1 avenue section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0054000054000054,0.0054000054000054],[0.0054000054000054,0.0]]},\"properties\":{\"name\":\"2 avenue section 1R\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0,0.0],[0.0,0.0054000054000054]]},\"properties\":{\"name\":\"1 avenue section 1\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0,0.0054000054000054],[0.0,0.0]]},\"properties\":{\"name\":\"1 avenue section 1R\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0,0.0],[0.0054000054000054,0.0]]},\"properties\":{\"name\":\"1 street section 1\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0054000054000054,0.0054000054000054],[0.0,0.0054000054000054]]},\"properties\":{\"name\":\"2 street section 1R\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0054000054000054,0.0],[0.0,0.0]]},\"properties\":{\"name\":\"1 street section 1R\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0,0.0054000054000054],[0.0054000054000054,0.0054000054000054]]},\"properties\":{\"name\":\"2 street section 1\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0054000054000054,0.0],[0.0054000054000054,0.0054000054000054]]},\"properties\":{\"name\":\"2 avenue section 1\",\"length\":\"600\",\"overload\":0}}]}");
        Assert.assertNotNull(db);
        StatisticsProvider sp = new DBStatisticsProvider(db);
        // NEED TO IMPLEMENT HERE !!!

        assertNotNull(sp);

    }

    /**
     * @Author: Shay
     * @Date: 4/5/18
     * testing DBStatisticsProvider module - with DB
     */

    @Test
    public void testAllFunctions() {

        BTWDataBaseImpl db = new BTWDataBaseImpl("ShayTest1");
        db.parseMap("{\"type\": \"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0]},\"properties\":{\"name\":\"from:1 street section 1R to:1 avenue section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0054000054000054]},\"properties\":{\"name\":\"from:2 street section 1R to:1 avenue section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0054000054000054]},\"properties\":{\"name\":\"from:2 avenue section 1 to:2 avenue section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0]},\"properties\":{\"name\":\"from:1 street section 1 to:1 street section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0054000054000054]},\"properties\":{\"name\":\"from:2 street section 1 to:2 avenue section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0054000054000054]},\"properties\":{\"name\":\"from:2 avenue section 1 to:2 street section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0054000054000054]},\"properties\":{\"name\":\"from:2 street section 1R to:2 street section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0054000054000054]},\"properties\":{\"name\":\"from:1 avenue section 1 to:2 street section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0054000054000054]},\"properties\":{\"name\":\"from:2 street section 1 to:2 street section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0]},\"properties\":{\"name\":\"from:1 avenue section 1R to:1 street section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0]},\"properties\":{\"name\":\"from:2 avenue section 1R to:1 street section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0]},\"properties\":{\"name\":\"from:1 street section 1 to:2 avenue section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0]},\"properties\":{\"name\":\"from:2 avenue section 1R to:2 avenue section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0]},\"properties\":{\"name\":\"from:1 avenue section 1R to:1 avenue section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0]},\"properties\":{\"name\":\"from:1 street section 1R to:1 street section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0054000054000054]},\"properties\":{\"name\":\"from:1 avenue section 1 to:1 avenue section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0054000054000054,0.0054000054000054],[0.0054000054000054,0.0]]},\"properties\":{\"name\":\"2 avenue section 1R\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0,0.0],[0.0,0.0054000054000054]]},\"properties\":{\"name\":\"1 avenue section 1\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0,0.0054000054000054],[0.0,0.0]]},\"properties\":{\"name\":\"1 avenue section 1R\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0,0.0],[0.0054000054000054,0.0]]},\"properties\":{\"name\":\"1 street section 1\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0054000054000054,0.0054000054000054],[0.0,0.0054000054000054]]},\"properties\":{\"name\":\"2 street section 1R\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0054000054000054,0.0],[0.0,0.0]]},\"properties\":{\"name\":\"1 street section 1R\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0,0.0054000054000054],[0.0054000054000054,0.0054000054000054]]},\"properties\":{\"name\":\"2 street section 1\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0054000054000054,0.0],[0.0054000054000054,0.0054000054000054]]},\"properties\":{\"name\":\"2 avenue section 1\",\"length\":\"600\",\"overload\":0}}]}");
        StatisticsProvider sp = new DBStatisticsProvider(db);
        Assert.assertTrue(sp.granularity() == 1800);
        System.out.println(sp.expectedTimeOnRoadAt(BTWTime.of(0),a));   // TO DO: DEBUG!!!!
        assertNotNull(sp);

    }
}
