package il.ac.technion.cs.yp.btw.db;

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
        Assert.assertTrue(a.getRoadName().equals("Road6"));
        Assert.assertTrue(b.getRoadName().equals("Road4"));
        Assert.assertTrue(a.getRoadLength() == 3);
        String s = a.toString();
        String s2 = b.toString();
        Assert.assertTrue(s.contains("Road6"));
        Assert.assertTrue(s2.contains("STR2"));
    }
}
