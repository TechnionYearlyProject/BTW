package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.Road;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Guy Rephaeli
 * @date 19-Jun-18.
 *
 * Testing class for NaiveNavigator
 */
public class NaiveNavigatorTest {
    private Road r1;
    private Road r2;
    private Road r3;
    private Navigator tested;

    public NaiveNavigatorTest() {
        this.r1 = Mockito.mock(Road.class);
        this.r2 = Mockito.mock(Road.class);
        this.r3 = Mockito.mock(Road.class);
    }

    @Before
    public void setUp() {
        List<Road> route = Arrays.asList(this.r1, this.r2, this.r3);
        this.tested = new NaiveNavigator(route);
    }

    @Test
    public void testDestination() {
        Assert.assertEquals(this.r3, this.tested.getDestination());
    }

    @Test
    public void testNext() {
        Assert.assertFalse(this.tested.hasArrived());
        Assert.assertEquals(this.r1, this.tested.getNextRoad());
        Assert.assertFalse(this.tested.hasArrived());
        Assert.assertEquals(this.r2, this.tested.getNextRoad());
        Assert.assertFalse(this.tested.hasArrived());
        Assert.assertEquals(this.r3, this.tested.getNextRoad());
        Assert.assertTrue(this.tested.hasArrived());
    }

    @Test
    public void testShowNextRoads() {
        List<Road> firstTwo = this.tested.showNextRoads(2);
        Assert.assertEquals(2, firstTwo.size());
        Assert.assertEquals(r1, firstTwo.get(0));
        Assert.assertEquals(r2, firstTwo.get(1));

        List<Road> all = this.tested.showNextRoads(10);
        Assert.assertEquals(3, all.size());
        Assert.assertEquals(r1, all.get(0));
        Assert.assertEquals(r2, all.get(1));
        Assert.assertEquals(r3, all.get(2));

        this.tested.getNextRoad();
        List<Road> nextTwo = this.tested.showNextRoads(2);
        Assert.assertEquals(2, nextTwo.size());
        Assert.assertEquals(r2, nextTwo.get(0));
        Assert.assertEquals(r3, nextTwo.get(1));

        List<Road> rest = this.tested.showNextRoads(10);
        Assert.assertEquals(2, rest.size());
        Assert.assertEquals(r2, rest.get(0));
        Assert.assertEquals(r3, rest.get(1));
    }
}
