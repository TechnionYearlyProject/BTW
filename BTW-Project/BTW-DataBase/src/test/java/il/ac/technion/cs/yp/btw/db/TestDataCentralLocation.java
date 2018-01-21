package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.CentralLocation;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataCentralLocation;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by shay on 15/01/2018.
 */
public class TestDataCentralLocation {
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    @Test
    public void testCentralLocation() {
        Point a = new PointImpl(3,4);
        Point b = new PointImpl(1,2);
        Set<Point> points = new HashSet<Point>();
        points.add(a);
        points.add(b);
        CentralLocation cl = new DataCentralLocation(points,"Bank","Namir","try");
        String s = cl.toString();
        String sn = cl.getName();
        Assert.assertTrue(s.contains(sn));

    }
}
