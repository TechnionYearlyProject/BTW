package il.ac.technion.cs.yp.btw.citysimulation;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Guy Rephaeli on 18-Jan-18.
 * Testing RoadData
 */
public class RoadDataTest {
    private RoadData tested;

    public RoadDataTest() {
        this.tested = new RoadData(100, 50.0, 2);
    }

    @Test
    public void getRoadLengthTest() {
        Assert.assertEquals(100, this.tested.getRoadLength());
    }

    @Test
    public void getAverageSpeedTest() {
        Assert.assertTrue(50.0 == this.tested.getAverageSpeed());
    }

    @Test
    public void getMunOfVehiclesTest() {
        Assert.assertEquals(2, this.tested.getNumOfVehicles());
    }
}