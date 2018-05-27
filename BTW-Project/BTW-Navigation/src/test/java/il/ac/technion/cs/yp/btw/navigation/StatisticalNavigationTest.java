package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.statistics.StatisticsProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.*;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

/**
 * @author Guy Rephaeli
 * @date 27-May-18.
 */
public class StatisticalNavigationTest extends AbstractNavigationTest{
    private Road road9;
    private Road road10;
    private TrafficLight trafficLight2_9;
    private TrafficLight trafficLight9_10;
    private TrafficLight trafficLight10_5;
    private TrafficLight trafficLight10_7;
    private Crossroad crossroad4;


    public StatisticalNavigationTest() {
        super();
        //Crossroads
        crossroad4 = Mockito.mock(Crossroad.class);

        //roads
        road9 = Mockito.mock(Road.class);
        road10 = Mockito.mock(Road.class);

        // traffic lights
        trafficLight2_9 = Mockito.mock(TrafficLight.class);
        trafficLight9_10 = Mockito.mock(TrafficLight.class);
        trafficLight10_5 = Mockito.mock(TrafficLight.class);
        trafficLight10_7 = Mockito.mock(TrafficLight.class);

        trafficLights.add(trafficLight2_9);
        trafficLights.add(trafficLight9_10);
        trafficLights.add(trafficLight10_5);
        trafficLights.add(trafficLight10_7);
    }

    @Override
    protected void configGraphMock() {
        super.configGraphMock();
        ArgumentCaptor<Road> roadCaptor = ArgumentCaptor.forClass(Road.class);
        ArgumentCaptor<BTWTime> timeCaptor = ArgumentCaptor.forClass(BTWTime.class);

        // crossroad2
        Mockito.when(crossroad2.getTrafficLightsFromRoad(road2))
                .thenAnswer( invocation -> {
                    Set<TrafficLight> trafficLights = new HashSet<>();
                    trafficLights.add(trafficLight2_4);
                    trafficLights.add(trafficLight2_9);
                    return trafficLights;
                });

        // crossroad3
        Mockito.when(crossroad3.getTrafficLightsFromRoad(road10))
                .thenAnswer( invocation -> {
                    Set<TrafficLight> trafficLights = new HashSet<>();
                    trafficLights.add(trafficLight10_5);
                    trafficLights.add(trafficLight10_7);
                    return trafficLights;
                });

        // crossroad4
        Mockito.when(crossroad4.getTrafficLightsFromRoad(road9))
                .thenAnswer( invocation -> {
                    Set<TrafficLight> trafficLights = new HashSet<>();
                    trafficLights.add(trafficLight9_10);
                    return trafficLights;
                });

        // road3
        Mockito.when(road3.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(50));

        // road4
        Mockito.when(road4.getWeightByTime(timeCaptor.capture()))
                .thenAnswer(invocation -> {
                    if (timeCaptor.getValue().seconds() < 3) {
                        return BTWWeight.of(1);
                    }
                    return BTWWeight.of(4);
                });

        // road9
        Mockito.when(road9.getRoadName())
                .thenReturn("9");

        Mockito.when(road9.getMinimumWeight())
                .thenReturn(BTWWeight.of(1));

        Mockito.when(road9.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(road9.getHeuristicDist(roadCaptor.capture()))
                .thenAnswer(invocation ->
                        BTWWeight.of(
                                this.heuristics
                                        .get("9")
                                        .get(roadCaptor
                                                .getValue()
                                                .getRoadName())));

        Mockito.when(road9.getSourceCrossroad())
                .thenReturn(crossroad2);

        Mockito.when(road9.getDestinationCrossroad())
                .thenReturn(crossroad4);

        // road10
        Mockito.when(road10.getRoadName())
                .thenReturn("10");

        Mockito.when(road10.getMinimumWeight())
                .thenReturn(BTWWeight.of(1));

        Mockito.when(road10.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(road10.getHeuristicDist(roadCaptor.capture()))
                .thenAnswer(invocation ->
                        BTWWeight.of(
                                this.heuristics
                                        .get("10")
                                        .get(roadCaptor
                                                .getValue()
                                                .getRoadName())));

        Mockito.when(road10.getSourceCrossroad())
                .thenReturn(crossroad4);

        Mockito.when(road10.getDestinationCrossroad())
                .thenReturn(crossroad3);

        // trafficLight2_9
        Mockito.when(trafficLight2_9.getName())
                .thenReturn("2_9");

        Mockito.when(trafficLight2_9.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        Mockito.when(trafficLight2_9.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(trafficLight2_9.getSourceRoad())
                .thenReturn(road2);

        Mockito.when(trafficLight2_9.getDestinationRoad())
                .thenReturn(road9);

        // trafficLight9_10
        Mockito.when(trafficLight9_10.getName())
                .thenReturn("9_10");

        Mockito.when(trafficLight9_10.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        Mockito.when(trafficLight9_10.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(trafficLight9_10.getSourceRoad())
                .thenReturn(road9);

        Mockito.when(trafficLight9_10.getDestinationRoad())
                .thenReturn(road10);

        // trafficLight10_5
        Mockito.when(trafficLight10_5.getName())
                .thenReturn("10_5");

        Mockito.when(trafficLight10_5.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        Mockito.when(trafficLight10_5.getWeightByTime(timeCaptor.capture()))
                .thenAnswer(invocation -> {
                    Long secs = timeCaptor.getValue().seconds();
                    if (secs > 7) {
                        return BTWWeight.of(100);
                    }
                    return BTWWeight.of(1);
                });

        Mockito.when(trafficLight10_5.getSourceRoad())
                .thenReturn(road10);

        Mockito.when(trafficLight10_5.getDestinationRoad())
                .thenReturn(road5);

        // trafficLight10_7
        Mockito.when(trafficLight10_7.getName())
                .thenReturn("10_7");

        Mockito.when(trafficLight10_7.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        Mockito.when(trafficLight10_7.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(trafficLight10_7.getSourceRoad())
                .thenReturn(road10);

        Mockito.when(trafficLight10_7.getDestinationRoad())
                .thenReturn(road7);
    }


    @Test
    public void testSimpleRoute() {
        NavigationManager manager = new StatisticalNavigationManager(db);
        List<String> expectedRoute = new ArrayList<>();
        expectedRoute.add("1");
        expectedRoute.add("2");
        expectedRoute.add("4");
        expectedRoute.add("5");
        Navigator navigator = manager.getNavigator(null, road1, 0.0, road5, 0.0, BTWTime.of(0));
        Iterator<String> expectedRoad = expectedRoute.iterator();
        while (! navigator.hasArrived()) {
            Road nextRoad = navigator.getNextRoad();
            Assert.assertEquals(expectedRoad.next(), nextRoad.getRoadName());
        }
        Assert.assertFalse(expectedRoad.hasNext());
    }

    @Test
    public void testCycle() {
        NavigationManager manager = new StatisticalNavigationManager(db);
        thrown.expect(PathNotFoundException.class);
        thrown.expectMessage("A cycle was encountered");
        Navigator navigator = manager.getNavigator(null, road1, 0.0, road5, 0.0, BTWTime.of(2));
    }

    @Test
    public void testAlteredRoute() {
        NavigationManager manager = new StatisticalNavigationManager(db);
        List<String> expectedRoute = new ArrayList<>();
        expectedRoute.add("1");
        expectedRoute.add("2");
        expectedRoute.add("9");
        expectedRoute.add("10");
        expectedRoute.add("5");
        Navigator navigator = manager.getNavigator(null, road1, 0.0, road5, 0.0, BTWTime.of(1));
        Iterator<String> expectedRoad = expectedRoute.iterator();
        while (! navigator.hasArrived()) {
            Road nextRoad = navigator.getNextRoad();
            Assert.assertEquals(expectedRoad.next(), nextRoad.getRoadName());
        }
        Assert.assertFalse(expectedRoad.hasNext());
    }
}
