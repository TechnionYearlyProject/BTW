package il.ac.technion.cs.yp.btw.gui;

import il.ac.technion.cs.yp.btw.citysimulation.CityCrossroad;
import il.ac.technion.cs.yp.btw.citysimulation.CityMap;
import il.ac.technion.cs.yp.btw.citysimulation.CityRoad;
import il.ac.technion.cs.yp.btw.citysimulation.CityTrafficLight;
import il.ac.technion.cs.yp.btw.classes.*;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by shay on 09/01/2018.
 */
public class MapNaiveTest {
    private CityMap cityMap;
    private CityRoad road1;
    private CityRoad road2;
    private CityTrafficLight trafficLight;
    private CityCrossroad crossroad;
    private CityCrossroad crossroad1;
    private CityCrossroad crossroad2;
    private Set<CityRoad> roads;
    private Set<CityTrafficLight> trafficLights;

    void configMock() {

//        ArgumentCaptor<Road> captorR = ArgumentCaptor.forClass(Road.class);
//        Mockito.when(road1.getHeuristicDist(captorR.capture()))
//                .thenAnswer(invocation -> {
//                    System.out.println(captorR.getValue().getRoadName());
//                    return BTWWeight.of(1L);
//                });

        // road1
        Mockito.when(road1.getSourceCrossroad())
                .thenReturn(this.crossroad1);

        Mockito.when(road1.getDestinationCrossroad())
                .thenReturn(this.crossroad);

        // road2
        Mockito.when(road2.getSourceCrossroad())
                .thenReturn(this.crossroad);

        Mockito.when(road2.getDestinationCrossroad())
                .thenReturn(crossroad2);

        // crossroad
        Mockito.when(crossroad.getCoordinateX())
                .thenReturn(0.0);

        Mockito.when(crossroad.getCoordinateY())
                .thenReturn(0.0);

        // crossroad1
        Mockito.when(crossroad1.getCoordinateX())
                .thenReturn(0.0);

        Mockito.when(crossroad1.getCoordinateY())
                .thenReturn(-1.0);

        // crossroad2
        Mockito.when(crossroad2.getCoordinateX())
                .thenReturn(0.0);

        Mockito.when(crossroad2.getCoordinateY())
                .thenReturn(1.0);

        // trafficlight
        Mockito.when(trafficLight.getCoordinateX())
                .thenReturn(0.0);

        Mockito.when(trafficLight.getCoordinateY())
                .thenReturn(0.0);

        Mockito.when(trafficLight.getName())
                .thenReturn("t");

        Mockito.when(trafficLight.getState())
                .thenReturn(CityTrafficLight.TrafficLightState.GREEN);

        // cityMap
        Mockito.when(cityMap.getAllRoads())
                .thenReturn(this.roads);

        Mockito.when(cityMap.getAllTrafficLights())
                .thenReturn(this.trafficLights);
    }

    MapNaiveTest() {
        this.roads = new HashSet<>();
        this.trafficLights = new HashSet<>();
        this.cityMap = Mockito.mock(CityMap.class);
        this.road1 = Mockito.mock(CityRoad.class);
        this.road2 = Mockito.mock(CityRoad.class);
        this.trafficLight = Mockito.mock(CityTrafficLight.class);
        this.crossroad = Mockito.mock(CityCrossroad.class);
        this.roads.add(road1);
        this.roads.add(road2);
        this.trafficLights.add(trafficLight);
        configMock();
    }
/*
    class TestingRoad implements Road {
        private String name;
        private Crossroad source;
        private Crossroad destination;


        TestingRoad(String name, Crossroad src, Crossroad dst) {
            this.name = name;
            this.source = src;
            this.destination = dst;
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
            return null;
        }

        @Override
        public BTWWeight getHeuristicDist(Road road){return null;}

        @Override
        public Crossroad getSourceCrossroad() {
            return source;
        }

        @Override
        public Crossroad getDestinationCrossroad() {
            return destination;
        }
    }

    static class TestingTrafficLight implements TrafficLight {
        private Road src;
        private Road dst;
        private double x;
        private double y;

        TestingTrafficLight(Road src, Road dst, double x, double y) {
            this.src = src;
            this.dst = dst;
            this.x = x;
            this.y = y;
        }

        @Override
        public double getCoordinateX() {
            return x;
        }

        @Override
        public double getCoordinateY() {
            return y;
        }

        @Override
        public String getName() {
            return null;
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
            return null;
        }
    }

    class TestingCrossRoad implements Crossroad {
        private double x;
        private double y;

        TestingCrossRoad(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String getName() {return null;}

        @Override
        public Set<TrafficLight> getTrafficLights() {return null;}

        @Override
        public Set<TrafficLight> getTrafficLightsFromRoad(Road road) {return null;}

        @Override
        public Crossroad addTrafficLight(TrafficLight tl) {return null;}

        @Override
        public double getCoordinateX() {return x;}

        @Override
        public double getCoordinateY() {return y;}
    }
    */

    @Test
    public void simpleTest() {
        DrawMap drawMap = new DrawMap();
        drawMap = drawMap.draw(this.cityMap);
    }


/*
    @Test
    public void testMapDraw() {
        TestingCrossRoad c1 = new TestingCrossRoad(100,0);
        TestingCrossRoad c2 = new TestingCrossRoad(100,500);
        TestingRoad r1 = new TestingRoad("a",c1,c2);
        TestingCrossRoad c3 = new TestingCrossRoad(0,500);
        TestingCrossRoad c4 = new TestingCrossRoad(100,500);
        TestingRoad r2 = new TestingRoad("b",c1,c2);
        TestingTrafficLight l1 = new TestingTrafficLight(r1,r2,100,500);
        HashSet<Road> roads = new HashSet<Road>();
        roads.add(r1);
        roads.add(r2);
        HashSet<TrafficLight> traffics = new HashSet<TrafficLight>();
        traffics.add(l1);
        //MapGraphics map = new MapGraphics(traffics,roads);
        //assert(map.getCircles().size() == 1);
        //assert(map.getLines().size() == 4);
    }
    */
}
