package il.ac.technion.cs.yp.btw.classes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Guy Rephaeli
 * @date 23-Jun-18.
 */
public class StatisticalReportTest {
    private StatisticalReport tested;

    public StatisticalReportTest() {
        this.tested = new StatisticalReport(BTWTime.of(5));
    }

    @Before
    public void setUp() {
        this.tested = new StatisticalReport(BTWTime.of(5));
    }

    @Test
    public void testUpdate() {
        Assert.assertEquals(5, this.tested.getTimeOfReport().seconds().intValue());

        Assert.assertEquals(0, this.tested.getNumOfReporters().intValue());
        Assert.assertEquals(0, this.tested.timeTaken().seconds().intValue());

        this.tested.update(BTWWeight.of(2));
        Assert.assertEquals(1, this.tested.getNumOfReporters().intValue());
        Assert.assertEquals(2, this.tested.timeTaken().seconds().intValue());

        this.tested.update(BTWWeight.of(4));
        Assert.assertEquals(2, this.tested.getNumOfReporters().intValue());
        Assert.assertEquals(3, this.tested.timeTaken().seconds().intValue());
    }
}
