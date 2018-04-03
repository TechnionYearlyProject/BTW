package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataTrafficLight;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataCrossRoad;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataRoad;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.*;

public class TestGeoJasonToJavaParser {

    private static String geoJson = "{\"type\": \"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[0.0014190683853251293,0.014645366930914668]},\"properties\":{\"name\":\"from:3 StreetR to:3 Street\",\"overload\":0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[0.01635651734518988,0.008806996592190556],[0.014402113034462271,0.006739623239087599]]},\"properties\":{\"name\":\"31 StreetR\",\"length\":\"316\",\"overload\":0}}]}";
    private Set<TrafficLight> trafficLights;
    private Set<Crossroad> crossRoads;
    private Set<Road> roads;
    private String mapName = "test";


    @Test
    public void testConstructorIsNotNull(){
        GeoJasonToJavaParser parser = new GeoJasonToJavaParser(mapName, geoJson);
        assertNotNull(parser);
    }

    @Test
    public void testTrafficLightsNotNull(){
        GeoJasonToJavaParser parser = new GeoJasonToJavaParser(mapName, geoJson);
        assertNotNull(parser.getTrafficLights());
    }

    @Test
    public void testTrafficLightsCorrect(){
        GeoJasonToJavaParser parser = new GeoJasonToJavaParser(mapName, geoJson);
        assertNotNull(parser.getTrafficLights());
        Iterator<TrafficLight> trafficLightsIterator = parser.getTrafficLights().iterator();
        DataTrafficLight dataTrafficLight = (DataTrafficLight)(trafficLightsIterator.next());
        assertNotNull(dataTrafficLight);
        assertEquals("from:3 StreetR to:3 Street", dataTrafficLight.getName());
        assertEquals("3 StreetR", dataTrafficLight.getSourceRoadName());
        assertEquals("3 Street", dataTrafficLight.getDestinationRoadName());
        assertEquals(0.0014190683853251293, dataTrafficLight.getCoordinateX(), 0.0000000000000000000001);
        assertEquals(0.014645366930914668, dataTrafficLight.getCoordinateY(), 0.0000000000000000000001);
        assertEquals(1, parser.getTrafficLights().size());

    }
    @Test
    public void testCrossRoadsNotNull() {
        GeoJasonToJavaParser parser = new GeoJasonToJavaParser(mapName, geoJson);
        assertNotNull(parser.getTrafficLights());
    }

    @Test
    public void testCrossRoadsCorrect(){
        GeoJasonToJavaParser parser = new GeoJasonToJavaParser(mapName, geoJson);
        assertNotNull(parser.getTrafficLights());
        Iterator<Crossroad> crossRoadsIterator = parser.getCrossRoads().iterator();
        DataCrossRoad dataCrossroad = (DataCrossRoad)(crossRoadsIterator.next());
        assertNotNull(dataCrossroad);
        assertEquals(0.0014190683853251293, dataCrossroad.getCoordinateX(), 0.0000000000000000000001);
        assertEquals(0.014645366930914668, dataCrossroad.getCoordinateY(), 0.0000000000000000000001);
        assertEquals(1, parser.getCrossRoads().size());

    }

    @Test
    public void testRoadsNotNull() {
        GeoJasonToJavaParser parser = new GeoJasonToJavaParser(mapName, geoJson);
        assertNotNull(parser.getRoads());
    }

    @Test
    public void testRoadsCorrect() {
        GeoJasonToJavaParser parser = new GeoJasonToJavaParser(mapName, geoJson);
        assertNotNull(parser.getRoads());
        Iterator<Road> roadsIterator = parser.getRoads().iterator();
        DataRoad dataRoad = (DataRoad)(roadsIterator.next());
        assertNotNull(dataRoad);
        assertEquals( "31 StreetR",dataRoad.getRoadName());
        assertEquals(316, dataRoad.getRoadLength());
        assertEquals("31", dataRoad.getStreetName());
        assertEquals(0.01635651734518988, dataRoad.getSourceCrossroadPosition().getCoordinateX(), 0.0000000000000000000001);
        assertEquals(0.008806996592190556, dataRoad.getSourceCrossroadPosition().getCoordinateY(), 0.0000000000000000000001);
        assertEquals(0.014402113034462271, dataRoad.getDestinationCrossroadPosition().getCoordinateX(), 0.0000000000000000000001);
        assertEquals(0.006739623239087599, dataRoad.getDestinationCrossroadPosition().getCoordinateY(), 0.0000000000000000000001);
    }


}