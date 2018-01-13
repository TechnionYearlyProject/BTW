package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.BTWWeight;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;

/**
 * Interface for the real TrafficLight operating unit - not just a data passing class
 */
public interface CityTrafficLight extends TrafficLight {

    /**
     * Open the traffic-light (set GREEN) and release waiting vehicle in some predefined mechanism
     * @return self
     */
    CityTrafficLight open();

    /**
     * Close the traffic-light (set RED) - do not let any vehicle to pass through
     * @return self
     */
    CityTrafficLight close();

    /**
     * @param vehicle - vehicle to wait on traffic-light
     * @return self
     */
    public CityTrafficLight addVehicle(Vehicle vehicle);
}
