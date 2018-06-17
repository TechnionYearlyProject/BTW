package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.BTWTime;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataRoad;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by shay on 15/01/2018.
 */
public class TestDataRoad {
    @Test
    public void testConstructing() {
        // first constructor
        Road a = new DataRoad("Road6",3,"STR1",new PointImpl(0,0),new PointImpl(6.6,6.6),"try");
        // second constructor
        Road b = new DataRoad("Road4",43346,"STR2",new PointImpl(11.32,77.234),new PointImpl(6.6,6.6),0,342,235,"try");
        Assert.assertTrue(b.isStreetNumberInRange(100));
        Assert.assertFalse(b.isStreetNumberInRange(1000));
        Assert.assertTrue(a.getName().equals("Road6"));
        Assert.assertTrue(b.getName().equals("Road4"));
        Assert.assertTrue(a.getRoadLength() == 3);
        String s = a.toString();
        String s2 = b.toString();
        Assert.assertTrue(s.contains("Road6"));
        Assert.assertTrue(s2.contains("STR2"));
        Assert.assertNull(((DataRoad) a).getWeights());
        Assert.assertNotNull(((DataRoad) a).getDistances());
        Assert.assertTrue(a.getSpeed()>-1 && a.getSpeed() <1);
        Assert.assertTrue(a.getOverload()>-1 && a.getOverload() <1);
        Assert.assertTrue(s2.contains("STR2"));
        System.out.println(a.getWeightByTime(BTWTime.of(0)).seconds());
    }
}
