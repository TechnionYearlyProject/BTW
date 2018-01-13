package il.ac.technion.cs.yp.btw.classes;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Testing CentralLocation
 */
public class CentralLocationTest {
    private Set<Point> vertices;

    public CentralLocationTest() {
        this.vertices = new HashSet<>();
        Point point1 = new PointImpl(0.1, -0.1);
        Point point2 = new PointImpl(1, -1);
        this.vertices.add(point1);
        this.vertices.add(point2);
    }

    @Test
    public void getVerticesTest() {
        CentralLocation tested = new CentralLocation("Tested");
        Assert.assertEquals(new HashSet<>(), tested.getVertices());
        tested = new CentralLocation(this.vertices, "Tested");
        Assert.assertEquals(this.vertices, tested.getVertices());
    }

    @Test
    public void toStringTest() {
        CentralLocation tested = new CentralLocation("Tested");
        Assert.assertEquals("", tested.toString());
        tested = new CentralLocation(this.vertices, "Tested");
        Assert.assertEquals("il.ac.technion.cs.yp.btw.classes.PointImpl@bff00000 il.ac.technion.cs.yp.btw.classes.PointImpl@f82a6e9d ", tested.toString());
    }

    @Test
    public void getNameTest() {
        CentralLocation tested = new CentralLocation("Tested");
        Assert.assertEquals("Tested", tested.getName());
    }
}
