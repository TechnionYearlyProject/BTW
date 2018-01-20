package il.ac.technion.cs.yp.btw.mapgeneration;

import il.ac.technion.cs.yp.btw.classes.Crossroad;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FreeFormMapSimulatorTest {

    private static final int DEFAULT_RADIUS_MARGIN_OF_ERROR = 150;

    @Test
    public void testDefaultMapRadius() {
        FreeFormMapSimulator f = new FreeFormMapSimulator();
        f.build();
        for (Crossroad crossroad : f.getCrossRoads()) {
            assertTrue(FreeFormMapSimulator
                    .calculateLengthBetween2Points
                            (f.getCityCenter(), crossroad) <= (f.getCityRadius() + DEFAULT_RADIUS_MARGIN_OF_ERROR));
        }
    }

    @Test
    public void testParameterMapRadius() {
        int testCityRadius = 10000;
        FreeFormMapSimulator f = new FreeFormMapSimulator().setCityRadius(testCityRadius);
        f.build();
        for (Crossroad crossroad : f.getCrossRoads()) {
            assertTrue(FreeFormMapSimulator
                    .calculateLengthBetween2Points
                            (f.getCityCenter(), crossroad) <= (f.getCityRadius() + DEFAULT_RADIUS_MARGIN_OF_ERROR));
        }
    }

    @Test
    public void testManyBlocksParameterMapRadius() {
        int testCityRadius = 10000;
        int testNumOfBlocks = 300;
        FreeFormMapSimulator f = new FreeFormMapSimulator()
                .setCityRadius(testCityRadius)
                .setNumOfCityBlocks(testNumOfBlocks);
        f.build();
        for (Crossroad crossroad : f.getCrossRoads()) {
            assertTrue(FreeFormMapSimulator
                    .calculateLengthBetween2Points
                            (f.getCityCenter(), crossroad) <= (f.getCityRadius() + DEFAULT_RADIUS_MARGIN_OF_ERROR));
        }
    }
}