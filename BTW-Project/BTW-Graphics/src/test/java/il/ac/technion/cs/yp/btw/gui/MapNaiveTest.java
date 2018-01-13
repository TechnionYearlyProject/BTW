package il.ac.technion.cs.yp.btw.gui;

import il.ac.technion.cs.yp.btw.classes.*;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by shay on 09/01/2018.
 */
public class MapNaiveTest {

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
        MapGraphics map = new MapGraphics(traffics,roads);
        assert(map.getCircles().size() == 1);
        assert(map.getLines().size() == 4);
    }
}
