package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataTrafficLight;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * Created by shay on 14/01/2018.
 */
public class TestDataTrafficLight {

    @Test
    public void testGetParameters() {
        BTWDataBase btw = new BTWDataBaseImpl("test");
        Set<TrafficLight> tls = btw.getAllTrafficLights();

        for (TrafficLight tl: tls) {
            if (tl.getName().equals("from:cc to:dd")) {
                Assert.assertTrue(tl.getCoordinateX() == 0.0);
                Assert.assertTrue(tl.getCoordinateY() == 0.0);
                Assert.assertTrue(tl.getMinimumWeight().seconds() == 4647);
            }
            if (tl.getName().equals("from:aa to:bb")) {
                Assert.assertTrue(tl.getCoordinateX() == 0.0);
                Assert.assertTrue(tl.getCoordinateY() == 0.0);
                Assert.assertTrue(tl.getMinimumWeight().seconds() == 435345445);
            }
            if (tl.getName().equals("from:bb to:ee")) {
                Assert.assertTrue(tl.getCoordinateX() == 2.0);
                Assert.assertTrue(tl.getCoordinateY() == 0.0);
                Assert.assertTrue(tl.getMinimumWeight().seconds() == 4647);
            }

        }

    }

    @Test
    public void testConstruct() {
        TrafficLight tl = new DataTrafficLight("tl12009",new PointImpl(2,5.12313),"aa","cc",239732623,"test1");
        BTWDataBase b = new BTWDataBaseImpl("test1");
        Road rd = tl.getDestinationRoad();
        Road rs = tl.getSourceRoad();
        Assert.assertNotNull(rd);
        Assert.assertNotNull(rs);
        String s = tl.toString();
        Assert.assertTrue(s.contains("tl12009"));


    }
}
