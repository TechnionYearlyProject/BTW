package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.Crossroad;

import java.util.Set;

/**
 * Interface for the real Crossroad operating unit - not just a data passing class
 */
public interface CityCrossroad extends Crossroad, StatisticsProviding<CrossroadData> {
    /**
     * add a Vehicle to this Crossroad and return the traffic-light on which the vehicle is going to wait
     * @param vehicle - the Vehicle to be added
     * @return the traffic-light the vehicle is going to wait on
     */
    CityTrafficLight addVehicleOnTrafficLight(Vehicle vehicle);

    /**
     * @return real instances of all traffic-lights in the crossroad
     */
    Set<CityTrafficLight> getRealTrafficLights();

    /**
     * @Author Sharon Hadar
     * @Date 2/6/2018
     * @return the overload on the whole crossroad
     */
    Double getOverload();

    /**
     * @author Guy Repaheli
     * @date
     *
     * @param rd the road from which we want the traffic-lights
     * @return real instances of all traffic-lights form road 'rd'
     */
    Set<CityTrafficLight> getRealTrafficLightsFromRoad(CityRoad rd);

    /**
     * progress everything by a clock all TrafficLights
     * in this Crossroad, and manage the opening and closing
     * of TrafficLights during the tick
     * @return self
     */
    CityCrossroad tick();


}
