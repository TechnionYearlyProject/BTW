package il.ac.technion.cs.yp.btw.mapsimulation;

import il.ac.technion.cs.yp.btw.classes.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Test class for GridCityMapSimulator
 */
public class GridCityMapSimulatorTest {

    private static final int DEFAULT_NUM_OF_STREETS = 14;
    private static final int DEFAULT_NUM_OF_ROADS_IN_STREET = 12;
    private static final int DEFAULT_NUM_OF_ROADS = DEFAULT_NUM_OF_STREETS * DEFAULT_NUM_OF_ROADS_IN_STREET;
    private static final int DEFAULT_NUM_OF_CROSSROADS = (DEFAULT_NUM_OF_STREETS/2)*(DEFAULT_NUM_OF_STREETS/2);
    private static final int DEFAULT_NUM_OF_TRAFFICLIGHTS = ((DEFAULT_NUM_OF_STREETS/2)-2)*((DEFAULT_NUM_OF_STREETS/2)-2)*4*4 + 4*((DEFAULT_NUM_OF_STREETS/2)-2)*3*3 + 4*2*2;
    private static Set<String> defaultStreetsNames;
    private static Set<String> defaultRoadsNames;
    private static Set<Point> defaultCrossRoadsPoints;
    @BeforeClass
    public static void setDefaultStreetsNames() throws Exception {
        defaultStreetsNames = new HashSet<>();
        String streetBaseName = "street";
        for (int i = 1 ; i <= DEFAULT_NUM_OF_STREETS/2 ; i++){
            defaultStreetsNames.add(i+" "+streetBaseName);
        }
        streetBaseName = "avenue";
        for (int i = 1 ; i <= DEFAULT_NUM_OF_STREETS/2 ; i++){
            defaultStreetsNames.add(i+" "+streetBaseName);
        }
    }
    @BeforeClass
    public static void setDefaultRoadsNames() throws Exception {
        defaultRoadsNames = new HashSet<>();
        String streetBaseName = "street";
        for (int i = 1 ; i <= DEFAULT_NUM_OF_STREETS/2 ; i++){
            for (int j = 1 ; j <= DEFAULT_NUM_OF_ROADS_IN_STREET/2 ; j++){
                defaultRoadsNames.add(i+" "+streetBaseName+" section "+j);
                defaultRoadsNames.add(i+" "+streetBaseName+" section "+j+"'");
            }
        }
        streetBaseName = "avenue";
        for (int i = 1 ; i <= DEFAULT_NUM_OF_STREETS/2 ; i++){
            for (int j = 1 ; j <= DEFAULT_NUM_OF_ROADS_IN_STREET/2 ; j++){
                defaultRoadsNames.add(i+" "+streetBaseName+" section "+j);
                defaultRoadsNames.add(i+" "+streetBaseName+" section "+j+"'");
            }
        }
    }
    @BeforeClass
    public static void setDefaultCrossRoadsPoints() throws Exception {
        defaultCrossRoadsPoints = new HashSet<>();
        for (double x = 0 ; x < DEFAULT_NUM_OF_STREETS/2 ; x++){
            for (double y = 0 ; y < DEFAULT_NUM_OF_STREETS/2 ; y++){
                defaultCrossRoadsPoints.add(new PointImpl(x,y));
            }
        }
    }
    @Test
    public void testDefaultSimulationStreets(){
        GridCityMapSimulator sim = new GridCityMapSimulator();
        sim.build();
        assertEquals(sim.getStreets().size(), DEFAULT_NUM_OF_STREETS);
        assertEquals(sim.getStreets()
                        .stream()
                        .map(Street::getStreetName)
                        .collect(Collectors.toSet())
                , defaultStreetsNames);
        Set<Integer> setOfNumOfRoadsInEachStreet = sim.getStreets()
                .stream()
                .map(street -> street.getAllRoadsInStreet().size())
                .collect(Collectors.toSet());
        assertEquals(setOfNumOfRoadsInEachStreet.size(),1);
        assertTrue(setOfNumOfRoadsInEachStreet.contains(DEFAULT_NUM_OF_ROADS_IN_STREET));
    }
    @Test
    public void testDefaultSimulationRoads(){
        GridCityMapSimulator sim = new GridCityMapSimulator();
        sim.build();
        assertEquals(sim.getRoads().size(), DEFAULT_NUM_OF_ROADS);
        assertEquals(sim.getRoads()
                        .stream()
                        .map(Road::getRoadName)
                        .collect(Collectors.toSet())
                , defaultRoadsNames);
    }
    @Test
    public void testDefaultSimulationCrossroads(){
        GridCityMapSimulator sim = new GridCityMapSimulator();
        sim.build();
        assertEquals(sim.getCrossRoads().size(), DEFAULT_NUM_OF_CROSSROADS);
        assertEquals(sim.getCrossRoads()
                        .stream()
                        .map(Crossroad::getLocation)
                        .map(PointImpl::new)
                        .collect(Collectors.toSet())
                , defaultCrossRoadsPoints);
    }

    @Test
    public void testDefaultSimulationTrafficLights(){
        GridCityMapSimulator sim = new GridCityMapSimulator();
        sim.build();
        assertEquals(sim.getTrafficLights().size(), DEFAULT_NUM_OF_TRAFFICLIGHTS);
        assertEquals(sim.getTrafficLights()
                        .stream()
                        .map(TrafficLight::getLocation)
                        .map(PointImpl::new)
                        .collect(Collectors.toSet())
                , defaultCrossRoadsPoints);
    }
}