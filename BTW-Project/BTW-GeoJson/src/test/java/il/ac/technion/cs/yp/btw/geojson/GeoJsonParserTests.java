package il.ac.technion.cs.yp.btw.geojson;

import il.ac.technion.cs.yp.btw.citysimulation.CityRoad;
import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.mapgeneration.FreeFormMapSimulator;
import il.ac.technion.cs.yp.btw.mapgeneration.GridCityMapSimulator;
import il.ac.technion.cs.yp.btw.mapgeneration.MapSimulator;
import il.ac.technion.cs.yp.btw.mapgeneration.objects.MapSimulationCrossroadImpl;
import il.ac.technion.cs.yp.btw.mapgeneration.objects.MapSimulationRoadImpl;
import il.ac.technion.cs.yp.btw.mapgeneration.objects.MapSimulationStreetImpl;
import org.junit.*;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by anat ana on 14/01/2018.
 */
public class GeoJsonParserTests {

    public String oneRoadMap = "";

    public String oneTrafficLightMap = "";

    public String oneFromEveryObjectMap = "";

    public String multipleFromEveryObjectMap = "";

    private MapSimulator simulatorOneRoad;
    private MapSimulator simulatorOneTrafficLight;
    private MapSimulator simulatorOneFromEachObject;
    private MapSimulator simulatorMultiple;

    /*Objects for first simulator*/
    private Road oneRoad;
    private Set<Road> oneRoads;

    /*Objects for second simulator*/
    private TrafficLight oneTrafficLight;
    private Set<TrafficLight> oneTraffics;

    /*Objects for third simulator*/
    private Road eachOneRoad;
    private Set<Road> eachOneRoads;
    private TrafficLight eachOneTrafficLight;
    private Set<TrafficLight> eachOneTraffics;
    private CentralLocation eachOneCentralLocation;
    private Set<CentralLocation> eachOneCentrals;
    private  Set<Point> pointsOfCentralLocation = null;
    private Street eachOneStreet;
    private Set<Street> eachOneStreets;

    /*Objects for fourth simulator*/
    private Road multiRoad1;
    private Road multiRoad2;
    private Set<Road> multiRoads;

    private TrafficLight multiTrafficLight1;
    private TrafficLight multiTrafficLight2;
    private Set<TrafficLight> multiTrafficLights;

    private CentralLocation multiCentralLocation1;
    private CentralLocation multiCentralLocation2;
    private  Set<Point> pointsOfCentralLocation1 = null;
    private  Set<Point> pointsOfCentralLocation2 = null;
    private Set<CentralLocation> multiCentrals;

    private Street multiStreet1;
    private Street multiStreet2;
    private Set<Street> multiStreets;

    /*
     *Setup for tests
     */
    public GeoJsonParserTests() throws BTWIllegalTimeException {

        /************Setup variables for First simulator*********/
        simulatorOneRoad = Mockito.mock(MapSimulator.class);
        oneRoad = Mockito.mock(Road.class);
        Mockito.when(oneRoad.getRoadName())
                .thenReturn("road1");
        Mockito.when(oneRoad.getSourceCrossroad().getCoordinateX())
                .thenReturn(6.0);
        Mockito.when(oneRoad.getSourceCrossroad().getCoordinateY())
                .thenReturn(7.2);
        Mockito.when(oneRoad.getDestinationCrossroad().getCoordinateX())
                .thenReturn(5.0);
        Mockito.when(oneRoad.getDestinationCrossroad().getCoordinateY())
                .thenReturn(5.0);
        Mockito.when(oneRoad.getRoadLength())
                .thenReturn(2);
        Mockito.when(oneRoad.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        oneRoads = new HashSet<>();
        oneRoads.add(oneRoad);
        Mockito.when(simulatorOneRoad.getRoads())
                .thenAnswer(invocation -> this.oneRoads);

        /************Setup variables for Second simulator*********/
        simulatorOneTrafficLight = Mockito.mock(MapSimulator.class);
        oneTrafficLight = Mockito.mock(TrafficLight.class);
        Mockito.when(oneTrafficLight.getCoordinateX())
                .thenReturn(4.5);
        Mockito.when(oneTrafficLight.getCoordinateY())
                .thenReturn(4.5);
        Mockito.when(oneTrafficLight.getName())
                .thenReturn("From roze st to Eve st");
        Mockito.when(oneTrafficLight.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        oneTraffics = new HashSet<>();
        oneTraffics.add(oneTrafficLight);
        Mockito.when(simulatorOneTrafficLight.getRoads())
                .thenAnswer(invocation -> this.oneTraffics);

        /************Setup variables for Third simulator*********/
        simulatorOneFromEachObject = Mockito.mock(MapSimulator.class);

        //Traffic light
        eachOneTrafficLight = Mockito.mock(TrafficLight.class);
        Mockito.when(eachOneTrafficLight.getCoordinateX())
                .thenReturn(3.2);
        Mockito.when(eachOneTrafficLight.getCoordinateY())
                .thenReturn(3.2);
        Mockito.when(eachOneTrafficLight.getName())
                .thenReturn("From roze st to Eve st");
        Mockito.when(eachOneTrafficLight.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        //Road
        eachOneRoad = Mockito.mock(Road.class);
        Mockito.when(eachOneRoad.getRoadName())
                .thenReturn("eachOneRoad1");
        Mockito.when(eachOneRoad.getSourceCrossroad().getCoordinateX())
                .thenReturn(6.0);
        Mockito.when(eachOneRoad.getSourceCrossroad().getCoordinateY())
                .thenReturn(7.2);
        Mockito.when(eachOneRoad.getDestinationCrossroad().getCoordinateX())
                .thenReturn(5.0);
        Mockito.when(eachOneRoad.getDestinationCrossroad().getCoordinateY())
                .thenReturn(5.0);
        Mockito.when(eachOneRoad.getRoadLength())
                .thenReturn(2);
        Mockito.when(eachOneRoad.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        //Central location
        eachOneCentralLocation = Mockito.mock(CentralLocation.class);

        pointsOfCentralLocation.add(new PointImpl(3.0,4.0));
        pointsOfCentralLocation.add(new PointImpl(4.0,5.0));
        pointsOfCentralLocation.add(new PointImpl(3.0,5.0));
        pointsOfCentralLocation.add(new PointImpl(4.0,4.0));

        Mockito.when(eachOneCentralLocation.getVertices()).
                thenReturn(pointsOfCentralLocation);
        Mockito.when(eachOneCentralLocation.getName()).
                thenReturn("Empire building");

        //Street
        eachOneStreet = Mockito.mock(Street.class);
        Mockito.when(eachOneStreet.getAllRoadsInStreet()).
                thenReturn(null);
        Mockito.when(eachOneStreet.getStreetName()).
                thenReturn("Roze st 3-4");


        /************Setup variables for Fourth simulator*********/
        simulatorMultiple = Mockito.mock(MapSimulator.class);

        //Traffic light **1**
        multiTrafficLight1 = Mockito.mock(TrafficLight.class);
        Mockito.when(multiTrafficLight1.getCoordinateX())
                .thenReturn(3.1);
        Mockito.when(multiTrafficLight1.getCoordinateY())
                .thenReturn(3.1);
        Mockito.when(multiTrafficLight1.getName())
                .thenReturn("From roze st to Eve st");
        Mockito.when(multiTrafficLight1.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        //Traffic light **2**
        multiTrafficLight2 = Mockito.mock(TrafficLight.class);
        Mockito.when(multiTrafficLight2.getCoordinateX())
                .thenReturn(4.5);
        Mockito.when(multiTrafficLight2.getCoordinateY())
                .thenReturn(4.9);
        Mockito.when(multiTrafficLight2.getName())
                .thenReturn("From liz st to Eve st");
        Mockito.when(multiTrafficLight2.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        //Road **1**
        multiRoad1 = Mockito.mock(Road.class);
        Mockito.when(multiRoad1.getRoadName())
                .thenReturn("eachOneRoad1");
        Mockito.when(multiRoad1.getSourceCrossroad().getCoordinateX())
                .thenReturn(6.0);
        Mockito.when(multiRoad1.getSourceCrossroad().getCoordinateY())
                .thenReturn(7.2);
        Mockito.when(multiRoad1.getDestinationCrossroad().getCoordinateX())
                .thenReturn(5.0);
        Mockito.when(multiRoad1.getDestinationCrossroad().getCoordinateY())
                .thenReturn(5.0);
        Mockito.when(multiRoad1.getRoadLength())
                .thenReturn(2);
        Mockito.when(multiRoad1.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        //Road **2**
        multiRoad2 = Mockito.mock(Road.class);
        Mockito.when(multiRoad2.getRoadName())
                .thenReturn("eachOneRoad2");
        Mockito.when(multiRoad2.getSourceCrossroad().getCoordinateX())
                .thenReturn(5.0);
        Mockito.when(multiRoad2.getSourceCrossroad().getCoordinateY())
                .thenReturn(8.2);
        Mockito.when(multiRoad2.getDestinationCrossroad().getCoordinateX())
                .thenReturn(1.0);
        Mockito.when(multiRoad2.getDestinationCrossroad().getCoordinateY())
                .thenReturn(2.0);
        Mockito.when(multiRoad2.getRoadLength())
                .thenReturn(2);
        Mockito.when(multiRoad2.getMinimumWeight())
                .thenReturn(BTWWeight.of(0));

        //Central location **1**
        multiCentralLocation1 = Mockito.mock(CentralLocation.class);

        pointsOfCentralLocation1.add(new PointImpl(3.0,4.0));
        pointsOfCentralLocation1.add(new PointImpl(4.0,5.0));
        pointsOfCentralLocation1.add(new PointImpl(3.0,5.0));
        pointsOfCentralLocation1.add(new PointImpl(4.0,4.0));

        Mockito.when(multiCentralLocation1.getVertices()).
                thenReturn(pointsOfCentralLocation1);
        Mockito.when(multiCentralLocation1.getName()).
                thenReturn("Empire building");

        //Central location **2**
        multiCentralLocation2 = Mockito.mock(CentralLocation.class);

        pointsOfCentralLocation2.add(new PointImpl(1.0,4.0));
        pointsOfCentralLocation2.add(new PointImpl(1.0,5.0));
        pointsOfCentralLocation2.add(new PointImpl(2.0,5.0));
        pointsOfCentralLocation2.add(new PointImpl(2.0,4.0));

        Mockito.when(multiCentralLocation2.getVertices()).
                thenReturn(pointsOfCentralLocation2);
        Mockito.when(multiCentralLocation2.getName()).
                thenReturn("Post office building");

        //Street **1**
        multiStreet1 = Mockito.mock(Street.class);
        Mockito.when(multiStreet1.getAllRoadsInStreet()).
                thenReturn(null);
        Mockito.when(multiStreet1.getStreetName()).
                thenReturn("Roze st 3-4");

        //Street **2**
        multiStreet2 = Mockito.mock(Street.class);
        Mockito.when(multiStreet2.getAllRoadsInStreet()).
                thenReturn(null);
        Mockito.when(multiStreet2.getStreetName()).
                thenReturn("liz st 3-4");

    }

    /*
     *Initial test - checks that the file creation is working
     */
    @Test
    public void AppTest()
    {
        GridCityMapSimulator mapSimulator = new GridCityMapSimulator();
        mapSimulator.build();
        GeoJsonParserImpl geoJsonParser = new GeoJsonParserImpl();
        File emptyFile = geoJsonParser.buildGeoJsonFromSimulation(mapSimulator);
    }

    @Test
    public void oneRoadMapTest (){
        GeoJsonParserImpl geoJsonParser = new GeoJsonParserImpl();
        File emptyFile = geoJsonParser.buildGeoJsonFromSimulation(simulatorOneRoad);
        Assert.assertEquals(emptyFile.toString(),oneRoadMap);
    }

    @Test
    public void oneTrafficLightMapTest (){
        GeoJsonParserImpl geoJsonParser = new GeoJsonParserImpl();
        File emptyFile = geoJsonParser.buildGeoJsonFromSimulation(simulatorOneTrafficLight);
        Assert.assertEquals(emptyFile.toString(),oneTrafficLightMap);
    }

    @Test
    public void oneFromEveryObjectMapTest (){
        GeoJsonParserImpl geoJsonParser = new GeoJsonParserImpl();
        File emptyFile = geoJsonParser.buildGeoJsonFromSimulation(simulatorOneFromEachObject);
        Assert.assertEquals(emptyFile.toString(),oneFromEveryObjectMap);
    }

    @Test
    public void multipleFromEveryObjectMapTest (){
        GeoJsonParserImpl geoJsonParser = new GeoJsonParserImpl();
        File emptyFile = geoJsonParser.buildGeoJsonFromSimulation(simulatorMultiple);
        Assert.assertEquals(emptyFile.toString(),multipleFromEveryObjectMap);
    }

    /*
    * Clean created GeoJson file after each test have finished.
    * */
    @After
    public void clean(){
        try {
            Files.deleteIfExists(Paths.get("C:\\Users\\anat ana\\Yearly Project\\BTW\\BTWProject\\BTW-GeoJson\\JsonFile.json"));
        } catch (IOException e) {
            System.out.println("Error deleting geoJson file");
            e.printStackTrace();
        }
    }
}
