package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.*;

public interface Vehicle {
    /**
     * @return VehicleDescriptor of this Vehicle,
     *         which contain technical information
     *         about the driven Vehicle
     */
    VehicleDescriptor getVehicleDescriptor();
    /**
     * @return the Road this Vehicle is currently located in
     */
    Road getCurrentRoad();

    /**
     * @return the Road this Vehicle is headed to
     */
    Road getNextRoad();
    /**
     * @return the destination Road of this Vehicle
     */
    Road getDestinationRoad();

    /**
     * Commit a drive on Road rd, on the part
     * described by the given ratios
     * @param rd - The Road to drive on
     * @param ratioStart - the ratio from the beginning
     *                     of the Road, We begin our drive at
     * @param ratioEnd - the ratio from the the beginning
     *                   of the Road, We end our drive at
     * @return self
     */
    Vehicle driveOnRoad(Road rd,double ratioStart, double ratioEnd);
    /**
     * Commit a drive on Road rd
     * @param rd - The Road to drive on
     * @return self
     */
    default Vehicle driveOnRoad(Road rd){
        return this.driveOnRoad(rd,0.0,1.0);
    }
    /**
     * puts this Vehicle in wait on the given TrafficLight
     * until the TrafficLight is GREEN and this Vehicle
     * is at the top of the TrafficLight's lane
     * TODO probably should send the wait request for the
     * TODO     Crossroad to manage all of the TrafficLights and
     * TODO     probably also have to waitqueues in him, instead
     * TODO     of the TrafficLights
     * TODO another approach can be an interface like the
     * TODO     Navigator for each Vehicle to do the TrafficLight
     * TODO     talking
     * @param crossroad - the Crossroad containing the TrafficLight this Vehicle is waiting on
     * @return self
     */
    Vehicle waitOnTrafficLight(Crossroad crossroad);

    /**
     * not sure if possible because it is a single thread
     * @return value
     */
    boolean isWaitingForTrafficLight();

    /**
     * move on to the next road on the route
     * @return self
     */
    Vehicle driveToNextRoad();

    /**
     * @return how much time on the current road is needed to finish it
     */
    BTWWeight getRemainingTimeOnRoad();

    /**
     * down-count time on road
     * @return self
     */
    Vehicle progressOnRoad();

    /**
     * start driving using the navigator if the time for driving has come
     * @param now - current time in the system
     * @return indicator if time for driving has come
     */
    boolean driveOnTime(long now);

    /**
     * return the time in which the driving starts
     *
     * @return the time in which the driving starts
     */
    BTWTime getStartingTime();

    /*
    * @Author Sharon Hadar
    * @Date 2/6/2018
    * @return the overload of the vehicle on the current road
    * the overload is computed like this:
    * (remainingLengthOnRoad)/(roadLength)
    * */
    double getOverloadOfVehicleOnCurrentRoad();
}
