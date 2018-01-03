package il.ac.technion.cs.yp.btw.navigation;
import il.ac.technion.cs.yp.btw.classes.*;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

public class BTWNavigatorImpTest {
    private Map<String, Map<String, Long>> heuristics;
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
    private Crossroad crossroad1;
    private Crossroad crossroad2;
    private Crossroad crossroad3;


    class TestingDB implements BTWDataBase {

        @Override
        public Set<TrafficLight> getAllTrafficLights() {
            return new HashSet<>(Arrays.asList(
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
        }

        @Override
        public Street getStreetByName(String streetName) {
            return null;
        }

        @Override
        public CentralLocation getCentralLocationByName(String locationName) {
            return null;
        }

        @Override
        public Set<Road> getAllRoadsNextToCentralLocation(CentralLocation centralLocation) {
            return null;
        }

        @Override
        public BTWDataBase updateWeight() {
            return this;
        }

        @Override
        public BTWDataBase saveMap(String geoJson) {
            return this;
        }

        @Override
        public BTWDataBase updateHeuristics() {
            heuristics = BTWGraphInfo.calculateHeuristics(this);
            return this;
        }
    }

    static class TestingTrafficLight implements TrafficLight {
        private String name;
        private Road src;
        private Road dst;
        private BTWWeight weight;

        TestingTrafficLight(String name, Road src, Road dst, BTWWeight weight) {
            this.name = name;
            this.src = src;
            this.dst = dst;
            this.weight = weight;
        }

        TrafficLight update(BTWWeight weight) {
            this.weight = weight;
            return this;
        }

        @Override
        public double getCoordinateX() {
            return 0;
        }

        @Override
        public double getCoordinateY() {
            return 0;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Road getSourceRoad() {
            return src;
        }

        @Override
        public Road getDestinationRoad() {
            return dst;
        }

        @Override
        public BTWWeight getWeightByTime(BTWTime time) {
            return null;
        }

        @Override
        public BTWWeight getMinimumWeight() {
            return weight;
        }

        @Override
        public BTWWeight getCurrentWeight() {
            return null;
        }
    }

    class TestingRoad implements Road {
        private String name;
        private BTWWeight weight;
        private Crossroad source;
        private Crossroad destination;

        TestingRoad(String name, BTWWeight weight, Crossroad src, Crossroad dst) {
            this.name = name;
            this.weight = weight;
            this.source = src;
            this.destination = dst;
        }

        Road update(BTWWeight weight) {
            this.weight = weight;
            return this;
        }

        @Override
        public boolean isStreetNumberInRange(int streetNumber) {
            return false;
        }

        @Override
        public int getRoadLength() {
            return 0;
        }

        @Override
        public String getRoadName() {
            return name;
        }

        @Override
        public Street getStreet() {
            return null;
        }

        @Override
        public BTWWeight getWeightByTime(BTWTime time) {
            return null;
        }

        @Override
        public BTWWeight getMinimumWeight() {
            return weight;
        }

        @Override
        public BTWWeight getHeuristicDist(Road road){
            try {
                return BTWWeight.of(heuristics.get(name).get(road.getRoadName()));
            } catch (BTWIllegalTimeException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public Crossroad getSourceCrossroad() {
            return source;
        }

        @Override
        public Crossroad getDestinationCrossroad() {
            return destination;
        }
    }

    public BTWNavigatorImpTest() {
        try {
            configGraphMock();
        } catch (BTWIllegalTimeException e) {
            throw new RuntimeException(e);
        }
        heuristics = new HashMap<>();
    }

    private void configGraphMock() throws BTWIllegalTimeException {
        // Data Base
        db = new TestingDB();

        //Crossroads
        crossroad1 = Mockito.mock(Crossroad.class);
        crossroad2 = Mockito.mock(Crossroad.class);
        crossroad3 = Mockito.mock(Crossroad.class);

        //roads
        road1 = new TestingRoad("1", BTWWeight.of(1), null, crossroad1);
        road2 = new TestingRoad("2", BTWWeight.of(1), crossroad1, crossroad2);
        road3 = new TestingRoad("3", BTWWeight.of(1), crossroad1, crossroad3);
        road4 = new TestingRoad("4", BTWWeight.of(1), crossroad2, crossroad3);
        road5 = new TestingRoad("5", BTWWeight.of(1), crossroad3, null);
        road6 = new TestingRoad("6", BTWWeight.of(1), crossroad2, crossroad1);
        road7 = new TestingRoad("7", BTWWeight.of(1), crossroad3, crossroad1);
        road8 = new TestingRoad("8", BTWWeight.of(1), crossroad3, crossroad2);

        // traffic lights
        trafficLight1_2 = new TestingTrafficLight("1_2", road1, road2, BTWWeight.of(1));
        trafficLight1_3 = new TestingTrafficLight("1_3", road1, road3, BTWWeight.of(1));
        trafficLight6_3 = new TestingTrafficLight("6_3", road6, road3, BTWWeight.of(1));
        trafficLight7_2 = new TestingTrafficLight("7_2", road7, road2, BTWWeight.of(1));
        trafficLight2_4 = new TestingTrafficLight("2_4", road2, road4, BTWWeight.of(1));
        trafficLight8_6 = new TestingTrafficLight("8_6", road8, road6, BTWWeight.of(1));
        trafficLight4_5 = new TestingTrafficLight("4_5", road4, road5, BTWWeight.of(1));
        trafficLight3_5 = new TestingTrafficLight("3_5", road3, road5, BTWWeight.of(1));
        trafficLight3_8 = new TestingTrafficLight("3_8", road3, road8, BTWWeight.of(1));
        trafficLight4_7 = new TestingTrafficLight("4_7", road4, road7, BTWWeight.of(1));

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
        Mockito.when(crossroad2.getTrafficLightsFromRoad(road2))
                .thenAnswer( invocation -> {
                    Set<TrafficLight> trafficLights = new HashSet<>();
                    trafficLights.add(trafficLight2_4);
                    return trafficLights;
                });

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
    }



    @Before
    public void setUp() {
        heuristics = new HashMap<>();
    }

    @Test
    public void testUniformWeights() {
        navigator = new BTWNavigatorImp(db);
        try {
            List<String> path = navigator.navigate(road1, road5)
                    .stream()
                    .map(Road::getRoadName)
                    .collect(Collectors.toList());
            Assert.assertArrayEquals(new String[]{"1", "3", "5"}, path.toArray());
        } catch (PathNotFoundException e) {
            Assert.fail();
        }

    }

    @Test
    public void testDifferentWeights() {
        try {
            road3 = ((TestingRoad)road3).update(BTWWeight.of(2));
            trafficLight1_3 = ((TestingTrafficLight)trafficLight1_3).update(BTWWeight.of(2));
            trafficLight3_5 = ((TestingTrafficLight)trafficLight3_5).update(BTWWeight.of(2));
            navigator = new BTWNavigatorImp(db);
            List<String> path = navigator.navigate(road1, road5)
                    .stream()
                    .map(Road::getRoadName)
                    .collect(Collectors.toList());
            Assert.assertArrayEquals(new String[]{"1", "2", "4", "5"}, path.toArray());
        } catch (PathNotFoundException | BTWIllegalTimeException e) {
            Assert.fail();
        }
    }
}