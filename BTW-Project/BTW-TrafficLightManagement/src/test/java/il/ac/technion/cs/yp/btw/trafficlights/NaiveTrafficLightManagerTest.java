package il.ac.technion.cs.yp.btw.trafficlights;

import il.ac.technion.cs.yp.btw.citysimulation.CityCrossroad;
import il.ac.technion.cs.yp.btw.citysimulation.CityRoad;
import il.ac.technion.cs.yp.btw.citysimulation.CityTrafficLight;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Guy Rephaeli on 19-Jan-18.
 * Testing NaiveTrafficLightManagerTest
 */
public class NaiveTrafficLightManagerTest {
    private CityCrossroad crossroad;
    private CityTrafficLight trafficLight1;
    private CityTrafficLight.TrafficLightState state1;
    private CityTrafficLight trafficLight2;
    private CityTrafficLight.TrafficLightState state2;
    private CityRoad road1;
    private CityRoad road2;
    private Set<CityCrossroad> crossroads;

    private void configMock() {
        ArgumentCaptor<CityTrafficLight.TrafficLightState> captorTS = ArgumentCaptor.forClass(CityTrafficLight.TrafficLightState.class);
        // trafficLight1
        Mockito.when(trafficLight1.getSourceRoad())
                .thenReturn(road1);

        Mockito.when(trafficLight1.getMinimumOpenTime())
                .thenReturn(2);

        Mockito.when(trafficLight1.setTrafficLightState(captorTS.capture()))
                .thenAnswer(invocation -> {
                    this.state1 = captorTS.getValue();
                    return trafficLight1;
                });

        // trafficLight2
        Mockito.when(trafficLight2.getSourceRoad())
                .thenReturn(road2);

        Mockito.when(trafficLight2.getMinimumOpenTime())
                .thenReturn(2);

        Mockito.when(trafficLight2.setTrafficLightState(captorTS.capture()))
                .thenAnswer(invocation -> {
                    this.state2 = captorTS.getValue();
                    return trafficLight2;
                });

        // crossroad
        Mockito.when(crossroad.getRealTrafficLights())
                .thenAnswer(invocation -> {
                    Set<CityTrafficLight> trafficLights = new HashSet<>();
                    trafficLights.add(this.trafficLight1);
                    trafficLights.add(this.trafficLight2);
                    return trafficLights;
                });

    }

    public NaiveTrafficLightManagerTest() {
        this.crossroads = new HashSet<>();
        this.state1 = CityTrafficLight.TrafficLightState.RED;
        this.state2 = CityTrafficLight.TrafficLightState.RED;
        this.crossroad = Mockito.mock(CityCrossroad.class);
        this.trafficLight1 = Mockito.mock(CityTrafficLight.class);
        this.trafficLight2 = Mockito.mock(CityTrafficLight.class);
        this.road1 = Mockito.mock(CityRoad.class);
        this.road2 = Mockito.mock(CityRoad.class);
        this.configMock();
        this.crossroads.add(this.crossroad);
    }

    @Test
    public void tickTest() {
        TrafficLightManager tested = new NaiveTrafficLightManager(this.crossroads);
        for (int i = 0; i < 5; i++) {
            tested.tick();
            Assert.assertNotEquals(state1, state2);
            CityTrafficLight.TrafficLightState tmpState = state1;
            tested.tick();
            Assert.assertNotEquals(state1, state2);
            Assert.assertNotEquals(state1, tmpState);
        }
    }
}