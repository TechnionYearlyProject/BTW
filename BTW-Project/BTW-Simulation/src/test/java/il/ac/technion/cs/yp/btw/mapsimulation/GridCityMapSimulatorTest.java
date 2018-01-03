package il.ac.technion.cs.yp.btw.mapsimulation;

import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.Street;
import org.junit.Before;
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

    private static final int DEFAULT_NUM_OF_STREETS = 12;
    private static final int DEFAULT_NUM_OF_ROADS_IN_STREET = 12;
    private static final int DEFAULT_NUM_OF_ROADS = DEFAULT_NUM_OF_STREETS * DEFAULT_NUM_OF_ROADS_IN_STREET;
    private static final int DEFAULT_NUM_OF_CROSSROADS = 49;
    private static Set<String> streetsNames;
    private static Set<String> roadsNames;
    @BeforeClass
    public static void setDefaultStreetsNames() throws Exception {
        streetsNames = new HashSet<>();
        streetsNames.add("1 street");
        streetsNames.add("2 street");
        streetsNames.add("3 street");
        streetsNames.add("4 street");
        streetsNames.add("5 street");
        streetsNames.add("6 street");
        streetsNames.add("1 avenue");
        streetsNames.add("2 avenue");
        streetsNames.add("3 avenue");
        streetsNames.add("4 avenue");
        streetsNames.add("5 avenue");
        streetsNames.add("6 avenue");
    }
    @BeforeClass
    public static void setDefaultRoadsNames() throws Exception {
        roadsNames = new HashSet<>();
        String streetBaseName = "street";
        for (int i = 1 ; i <= 6 ; i++){
            for (int j = 1 ; j <= 6 ; j++){
                roadsNames.add(i+" "+streetBaseName+" section "+j);
                roadsNames.add(i+" "+streetBaseName+" section "+j+"'");
            }
        }
        streetBaseName = "avenue";
        for (int i = 1 ; i <= 6 ; i++){
            for (int j = 1 ; j <= 6 ; j++){
                roadsNames.add(i+" "+streetBaseName+" section "+j);
                roadsNames.add(i+" "+streetBaseName+" section "+j+"'");
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
                ,streetsNames);
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
                ,roadsNames);
    }
    @Test
    public void testDefaultSimulationCrossroads(){
        GridCityMapSimulator sim = new GridCityMapSimulator();
        sim.build();
        assertEquals(sim.getCrossRoads().size(), DEFAULT_NUM_OF_CROSSROADS);
    }
}