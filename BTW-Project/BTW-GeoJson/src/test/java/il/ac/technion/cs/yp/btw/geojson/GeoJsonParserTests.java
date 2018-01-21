package il.ac.technion.cs.yp.btw.geojson;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.mapgeneration.GridCityMapSimulator;
import il.ac.technion.cs.yp.btw.mapgeneration.MapSimulator;
import org.junit.*;
import org.mockito.Mockito;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by anat ana on 14/01/2018.
 */

/*
* Important - delete JsonFile.json before every test.
* */

public class GeoJsonParserTests {

    public String oneRoadMap = "{\"type\": \"FeatureCollection\",\"features\":[{\"type\":\"Feature\","+
            "\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[6.0,7.2],"+
            "[5.0,5.0]]},\"properties\":{\"name\":\"road1\",\"length\":\"2\",\"overload\":0}}]}";

    public String oneTrafficLightMap = "{\"type\": \"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"geometry\":{\"type\":"+
            "\"Point\",\"coordinates\":[4.5,4.5]},\"properties\":{\"name\":\"From roze st to Eve st\",\"overload\":0}}]}";;


    private MapSimulator simulatorOneRoad;
    private MapSimulator simulatorOneTrafficLight;

    /*Objects for first simulator*/
    private Road oneRoad;
    private Set<Road> oneRoads;

    /*Objects for second simulator*/
    private TrafficLight oneTrafficLight;
    private Set<TrafficLight> oneTraffics;


    /*
     *Setup for tests
     */
    public GeoJsonParserTests() throws BTWIllegalTimeException {

        /************Setup variables for First simulator*********/
        simulatorOneRoad = Mockito.mock(MapSimulator.class);
        oneRoad = Mockito.mock(Road.class);
        Crossroad sourceCrossroad = Mockito.mock(Crossroad.class);
        Crossroad destCrossroad = Mockito.mock(Crossroad.class);

        Mockito.when(oneRoad.getRoadName())
                .thenReturn("road1");
        Mockito.when(oneRoad.getSourceCrossroad())
                .thenReturn(sourceCrossroad);
        Mockito.when(oneRoad.getSourceCrossroad().getCoordinateX())
                .thenReturn(6.0);
        Mockito.when(oneRoad.getSourceCrossroad().getCoordinateY())
                .thenReturn(7.2);
        Mockito.when(oneRoad.getDestinationCrossroad())
                .thenReturn(destCrossroad);
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
        Mockito.when(simulatorOneTrafficLight.getTrafficLights())
                .thenAnswer(invocation -> this.oneTraffics);
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

        String mapString = "";
        FileReader fileReader = null;
        try {
            String line;
            fileReader = new FileReader(emptyFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                mapString = mapString+line;
            }
            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Assert.assertEquals(mapString,oneRoadMap);
    }

    @Test
    public void oneTrafficLightMapTest (){
        GeoJsonParserImpl geoJsonParser = new GeoJsonParserImpl();
        File emptyFile = geoJsonParser.buildGeoJsonFromSimulation(simulatorOneTrafficLight);

        String mapString = "";
        FileReader fileReader = null;
        try {
            String line;
            fileReader = new FileReader(emptyFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                mapString = mapString+line;
            }
            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Assert.assertEquals(mapString,oneTrafficLightMap);
    }

}
