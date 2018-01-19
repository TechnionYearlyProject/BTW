package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.BTWWeight;
import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.navigation.NavigationManager;
import il.ac.technion.cs.yp.btw.navigation.Navigator;
import il.ac.technion.cs.yp.btw.navigation.PathNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Guy Rephaeli on 16-Jan-18.
 * Testing CityCrossroadImpl
 */
public class CityCrossroadImplTest {
    private CitySimulator simulator;
    private NavigationManager navigationManager;
    private Navigator navigator;
    private VehicleDescriptor descriptor;
    private Road road1;
    private Road road2;
    private Crossroad crossroad;
    private TrafficLight trafficLight;
    private Vehicle vehicle;
    private boolean ticked;

    private void configMock() throws PathNotFoundException {
        // navigationManager
        Mockito.when(navigationManager.getNavigator(this.descriptor, road1, 0.0, road2, 1.0))
                .thenAnswer(invocation -> this.navigator);

        //crossroad
        Mockito.when(crossroad.getName())
                .thenReturn("crossroad");

        Mockito.when(crossroad.getCoordinateX())
                .thenReturn(0.0);

        Mockito.when(crossroad.getCoordinateY())
                .thenReturn(0.0);

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
        Mockito.when(road1.getRoadName())
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
        Mockito.when(road2.getRoadName())
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
        Mockito.when(vehicle.getCurrentRoad())
                .thenReturn(this.road1);

        Mockito.when(vehicle.getNextRoad())
                .thenReturn(this.road2);

        Mockito.when(vehicle.driveToNextRoad())
                .thenAnswer(invocation -> {
                    this.ticked = true;
                    return this.vehicle;
                });
    }

    public CityCrossroadImplTest() {
        this.descriptor = Mockito.mock(VehicleDescriptor.class);
        this.road1 = Mockito.mock(Road.class);
        this.road2 = Mockito.mock(Road.class);
        this.crossroad = Mockito.mock(Crossroad.class);
        this.trafficLight = Mockito.mock(TrafficLight.class);
        this.navigationManager = Mockito.mock(NavigationManager.class);
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
        this.simulator = new CitySimulatorImpl(roads, trafficLights, crossroads, navigationManager);
    }

    @Before
    public void setUp() {
        ticked = false;
    }

    @Test
    public void vehicleAndTickTest() {
        CityCrossroad tested = simulator.getRealCrossroad(this.crossroad);
        Assert.assertNotNull(tested.addVehicle(this.vehicle));
        CityTrafficLight t = simulator.getRealTrafficLight(this.trafficLight);
        t.setTrafficLightState(CityTrafficLight.TrafficLightState.GREEN);
        tested.tick();
        Assert.assertFalse(this.ticked);
        tested.tick();
        Assert.assertTrue(this.ticked);
        this.ticked = false;
        tested.tick();
        Assert.assertFalse(this.ticked);
        tested.tick();
        Assert.assertFalse(this.ticked);
    }

    @Test
    public void getNameTest() {
        CityCrossroad tested = simulator.getRealCrossroad(this.crossroad);
        Assert.assertEquals("crossroad", tested.getName());
    }

    @Test
    public void getTrafficLightsTest() {
        CityCrossroad tested = simulator.getRealCrossroad(this.crossroad);
        Set<TrafficLight> trafficLights = tested.getTrafficLights();
        Assert.assertEquals(1, trafficLights.size());
        Assert.assertTrue(trafficLights.contains(this.trafficLight));
    }

    @Test
    public void getTrafficLightsFromRoadTest() {
        CityCrossroad tested = simulator.getRealCrossroad(this.crossroad);
        Set<TrafficLight> trafficLights = tested.getTrafficLightsFromRoad(this.road1);
        Assert.assertEquals(1, trafficLights.size());
        Assert.assertTrue(trafficLights.contains(this.trafficLight));
    }

    @Test
    public void getCoordinatesTest() {
        CityCrossroad tested = simulator.getRealCrossroad(this.crossroad);
        Assert.assertTrue(tested.getCoordinateX() == 0.0);
        Assert.assertTrue(tested.getCoordinateY() == 0.0);
    }

    @Test
    public void getStatisticalDataTest() {
        // TODO
    }
}
