package il.ac.technion.cs.yp.btw.classes;

import org.junit.Test;

import static org.junit.Assert.*;

public class PointTest {
    @Test
    public void testGetCoordinateX(){
        Point p = new PointImpl(1.0,2.0);
        assertEquals(1.0,p.getCoordinateX(),0.001);
    }
    @Test
    public void testGetCoordinateY(){
        Point p = new PointImpl(1.0,2.0);
        assertEquals(2.0,p.getCoordinateY(),0.001);
    }
    @Test
    public void testGetLocation(){
        Point p = new PointImpl(1.0,2.0);
        assertEquals(new PointImpl(1.0,2.0),p);
    }
    @Test
    public void testCopyConstructor(){
        Point p = new PointImpl(1.0,2.0);
        assertEquals(new PointImpl(p),p);
    }
    @Test
    public void testHashCode(){
        Point p1 = new PointImpl(1.0,2.0);
        Point p2 = new PointImpl(1.0,2.0);
        assertEquals(p1.hashCode(),p2.hashCode());
        p1 = new PointImpl(5.0,7.0);
        p2 = new PointImpl(5.0,7.0);
        assertEquals(p1.hashCode(),p2.hashCode());
    }
    @Test
    public void testEquals(){
        Point p1 = new PointImpl(1.0,2.0);
        Point p2 = new PointImpl(2.0,1.0);
        assertEquals(true,p1.equals(p1));
        assertEquals(false,p2.equals(new Object()));
        assertEquals(false,p2.equals(p1));
        assertEquals(p1.equals(p2),p2.equals(p1));
    }
}