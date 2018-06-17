package il.ac.technion.cs.yp.btw.statistics;

import il.ac.technion.cs.yp.btw.classes.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Guy Rephaeli
 * @date 23-May-18.
 */
public class NaiveStatisticsCalculatorTest {
    private Road rd1;
    private Road rd2;
    private TrafficLight tl1;
    private TrafficLight tl2;
    private StatisticalReport report1;
    private StatisticalReport report2;
    private StatisticalReport report3;
    private BTWDataBase db;
    private StatisticsCalculator tested;

    public NaiveStatisticsCalculatorTest() {
        this.rd1 = Mockito.mock(Road.class);
        this.rd2 = Mockito.mock(Road.class);
        this.tl1 = Mockito.mock(TrafficLight.class);
        this.tl2 = Mockito.mock(TrafficLight.class);
        this.report1 = Mockito.mock(StatisticalReport.class);
        this.report2 = Mockito.mock(StatisticalReport.class);
        this.report3 = Mockito.mock(StatisticalReport.class);
        this.db = Mockito.mock(BTWDataBase.class);
        this.configMock();
        this.tested = new NaiveStatisticsCalculator(this.db);
    }

    private void configMock() {
        // db
        Mockito.when(db.getStatisticsPeriod())
                .thenReturn(15 * 60L);

        // rd1
        Mockito.when(rd1.getName())
                .thenReturn("rd1");

        // rd2
        Mockito.when(rd2.getName())
                .thenReturn("rd2");

        // tl1
        Mockito.when(tl1.getName())
                .thenReturn("tl1");

        // tl2
        Mockito.when(tl2.getName())
                .thenReturn("tl2");

        // report1
        Mockito.when(report1.getTimeOfReport())
                .thenReturn(BTWTime.of(0));

        Mockito.when(report1.timeTaken())
                .thenReturn(BTWWeight.of(60));

        // report2
        Mockito.when(report2.getTimeOfReport())
                .thenReturn(BTWTime.of(16 * 60));

        Mockito.when(report2.timeTaken())
                .thenReturn(BTWWeight.of(70));

        // report3
        Mockito.when(report3.getTimeOfReport())
                .thenReturn(BTWTime.of(4 * 60));

        Mockito.when(report3.timeTaken())
                .thenReturn(BTWWeight.of(70));
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

    @Test(expected = BTWIllegalStatisticsException.class)
    public void testOneRoadDuplicateReport() {
        this.tested.addRoadReport(this.rd1, this.report1);
        this.tested.addRoadReport(this.rd1, this.report1);
    }

    @Test(expected = BTWIllegalStatisticsException.class)
    public void testOneRoadSameTimeReport() {
        this.tested.addRoadReport(this.rd1, this.report1);
        this.tested.addRoadReport(this.rd1, this.report3);
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

    @Test(expected = BTWIllegalStatisticsException.class)
    public void testOneTrafficLightDuplicateReport() {
        this.tested.addTrafficLightReport(this.tl1, this.report1);
        this.tested.addTrafficLightReport(this.tl1, this.report1);
    }

    @Test(expected = BTWIllegalStatisticsException.class)
    public void testOneTrafficLightSameTimeReport() {
        this.tested.addTrafficLightReport(this.tl1, this.report1);
        this.tested.addTrafficLightReport(this.tl1, this.report3);
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
