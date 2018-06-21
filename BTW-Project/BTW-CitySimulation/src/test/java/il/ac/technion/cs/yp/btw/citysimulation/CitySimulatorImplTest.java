package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.evaluation.Evaluator;
import il.ac.technion.cs.yp.btw.navigation.NavigationManager;
import il.ac.technion.cs.yp.btw.navigation.Navigator;
import il.ac.technion.cs.yp.btw.navigation.PathNotFoundException;
import il.ac.technion.cs.yp.btw.statistics.StatisticalReport;
import il.ac.technion.cs.yp.btw.statistics.StatisticsCalculator;
import il.ac.technion.cs.yp.btw.trafficlights.TrafficLightManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.*;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;

/**
 * Created by Guy Rephaeli on 16-Jan-18.
 * Testing CitySimulationImpl
 */
public class CitySimulatorImplTest {
    private Set<Road> roads;
    private Set<TrafficLight> trafficLights;
    private Set<Crossroad> crossroads;
    private NavigationManager navigationManager;
    private TrafficLightManager trafficLightManager;
    private StatisticsCalculator calculator;
    private Evaluator evaluator;
    private Navigator navigator1;
    private Navigator navigator2;
    private Navigator navigator3;
    private VehicleDescriptor descriptor1;
    private VehicleDescriptor descriptor2;
    private Road road1;
    private Road road2;
    private Crossroad crossroad;
    private TrafficLight trafficLight;
    private List<Road> route;
    private Iterator<Road> routeIter1;
    private Iterator<Road> routeIter2;
    private Map<Road, BTWWeight> weightOfRoad;
    private Map<Road, BTWTime> reportTimeOfRoad;
    private Map<Road, Integer> reportersOnRoad;
    private Map<TrafficLight, BTWWeight> weightOfTrafficLight;
    private Map<TrafficLight, BTWTime> reportTimeOfTrafficLight;
    private Map<TrafficLight, Integer> reportersOnTrafficLight;
    private int timeWindow = 15 * 60;


    private void configMock() throws PathNotFoundException {
        ArgumentCaptor<Road> captorRoad = ArgumentCaptor.forClass(Road.class);
        ArgumentCaptor<TrafficLight> captorTrafficLight = ArgumentCaptor.forClass(TrafficLight.class);
        ArgumentCaptor<StatisticalReport> captorReport = ArgumentCaptor.forClass(StatisticalReport.class);

        // navigationManager
        Mockito.when(navigationManager.getNavigator(this.descriptor1, road1, 0.0, road2, 1.0, BTWTime.of(1)))
                .thenAnswer(invocation -> this.navigator1);

        Mockito.when(navigationManager.getNavigator(this.descriptor2, road1, 0.0, road2, 1.0, BTWTime.of(2)))
                .thenAnswer(invocation -> this.navigator2);

        Mockito.when(navigationManager.getNavigator(any(),Mockito.argThat(road -> road.getName().equals("r 2")),anyDouble(),any(),anyDouble(),any()))
                .thenAnswer(invocation -> this.navigator3);

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
        Mockito.when(road1.getName())
                .thenReturn("r 1");

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
        Mockito.when(road2.getName())
                .thenReturn("r 2");

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

        // navigator3
        Mockito.when(navigator3.getNextRoad())
                .thenAnswer(invocation -> routeIter2.next());

        //calculator
        Mockito.when(calculator.addRoadReport(captorRoad.capture(), captorReport.capture()))
                .thenAnswer(invocation -> {
                    this.weightOfRoad.put(captorRoad.getValue(), captorReport.getValue().timeTaken());
                    this.reportTimeOfRoad.put(captorRoad.getValue(), captorReport.getValue().getTimeOfReport());
                    this.reportersOnRoad.put(captorRoad.getValue(), captorReport.getValue().getNumOfReporters());
                    return this.calculator;
                });

        Mockito.when(calculator.addTrafficLightReport(captorTrafficLight.capture(), captorReport.capture()))
                .thenAnswer(invocation -> {
                    this.weightOfTrafficLight.put(captorTrafficLight.getValue(), captorReport.getValue().timeTaken());
                    this.reportTimeOfTrafficLight.put(captorTrafficLight.getValue(), captorReport.getValue().getTimeOfReport());
                    this.reportersOnTrafficLight.put(captorTrafficLight.getValue(), captorReport.getValue().getNumOfReporters());
                    return this.calculator;
                });
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
        this.trafficLightManager = Mockito.mock(TrafficLightManager.class);
        this.calculator = Mockito.mock(StatisticsCalculator.class);
        this.evaluator = Mockito.mock(Evaluator.class);
        this.navigator1 = Mockito.mock(Navigator.class);
        this.navigator2 = Mockito.mock(Navigator.class);
        this.navigator3 = Mockito.mock(Navigator.class);
        this.route.add(this.road1);
        this.route.add(this.road2);
        this.routeIter1 = route.iterator();
        this.routeIter2 = route.iterator();
        this.weightOfRoad = new HashMap<>();
        this.reportersOnRoad = new HashMap<>();
        this.weightOfTrafficLight = new HashMap<>();
        this.reportersOnTrafficLight = new HashMap<>();
        this.reportTimeOfRoad = new HashMap<>();
        this.reportTimeOfTrafficLight = new HashMap<>();
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
        CitySimulatorImpl tested = new CitySimulatorImpl(this.roads, this.trafficLights, this.crossroads, this.navigationManager, this.trafficLightManager, this.calculator, this.timeWindow, this.evaluator);
        Vehicle vehicle;
        try {
            vehicle = tested.addVehicle(this.descriptor1, this.road1, 0.0, this.road2, 1.0);
        } catch (PathNotFoundException e) {
            throw new RuntimeException(e);
        }
        tested.tick();
        Assert.assertEquals(Long.valueOf(1), tested.getCurrentTime());
        for (long i = road1.getMinimumWeight().seconds(); i > 0; i--) {
            Assert.assertEquals(road1, vehicle.getCurrentRoad());
            Assert.assertEquals(Long.valueOf(i), vehicle.getRemainingTimeOnRoad().seconds());
            Assert.assertFalse(vehicle.isWaitingForTrafficLight());
            tested.tick();
        }
        Assert.assertEquals(Long.valueOf(road1.getMinimumWeight().seconds() + 1), tested.getCurrentTime());
        Assert.assertTrue(vehicle.isWaitingForTrafficLight());
    }

    @Test
    public void addSeveralVehiclesTest() {
        CitySimulatorImpl tested = new CitySimulatorImpl(this.roads, this.trafficLights, this.crossroads, this.navigationManager, this.trafficLightManager, this.calculator, this.timeWindow, this.evaluator);
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
        CitySimulatorImpl tested = new CitySimulatorImpl(this.roads, this.trafficLights, this.crossroads, this.navigationManager, this.trafficLightManager, this.calculator, this.timeWindow, this.evaluator);
        CityMap map = tested.saveMap();
        Assert.assertEquals(2, map.getAllRoads().size());
        Assert.assertEquals(1, map.getAllTrafficLights().size());
        Assert.assertEquals(1, map.getAllCrossroads().size());
    }

    @Test
    public void reportTest1() {
        CitySimulatorImpl tested = new CitySimulatorImpl(this.roads, this.trafficLights, this.crossroads, this.navigationManager, this.trafficLightManager, this.calculator, this.timeWindow, this.evaluator);
        tested.tick();
        for (int i = 1; i < 3; i++) {
            tested.reportOnTrafficLight(this.trafficLight, 1L);
            tested.tick();
        }
        for (int i = 3; i < 5; i++) {
            long time = 3 + ((i + 1) % 2);
            tested.reportOnRoad(this.road1, time);
            tested.reportOnTrafficLight(this.trafficLight, 1L);
            tested.tick();
        }
        for (int i = 5; i < 7; i++) {
            long time = 3 + ((i + 1) % 2);
            tested.reportOnRoad(this.road1, time);
            tested.reportOnTrafficLight(this.trafficLight, 5L);
            tested.reportOnTrafficLight(this.trafficLight, 5L);
            tested.reportOnTrafficLight(this.trafficLight, 5L);
            tested.reportOnTrafficLight(this.trafficLight, 5L);
            tested.reportOnTrafficLight(this.trafficLight, 1L);
            tested.tick();
        }
        for (int i = 7; i < this.timeWindow + 1; i++) {
            long time = 3 + ((i + 1) % 2);
            tested.reportOnRoad(this.road1, time);
            tested.reportOnRoad(this.road2, 2 * time);
            tested.reportOnTrafficLight(this.trafficLight, 5L);
            tested.reportOnTrafficLight(this.trafficLight, 5L);
            tested.reportOnTrafficLight(this.trafficLight, 5L);
            tested.reportOnTrafficLight(this.trafficLight, 5L);
            tested.reportOnTrafficLight(this.trafficLight, 1L);
            tested.tick();
        }
        for (int i = this.timeWindow + 1; i < this.timeWindow + 4; i++) {
            long time = 3 + ((i + 1) % 2);
            tested.reportOnRoad(this.road1, time);
            tested.reportOnRoad(this.road2, 2 * time);
            tested.reportOnTrafficLight(this.trafficLight, 5L);
            tested.reportOnTrafficLight(this.trafficLight, 5L);
            tested.reportOnTrafficLight(this.trafficLight, 5L);
            tested.reportOnTrafficLight(this.trafficLight, 5L);
            tested.tick();
        }
        for (int i = this.timeWindow + 4; i < this.timeWindow + 6; i++) {
            long time = 3 + ((i + 1) % 2);
            tested.reportOnRoad(this.road2, 2 * time);
            tested.reportOnTrafficLight(this.trafficLight, 5L);
            tested.reportOnTrafficLight(this.trafficLight, 5L);
            tested.reportOnTrafficLight(this.trafficLight, 5L);
            tested.reportOnTrafficLight(this.trafficLight, 5L);
            tested.tick();
        }

        for (int i = this.timeWindow + 6; i < this.timeWindow + 8; i++) {
            long time = 3 + ((i + 1) % 2);
            tested.reportOnRoad(this.road2, 2 * time);
            tested.tick();
        }

        tested.tick();
        Assert.assertEquals(2, this.weightOfRoad.size());
        Assert.assertEquals(1, this.weightOfTrafficLight.size());

        Assert.assertEquals(Long.valueOf(3), this.weightOfRoad.get(this.road1).seconds());
        Assert.assertEquals(Long.valueOf(7), this.weightOfRoad.get(this.road2).seconds());
        Assert.assertEquals(Long.valueOf(4), this.weightOfTrafficLight.get(this.trafficLight).seconds());

        Assert.assertEquals(Integer.valueOf(this.timeWindow), this.reportersOnRoad.get(this.road1));
        Assert.assertEquals(Integer.valueOf(this.timeWindow), this.reportersOnRoad.get(this.road2));
        Assert.assertEquals(Integer.valueOf(this.timeWindow * 5), this.reportersOnTrafficLight.get(this.trafficLight));

        Assert.assertEquals(Long.valueOf(0), this.reportTimeOfRoad.get(this.road1).seconds());
        Assert.assertEquals(Long.valueOf(0), this.reportTimeOfRoad.get(this.road2).seconds());
        Assert.assertEquals(Long.valueOf(0), this.reportTimeOfTrafficLight.get(this.trafficLight).seconds());
    }

    @Test
    public void reportTest2() {
        CitySimulatorImpl tested = new CitySimulatorImpl(this.roads, this.trafficLights, this.crossroads, this.navigationManager, this.trafficLightManager, this.calculator, this.timeWindow, this.evaluator);
        tested.tick();
        tested.tick();
        for (int i = 0; i < this.timeWindow; i++) {
            tested.tick();
        }
        for (int i = 0; i < this.timeWindow + 1; i++) {
            if ((i % 2) == 0) {
                tested.reportOnRoad(this.road1, 2L);
            }
            tested.tick();
        }
        tested.tick();
        Assert.assertEquals(1, this.weightOfRoad.size());
        Assert.assertEquals(0, this.weightOfTrafficLight.size());

        Assert.assertEquals(Long.valueOf(2), this.weightOfRoad.get(this.road1).seconds());

        Assert.assertEquals(Integer.valueOf(15 * 30), this.reportersOnRoad.get(this.road1));

        Assert.assertEquals(Long.valueOf(15 * 60), this.reportTimeOfRoad.get(this.road1).seconds());
    }

    @Test(expected = RoadNameDoesntExistException.class)
    public void invalidVehicleEntryListTest() throws PathNotFoundException {
        CitySimulatorImpl tested = new CitySimulatorImpl(this.roads, this.trafficLights, this.crossroads, this.navigationManager, this.trafficLightManager, this.calculator, this.timeWindow, this.evaluator);
        List<VehicleEntry> entriesList = new ArrayList<>();
        VehicleEntry entry = Mockito.mock(VehicleEntry.class);
        Mockito.when(entry.getDestinationRoadName()).thenReturn(Optional.of(new RoadName("1 Street")));
        Mockito.when(entry.getSourceRoadName()).thenReturn(Optional.of(new RoadName("1 Street")));
        Mockito.when(entry.getSourceRoadRatio()).thenReturn(Optional.of(new Ratio(0.2)));
        Mockito.when(entry.getDestinationRoadRatio()).thenReturn(Optional.of(new Ratio(0.14)));
        Mockito.when(entry.getTimeOfDrivingStart()).thenReturn(Optional.of(BTWTime.of("00:00:01")));
        entriesList.add(entry);

        tested.addVehiclesFromVehicleEntriesList(entriesList);
    }

    @Test
    public void validVehicleEntryListTest() throws PathNotFoundException {
        CitySimulatorImpl tested = new CitySimulatorImpl(this.roads, this.trafficLights, this.crossroads, this.navigationManager, this.trafficLightManager, this.calculator, this.timeWindow, this.evaluator);
        List<VehicleEntry> entriesList = new ArrayList<>();
        VehicleEntry entry = Mockito.mock(VehicleEntry.class);
        Mockito.when(entry.getSourceRoadName()).thenReturn(Optional.of(new RoadName("r 2")));
        Mockito.when(entry.getDestinationRoadName()).thenReturn(Optional.of(new RoadName("r 1")));
        Mockito.when(entry.getSourceRoadRatio()).thenReturn(Optional.of(new Ratio(0.0)));
        Mockito.when(entry.getDestinationRoadRatio()).thenReturn(Optional.of(new Ratio(1.0)));
        Mockito.when(entry.getTimeOfDrivingStart()).thenReturn(Optional.of(BTWTime.of("00:00:01")));
        entriesList.add(entry);

        tested.addVehiclesFromVehicleEntriesList(entriesList);
    }
}
