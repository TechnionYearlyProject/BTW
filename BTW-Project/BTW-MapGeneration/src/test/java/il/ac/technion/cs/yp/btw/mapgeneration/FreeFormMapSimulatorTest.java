package il.ac.technion.cs.yp.btw.mapgeneration;

import il.ac.technion.cs.yp.btw.classes.Crossroad;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FreeFormMapSimulatorTest {

    private static final int DEFAULT_RADIUS_MARGIN_OF_ERROR = 50;

    @Test
    public void testMapRadius() {
        FreeFormMapSimulator f = new FreeFormMapSimulator();
        f.build();
        for (Crossroad crossroad : f.getCrossRoads()) {
            assertTrue(FreeFormMapSimulator
                    .calculateLengthBetween2Points
                            (f.getCityCenter(), crossroad) <= (f.getCityRadius() + DEFAULT_RADIUS_MARGIN_OF_ERROR));
        }
    }
}