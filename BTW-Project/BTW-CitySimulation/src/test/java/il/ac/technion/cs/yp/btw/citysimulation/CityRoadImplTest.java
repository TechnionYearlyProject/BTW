package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.navigation.NavigationManager;
import il.ac.technion.cs.yp.btw.navigation.PathNotFoundException;
import il.ac.technion.cs.yp.btw.statistics.StatisticsCalculator;
import il.ac.technion.cs.yp.btw.trafficlights.TrafficLightManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Testing CityRoadImpl
 */
public class CityRoadImplTest {
    private CitySimulator simulator;
    private Road road;
    private Crossroad crossroad1;
    private Crossroad crossroad2;
    private TrafficLight trafficLight1;
    private TrafficLight trafficLight2;
    private Vehicle vehicle;
    private Vehicle v2;
    private Vehicle v3;
    private Vehicle v4;
    private Vehicle v5;
    private Vehicle v6;
    private boolean ticked;
    private long timeWindow = 15 * 60;

    private void configMock() throws PathNotFoundException {
        //crossroad1
        Mockito.when(crossroad1.getTrafficLights())
                .thenAnswer( invocation -> {
                    Set<TrafficLight> trafficLights = new HashSet<>();
                    trafficLights.add(trafficLight1);
                    return trafficLights;
                });

        //crossroad2
        Mockito.when(crossroad2.getTrafficLightsFromRoad(road))
                .thenAnswer( invocation -> {
                    Set<TrafficLight> trafficLights = new HashSet<>();
                    trafficLights.add(trafficLight2);
                    return trafficLights;
                });

        Mockito.when(crossroad2.getTrafficLights())
                .thenAnswer( invocation -> {
                    Set<TrafficLight> trafficLights = new HashSet<>();
                    trafficLights.add(trafficLight2);
                    return trafficLights;
                });

        // trafficlight1
        Mockito.when(trafficLight1.getSourceRoad())
                .thenReturn(null);

        Mockito.when(trafficLight1.getDestinationRoad())
                .thenReturn(road);

        // trafficlight2
        Mockito.when(trafficLight2.getSourceRoad())
                .thenReturn(road);

        Mockito.when(trafficLight1.getDestinationRoad())
                .thenReturn(null);

        // road
        Mockito.when(road.getRoadName())
                .thenReturn("r-1");

        Mockito.when(road.getMinimumWeight())
                .thenAnswer(invocation -> BTWWeight.of(2L));

        Mockito.when(road.getStreet())
                .thenReturn(null);

        Mockito.when(road.getSourceCrossroad())
                .thenReturn(crossroad1);

        Mockito.when(road.getDestinationCrossroad())
                .thenReturn(crossroad2);

        Mockito.when(road.getRoadLength())
                .thenReturn(250);

        // vehicle
        Mockito.when(vehicle.progressOnRoad())
                .thenAnswer(invocation -> {
                    this.ticked = true;
                    return this.vehicle;
                });
    }

    public CityRoadImplTest() {
        this.road = Mockito.mock(Road.class);
        this.crossroad1 = Mockito.mock(Crossroad.class);
        this.crossroad2 = Mockito.mock(Crossroad.class);
        this.trafficLight1 = Mockito.mock(TrafficLight.class);
        this.trafficLight2 = Mockito.mock(TrafficLight.class);

        this.vehicle= Mockito.mock(Vehicle.class);
        this.v2= Mockito.mock(Vehicle.class);
        this.v3= Mockito.mock(Vehicle.class);
        this.v4= Mockito.mock(Vehicle.class);
        this.v5= Mockito.mock(Vehicle.class);
        this.v6= Mockito.mock(Vehicle.class);
        this.ticked = false;
        try {
            this.configMock();
        } catch(PathNotFoundException e) {
            throw new RuntimeException(e);
        }
        Set<Road> roads = new HashSet<>();
        roads.add(this.road);
        Set<TrafficLight> trafficLights = new HashSet<>();
        trafficLights.add(this.trafficLight1);
        trafficLights.add(this.trafficLight2);
        Set<Crossroad> crossroads = new HashSet<>();
        crossroads.add(this.crossroad1);
        crossroads.add(this.crossroad2);
        NavigationManager navigationManager = Mockito.mock(NavigationManager.class);
        TrafficLightManager trafficLightManager= Mockito.mock(TrafficLightManager.class);
        StatisticsCalculator calculator = Mockito.mock(StatisticsCalculator.class);
        this.simulator = new CitySimulatorImpl(roads, trafficLights, crossroads, navigationManager, trafficLightManager, calculator, this.timeWindow);
    }

    @Before
    public void setUp() {
        this.ticked = false;
    }

    @Test
    public void isStreetNumberInRangeTest() {
        // TODO
//        CityRoad tested = this.simulator.getRealRoad(road);
//        Assert.assertFalse(tested.isStreetNumberInRange(0));
    }

    @Test
    public void getRoadLengthTest() {
        CityRoad tested = this.simulator.getRealRoad(road);
        Assert.assertEquals(250, tested.getRoadLength());
    }

    @Test
    public void getRoadNameTest() {
        CityRoad tested = this.simulator.getRealRoad(road);
        Assert.assertEquals("r-1", tested.getRoadName());
    }

    @Test
    public void getStreetTest() {
        CityRoad tested = this.simulator.getRealRoad(road);
        Assert.assertNull(tested.getStreet());
    }

    @Test
    public void getSourceCrossroadTest() {
        CityRoad tested = this.simulator.getRealRoad(road);
        Assert.assertEquals(this.crossroad1, tested.getSourceCrossroad());
    }

    @Test
    public void getDestinationCrossroadTest() {
        CityRoad tested = this.simulator.getRealRoad(road);
        Assert.assertEquals(this.crossroad2, tested.getDestinationCrossroad());
    }

    @Test
    public void vehicleAndTickTest() {
        CityRoad tested = this.simulator.getRealRoad(road);
        Assert.assertNotNull(tested.addVehicle(this.vehicle));
        Assert.assertFalse(ticked);
        tested.tick();
        Assert.assertTrue(ticked);
        tested.removeVehicle(this.vehicle);
        this.ticked = false;
        tested.tick();
        Assert.assertFalse(ticked);
    }

    @Test
    public void getCurrentWeightTest() {
        CityRoad tested = this.simulator.getRealRoad(road);
        Assert.assertEquals(Long.valueOf(18), tested.getCurrentWeight().seconds());
        tested.addVehicle(this.vehicle);
        Assert.assertEquals(Long.valueOf(18), tested.getCurrentWeight().seconds());
        tested.addVehicle(this.v2);
        Assert.assertEquals(Long.valueOf(18), tested.getCurrentWeight().seconds());
        tested.addVehicle(this.v3);
        Assert.assertEquals(Long.valueOf(18), tested.getCurrentWeight().seconds());
        tested.addVehicle(this.v4);
        Assert.assertEquals(Long.valueOf(18), tested.getCurrentWeight().seconds());
        tested.addVehicle(this.v5);
        Assert.assertEquals(Long.valueOf(18), tested.getCurrentWeight().seconds());
        tested.addVehicle(this.v6);
        Assert.assertEquals(Long.valueOf(19), tested.getCurrentWeight().seconds());
    }

    @Test
    public void getStatisticalDataTest() {
        CityRoad tested = this.simulator.getRealRoad(road);
        RoadData rData = tested.getStatisticalData();
        Assert.assertEquals(250, rData.getRoadLength());
        Assert.assertTrue(50.0 == rData.getAverageSpeed());
        Assert.assertEquals(0, rData.getNumOfVehicles());
    }
}