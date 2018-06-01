package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.TrafficLight;

import java.util.Set;

/**
 * Interface for the real TrafficLight operating unit - not just a data passing class
 */
public interface CityTrafficLight extends TrafficLight, StatisticsProviding<TrafficLightData> {
    /**
     * TrafficLightState enum to describe the
     * different state the TrafficLight can
     * be in, such as Green and Red
     */
    enum TrafficLightState{
        GREEN,RED
    }
    /**
     * sets this Traffic light to the given state
     * @param state - TrafficLightState, such as GREEN/RED
     * @return self
     */
    CityTrafficLight setTrafficLightState(TrafficLightState state);
    /**
     * Open the traffic-light (set GREEN) and release waiting vehicle in some predefined mechanism
     * @return self
     */
    default CityTrafficLight open(){
        return this.setTrafficLightState(TrafficLightState.GREEN);
    }
    /**
     * Close the traffic-light (set RED) - do not let any vehicle to pass through
     * @return self
     */
    default CityTrafficLight close(){
        return this.setTrafficLightState(TrafficLightState.RED);
    }
    /**
     * @param vehicle - vehicle to wait on traffic-light
     * @return self
     */
    CityTrafficLight addVehicle(Vehicle vehicle);
    /**
     * progress everything by a clock progressOnRoad
     * @return self
     */
    CityTrafficLight tick();

    /**
     * @return minimum amount in seconds the TrafficLight
     * needs to be open
     */
    int getMinimumOpenTime();

    /**
     * @return the current state of the traffic-light
     */
    TrafficLightState getState();

    /**
     * @return the number of waiting vehicles
     *         in this Traffic Light
     */
    int getNumOfWaitingVehiclesInTrafficLights();
    /**
     * @author Adam Elgressy
     * @date 30-5-2018
     * returns all the vehicles currently waiting
     * on this traffic lights
     * @return - Set of above mentioned vehicles
     */
    Set<Vehicle> getVehiclesOnTrafficLight();
}
