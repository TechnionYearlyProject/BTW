package il.ac.technion.cs.yp.btw.citysimulation;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class CityTrafficLightTest {

    @Test
    public void testOpen() {
        CityTrafficLight ctl = Mockito.mock(CityTrafficLight.class);
        Mockito.when(ctl.open()).thenCallRealMethod();
        ctl.open();
        Mockito.verify(ctl,Mockito.atLeastOnce()).setTrafficLightState(Mockito.same(CityTrafficLight.TrafficLightState.GREEN));
    }

    @Test
    public void testCloseCallsTheRightWay() {
        CityTrafficLight ctl = Mockito.mock(CityTrafficLight.class);
        Mockito.when(ctl.close()).thenCallRealMethod();
        ctl.close();
        Mockito.verify(ctl,Mockito.atLeastOnce()).setTrafficLightState(Mockito.same(CityTrafficLight.TrafficLightState.RED));
    }
}