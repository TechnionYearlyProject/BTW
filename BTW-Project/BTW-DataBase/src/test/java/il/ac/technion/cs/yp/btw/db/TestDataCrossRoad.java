package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataCrossRoad;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataRoad;
import org.junit.Assert;
import org.junit.Test;
import java.util.Iterator;
import java.util.Set;

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
//        TrafficLight tl1 = new TestingTrafficLight(0.12312,2.2342340);
//        TrafficLight tl2 = new TestingTrafficLight(0.12313,2.2342940);
//        TrafficLight tl3 = new TestingTrafficLight(0.12362,2.2382340);
//        Crossroad cr = new DataCrossRoad(new PointImpl(0.12312,2.2342340),new HashSet<>(),"mapName");
//        Assert.assertTrue(cr.getTrafficLights().size() == 0);
//        cr.addTrafficLight(tl1);
//        Assert.assertTrue(cr.getTrafficLights().size() == 1);
//        cr.addTrafficLight(tl2);
//        cr.addTrafficLight(tl3);
//        Assert.assertTrue(cr.getTrafficLights().size() == 3);
//        String s = cr.toString();
//        Assert.assertTrue(s.contains("(0.12312,2.234234)"));
//        BTWDataBase b = new BTWDataBaseImpl("test");
//        Crossroad cros = new DataCrossRoad(new PointImpl(0,0),"test");
//        Assert.assertNull(cros.getName());
//        Assert.assertNull(cros.getTrafficLightsFromRoad(new DataRoad("Road6",3,"STR1",new PointImpl(0,0),new PointImpl(6.6,6.6),"try")));
//        Assert.assertNotNull(cros.getTrafficLights());
    }

    @Test
    public void testDataCrossRoad() {
        BTWDataBase db = new BTWDataBaseImpl("anat_simulate");
        String s = "{\"type\": \"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0]},\"properties\":{\"name\":\"from:1 avenue section 1R to:1 avenue section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0]},\"properties\":{\"name\":\"from:2 avenue section 1R to:2 avenue section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0]},\"properties\":{\"name\":\"from:2 avenue section 1R to:1 street section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0]},\"properties\":{\"name\":\"from:1 street section 1R to:1 street section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0054000054000054]},\"properties\":{\"name\":\"from:2 street section 1R to:2 street section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0]},\"properties\":{\"name\":\"from:1 street section 1 to:1 street section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0]},\"properties\":{\"name\":\"from:1 avenue section 1R to:1 street section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0054000054000054]},\"properties\":{\"name\":\"from:2 avenue section 1 to:2 street section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0054000054000054]},\"properties\":{\"name\":\"from:2 avenue section 1 to:2 avenue section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0054000054000054]},\"properties\":{\"name\":\"from:1 avenue section 1 to:2 street section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0]},\"properties\":{\"name\":\"from:1 street section 1 to:2 avenue section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0054000054000054]},\"properties\":{\"name\":\"from:2 street section 1 to:2 avenue section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0]},\"properties\":{\"name\":\"from:1 street section 1R to:1 avenue section 1\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0054000054000054,0.0054000054000054]},\"properties\":{\"name\":\"from:2 street section 1 to:2 street section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0054000054000054]},\"properties\":{\"name\":\"from:2 street section 1R to:1 avenue section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0,0.0054000054000054]},\"properties\":{\"name\":\"from:1 avenue section 1 to:1 avenue section 1R\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0,0.0054000054000054],[0.0,0.0]]},\"properties\":{\"name\":\"1 avenue section 1R\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0054000054000054,0.0054000054000054],[0.0,0.0054000054000054]]},\"properties\":{\"name\":\"2 street section 1R\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0054000054000054,0.0054000054000054],[0.0054000054000054,0.0]]},\"properties\":{\"name\":\"2 avenue section 1R\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0,0.0],[0.0054000054000054,0.0]]},\"properties\":{\"name\":\"1 street section 1\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0,0.0],[0.0,0.0054000054000054]]},\"properties\":{\"name\":\"1 avenue section 1\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0,0.0054000054000054],[0.0054000054000054,0.0054000054000054]]},\"properties\":{\"name\":\"2 street section 1\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0054000054000054,0.0],[0.0054000054000054,0.0054000054000054]]},\"properties\":{\"name\":\"2 avenue section 1\",\"length\":\"600\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.0054000054000054,0.0],[0.0,0.0]]},\"properties\":{\"name\":\"1 street section 1R\",\"length\":\"600\",\"overload\":0}}]}";
        db.parseMap(s);
        Set<Crossroad> crossroads = db.getAllCrossroads();
        Set<Road> roads = db.getAllRoads();
        for (Crossroad crossroad : crossroads) {
            Assert.assertNotNull(crossroad.getName());
            Assert.assertNotNull(crossroad.getTrafficLights());
            for (Road road : roads) {
                if (road.getDestinationCrossroad().equals(crossroad)) {
                    Assert.assertNotNull(crossroad.getTrafficLightsFromRoad(road));
                }
            }
        }
        MainDataBase.openConnection();
        Point position = new PointImpl(0.0,2.0);
        //TrafficLight t = TrafficLightsDataBase.getAllTrafficLights("mapName");
        Crossroad tl1 = CrossRoadsDataBase.getCrossRoad(position, "mapName");
        System.out.println(tl1.toString());
        MainDataBase.closeConnection();
    }
/*
    @Test
    public void testGetAllCrossRoad() {
        MainDataBase.openConnection();
        String mapName = "mapName";
        Set<Crossroad> crossroads = CrossRoadsDataBase.getAllCrossRoads(mapName);
        Iterator<Crossroad> iterator = crossroads.iterator();
        while(iterator.hasNext()){
            Crossroad crossroad= iterator.next();
            Assert.assertNotNull(crossroad);
        }
        MainDataBase.closeConnection();
    }
    */
}
