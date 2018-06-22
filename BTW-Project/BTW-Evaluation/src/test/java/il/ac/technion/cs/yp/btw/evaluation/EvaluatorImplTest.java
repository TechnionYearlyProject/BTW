package il.ac.technion.cs.yp.btw.evaluation;

import il.ac.technion.cs.yp.btw.citysimulation.VehicleDescriptor;
import il.ac.technion.cs.yp.btw.citysimulation.VehicleDescriptorFactory;
import il.ac.technion.cs.yp.btw.classes.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;
import java.util.Map;

/**
 * @author Guy Rephaeli
 * @date 05-Jun-18.
 */
public class EvaluatorImplTest {
    private Evaluator tested;
    private BTWDataBase db;
    private List<VehicleDescriptor> descriptors;
    private VehicleDescriptor desc1;
    private VehicleDescriptor desc2;
    private VehicleDescriptor desc3;
    private VehicleDescriptor desc4;
    private Road rd1;
    private Road rd2;
    private Road rd3;
    private StatisticalReport rd1Report;
    private StatisticalReport rd2Report;
    private TrafficLight tl1;
    private TrafficLight tl2;
    private TrafficLight tl3;
    private StatisticalReport tl1Report;
    private StatisticalReport tl2Report;



    public EvaluatorImplTest() {
        long timeWindow = 30 * 60;
        this.db = Mockito.mock(BTWDataBase.class);
        VehicleDescriptorFactory descriptorFactory = new VehicleDescriptorFactory();
        this.descriptors = new ArrayList<>();
        this.desc1 = descriptorFactory.get();
        this.descriptors.add(this.desc1);
        this.desc2 = descriptorFactory.get();
        this.descriptors.add(this.desc2);
        this.desc3 = descriptorFactory.get();
        this.descriptors.add(this.desc3);
        this.desc4 = descriptorFactory.get();
        this.rd1 = Mockito.mock(Road.class);
        this.rd2 = Mockito.mock(Road.class);
        this.rd3 = Mockito.mock(Road.class);
        this.tl1 = Mockito.mock(TrafficLight.class);
        this.tl2 = Mockito.mock(TrafficLight.class);
        this.tl3 = Mockito.mock(TrafficLight.class);
        this.rd1Report = new StatisticalReport(BTWTime.of(0), timeWindow);
        this.rd2Report = new StatisticalReport(BTWTime.of(0), timeWindow);
        this.tl1Report = new StatisticalReport(BTWTime.of(0), timeWindow);
        this.tl2Report = new StatisticalReport(BTWTime.of(0), timeWindow);
        this.rd1Report.update(BTWWeight.of(2));
        this.rd2Report.update(BTWWeight.of(4));
        this.tl1Report.update(BTWWeight.of(2));
        this.tl2Report.update(BTWWeight.of(4));
        configMock();
    }

    private void configMock() {
        Set<Road> roads = new HashSet<>();
        roads.add(this.rd1);
        roads.add(this.rd2);
        Set<TrafficLight> trafficLights = new HashSet<>();
        trafficLights.add(this.tl1);
        trafficLights.add(this.tl2);
        Mockito.when(this.db.getAllRoads())
                .thenReturn(roads);
        Mockito.when(this.db.getAllTrafficLights())
                .thenReturn(trafficLights);
    }

    @Before
    public void setUp() {
        this.tested = new EvaluatorImpl(this.db, this.descriptors);
        this.tested.addVehicleInfo(this.desc1, BTWWeight.of(2));
        this.tested.addVehicleInfo(this.desc2, BTWWeight.of(4));
        Map<Road, StatisticalReport> reportOfRoad = new HashMap<>();
        reportOfRoad.put(this.rd1, this.rd1Report);
        reportOfRoad.put(this.rd2, this.rd2Report);
        this.tested.addRoadReports(reportOfRoad);
        Map<TrafficLight, StatisticalReport> reportOfTrafficLight = new HashMap<>();
        reportOfTrafficLight.put(this.tl1, this.tl1Report);
        reportOfTrafficLight.put(this.tl2, this.tl2Report);
        this.tested.addTrafficLightReports(reportOfTrafficLight);
    }

    @Test
    public void testVehicleEvaluation() {
        Assert.assertEquals(BTWWeight.of(2).seconds(), this.tested.totalDrivingTime(this.desc1).seconds());
        Assert.assertEquals(BTWWeight.of(4).seconds(), this.tested.totalDrivingTime(this.desc2).seconds());
        Assert.assertEquals(BTWWeight.of(3).seconds(), this.tested.averageTotalDrivingTime().seconds());
        Assert.assertEquals(Integer.valueOf(1), this.tested.unaccomplishedDrives());
    }

    @Test(expected = NoSuchDescriptorException.class)
    public void testIllegalVehicle() {
        this.tested.totalDrivingTime(this.desc4);
    }

    @Test(expected = UnfinishedVehicleException.class)
    public void testUnfinishedVehicle() {
        this.tested.totalDrivingTime(this.desc3);
    }

    @Test(expected = DescriptorAlreadySeenException.class)
    public void testAlreadySeenVehicle() {
        this.tested.addVehicleInfo(this.desc2, BTWWeight.of(4));
    }

    @Test
    public void testRoadEvaluation() {
        Assert.assertEquals(BTWWeight.of(2).seconds(), this.tested.averageDrivingTimeOnRoad(this.rd1).seconds());
        Assert.assertEquals(BTWWeight.of(4).seconds(), this.tested.averageDrivingTimeOnRoad(this.rd2).seconds());
        Assert.assertEquals(BTWWeight.of(3).seconds(), this.tested.averageDrivingTimeOnAllRoads().seconds());
    }

    @Test(expected = NoSuchRoadException.class)
    public void testIllegalRoad() {
        this.tested.averageDrivingTimeOnRoad(this.rd3);
    }

    @Test(expected = NoSuchRoadException.class)
    public void testIllegalRoadReport() {
        Map<Road, StatisticalReport> reportOfRoad = new HashMap<>();
        reportOfRoad.put(this.rd3, this.rd1Report);
        this.tested.addRoadReports(reportOfRoad);
    }

    @Test
    public void testTrafficLightEvaluation() {
        Assert.assertEquals(BTWWeight.of(2).seconds(), this.tested.averageWaitingTimeOnTrafficLight(this.tl1).seconds());
        Assert.assertEquals(BTWWeight.of(4).seconds(), this.tested.averageWaitingTimeOnTrafficLight(this.tl2).seconds());
        Assert.assertEquals(BTWWeight.of(3).seconds(), this.tested.averageWaitingTimeOnAllTrafficLights().seconds());
    }

    @Test(expected = NoSuchTrafficLightException.class)
    public void testIllegalTrafficLight() {
        this.tested.averageWaitingTimeOnTrafficLight(this.tl3);
    }

    @Test(expected = NoSuchTrafficLightException.class)
    public void testIllegalTrafficLightReport() {
        Map<TrafficLight, StatisticalReport> reportOfTrafficLight = new HashMap<>();
        reportOfTrafficLight.put(this.tl3, this.tl1Report);
        this.tested.addTrafficLightReports(reportOfTrafficLight);
    }
}
