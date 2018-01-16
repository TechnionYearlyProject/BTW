package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.navigation.NavigationManager;
import il.ac.technion.cs.yp.btw.navigation.Navigator;
import il.ac.technion.cs.yp.btw.navigation.PathNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Testing LiveRoad
 */
public class CityRoadImplTest {
    private CitySimulator simulator;
    private Set<Road> roads;
    private Set<TrafficLight> trafficLights;
    private Set<Crossroad> crossroads;
    private NavigationManager navigationManager;
    private Navigator navigator;
    private VehicleDescriptor descriptor;
    private Road road;
    private List<Road> route;
    private Crossroad crossroad1;
    private Crossroad crossroad2;
    private TrafficLight trafficLight1;
    private TrafficLight trafficLight2;

    void configMock() throws PathNotFoundException {
        ArgumentCaptor<Vehicle> captorV = ArgumentCaptor.forClass(Vehicle.class);

        // navigationManager
        Mockito.when(navigationManager.getNavigator(this.descriptor, road, 0.0, road, 1.0))
                .thenAnswer(invocation -> this.navigator);

        //crossroad2
        Mockito.when(crossroad2.getTrafficLightsFromRoad(road))
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

        // road1
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
    }

    CityRoadImplTest() {
        this.route = new ArrayList<>();
        this.descriptor = Mockito.mock(VehicleDescriptor.class);
        this.road = Mockito.mock(Road.class);
        this.crossroad1 = Mockito.mock(Crossroad.class);
        this.crossroad2 = Mockito.mock(Crossroad.class);
        this.trafficLight1 = Mockito.mock(TrafficLight.class);
        this.trafficLight2 = Mockito.mock(TrafficLight.class);
        this.navigationManager = Mockito.mock(NavigationManager.class);
        this.navigator = Mockito.mock(Navigator.class);
        try {
            this.configMock();
        } catch(PathNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.route.add(road);
        this.roads = new HashSet<>();
        this.roads.add(this.road);
        this.trafficLights = new HashSet<>();
        this.trafficLights.add(this.trafficLight1);
        this.trafficLights.add(this.trafficLight2);
        this.crossroads = new HashSet<>();
        this.simulator = new CitySimulatorImpl(this.roads, this.trafficLights, this.crossroads, navigationManager);
    }

    @Test
    public void isStreetNumberInRangeTest() {
        CityRoad tested = this.simulator.getRealRoad(road);
        Assert.assertNull(tested.isStreetNumberInRange(0));
    }

    @Test
    public void getRoadLengthTest() {

    }

    @Test
    public void getRoadNameTest() {

    }

    @Test
    public void getStreetTest() {

    }

    @Test
    public void getSourceCrossroadTest() {

    }

    @Test
    public void getDestinationCrossroadTest() {

    }

    @Test
    public void addVehicleTest() {

    }

    @Test
    public void removeVehicleTest() {

    }

    @Test
    public void tickTest() {

    }

    @Test
    public void getCurrentWeightTest() {
        // TODO
    }
}
