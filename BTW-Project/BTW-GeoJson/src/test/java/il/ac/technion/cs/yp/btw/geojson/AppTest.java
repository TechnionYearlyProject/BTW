package il.ac.technion.cs.yp.btw.geojson;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;

import il.ac.technion.cs.yp.btw.geojson.GeoJsonParserImpl;
import il.ac.technion.cs.yp.btw.mapsimulation.GridCityMapSimulator;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
        GridCityMapSimulator mapSimulator = new GridCityMapSimulator();
        mapSimulator.build();

        //setup the map simulator
        //TraficLight trafficLight = new TrafficLight(1,1,"from Roza st 1-4 to Ela st 2-4",0,null);

        GeoJsonParserImpl geoJsonParser = new GeoJsonParserImpl();
        File emptyFile = geoJsonParser.buildGeoJsonFromSimulation(mapSimulator);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {

    }
}
