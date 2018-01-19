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

import java.util.*;

/**
 * Created by Guy Rephaeli on 16-Jan-18.
 * Testing CitySimulationImpl
 */
public class CitySimulatorImplTest {
    private Set<Road> roads;
    private Set<TrafficLight> trafficLights;
    private Set<Crossroad> crossroads;
    private NavigationManager navigationManager;
    private Navigator navigator1;
    private Navigator navigator2;
    private VehicleDescriptor descriptor1;
    private VehicleDescriptor descriptor2;
    private Road road1;
    private Road road2;
    private Crossroad crossroad;
    private TrafficLight trafficLight;
    private List<Road> route;
    private Iterator<Road> routeIter1;
    private Iterator<Road> routeIter2;

    private void configMock() throws PathNotFoundException {
        // navigationManager
        Mockito.when(navigationManager.getNavigator(this.descriptor1, road1, 0.0, road2, 1.0))
                .thenAnswer(invocation -> this.navigator1);

        Mockito.when(navigationManager.getNavigator(this.descriptor2, road1, 0.0, road2, 1.0))
                .thenAnswer(invocation -> this.navigator2);

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
                .thenAnswer(invocation -> BTWWeight.of(18L));

        Mockito.when(road1.getStreet())
                .thenReturn(null);

        Mockito.when(road1.getSourceCrossroad())
                .thenReturn(null);

        Mockito.when(road1.getDestinationCrossroad())
                .thenReturn(crossroad);

        Mockito.when(road1.getRoadLength())
                .thenReturn(250);

        // road2
        Mockito.when(road2.getRoadName())
                .thenReturn("r-2");

        Mockito.when(road2.getMinimumWeight())
                .thenAnswer(invocation -> BTWWeight.of(18L));

        Mockito.when(road2.getStreet())
                .thenReturn(null);

        Mockito.when(road2.getSourceCrossroad())
                .thenReturn(crossroad);

        Mockito.when(road2.getDestinationCrossroad())
                .thenReturn(null);

        Mockito.when(road2.getRoadLength())
                .thenReturn(250);

        // navigator1
        Mockito.when(navigator1.getNextRoad())
                .thenAnswer(invocation -> routeIter1.next());

        // navigator2
        Mockito.when(navigator2.getNextRoad())
                .thenAnswer(invocation -> routeIter2.next());
    }

    public CitySimulatorImplTest() {
        this.route = new ArrayList<>();
        this.descriptor1 = Mockito.mock(VehicleDescriptor.class);
        this.descriptor2 = Mockito.mock(VehicleDescriptor.class);
        this.road1 = Mockito.mock(Road.class);
        this.road2 = Mockito.mock(Road.class);
        this.crossroad = Mockito.mock(Crossroad.class);
        this.trafficLight = Mockito.mock(TrafficLight.class);
        this.navigationManager = Mockito.mock(NavigationManager.class);
        this.navigator1 = Mockito.mock(Navigator.class);
        this.navigator2 = Mockito.mock(Navigator.class);
        this.route.add(this.road1);
        this.route.add(this.road2);
        this.routeIter1 = route.iterator();
        this.routeIter2 = route.iterator();
        try {
            this.configMock();
        } catch (PathNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.roads = new HashSet<>();
        this.roads.add(this.road1);
        this.roads.add(this.road2);
        this.trafficLights = new HashSet<>();
        this.trafficLights.add(this.trafficLight);
        this.crossroads = new HashSet<>();
        this.crossroads.add(this.crossroad);
    }

    @Before
    public void setUp() {
        this.routeIter1 = route.iterator();
        this.routeIter2 = route.iterator();
    }

    @Test
    public void addVehicleAndTickTest() {
        CitySimulatorImpl tested = new CitySimulatorImpl(this.roads, this.trafficLights, this.crossroads, navigationManager);
        Vehicle vehicle;
        try {
            vehicle = tested.addVehicle(this.descriptor1, this.road1, 0.0, this.road2, 1.0);
        } catch (PathNotFoundException e) {
            throw new RuntimeException(e);
        }
        tested.tick();
        for (long i = road1.getMinimumWeight().seconds(); i > 0; i--) {
            Assert.assertEquals(road1, vehicle.getCurrentRoad());
            Assert.assertEquals(Long.valueOf(i), vehicle.getRemainingTimeOnRoad().seconds());
            Assert.assertFalse(vehicle.isWaitingForTrafficLight());
            tested.tick();
        }
        Assert.assertTrue(vehicle.isWaitingForTrafficLight());
    }

    @Test
    public void addSeveralVehiclesTest() {
        CitySimulatorImpl tested = new CitySimulatorImpl(this.roads, this.trafficLights, this.crossroads, navigationManager);
        Vehicle vehicle1;
        Vehicle vehicle2;
        List<VehicleDescriptor> descriptors = new ArrayList<>();
        descriptors.add(this.descriptor1);
        descriptors.add(this.descriptor2);
        try {
            List<Vehicle> vehicles = tested.addSeveralVehicles(descriptors, this.road1, 0.0, this.road2, 1.0, 1);
            Assert.assertEquals(2, vehicles.size());
            vehicle1 = vehicles.get(0);
            vehicle2 = vehicles.get(1);
        } catch (PathNotFoundException e) {
            throw new RuntimeException(e);
        }
        tested.tick();
        Assert.assertEquals(road1, vehicle1.getCurrentRoad());
        Assert.assertEquals(Long.valueOf(18), vehicle1.getRemainingTimeOnRoad().seconds());
        Assert.assertFalse(vehicle1.isWaitingForTrafficLight());
        Assert.assertNull(vehicle2.getCurrentRoad());
        tested.tick();
        for (long i = road1.getMinimumWeight().seconds(); i > 1; i--) {
            Assert.assertEquals(road1, vehicle1.getCurrentRoad());
            Assert.assertEquals(road1, vehicle2.getCurrentRoad());
            Assert.assertEquals(Long.valueOf(i - 1), vehicle1.getRemainingTimeOnRoad().seconds());
            Assert.assertEquals(Long.valueOf(i), vehicle2.getRemainingTimeOnRoad().seconds());
            Assert.assertFalse(vehicle1.isWaitingForTrafficLight());
            Assert.assertFalse(vehicle2.isWaitingForTrafficLight());
            tested.tick();
        }
        Assert.assertTrue(vehicle1.isWaitingForTrafficLight());
        Assert.assertEquals(road1, vehicle2.getCurrentRoad());
        Assert.assertEquals(Long.valueOf(1), vehicle2.getRemainingTimeOnRoad().seconds());
        Assert.assertFalse(vehicle2.isWaitingForTrafficLight());
        tested.tick();
        Assert.assertTrue(vehicle2.isWaitingForTrafficLight());
    }

    @Test
    public void saveMapTest() {
        CitySimulatorImpl tested = new CitySimulatorImpl(this.roads, this.trafficLights, this.crossroads, navigationManager);
        CityMap map = tested.saveMap();
        Assert.assertEquals(2, map.getAllRoads().size());
        Assert.assertEquals(1, map.getAllTrafficLights().size());
        Assert.assertEquals(1, map.getAllCrossroads().size());
    }
}
