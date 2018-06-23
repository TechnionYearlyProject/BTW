package il.ac.technion.cs.yp.btw.statistics;

import il.ac.technion.cs.yp.btw.classes.BTWTime;
import il.ac.technion.cs.yp.btw.classes.BTWWeight;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Guy Rephaeli
 * @date 23-Jun-18.
 */
public class SmartStatisticsCalculatorTest extends AbstractStatisticsCalculatorTest {
    private StatisticsProvider provider;

    public SmartStatisticsCalculatorTest() {
        super();
        this.provider = Mockito.mock(StatisticsProvider.class);
        this.configMock();
        this.tested = new SmartStatisticsCalculator(this.db);
    }

    @Override
    protected void configMock() {
        super.configMock();

        // rd1
        Mockito.when(this.rd1.getMinimumWeight())
                .thenReturn(BTWWeight.of(52));

        // rd2
        Mockito.when(this.rd2.getMinimumWeight())
                .thenReturn(BTWWeight.of(62));

        // tl1
        Mockito.when(this.tl1.getMinimumWeight())
                .thenReturn(BTWWeight.of(52));

        // tl2
        Mockito.when(this.tl2.getMinimumWeight())
                .thenReturn(BTWWeight.of(62));



        // provider
        Mockito.when(this.provider.granularity())
                .thenReturn(15 * 60L);

        for (int i = 0; i < 24 * 3600; i += 15 * 60) {
            Mockito.when(this.provider.expectedTimeOnRoadAt(BTWTime.of(i), this.rd1))
                    .thenReturn(BTWWeight.of(68));

            Mockito.when(this.provider.expectedTimeOnRoadAt(BTWTime.of(i), this.rd2))
                    .thenReturn(BTWWeight.of(78));

            Mockito.when(this.provider.expectedTimeOnTrafficLightAt(BTWTime.of(i), this.tl1))
                    .thenReturn(BTWWeight.of(68));

            Mockito.when(this.provider.expectedTimeOnTrafficLightAt(BTWTime.of(i), this.tl2))
                    .thenReturn(BTWWeight.of(78));
        }

        Mockito.when(db.getStatisticsFromDB())
                .thenReturn(this.provider);
    }


    @Before
    public void setUp() {
        this.tested = new SmartStatisticsCalculator(this.db);
    }

    @Test
    public void testUpdate() {
        for (int i = 0; i < 40; i++) {
            this.tested.addRoadReport(this.rd1, this.report1);
            this.tested.addRoadReport(this.rd2, this.report2);
            this.tested.addRoadReport(this.rd2, this.report3);
            this.tested.addTrafficLightReport(this.tl1, this.report1);
            this.tested.addTrafficLightReport(this.tl2, this.report2);
            this.tested.addTrafficLightReport(this.tl2, this.report3);
            StatisticsProvider currProvider = this.tested.getStatistics();
            int iterationStep = i / 10;
            int val = Double.valueOf(8 - Math.pow(2, (3 - iterationStep))).intValue();

            Assert.assertEquals(60 + val, currProvider.expectedTimeOnRoadAt(BTWTime.of(0), this.rd1).seconds().intValue());
            Assert.assertEquals(52 + (2 * val), currProvider.expectedTimeOnRoadAt(BTWTime.of(15 * 60), this.rd1).seconds().intValue());
            Assert.assertEquals(70 + val, currProvider.expectedTimeOnRoadAt(BTWTime.of(0), this.rd2).seconds().intValue());
            Assert.assertEquals(70 + val, currProvider.expectedTimeOnRoadAt(BTWTime.of(15 * 60), this.rd2).seconds().intValue());

            Assert.assertEquals(60 + val, currProvider.expectedTimeOnTrafficLightAt(BTWTime.of(0), this.tl1).seconds().intValue());
            Assert.assertEquals(52 + (2 * val), currProvider.expectedTimeOnTrafficLightAt(BTWTime.of(15 * 60), this.tl1).seconds().intValue());
            Assert.assertEquals(70 + val, currProvider.expectedTimeOnTrafficLightAt(BTWTime.of(0), this.tl2).seconds().intValue());
            Assert.assertEquals(70 + val, currProvider.expectedTimeOnTrafficLightAt(BTWTime.of(15 * 60), this.tl2).seconds().intValue());


        }
    }
}
