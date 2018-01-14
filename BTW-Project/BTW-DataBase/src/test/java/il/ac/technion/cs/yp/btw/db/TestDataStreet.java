package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataStreet;
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
    @Test
    public void testGet() {
        Road r1 = new TestingRoad("r1",null,null);
        Road r2 = new TestingRoad("r2",null,null);
        Set<Road> roads = new HashSet<Road>();
        roads.add(r1);
        roads.add(r2);
        Street street = new DataStreet("Alenbi",roads,"try");
        assert(street.getAllRoadsInStreet().size()==2);
        assert(street.getStreetName().equals("Alenbi"));

    }
}
