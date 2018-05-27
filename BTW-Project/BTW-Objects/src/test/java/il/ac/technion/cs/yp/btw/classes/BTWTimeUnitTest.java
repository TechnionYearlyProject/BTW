package il.ac.technion.cs.yp.btw.classes;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testing all subclasses of BTWTimeUnit
 */
public class BTWTimeUnitTest {
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
    public void testIllegalTime() {
        try {
            BTWTime time = BTWTime.of(-1);
            Assert.fail();
        } catch (BTWIllegalTimeException e) {
            Assert.assertEquals("Time must be positive", e.getMessage());
        }
        try {
            BTWTime time = BTWTime.of(86400);
            Assert.fail();
        } catch (BTWIllegalTimeException e) {
            Assert.assertEquals("Time must be less than 24 hours", e.getMessage());
        }
    }

    @Test
    public void testTimeProgress() {
        try {
            BTWWeight w1 = BTWWeight.of(1);
            BTWWeight w2 = BTWWeight.of(86400);
            BTWTime t1 = BTWTime.of(0).progressBy(w1);
            BTWTime t2 = BTWTime.of(0).progressBy(w2);
            BTWTime t3 = t1.progressBy(w2);
            Assert.assertEquals(new Long(1), t1.seconds());
            Assert.assertEquals(new Long(0), t2.seconds());
            Assert.assertEquals(new Long(1), t3.seconds());
        } catch (BTWIllegalTimeException e) {
            Assert.fail();
        }
    }
}
