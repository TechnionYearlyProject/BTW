package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import org.junit.Test;

import java.util.Set;

/**
 * Created by shay on 14/01/2018.
 */
public class TestDataTrafficLight {
    @Test
    public void testGetParameters() {
        BTWDataBase btw = new BTWDataBaseImpl("test");
        Set<TrafficLight> tls = btw.getAllTrafficLights();
        for (TrafficLight tl: tls) {
            if (tl.getName().equals("from:cc to:dd")) {
                assert(tl.getCoordinateX() == 0.0);
                assert(tl.getCoordinateY() == 0.0);
                assert(tl.getMinimumWeight().seconds() == 4647);
            }
            if (tl.getName().equals("from:aa to:bb")) {
                assert(tl.getCoordinateX() == 0.0);
                assert(tl.getCoordinateY() == 0.0);
                assert(tl.getMinimumWeight().seconds() == 435345445);
            }
            if (tl.getName().equals("from:bb to:ee")) {
                assert(tl.getCoordinateX() == 2.0);
                assert(tl.getCoordinateY() == 0.0);
                assert(tl.getMinimumWeight().seconds() == 4647);
            }

        }

    }
}
