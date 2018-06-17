package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataStreet;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by shay on 14/01/2018.
 */
public class TestDataStreet {
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
        public String getName() {
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

        /*
        * @Author Sharon Hadar
        * @Date 2/6/2018
        * @return the current speed on the road
        * */
        @Override
        public double getSpeed(){
            return 0.0;
        }

        /*
         * @Author Sharon Hadar
         * @Date 2/6/2018
         * @return the current overload on the road
         * */
        @Override
        public Double getOverload(){
            return 0.0;
        }
    }
    @Test
    public void testGet() {
        Road r1 = new TestingRoad("r1",null,null);
        Road r2 = new TestingRoad("r2",null,null);
        Road r3 = new TestingRoad("r3",null,null);
        Set<Road> roads = new HashSet<Road>();
        roads.add(r1);
        roads.add(r2);
        Street street = new DataStreet("Alenbi",roads,"try");
        Street street2 = new DataStreet("Rotchild","try");
        street.addRoad(r3);
        Assert.assertTrue(street2.getAllRoadsInStreet().size()==0);
        Assert.assertTrue(street.getAllRoadsInStreet().size()==3);
        Assert.assertTrue(street.getStreetName().equals("Alenbi"));
        String s = street.toString();
        String s2 = street2.toString();
        Assert.assertTrue(s.contains("Alenbi"));
        Assert.assertTrue(s2.contains("Rotchild"));

    }
}
