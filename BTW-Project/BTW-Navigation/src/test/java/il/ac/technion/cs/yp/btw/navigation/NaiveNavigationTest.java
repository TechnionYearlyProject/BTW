package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.*;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;

/**
 * @author Guy Rephaeli
 * Testing the implementation of NaiveNavigationManager and NaiveNavigator
 */
public class NaiveNavigationTest extends AbstractNavigationTest {
    public NaiveNavigationTest() {
        super();
    }

    @Override
    protected void configGraphMock() {
        super.configGraphMock();

        // crossroad2
        Mockito.when(crossroad2.getTrafficLightsFromRoad(road2))
                .thenAnswer( invocation -> {
                    Set<TrafficLight> trafficLights = new HashSet<>();
                    trafficLights.add(trafficLight2_4);
                    return trafficLights;
                });

        // road3
        Mockito.when(road3.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        // road4
        Mockito.when(road4.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));
    }

    @Test
    public void testUniformWeights() {
        NavigationManager manager = new NaiveNavigationManager(db);
        List<String> expectedRoute = new ArrayList<>();
        expectedRoute.add("1");
        expectedRoute.add("3");
        expectedRoute.add("5");
        Navigator navigator = manager.getNavigator(null, road1, 0.0, road5, 0.0, BTWTime.of(0));
        Iterator<String> expectedRoad = expectedRoute.iterator();
        while (! navigator.hasArrived()) {
            Road nextRoad = navigator.getNextRoad();
            Assert.assertEquals(expectedRoad.next(), nextRoad.getName());
        }
        Assert.assertFalse(expectedRoad.hasNext());
    }

    @Test
    public void testNoPath() {
        NavigationManager manager = new NaiveNavigationManager(db);
        thrown.expect(PathNotFoundException.class);
        thrown.expectMessage("No path from 2 to 3");
        Navigator navigator = manager.getNavigator(null, road2, 0.0, road3, 0.0, BTWTime.of(0));
    }

    @Test
    public void testDifferentWeights() {
        Mockito.when(road3.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(2));
        Mockito.when(trafficLight1_3.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(2));
        Mockito.when(trafficLight3_5.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(2));
        NavigationManager manager = new NaiveNavigationManager(db);
        List<String> expectedRoute = new ArrayList<>();
        expectedRoute.add("1");
        expectedRoute.add("2");
        expectedRoute.add("4");
        expectedRoute.add("5");
        Navigator navigator = manager.getNavigator(null, road1, 0.0, road5, 0.0, BTWTime.of(0));
        Iterator<String> expectedRoad = expectedRoute.iterator();
        while (! navigator.hasArrived()) {
            Road nextRoad = navigator.getNextRoad();
            String expected = expectedRoad.next();
            Assert.assertEquals(expected, nextRoad.getName());
        }
        Assert.assertFalse(expectedRoad.hasNext());
    }
}
