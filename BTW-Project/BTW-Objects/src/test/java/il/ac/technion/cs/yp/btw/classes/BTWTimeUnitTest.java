package il.ac.technion.cs.yp.btw.classes;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Guy Rephaeli
 *
 * Testing all subclasses of BTWTimeUnit
 */
public class BTWTimeUnitTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testLegalWeight() {
        try {
            BTWWeight weight = BTWWeight.of(2);
            Assert.assertEquals(weight.seconds().intValue(), 2);

        } catch (BTWIllegalTimeException e) {
            Assert.fail();
        }
    }

    @Test
    public void testLegalTime() {
        try {
            BTWTime time = BTWTime.of(2);
            Assert.assertEquals(time.seconds().intValue(), 2);

        } catch (BTWIllegalTimeException e) {
            Assert.fail();
        }
    }

    @Test
    public void testIllegalWeight() {
        try {
            BTWWeight weight = BTWWeight.of(-1);
            Assert.fail();
        } catch (BTWIllegalTimeException e) {
            Assert.assertEquals("Time must be positive", e.getMessage());
        }
    }



    @Test
    public void testNegativeTime() {
        thrown.expect(BTWIllegalTimeException.class);
        thrown.expectMessage("Time must be positive");
        BTWTime.of(-1);
    }

    @Test
    public void testIllegalTime() {
        thrown.expect(BTWIllegalTimeException.class);
        thrown.expectMessage("Time must be less than 24 hours");
        BTWTime.of(86400);
    }

    @Test
    public void testTimeProgress() {
        BTWWeight w1 = BTWWeight.of(1);
        BTWWeight w2 = BTWWeight.of(86400);
        BTWTime t1 = BTWTime.of(0).progressBy(w1);
        BTWTime t2 = BTWTime.of(0).progressBy(w2);
        BTWTime t3 = t1.progressBy(w2);
        Assert.assertEquals(new Long(1), t1.seconds());
        Assert.assertEquals(new Long(0), t2.seconds());
        Assert.assertEquals(new Long(1), t3.seconds());
    }

    @Test
    public void testWeightedAverage() {
        BTWWeight w1 = BTWWeight.of(0);
        BTWWeight w2 = BTWWeight.of(10);
        BTWWeight avg = BTWWeight.weightedAverage(w1, w2, 0.5);
        Assert.assertEquals(5, avg.seconds().intValue());
        avg = BTWWeight.weightedAverage(w1, w2, 0.7);
        Assert.assertEquals(7, avg.seconds().intValue());
        avg = BTWWeight.weightedAverage(w1, w2, 0.3);
        Assert.assertEquals(3, avg.seconds().intValue());
    }

    @Test
    public void testNegativeWeight() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Weight must be between 0 and 1");
        BTWWeight.weightedAverage(BTWWeight.of(0), BTWWeight.of(0), -0.1);
    }

    @Test
    public void testIllegalWeightAverage() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Weight must be between 0 and 1");
        BTWWeight.weightedAverage(BTWWeight.of(0), BTWWeight.of(0), 1.1);
    }

    @Test
    public void testTimeString() {
        BTWTime time = BTWTime.of("01:35:54");
        Assert.assertEquals((1 * 3600) + (35 * 60) + 54, time.seconds().intValue());
        Assert.assertEquals(1, time.getHoursOnly().intValue());
        Assert.assertEquals(35, time.getMinutesOnly().intValue());
        Assert.assertEquals(54, time.getSecondsOnly().intValue());
    }

    @Test
    public void testIllegalHoursTimeString() {
        thrown.expect(BTWIllegalTimeException.class);
        thrown.expectMessage("Number of hours must be between 0 and 23");
        BTWTime.of("24:35:54");
    }

    @Test
    public void testIllegalMinutesTimeString() {
        thrown.expect(BTWIllegalTimeException.class);
        thrown.expectMessage("Number of minutes must be between 0 and 59");
        BTWTime.of("01:60:54");
    }

    @Test
    public void testIllegalSecondsTimeString() {
        thrown.expect(BTWIllegalTimeException.class);
        thrown.expectMessage("Number of seconds must be between 0 and 59");
        BTWTime.of("01:35:60");
    }

    @Test
    public void testTimeWindow() {
        BTWTime time = BTWTime.of("01:35:54");
        Assert.assertEquals(BTWTime.of("01:30:00").seconds(), time.startTimeWindow(60 * 30).seconds());
    }

    @Test
    public void testHashCode() {
        BTWTime time1 = BTWTime.of("01:35:54");
        BTWTime time2 = BTWTime.of(1, 35, 54);
        Assert.assertEquals(time1.hashCode(), time2.hashCode());
    }

    @Test
    public void testEquals() {
        BTWTime time1 = BTWTime.of("01:35:54");
        BTWTime time2 = BTWTime.of(1, 35, 54);
        Assert.assertTrue(time1.equals(time2));
    }

    @Test
    public void testNotEquals() {
        BTWTime time1 = BTWTime.of("01:35:54");
        BTWTime time2 = BTWTime.of(1, 35, 55);
        Assert.assertFalse(time1.equals(time2));
    }

    @Test
    public void testNotEqualsObject() {
        BTWTime time = BTWTime.of("01:35:54");
        Assert.assertFalse(time.equals(time.seconds().intValue()));
    }
}
