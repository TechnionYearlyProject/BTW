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
import java.util.HashSet;
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
    private Street eachOneStreet;
    private Set<Street> eachOneStreets;

    /*Objects for fourth simulator*/
    private Road multiRoad1;
    private Road multiRoad2;
    private Set<Road> multiRoads;

    private TrafficLight multiTrafficLight1;
    private TrafficLight multiTrafficLight2;
    private Set<TrafficLight> multiTraffics;

    private CentralLocation multiCentralLocation1;
    private CentralLocation multiCentralLocation2;
    private Set<CentralLocation> multiCentrals;

    private Street multiStreet1;
    private Street multiStreet2;
    private Set<Street> multiStreets;

    /*
     *Setup for tests
     */
    public GeoJsonParserTests() throws BTWIllegalTimeException {

        /************First simulator*********/
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

        /************Second simulator*********/
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

        /************Third simulator*********/

        /************Fourth simulator*********/

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

    /*@Test
    public void oneFromEveryObjectMapTest (){
        GeoJsonParserImpl geoJsonParser = new GeoJsonParserImpl();
        File emptyFile = geoJsonParser.buildGeoJsonFromSimulation(simulatorOneFromEachObject);
        Assert.assertEquals(emptyFile.toString(),oneFromEveryObjectMap);
    }*/

   /* @Test
    public void multipleFromEveryObjectMapTest (){
        GeoJsonParserImpl geoJsonParser = new GeoJsonParserImpl();
        File emptyFile = geoJsonParser.buildGeoJsonFromSimulation(simulatorMultiple);
        Assert.assertEquals(emptyFile.toString(),multipleFromEveryObjectMap);
    }*/

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
