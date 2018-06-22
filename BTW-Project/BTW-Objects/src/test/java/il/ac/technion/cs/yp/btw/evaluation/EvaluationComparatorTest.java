package il.ac.technion.cs.yp.btw.evaluation;

import il.ac.technion.cs.yp.btw.citysimulation.VehicleDescriptor;
import il.ac.technion.cs.yp.btw.classes.BTWWeight;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Guy Rephaeli
 * @date 22-Jun-18.
 */
public class EvaluationComparatorTest {
    private Evaluator e1;
    private Evaluator e2;
    private EvaluationComparator tested;
    private VehicleDescriptor descriptor;
    private Road road;
    private TrafficLight trafficLight;

    public EvaluationComparatorTest() {
        this.e1 = Mockito.mock(Evaluator.class);
        this.e2 = Mockito.mock(Evaluator.class);
        this.descriptor = Mockito.mock(VehicleDescriptor.class);
        this.road = Mockito.mock(Road.class);
        this.trafficLight = Mockito.mock(TrafficLight.class);
        this.configMock();
        this.tested = new EvaluationComparator(this.e1, this.e2);
    }

    private void configMock() {
        Mockito.when(this.e1.totalDrivingTime(Mockito.any()))
                .thenReturn(BTWWeight.of(3));
        Mockito.when(this.e1.averageTotalDrivingTime())
                .thenReturn(BTWWeight.of(3));
        Mockito.when(this.e1.averageDrivingTimeOnRoad(Mockito.any()))
                .thenReturn(BTWWeight.of(1));
        Mockito.when(this.e1.averageDrivingTimeOnAllRoads())
                .thenReturn(BTWWeight.of(1));
        Mockito.when(this.e1.averageWaitingTimeOnTrafficLight(Mockito.any()))
                .thenReturn(BTWWeight.of(1));
        Mockito.when(this.e1.averageWaitingTimeOnAllTrafficLights())
                .thenReturn(BTWWeight.of(1));
        Mockito.when(this.e1.unaccomplishedDrives())
                .thenReturn(2);

        Mockito.when(this.e2.totalDrivingTime(Mockito.any()))
                .thenReturn(BTWWeight.of(6));
        Mockito.when(this.e2.averageTotalDrivingTime())
                .thenReturn(BTWWeight.of(6));
        Mockito.when(this.e2.averageDrivingTimeOnRoad(Mockito.any()))
                .thenReturn(BTWWeight.of(2));
        Mockito.when(this.e2.averageDrivingTimeOnAllRoads())
                .thenReturn(BTWWeight.of(2));
        Mockito.when(this.e2.averageWaitingTimeOnTrafficLight(Mockito.any()))
                .thenReturn(BTWWeight.of(2));
        Mockito.when(this.e2.averageWaitingTimeOnAllTrafficLights())
                .thenReturn(BTWWeight.of(2));
        Mockito.when(this.e2.unaccomplishedDrives())
                .thenReturn(4);
    }

    @Test
    public void testEverything() {
        Assert.assertEquals(-3, tested.compareDrivingTimeOfVehicle(this.descriptor).intValue());
        Assert.assertEquals(-3, tested.compareAverageDrivingTimeOfVehicles().intValue());
        Assert.assertEquals(-1, tested.compareDrivingTimeOnRoad(this.road).intValue());
        Assert.assertEquals(-1, tested.compareAverageDrivingTimeOnRoads().intValue());
        Assert.assertEquals(-1, tested.compareWaintingTimeOnTarfficLight(this.trafficLight).intValue());
        Assert.assertEquals(-1, tested.compareAverageWaintingTimeOnTarfficLights().intValue());
        Assert.assertEquals(-2, tested.compareUnaccomplishedDrives().intValue());
    }
}
