package il.ac.technion.cs.yp.btw.navigation;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.classes.Weight;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.*;
import java.util.stream.Collectors;

public class BTWNavigatorImpTest {
    private BTWNavigator navigator;
    private BTWDataBase db;
    private Road road1;
    private Road road2;
    private Road road3;
    private Road road4;
    private Road road5;
    private Road road6;
    private Road road7;
    private Road road8;
    private TrafficLight trafficLight1_2;
    private TrafficLight trafficLight1_3;
    private TrafficLight trafficLight6_3;
    private TrafficLight trafficLight7_2;
    private TrafficLight trafficLight2_4;
    private TrafficLight trafficLight8_6;
    private TrafficLight trafficLight4_5;
    private TrafficLight trafficLight3_5;
    private TrafficLight trafficLight3_8;
    private TrafficLight trafficLight4_7;
    private Weight w1;
    private Weight w2;
    private Weight w3;
    private Weight w4;
    private Weight w5;
    private Weight w6;
    private Weight w7;
    private Weight w8;
    private Weight w1_2;
    private Weight w1_3;
    private Weight w6_3;
    private Weight w7_2;
    private Weight w2_4;
    private Weight w8_6;
    private Weight w4_5;
    private Weight w3_5;
    private Weight w3_8;
    private Weight w4_7;

    private Set<Weight> initializedWeights;

    public BTWNavigatorImpTest() {
        configGraphMock();
        initializedWeights = new HashSet<>();
    }

    private void initializeWeight(Weight w, Long value) {
        if (! initializedWeights.contains(w)) {
            Mockito.when(w.getWeightValue()).thenReturn(value);
            initializedWeights.add(w);
        }
    }


    private void configGraphMock() {
        // Data Base
        db = Mockito.mock(BTWDataBase.class);

        // weighs
        w1 = Mockito.mock(Weight.class);
        w2 = Mockito.mock(Weight.class);
        w3 = Mockito.mock(Weight.class);
        w4 = Mockito.mock(Weight.class);
        w5 = Mockito.mock(Weight.class);
        w6 = Mockito.mock(Weight.class);
        w7 = Mockito.mock(Weight.class);
        w8 = Mockito.mock(Weight.class);
        w1_2 = Mockito.mock(Weight.class);
        w1_3 = Mockito.mock(Weight.class);
        w6_3 = Mockito.mock(Weight.class);
        w7_2 = Mockito.mock(Weight.class);
        w2_4 = Mockito.mock(Weight.class);
        w8_6 = Mockito.mock(Weight.class);
        w4_5 = Mockito.mock(Weight.class);
        w3_5 = Mockito.mock(Weight.class);
        w3_8 = Mockito.mock(Weight.class);
        w4_7 = Mockito.mock(Weight.class);

        // roads
        road1 = Mockito.mock(Road.class);
        road2 = Mockito.mock(Road.class);
        road3 = Mockito.mock(Road.class);
        road4 = Mockito.mock(Road.class);
        road5 = Mockito.mock(Road.class);
        road6 = Mockito.mock(Road.class);
        road7 = Mockito.mock(Road.class);
        road8 = Mockito.mock(Road.class);

        // traffic lights
        trafficLight1_2 = Mockito.mock(TrafficLight.class);
        trafficLight1_3 = Mockito.mock(TrafficLight.class);
        trafficLight6_3 = Mockito.mock(TrafficLight.class);
        trafficLight7_2 = Mockito.mock(TrafficLight.class);
        trafficLight2_4 = Mockito.mock(TrafficLight.class);
        trafficLight8_6 = Mockito.mock(TrafficLight.class);
        trafficLight4_5 = Mockito.mock(TrafficLight.class);
        trafficLight3_5 = Mockito.mock(TrafficLight.class);
        trafficLight3_8 = Mockito.mock(TrafficLight.class);
        trafficLight4_7 = Mockito.mock(TrafficLight.class);

        //Data Base
        Mockito.when(db.getAllTrafficLights()).thenReturn(new HashSet<>(Arrays.asList(
                trafficLight1_2,
                trafficLight1_3,
                trafficLight6_3,
                trafficLight7_2,
                trafficLight2_4,
                trafficLight8_6,
                trafficLight4_5,
                trafficLight3_5,
                trafficLight3_8,
                trafficLight4_7)));

        // roads
        Mockito.when(road1.getRoadName()).thenReturn("1");
        Mockito.when(road1.getMinimumWeight()).thenReturn(w1);

        Mockito.when(road2.getRoadName()).thenReturn("2");
        Mockito.when(road2.getMinimumWeight()).thenReturn(w2);

        Mockito.when(road3.getRoadName()).thenReturn("3");
        Mockito.when(road3.getMinimumWeight()).thenReturn(w3);

        Mockito.when(road4.getRoadName()).thenReturn("4");
        Mockito.when(road4.getMinimumWeight()).thenReturn(w4);

        Mockito.when(road5.getRoadName()).thenReturn("5");
        Mockito.when(road5.getMinimumWeight()).thenReturn(w5);

        Mockito.when(road6.getRoadName()).thenReturn("6");
        Mockito.when(road6.getMinimumWeight()).thenReturn(w6);

        Mockito.when(road7.getRoadName()).thenReturn("7");
        Mockito.when(road7.getMinimumWeight()).thenReturn(w7);

        Mockito.when(road8.getRoadName()).thenReturn("8");
        Mockito.when(road8.getMinimumWeight()).thenReturn(w8);

        // traffic lights
        Mockito.when(trafficLight1_2.getName()).thenReturn("1_2");
        Mockito.when(trafficLight1_2.getMinimumWeight()).thenReturn(w1_2);
        Mockito.when(trafficLight1_2.getSourceRoad()).thenReturn(road1);
        Mockito.when(trafficLight1_2.getDestinationRoad()).thenReturn(road2);

        Mockito.when(trafficLight1_3.getName()).thenReturn("1_3");
        Mockito.when(trafficLight1_3.getMinimumWeight()).thenReturn(w1_3);
        Mockito.when(trafficLight1_3.getSourceRoad()).thenReturn(road1);
        Mockito.when(trafficLight1_3.getDestinationRoad()).thenReturn(road3);

        Mockito.when(trafficLight6_3.getName()).thenReturn("6_3");
        Mockito.when(trafficLight6_3.getMinimumWeight()).thenReturn(w6_3);
        Mockito.when(trafficLight6_3.getSourceRoad()).thenReturn(road6);
        Mockito.when(trafficLight6_3.getDestinationRoad()).thenReturn(road3);

        Mockito.when(trafficLight7_2.getName()).thenReturn("7_2");
        Mockito.when(trafficLight7_2.getMinimumWeight()).thenReturn(w7_2);
        Mockito.when(trafficLight7_2.getSourceRoad()).thenReturn(road7);
        Mockito.when(trafficLight7_2.getDestinationRoad()).thenReturn(road2);

        Mockito.when(trafficLight2_4.getName()).thenReturn("2_4");
        Mockito.when(trafficLight2_4.getMinimumWeight()).thenReturn(w2_4);
        Mockito.when(trafficLight2_4.getSourceRoad()).thenReturn(road2);
        Mockito.when(trafficLight2_4.getDestinationRoad()).thenReturn(road4);

        Mockito.when(trafficLight8_6.getName()).thenReturn("8_6");
        Mockito.when(trafficLight8_6.getMinimumWeight()).thenReturn(w8_6);
        Mockito.when(trafficLight8_6.getSourceRoad()).thenReturn(road8);
        Mockito.when(trafficLight8_6.getDestinationRoad()).thenReturn(road6);

        Mockito.when(trafficLight4_5.getName()).thenReturn("4_5");
        Mockito.when(trafficLight4_5.getMinimumWeight()).thenReturn(w4_5);
        Mockito.when(trafficLight4_5.getSourceRoad()).thenReturn(road4);
        Mockito.when(trafficLight4_5.getDestinationRoad()).thenReturn(road5);

        Mockito.when(trafficLight3_5.getName()).thenReturn("3_5");
        Mockito.when(trafficLight3_5.getMinimumWeight()).thenReturn(w3_5);
        Mockito.when(trafficLight3_5.getSourceRoad()).thenReturn(road3);
        Mockito.when(trafficLight3_5.getDestinationRoad()).thenReturn(road5);

        Mockito.when(trafficLight3_8.getName()).thenReturn("3_8");
        Mockito.when(trafficLight3_8.getMinimumWeight()).thenReturn(w3_8);
        Mockito.when(trafficLight3_8.getSourceRoad()).thenReturn(road3);
        Mockito.when(trafficLight3_8.getDestinationRoad()).thenReturn(road8);

        Mockito.when(trafficLight4_7.getName()).thenReturn("4_7");
        Mockito.when(trafficLight4_7.getMinimumWeight()).thenReturn(w4_7);
        Mockito.when(trafficLight4_7.getSourceRoad()).thenReturn(road4);
        Mockito.when(trafficLight4_7.getDestinationRoad()).thenReturn(road7);
    }

    private void configWeightsMock() {
        // weighs
        initializeWeight(w1, 1L);
        initializeWeight(w2, 1L);
        initializeWeight(w3, 1L);
        initializeWeight(w4, 1L);
        initializeWeight(w5, 1L);
        initializeWeight(w6, 1L);
        initializeWeight(w7, 1L);
        initializeWeight(w8, 1L);

        initializeWeight(w1_2, 1L);
        initializeWeight(w1_3, 1L);
        initializeWeight(w6_3, 1L);
        initializeWeight(w7_2, 1L);
        initializeWeight(w2_4, 1L);
        initializeWeight(w8_6, 1L);
        initializeWeight(w4_5, 1L);
        initializeWeight(w3_5, 1L);
        initializeWeight(w3_8, 1L);
        initializeWeight(w4_7, 1L);
    }

    @Before
    public void setUp() {
        initializedWeights = new HashSet<>();
    }

    @Test
    public void testUniformWeights() {
        configWeightsMock();
        navigator = new BTWNavigatorImp(db);
        List<String> path = navigator.navigate(road1, road5)
                .stream()
                .map(Road::getRoadName)
                .collect(Collectors.toList());
        Assert.assertArrayEquals(path.toArray(), new String[]{"1", "3", "5"});
    }

    @Test
    public void testDifferentWeights() {
        initializeWeight(w3, 2L);
        initializeWeight(w1_3, 2L);
        initializeWeight(w3_5, 2L);
        configWeightsMock();
        navigator = new BTWNavigatorImp(db);
        List<String> path = navigator.navigate(road1, road5)
                .stream()
                .map(Road::getRoadName)
                .collect(Collectors.toList());
        Assert.assertArrayEquals(path.toArray(), new String[]{"1", "2", "4", "5"});
    }
}