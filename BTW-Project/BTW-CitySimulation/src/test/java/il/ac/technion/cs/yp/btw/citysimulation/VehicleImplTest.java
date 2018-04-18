package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.BTWWeight;
import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.navigation.Navigator;
import il.ac.technion.cs.yp.btw.navigation.PathNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.*;

/**
 * Created by Guy Rephaeli on 16-Jan-18.
 * Testing VehicleImpl
 */
public class VehicleImplTest {
    private int descCount = 0;
    private Road road1;
    private Road road2;
    private TrafficLight trafficLight;
    private Crossroad crossroad;
    private CitySimulator simulator;
    private CityRoad realRoad1;
    private CityRoad realRoad2;
    private CityCrossroad realCrossroad;
    private CityTrafficLight realTrafficLight;
    private List<Road> route;
    private Iterator<Road> routeIter;
    private Navigator navigator;


    class TestingVehicleDescriptor implements VehicleDescriptor {
        private int desc;

        private TestingVehicleDescriptor() {
            this.desc = descCount;
            descCount++;
        }

        @Override
        public int compareTo(VehicleDescriptor descriptor) {
            return this.hashCode() - descriptor.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (! (o instanceof VehicleDescriptor)) {
                return false;
            }
            VehicleDescriptor descriptor = (VehicleDescriptor)o;
            return descriptor.hashCode() == this.hashCode();
        }

        @Override
        public int hashCode() {
            return desc;
        }
    }

    private void configMock() {
        ArgumentCaptor<Vehicle> captorV = ArgumentCaptor.forClass(Vehicle.class);

        //crossroad
        Mockito.when(crossroad.getTrafficLightsFromRoad(road1))
                .thenAnswer( invocation -> {
                    Set<TrafficLight> trafficLights = new HashSet<>();
                    trafficLights.add(trafficLight);
                    return trafficLights;
                });

        // realCrossroad
        Mockito.when(realCrossroad.addVehicleOnTrafficLight(captorV.capture()))
                .thenAnswer(invocation -> realTrafficLight);

        // trafficlight
        Mockito.when(trafficLight.getSourceRoad())
                .thenReturn(road1);

        Mockito.when(trafficLight.getDestinationRoad())
                .thenReturn(road2);

        // realTrafficLight

        // road1
        Mockito.when(road1.getSourceCrossroad())
                .thenReturn(null);

        Mockito.when(road1.getDestinationCrossroad())
                .thenReturn(crossroad);

        // realRoad1
        Mockito.when(realRoad1.getCurrentWeight())
                .thenAnswer(invocation -> BTWWeight.of(2L));

        Mockito.when(realRoad1.addVehicle(captorV.capture()))
                .thenAnswer(invocation -> realRoad1);

        Mockito.when(realRoad1.removeVehicle(captorV.capture()))
                .thenAnswer(invocation -> realRoad1);

        // road2
        Mockito.when(road2.getSourceCrossroad())
                .thenReturn(crossroad);

        Mockito.when(road2.getDestinationCrossroad())
                .thenReturn(null);

        // realRoad2
        Mockito.when(realRoad2.getCurrentWeight())
                .thenAnswer(invocation -> BTWWeight.of(2L));

        Mockito.when(realRoad2.addVehicle(captorV.capture()))
                .thenAnswer(invocation -> realRoad2);

        Mockito.when(realRoad2.removeVehicle(captorV.capture()))
                .thenAnswer(invocation -> realRoad2);

        // navigator
        Mockito.when(navigator.getNextRoad())
                .thenAnswer(invocation -> routeIter.next());

        // simulator
        Mockito.when(simulator.getRealRoad(road1))
                .thenAnswer(invocation -> realRoad1);
        Mockito.when(simulator.getRealRoad(road2))
                .thenAnswer(invocation -> realRoad2);
        Mockito.when(simulator.getRealCrossroad(crossroad))
                .thenAnswer(invocation -> realCrossroad);

    }

    public VehicleImplTest() {
        this.route = new ArrayList<>();
        crossroad = Mockito.mock(Crossroad.class);
        trafficLight = Mockito.mock(TrafficLight.class);
        trafficLight = Mockito.mock(TrafficLight.class);
        road1 = Mockito.mock(Road.class);
        road2 = Mockito.mock(Road.class);
        route.add(road1);
        route.add(road2);
        this.routeIter = route.iterator();
        this.navigator = Mockito.mock(Navigator.class);
        this.simulator = Mockito.mock(CitySimulator.class);
        this.realRoad1 = Mockito.mock(CityRoad.class);
        this.realRoad2 = Mockito.mock(CityRoad.class);
        this.realCrossroad = Mockito.mock(CityCrossroad.class);
        this.realTrafficLight = Mockito.mock(CityTrafficLight.class);
        configMock();
    }

    @Test
    public void getVehicleDescriptorTest() {
        Vehicle tested = null;
        try {
            tested = new VehicleImpl(new VehicleImplTest.TestingVehicleDescriptor(), road1, 0.0, road2, 1.0, navigator, simulator, 0);
        } catch (PathNotFoundException e) {
            Assert.fail();
        }
        Assert.assertEquals(descCount - 1, tested.getVehicleDescriptor().hashCode());
    }

    @Test
    public void roadGettersAndProgressTest() {
        Vehicle tested = null;
        try {
            tested = new VehicleImpl(new VehicleImplTest.TestingVehicleDescriptor(), road1, 0.0, road2, 1.0, navigator, simulator, 0);
        } catch (PathNotFoundException e) {
            Assert.fail();
        }
        Assert.assertNull(tested.getCurrentRoad());
        Assert.assertEquals(road1, tested.getNextRoad());
        tested.driveOnTime(0L);
        Assert.assertEquals(road1, tested.getCurrentRoad());
        Assert.assertEquals(road2, tested.getNextRoad());
        tested.waitOnTrafficLight(crossroad);
        Assert.assertEquals(road2, tested.driveToNextRoad().getCurrentRoad());
        Assert.assertNull(tested.getNextRoad());
    }

    @Test
    public void driveOnTimeTest() {
        Vehicle tested = null;
        try {
            tested = new VehicleImpl(new VehicleImplTest.TestingVehicleDescriptor(), road1, 0.0, road2, 1.0, navigator, simulator, 1);
        } catch (PathNotFoundException e) {
            Assert.fail();
        }
        Assert.assertFalse(tested.driveOnTime(0L));
        Assert.assertNull(tested.getCurrentRoad());
        Assert.assertEquals(road1, tested.getNextRoad());
        Assert.assertTrue(tested.driveOnTime(1L));
        Assert.assertEquals(road1, tested.getCurrentRoad());
        Assert.assertEquals(road2, tested.getNextRoad());
    }

    @Test
    public void getDestinationRoadTest() {
        Vehicle tested = null;
        try {
            tested = new VehicleImpl(new VehicleImplTest.TestingVehicleDescriptor(), road1, 0.0, road2, 1.0, navigator, simulator, 0);
        } catch (PathNotFoundException e) {
            Assert.fail();
        }
        Assert.assertEquals(road2, tested.getDestinationRoad());
    }

    @Test
    public void waitOnTrafficLightTest() {
        Vehicle tested = null;
        try {
            tested = new VehicleImpl(new VehicleImplTest.TestingVehicleDescriptor(), road1, 0.0, road2, 1.0, navigator, simulator, 0);
        } catch (PathNotFoundException e) {
            Assert.fail();
        }
        Assert.assertFalse(tested.isWaitingForTrafficLight());
        tested.driveOnTime(0L);
        Assert.assertTrue(tested.waitOnTrafficLight(this.crossroad).isWaitingForTrafficLight());
    }

    @Test
    public void progressOnRoadTest() {
        Vehicle tested = null;
        try {
            tested = new VehicleImpl(new VehicleImplTest.TestingVehicleDescriptor(), road1, 0.0, road2, 0.5, navigator, simulator, 0);
        } catch (PathNotFoundException e) {
            Assert.fail();
        }
        tested.driveOnTime(0L);
        Assert.assertEquals(road1, tested.getCurrentRoad());
        Assert.assertEquals(Long.valueOf(2L), tested.getRemainingTimeOnRoad().seconds());
        Assert.assertEquals(road1, tested.progressOnRoad().getCurrentRoad());
        Assert.assertEquals(Long.valueOf(1L), tested.getRemainingTimeOnRoad().seconds());
        Assert.assertFalse(tested.isWaitingForTrafficLight());
        Assert.assertTrue(tested.progressOnRoad().isWaitingForTrafficLight());
        Assert.assertEquals(Long.valueOf(0L), tested.getRemainingTimeOnRoad().seconds());
        Assert.assertEquals(Long.valueOf(1L), tested.driveToNextRoad().getRemainingTimeOnRoad().seconds());
        Assert.assertEquals(tested, tested.progressOnRoad());
        Assert.assertFalse(tested.isWaitingForTrafficLight());
        Assert.assertEquals(tested, tested.progressOnRoad());
        Assert.assertFalse(tested.isWaitingForTrafficLight());
        Assert.assertEquals(tested, tested.progressOnRoad());
        Assert.assertFalse(tested.isWaitingForTrafficLight());
        Assert.assertEquals(Long.valueOf(0L), tested.getRemainingTimeOnRoad().seconds());
    }

    @Test
    public void oneRoadRouteTest() {
        Vehicle tested = null;
        try {
            tested = new VehicleImpl(new VehicleImplTest.TestingVehicleDescriptor(), road1, 0.0, road1, 1.0, navigator, simulator, 0);
        } catch (PathNotFoundException e) {
            Assert.fail();
        }
        Assert.assertTrue(tested.driveOnTime(0L));
        Assert.assertEquals(road1, tested.getCurrentRoad());
        Assert.assertNull(tested.getNextRoad());
        Assert.assertEquals(Long.valueOf(2L), tested.getRemainingTimeOnRoad().seconds());
        Assert.assertEquals(tested, tested.progressOnRoad());
        Assert.assertEquals(Long.valueOf(1L), tested.getRemainingTimeOnRoad().seconds());
        Assert.assertFalse(tested.isWaitingForTrafficLight());
        Assert.assertEquals(tested, tested.progressOnRoad());
        Assert.assertFalse(tested.isWaitingForTrafficLight());
        Assert.assertEquals(tested, tested.progressOnRoad());
        Assert.assertFalse(tested.isWaitingForTrafficLight());
        Assert.assertEquals(Long.valueOf(0L), tested.getRemainingTimeOnRoad().seconds());
    }
}
