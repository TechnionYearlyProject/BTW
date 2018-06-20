package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.evaluation.Evaluator;
import il.ac.technion.cs.yp.btw.navigation.NavigationManager;
import il.ac.technion.cs.yp.btw.navigation.Navigator;
import il.ac.technion.cs.yp.btw.navigation.PathNotFoundException;
import il.ac.technion.cs.yp.btw.statistics.StatisticsCalculator;
import il.ac.technion.cs.yp.btw.trafficlights.TrafficLightManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.HashSet;
import java.util.Set;

/**
 * Testing CityTrafficLightImpl
 */
public class CityTrafficLightImplTest {
    private CitySimulator simulator;
    private NavigationManager navigationManager;
    private TrafficLightManager trafficLightManager;
    private StatisticsCalculator calculator;
    private Evaluator evaluator;
    private Navigator navigator;
    private VehicleDescriptor descriptor;
    private Road road1;
    private Road road2;
    private Crossroad crossroad;
    private TrafficLight trafficLight;
    private Vehicle vehicle;
    private boolean ticked;
    private long timeWindow = 15 * 60;

    private void configMock() throws PathNotFoundException {
        // navigationManager
        Mockito.when(navigationManager.getNavigator(this.descriptor, road1, 0.0, road2, 1.0, BTWTime.of(0)))
                .thenAnswer(invocation -> this.navigator);

        //crossroad
        Mockito.when(crossroad.getTrafficLightsFromRoad(road1))
                .thenAnswer( invocation -> {
                    Set<TrafficLight> trafficLights = new HashSet<>();
                    trafficLights.add(trafficLight);
                    return trafficLights;
                });

        Mockito.when(crossroad.getTrafficLights())
                .thenAnswer( invocation -> {
                    Set<TrafficLight> trafficLights = new HashSet<>();
                    trafficLights.add(trafficLight);
                    return trafficLights;
                });

        // trafficlight
        Mockito.when(trafficLight.getCoordinateX())
                .thenReturn(0.0);

        Mockito.when(trafficLight.getCoordinateY())
                .thenReturn(0.0);

        Mockito.when(trafficLight.getName())
                .thenReturn("traffic-light");

        Mockito.when(trafficLight.getSourceRoad())
                .thenReturn(road1);

        Mockito.when(trafficLight.getDestinationRoad())
                .thenReturn(road2);

        // road1
        Mockito.when(road1.getName())
                .thenReturn("r-1");

        Mockito.when(road1.getMinimumWeight())
                .thenAnswer(invocation -> BTWWeight.of(2L));

        Mockito.when(road1.getStreet())
                .thenReturn(null);

        Mockito.when(road1.getSourceCrossroad())
                .thenReturn(null);

        Mockito.when(road1.getDestinationCrossroad())
                .thenReturn(crossroad);

        Mockito.when(road1.getRoadLength())
                .thenReturn(100);

        // road2
        Mockito.when(road2.getName())
                .thenReturn("r-2");

        Mockito.when(road2.getMinimumWeight())
                .thenAnswer(invocation -> BTWWeight.of(2L));

        Mockito.when(road2.getStreet())
                .thenReturn(null);

        Mockito.when(road2.getSourceCrossroad())
                .thenReturn(crossroad);

        Mockito.when(road2.getDestinationCrossroad())
                .thenReturn(null);

        Mockito.when(road2.getRoadLength())
                .thenReturn(100);

        // vehicle
        Mockito.when(vehicle.getVehicleDescriptor())
                .thenReturn(this.descriptor);

        Mockito.when(vehicle.driveToNextRoad())
                .thenAnswer(invocation -> {
                    this.ticked = true;
                    return this.vehicle;
                });
    }

    public CityTrafficLightImplTest() {
        this.descriptor = Mockito.mock(VehicleDescriptor.class);
        this.road1 = Mockito.mock(Road.class);
        this.road2 = Mockito.mock(Road.class);
        this.crossroad = Mockito.mock(Crossroad.class);
        this.trafficLight = Mockito.mock(TrafficLight.class);
        this.navigationManager = Mockito.mock(NavigationManager.class);
        this.trafficLightManager = Mockito.mock(TrafficLightManager.class);
        this.calculator = Mockito.mock(StatisticsCalculator.class);
        this.evaluator = Mockito.mock(Evaluator.class);
        this.navigator = Mockito.mock(Navigator.class);
        this.vehicle= Mockito.mock(Vehicle.class);
        this.ticked = false;
        try {
            this.configMock();
        } catch(PathNotFoundException e) {
            throw new RuntimeException(e);
        }
        Set<Road> roads = new HashSet<>();
        roads.add(this.road1);
        roads.add(this.road2);
        Set<TrafficLight> trafficLights = new HashSet<>();
        trafficLights.add(this.trafficLight);
        Set<Crossroad> crossroads = new HashSet<>();
        crossroads.add(this.crossroad);
        this.simulator = new CitySimulatorImpl(roads, trafficLights, crossroads, this.navigationManager, this.trafficLightManager, this.calculator, this.timeWindow, this.evaluator);
    }

    @Before
    public void setUp() {
        ticked = false;
    }

    @Test
    public void getCoordinatesTest() {
        CityTrafficLight tested = this.simulator.getRealTrafficLight(this.trafficLight);
        Assert.assertTrue(0.0 == tested.getCoordinateX());
        Assert.assertTrue(0.0 == tested.getCoordinateY());
    }

    @Test
    public void getNameTest() {
        CityTrafficLight tested = this.simulator.getRealTrafficLight(this.trafficLight);
        Assert.assertEquals("traffic-light", tested.getName());
    }

    @Test
    public void getRoadsTest() {
        CityTrafficLight tested = this.simulator.getRealTrafficLight(this.trafficLight);
        Assert.assertEquals(this.simulator.getRealRoad(this.road1), tested.getSourceRoad());
        Assert.assertEquals(this.simulator.getRealRoad(this.road2), tested.getDestinationRoad());
    }

    @Test
    public void vehicleTickAndStateTest() {
        CityTrafficLight tested = this.simulator.getRealTrafficLight(this.trafficLight);
        Assert.assertNotNull(tested.addVehicle(this.vehicle));
        Assert.assertNotNull(tested.setTrafficLightState(CityTrafficLight.TrafficLightState.GREEN));
        tested.tick();
        Assert.assertFalse(this.ticked);
        tested.tick();
        Assert.assertTrue(this.ticked);
        this.ticked = false;
        tested.tick();
        Assert.assertFalse(this.ticked);
        tested.tick();
        Assert.assertFalse(this.ticked);
        for (int i = 0; i < tested.getMinimumOpenTime() - 5; i++) {
            tested.tick();
            try {
                tested.setTrafficLightState(CityTrafficLight.TrafficLightState.RED);
                Assert.fail();
            } catch (Exception e) {
                Assert.assertTrue(true);
            }
        }
        tested.tick();
        try {
            tested.setTrafficLightState(CityTrafficLight.TrafficLightState.RED);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void getStatisticalDataTest() {
        // TODO
    }
}
