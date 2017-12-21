package il.ac.technion.cs.yp.btw.mapsimulation;

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
    }
}