package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataCrossRoad;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

/**
 * Created by shay on 15/01/2018.
 */
public class TestDataCrossRoad {

    static class TestingTrafficLight implements TrafficLight {
        private double x;
        private double y;

        TestingTrafficLight(double x, double y) {
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
            return null;
        }

        @Override
        public Road getDestinationRoad() {
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
    }

    @Test
    public void testDataCrossRoads() {
        TrafficLight tl1 = new TestingTrafficLight(0.12312,2.2342340);
        TrafficLight tl2 = new TestingTrafficLight(0.12313,2.2342940);
        TrafficLight tl3 = new TestingTrafficLight(0.12362,2.2382340);
        Crossroad cr = new DataCrossRoad(new PointImpl(0.12312,2.2342340),new HashSet<>(),"try");
        Assert.assertTrue(cr.getTrafficLights().size() == 0);
        cr.addTrafficLight(tl1);
        Assert.assertTrue(cr.getTrafficLights().size() == 1);
        cr.addTrafficLight(tl2);
        cr.addTrafficLight(tl3);
        Assert.assertTrue(cr.getTrafficLights().size() == 3);
        String s = cr.toString();
        Assert.assertTrue(s.contains("(0.12312,2.234234)"));
    }
}
