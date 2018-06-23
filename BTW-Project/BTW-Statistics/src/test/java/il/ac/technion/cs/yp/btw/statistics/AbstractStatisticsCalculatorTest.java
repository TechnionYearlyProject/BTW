package il.ac.technion.cs.yp.btw.statistics;

import il.ac.technion.cs.yp.btw.classes.*;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Guy Rephaeli
 * @date 23-Jun-18.
 */
abstract class AbstractStatisticsCalculatorTest {
    protected Road rd1;
    protected Road rd2;
    protected TrafficLight tl1;
    protected TrafficLight tl2;
    protected StatisticalReport report1;
    protected StatisticalReport report2;
    protected StatisticalReport report3;
    protected BTWDataBase db;
    protected StatisticsCalculator tested;

    public AbstractStatisticsCalculatorTest() {
        this.rd1 = Mockito.mock(Road.class);
        this.rd2 = Mockito.mock(Road.class);
        this.tl1 = Mockito.mock(TrafficLight.class);
        this.tl2 = Mockito.mock(TrafficLight.class);
        this.report1 = Mockito.mock(StatisticalReport.class);
        this.report2 = Mockito.mock(StatisticalReport.class);
        this.report3 = Mockito.mock(StatisticalReport.class);
        this.db = Mockito.mock(BTWDataBase.class);
    }

    protected void configMock() {
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
}
