package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.*;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

/**
 * @author Guy Rephaeli
 * @date 27-May-18.
 */
public class AbstractNavigationTest {
    protected Map<String, Map<String, Long>> heuristics;
    protected BTWDataBase db;
    protected Road road1;
    protected Road road2;
    protected Road road3;
    protected Road road4;
    protected Road road5;
    protected Road road6;
    protected Road road7;
    protected Road road8;
    protected TrafficLight trafficLight1_2;
    protected TrafficLight trafficLight1_3;
    protected TrafficLight trafficLight6_3;
    protected TrafficLight trafficLight7_2;
    protected TrafficLight trafficLight2_4;
    protected TrafficLight trafficLight8_6;
    protected TrafficLight trafficLight4_5;
    protected TrafficLight trafficLight3_5;
    protected TrafficLight trafficLight3_8;
    protected TrafficLight trafficLight4_7;
    protected Crossroad crossroad1;
    protected Crossroad crossroad2;
    protected Crossroad crossroad3;
    protected Set<TrafficLight> trafficLights;


    protected AbstractNavigationTest() {
        // Data Base
        db = Mockito.mock(BTWDataBase.class);

        //Crossroads
        crossroad1 = Mockito.mock(Crossroad.class);
        crossroad2 = Mockito.mock(Crossroad.class);
        crossroad3 = Mockito.mock(Crossroad.class);

        //roads
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

        trafficLights = new HashSet<>(
                Arrays.asList(
                        trafficLight1_2,
                        trafficLight1_3,
                        trafficLight6_3,
                        trafficLight7_2,
                        trafficLight2_4,
                        trafficLight8_6,
                        trafficLight4_5,
                        trafficLight3_5,
                        trafficLight3_8,
                        trafficLight4_7));

        this.heuristics = new HashMap<>();
    }

    protected void configGraphMock() {
        ArgumentCaptor<Road> roadCaptor = ArgumentCaptor.forClass(Road.class);

        // db
        Mockito.when(db.updateHeuristics())
                .thenAnswer(invocation -> {
                    heuristics = BTWGraphInfo.calculateHeuristics(db);
                    return db;
                });

        Mockito.when(db.getAllTrafficLights())
                .thenReturn(this.trafficLights);

        // crossroad1
        Mockito.when(crossroad1.getTrafficLightsFromRoad(road1))
                .thenAnswer( invocation -> {
                    Set<TrafficLight> trafficLights = new HashSet<>();
                    trafficLights.add(trafficLight1_2);
                    trafficLights.add(trafficLight1_3);
                    return trafficLights;
                });

        Mockito.when(crossroad1.getTrafficLightsFromRoad(road6))
                .thenAnswer( invocation -> {
                    Set<TrafficLight> trafficLights = new HashSet<>();
                    trafficLights.add(trafficLight6_3);
                    return trafficLights;
                });

        Mockito.when(crossroad1.getTrafficLightsFromRoad(road7))
                .thenAnswer( invocation -> {
                    Set<TrafficLight> trafficLights = new HashSet<>();
                    trafficLights.add(trafficLight7_2);
                    return trafficLights;
                });

        // crossroad2
        Mockito.when(crossroad2.getTrafficLightsFromRoad(road8))
                .thenAnswer( invocation -> {
                    Set<TrafficLight> trafficLights = new HashSet<>();
                    trafficLights.add(trafficLight8_6);
                    return trafficLights;
                });

        // crossroad3
        Mockito.when(crossroad3.getTrafficLightsFromRoad(road3))
                .thenAnswer( invocation -> {
                    Set<TrafficLight> trafficLights = new HashSet<>();
                    trafficLights.add(trafficLight3_5);
                    trafficLights.add(trafficLight3_8);
                    return trafficLights;
                });

        Mockito.when(crossroad3.getTrafficLightsFromRoad(road4))
                .thenAnswer( invocation -> {
                    Set<TrafficLight> trafficLights = new HashSet<>();
                    trafficLights.add(trafficLight4_5);
                    trafficLights.add(trafficLight4_7);
                    return trafficLights;
                });

        // road1
        Mockito.when(road1.getRoadName())
                .thenReturn("1");

        Mockito.when(road1.getMinimumWeight())
                .thenReturn(BTWWeight.of(1));

        Mockito.when(road1.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(road1.getHeuristicDist(roadCaptor.capture()))
                .thenAnswer(invocation ->
                        BTWWeight.of(
                                this.heuristics
                                        .get("1")
                                        .get(roadCaptor
                                                .getValue()
                                                .getRoadName())));

        Mockito.when(road1.getSourceCrossroad())
                .thenReturn(null);

        Mockito.when(road1.getDestinationCrossroad())
                .thenReturn(crossroad1);

        // road2
        Mockito.when(road2.getRoadName())
                .thenReturn("2");

        Mockito.when(road2.getMinimumWeight())
                .thenReturn(BTWWeight.of(1));

        Mockito.when(road2.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(road2.getHeuristicDist(roadCaptor.capture()))
                .thenAnswer(invocation ->
                        BTWWeight.of(
                                this.heuristics
                                        .get("2")
                                        .get(roadCaptor
                                                .getValue()
                                                .getRoadName())));

        Mockito.when(road2.getSourceCrossroad())
                .thenReturn(crossroad1);

        Mockito.when(road2.getDestinationCrossroad())
                .thenReturn(crossroad2);

        // road3
        Mockito.when(road3.getRoadName())
                .thenReturn("3");

        Mockito.when(road3.getMinimumWeight())
                .thenReturn(BTWWeight.of(1));

        Mockito.when(road3.getHeuristicDist(roadCaptor.capture()))
                .thenAnswer(invocation ->
                        BTWWeight.of(
                                this.heuristics
                                        .get("3")
                                        .get(roadCaptor
                                                .getValue()
                                                .getRoadName())));

        Mockito.when(road3.getSourceCrossroad())
                .thenReturn(crossroad1);

        Mockito.when(road3.getDestinationCrossroad())
                .thenReturn(crossroad3);

        // road4
        Mockito.when(road4.getRoadName())
                .thenReturn("4");

        Mockito.when(road4.getMinimumWeight())
                .thenReturn(BTWWeight.of(1));

        Mockito.when(road4.getHeuristicDist(roadCaptor.capture()))
                .thenAnswer(invocation ->
                        BTWWeight.of(
                                this.heuristics
                                        .get("4")
                                        .get(roadCaptor
                                                .getValue()
                                                .getRoadName())));

        Mockito.when(road4.getSourceCrossroad())
                .thenReturn(crossroad2);

        Mockito.when(road4.getDestinationCrossroad())
                .thenReturn(crossroad3);

        // road5
        Mockito.when(road5.getRoadName())
                .thenReturn("5");

        Mockito.when(road5.getMinimumWeight())
                .thenReturn(BTWWeight.of(1));

        Mockito.when(road5.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(road5.getHeuristicDist(roadCaptor.capture()))
                .thenAnswer(invocation ->
                        BTWWeight.of(
                                this.heuristics
                                        .get("5")
                                        .get(roadCaptor
                                                .getValue()
                                                .getRoadName())));

        Mockito.when(road5.getSourceCrossroad())
                .thenReturn(crossroad3);

        Mockito.when(road5.getDestinationCrossroad())
                .thenReturn(null);

        // road6
        Mockito.when(road6.getRoadName())
                .thenReturn("6");

        Mockito.when(road6.getMinimumWeight())
                .thenReturn(BTWWeight.of(1));

        Mockito.when(road6.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(road6.getHeuristicDist(roadCaptor.capture()))
                .thenAnswer(invocation ->
                        BTWWeight.of(
                                this.heuristics
                                        .get("6")
                                        .get(roadCaptor
                                                .getValue()
                                                .getRoadName())));

        Mockito.when(road6.getSourceCrossroad())
                .thenReturn(crossroad2);

        Mockito.when(road6.getDestinationCrossroad())
                .thenReturn(crossroad1);

        // road7
        Mockito.when(road7.getRoadName())
                .thenReturn("7");

        Mockito.when(road7.getMinimumWeight())
                .thenReturn(BTWWeight.of(1));

        Mockito.when(road7.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(road7.getHeuristicDist(roadCaptor.capture()))
                .thenAnswer(invocation ->
                        BTWWeight.of(
                                this.heuristics
                                        .get("7")
                                        .get(roadCaptor
                                                .getValue()
                                                .getRoadName())));

        Mockito.when(road7.getSourceCrossroad())
                .thenReturn(crossroad3);

        Mockito.when(road7.getDestinationCrossroad())
                .thenReturn(crossroad1);

        // road8
        Mockito.when(road8.getRoadName())
                .thenReturn("8");

        Mockito.when(road8.getMinimumWeight())
                .thenReturn(BTWWeight.of(1));

        Mockito.when(road8.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(road8.getHeuristicDist(roadCaptor.capture()))
                .thenAnswer(invocation ->
                        BTWWeight.of(
                                this.heuristics
                                        .get("8")
                                        .get(roadCaptor
                                                .getValue()
                                                .getRoadName())));

        Mockito.when(road8.getSourceCrossroad())
                .thenReturn(crossroad3);

        Mockito.when(road8.getDestinationCrossroad())
                .thenReturn(crossroad2);


        // trafficLight1_2
        Mockito.when(trafficLight1_2.getName())
                .thenReturn("1_2");

        Mockito.when(trafficLight1_2.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        Mockito.when(trafficLight1_2.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(trafficLight1_2.getSourceRoad())
                .thenReturn(road1);

        Mockito.when(trafficLight1_2.getDestinationRoad())
                .thenReturn(road2);

        // trafficLight1_3
        Mockito.when(trafficLight1_3.getName())
                .thenReturn("1_3");

        Mockito.when(trafficLight1_3.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        Mockito.when(trafficLight1_3.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(trafficLight1_3.getSourceRoad())
                .thenReturn(road1);

        Mockito.when(trafficLight1_3.getDestinationRoad())
                .thenReturn(road3);

        // trafficLight6_3
        Mockito.when(trafficLight6_3.getName())
                .thenReturn("6_3");

        Mockito.when(trafficLight6_3.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        Mockito.when(trafficLight6_3.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(trafficLight6_3.getSourceRoad())
                .thenReturn(road6);

        Mockito.when(trafficLight6_3.getDestinationRoad())
                .thenReturn(road3);

        // trafficLight7_2
        Mockito.when(trafficLight7_2.getName())
                .thenReturn("7_2");

        Mockito.when(trafficLight7_2.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        Mockito.when(trafficLight7_2.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(trafficLight7_2.getSourceRoad())
                .thenReturn(road7);

        Mockito.when(trafficLight7_2.getDestinationRoad())
                .thenReturn(road2);

        // trafficLight2_4
        Mockito.when(trafficLight2_4.getName())
                .thenReturn("2_4");

        Mockito.when(trafficLight2_4.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        Mockito.when(trafficLight2_4.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(trafficLight2_4.getSourceRoad())
                .thenReturn(road2);

        Mockito.when(trafficLight2_4.getDestinationRoad())
                .thenReturn(road4);

        // trafficLight8_6
        Mockito.when(trafficLight8_6.getName())
                .thenReturn("8_6");

        Mockito.when(trafficLight8_6.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        Mockito.when(trafficLight8_6.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(trafficLight8_6.getSourceRoad())
                .thenReturn(road8);

        Mockito.when(trafficLight8_6.getDestinationRoad())
                .thenReturn(road6);

        // trafficLight4_5
        Mockito.when(trafficLight4_5.getName())
                .thenReturn("4_5");

        Mockito.when(trafficLight4_5.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        Mockito.when(trafficLight4_5.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(trafficLight4_5.getSourceRoad())
                .thenReturn(road4);

        Mockito.when(trafficLight4_5.getDestinationRoad())
                .thenReturn(road5);

        // trafficLight3_5
        Mockito.when(trafficLight3_5.getName())
                .thenReturn("3_5");

        Mockito.when(trafficLight3_5.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        Mockito.when(trafficLight3_5.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(trafficLight3_5.getSourceRoad())
                .thenReturn(road3);

        Mockito.when(trafficLight3_5.getDestinationRoad())
                .thenReturn(road5);

        // trafficLight3_8
        Mockito.when(trafficLight3_8.getName())
                .thenReturn("3_8");

        Mockito.when(trafficLight3_8.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        Mockito.when(trafficLight3_8.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(trafficLight3_8.getSourceRoad())
                .thenReturn(road3);

        Mockito.when(trafficLight3_8.getDestinationRoad())
                .thenReturn(road8);

        // trafficLight4_7
        Mockito.when(trafficLight4_7.getName())
                .thenReturn("4_7");

        Mockito.when(trafficLight4_7.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        Mockito.when(trafficLight4_7.getWeightByTime(any()))
                .thenReturn(BTWWeight.of(1));

        Mockito.when(trafficLight4_7.getSourceRoad())
                .thenReturn(road4);

        Mockito.when(trafficLight4_7.getDestinationRoad())
                .thenReturn(road7);

    }

    @Before
    public void setUp() {
        heuristics = new HashMap<>();
        configGraphMock();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();
}
