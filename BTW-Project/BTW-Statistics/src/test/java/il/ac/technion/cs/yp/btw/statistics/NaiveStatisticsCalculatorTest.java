package il.ac.technion.cs.yp.btw.statistics;

import il.ac.technion.cs.yp.btw.classes.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Guy Rephaeli
 * @date 23-May-18.
 */
public class NaiveStatisticsCalculatorTest extends AbstractStatisticsCalculatorTest {
    public NaiveStatisticsCalculatorTest() {
        super();
        this.configMock();
        this.tested = new NaiveStatisticsCalculator(this.db);
    }

    @Before
    public void setUp() {
        this.tested = new NaiveStatisticsCalculator(this.db);
    }

    @Test
    public void testOneRoadReport() {
        this.tested.addRoadReport(this.rd1, this.report1);
        StatisticsProvider provider = this.tested.getStatistics();
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnRoadAt(BTWTime.of(0), rd1).seconds());
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnRoadAt(BTWTime.of(10 * 60), rd1).seconds());
    }

    @Test
    public void testOneRoadTwoReports() {
        this.tested.addRoadReport(this.rd1, this.report1);
        this.tested.addRoadReport(this.rd1, this.report2);
        StatisticsProvider provider = this.tested.getStatistics();
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnRoadAt(BTWTime.of(0), rd1).seconds());
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnRoadAt(BTWTime.of(10 * 60), rd1).seconds());
        Assert.assertEquals(Long.valueOf(70), provider.expectedTimeOnRoadAt(BTWTime.of(15 * 60), rd1).seconds());
        Assert.assertEquals(Long.valueOf(70), provider.expectedTimeOnRoadAt(BTWTime.of(25 * 60), rd1).seconds());
    }

    @Test
    public void testTwoRoadsWithTwoReports() {
        this.tested.addRoadReport(this.rd1, this.report1);
        this.tested.addRoadReport(this.rd1, this.report2);
        this.tested.addRoadReport(this.rd2, this.report1);
        this.tested.addRoadReport(this.rd2, this.report2);
        StatisticsProvider provider = this.tested.getStatistics();
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnRoadAt(BTWTime.of(0), rd1).seconds());
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnRoadAt(BTWTime.of(0), rd2).seconds());
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnRoadAt(BTWTime.of(10 * 60), rd1).seconds());
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnRoadAt(BTWTime.of(10 * 60), rd2).seconds());
        Assert.assertEquals(Long.valueOf(70), provider.expectedTimeOnRoadAt(BTWTime.of(15 * 60), rd1).seconds());
        Assert.assertEquals(Long.valueOf(70), provider.expectedTimeOnRoadAt(BTWTime.of(15 * 60), rd2).seconds());
        Assert.assertEquals(Long.valueOf(70), provider.expectedTimeOnRoadAt(BTWTime.of(25 * 60), rd1).seconds());
        Assert.assertEquals(Long.valueOf(70), provider.expectedTimeOnRoadAt(BTWTime.of(25 * 60), rd2).seconds());
    }

    @Test
    public void testOneTrafficLightReport() {
        this.tested.addTrafficLightReport(this.tl1, this.report1);
        StatisticsProvider provider = this.tested.getStatistics();
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnTrafficLightAt(BTWTime.of(0), tl1).seconds());
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnTrafficLightAt(BTWTime.of(10 * 60), tl1).seconds());
    }

    @Test
    public void testOneTrafficLightTwoReports() {
        this.tested.addTrafficLightReport(this.tl1, this.report1);
        this.tested.addTrafficLightReport(this.tl1, this.report2);
        StatisticsProvider provider = this.tested.getStatistics();
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnTrafficLightAt(BTWTime.of(0), tl1).seconds());
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnTrafficLightAt(BTWTime.of(10 * 60), tl1).seconds());
        Assert.assertEquals(Long.valueOf(70), provider.expectedTimeOnTrafficLightAt(BTWTime.of(15 * 60), tl1).seconds());
        Assert.assertEquals(Long.valueOf(70), provider.expectedTimeOnTrafficLightAt(BTWTime.of(25 * 60), tl1).seconds());
    }

    @Test
    public void testTwoTrafficLightsWithTwoReports() {
        this.tested.addTrafficLightReport(this.tl1, this.report1);
        this.tested.addTrafficLightReport(this.tl1, this.report2);
        this.tested.addTrafficLightReport(this.tl2, this.report1);
        this.tested.addTrafficLightReport(this.tl2, this.report2);
        StatisticsProvider provider = this.tested.getStatistics();
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnTrafficLightAt(BTWTime.of(0), tl1).seconds());
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnTrafficLightAt(BTWTime.of(0), tl2).seconds());
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnTrafficLightAt(BTWTime.of(10 * 60), tl1).seconds());
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnTrafficLightAt(BTWTime.of(10 * 60), tl2).seconds());
        Assert.assertEquals(Long.valueOf(70), provider.expectedTimeOnTrafficLightAt(BTWTime.of(15 * 60), tl1).seconds());
        Assert.assertEquals(Long.valueOf(70), provider.expectedTimeOnTrafficLightAt(BTWTime.of(15 * 60), tl2).seconds());
        Assert.assertEquals(Long.valueOf(70), provider.expectedTimeOnTrafficLightAt(BTWTime.of(25 * 60), tl1).seconds());
        Assert.assertEquals(Long.valueOf(70), provider.expectedTimeOnTrafficLightAt(BTWTime.of(25 * 60), tl2).seconds());
    }

    @Test
    public void testAllRoadsAndTrafficLights() {
        this.tested.addRoadReport(this.rd1, this.report1);
        this.tested.addRoadReport(this.rd1, this.report2);
        this.tested.addRoadReport(this.rd2, this.report1);
        this.tested.addRoadReport(this.rd2, this.report2);
        this.tested.addTrafficLightReport(this.tl1, this.report1);
        this.tested.addTrafficLightReport(this.tl1, this.report2);
        this.tested.addTrafficLightReport(this.tl2, this.report1);
        this.tested.addTrafficLightReport(this.tl2, this.report2);
        StatisticsProvider provider = this.tested.getStatistics();
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnRoadAt(BTWTime.of(0), rd1).seconds());
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnRoadAt(BTWTime.of(0), rd2).seconds());
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnRoadAt(BTWTime.of(10 * 60), rd1).seconds());
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnRoadAt(BTWTime.of(10 * 60), rd2).seconds());
        Assert.assertEquals(Long.valueOf(70), provider.expectedTimeOnRoadAt(BTWTime.of(15 * 60), rd1).seconds());
        Assert.assertEquals(Long.valueOf(70), provider.expectedTimeOnRoadAt(BTWTime.of(15 * 60), rd2).seconds());
        Assert.assertEquals(Long.valueOf(70), provider.expectedTimeOnRoadAt(BTWTime.of(25 * 60), rd1).seconds());
        Assert.assertEquals(Long.valueOf(70), provider.expectedTimeOnRoadAt(BTWTime.of(25 * 60), rd2).seconds());
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnTrafficLightAt(BTWTime.of(0), tl1).seconds());
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnTrafficLightAt(BTWTime.of(0), tl2).seconds());
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnTrafficLightAt(BTWTime.of(10 * 60), tl1).seconds());
        Assert.assertEquals(Long.valueOf(60), provider.expectedTimeOnTrafficLightAt(BTWTime.of(10 * 60), tl2).seconds());
        Assert.assertEquals(Long.valueOf(70), provider.expectedTimeOnTrafficLightAt(BTWTime.of(15 * 60), tl1).seconds());
        Assert.assertEquals(Long.valueOf(70), provider.expectedTimeOnTrafficLightAt(BTWTime.of(15 * 60), tl2).seconds());
        Assert.assertEquals(Long.valueOf(70), provider.expectedTimeOnTrafficLightAt(BTWTime.of(25 * 60), tl1).seconds());
        Assert.assertEquals(Long.valueOf(70), provider.expectedTimeOnTrafficLightAt(BTWTime.of(25 * 60), tl2).seconds());
    }
}
