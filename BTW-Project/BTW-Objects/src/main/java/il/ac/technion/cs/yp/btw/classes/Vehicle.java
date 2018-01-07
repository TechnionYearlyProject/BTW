package il.ac.technion.cs.yp.btw.classes;

public interface Vehicle extends Runnable{
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
     */
    void driveOnRoad(Road rd,double ratioStart, double ratioEnd);
    /**
     * Commit a drive on Road rd
     * @param rd - The Road to drive on
     */
    default void driveOnRoad(Road rd){
        this.driveOnRoad(rd,0.0,1.0);
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
     * @param tl - the TrafficLight this Vehicle is waiting on
     */
    void waitOnTrafficLight(TrafficLight tl);
    /**
     * not sure if possible because it is a single thread
     */
    boolean isWaitingForTrafficLight();
}
