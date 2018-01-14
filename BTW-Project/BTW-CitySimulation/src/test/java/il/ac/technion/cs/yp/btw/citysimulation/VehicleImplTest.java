package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.navigation.Navigator;
import il.ac.technion.cs.yp.btw.navigation.PathNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

/**
 * Testing VehicleImpl
 */
public class VehicleImplTest {
    private int descCount = 0;
    private Road road1;
    private Road road2;
    private TrafficLight trafficLight;
    private Crossroad crossroad;
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
        Mockito.when(crossroad.getTrafficLightsFromRoad(road1))
                .thenAnswer( invocation -> {
                    Set<TrafficLight> trafficLights = new HashSet<>();
                    trafficLights.add(trafficLight);
                    return trafficLights;
                });

        // trafficlight
        Mockito.when(trafficLight.getName())
                .thenReturn("tl-1");

        Mockito.when(trafficLight.getCoordinateX())
                .thenReturn(0.0);

        Mockito.when(trafficLight.getCoordinateY())
                .thenReturn(0.0);

        Mockito.when(trafficLight.getSourceRoad())
                .thenReturn(road1);

        Mockito.when(trafficLight.getDestinationRoad())
                .thenReturn(road2);

        // road1
        Mockito.when(road1.getRoadName())
                .thenReturn("rd-1");

        Mockito.when(road1.getRoadLength())
                .thenReturn(1);

        Mockito.when(road1.getStreet())
                .thenReturn(null);

        Mockito.when(road1.getSourceCrossroad())
                .thenReturn(null);

        Mockito.when(road1.getDestinationCrossroad())
                .thenReturn(crossroad);

        Mockito.when(road1.getMinimumWeight())
                .thenAnswer(invocation -> BTWWeight.of(1L));

        // road2
        Mockito.when(road2.getRoadName())
                .thenReturn("rd-2");

        Mockito.when(road2.getRoadLength())
                .thenReturn(1);

        Mockito.when(road2.getStreet())
                .thenReturn(null);

        Mockito.when(road2.getSourceCrossroad())
                .thenReturn(crossroad);

        Mockito.when(road2.getDestinationCrossroad())
                .thenReturn(null);

        Mockito.when(road2.getMinimumWeight())
                .thenAnswer(invocation -> BTWWeight.of(1L));

        // navigator
        Mockito.when(navigator.getNextRoad())
                .thenAnswer(invocation -> routeIter.next());

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
        configMock();
    }

    @Test
    public void getVehicleDescriptorTest() {
        Vehicle tested = null;
        try {
            tested = new VehicleImpl(new TestingVehicleDescriptor(), road1, 0.0, road2, 0.0, navigator);
        } catch (PathNotFoundException e) {
            Assert.fail();
        }
        Assert.assertEquals(descCount - 1, tested.getVehicleDescriptor().hashCode());
    }

    @Test
    public void currentRoadAndProgressTest() {
        Vehicle tested = null;
        try {
            tested = new VehicleImpl(new TestingVehicleDescriptor(), road1, 0.0, road2, 0.0, navigator);
        } catch (PathNotFoundException e) {
            Assert.fail();
        }
        Assert.assertNull(tested.getCurrentRoad());
        Assert.assertEquals(road1, tested.progressRoad().getCurrentRoad());
        Assert.assertEquals(road2, tested.progressRoad().getCurrentRoad());
    }

    @Test
    public void getDestinationRoadTest() {
        Vehicle tested = null;
        try {
            tested = new VehicleImpl(new TestingVehicleDescriptor(), road1, 0.0, road2, 0.0, navigator);
        } catch (PathNotFoundException e) {
            Assert.fail();
        }
        Assert.assertEquals(road2, tested.getDestinationRoad());
    }

    @Test
    public void driveOnRoadTest() {
//        Vehicle tested = null;
//        try {
//            tested = new VehicleImpl(new TestingVehicleDescriptor(), road1, 0.0, road2, 0.0, navigator);
//        } catch (PathNotFoundException e) {
//            Assert.fail();
//        }
//        Assert.assertEquals(road2, tested.getDestinationRoad());
    }

    @Test
    public void waitOnTrafficLightTest() {

    }

    @Test
    public void isWaitingForTrafficLightTest() {

    }


    @Test
    public void setRemainingTimeOnRoadTest() {

    }

    @Test
    public void progressOnRoadTest() {

    }
}
