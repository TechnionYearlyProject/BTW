package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.navigation.NavigationManager;
import il.ac.technion.cs.yp.btw.navigation.Navigator;
import il.ac.technion.cs.yp.btw.navigation.PathNotFoundException;

public class VehicleImpl implements Vehicle {

    private VehicleDescriptor descriptor;
    private Road currentRoad;
    private Road destination;
//    private Double sourceRoadRatio;
//    private Double destinationRoadRatio;
    private Navigator navigator;

    public VehicleImpl(VehicleDescriptor descriptor,
                       Road source, double sourceRoadRatio,
                       Road destination, double destinationRoadRatio,
                       NavigationManager manager) throws PathNotFoundException{
        this.descriptor = descriptor;
        this.destination = destination;
//        this.sourceRoadRatio = sourceRoadRatio;
//        this.destinationRoadRatio = destinationRoadRatio;
        this.navigator = manager.getNavigator(descriptor, source, sourceRoadRatio, destination, destinationRoadRatio);
        this.currentRoad = null;
    }
    /**
     * @return VehicleDescriptor of this Vehicle,
     * which contain technical information
     * about the driven Vehicle
     */
    @Override
    public VehicleDescriptor getVehicleDescriptor() {
        return this.descriptor;
    }

    /**
     * @return the Road this Vehicle is currently located in
     */
    @Override
    public Road getCurrentRoad() {
        return this.currentRoad;
    }

    /**
     * @return the destination Road of this Vehicle
     */
    @Override
    public Road getDestinationRoad() {
        return this.destination;
    }

    /**
     * Commit a drive on Road rd, on the part
     * described by the given ratios
     *
     * @param rd         - The Road to drive on
     * @param ratioStart - the ratio from the beginning
     *                   of the Road, We begin our drive at
     * @param ratioEnd   - the ratio from the the beginning
     */
    @Override
    public void driveOnRoad(Road rd, double ratioStart, double ratioEnd) {
        this.currentRoad = rd;
        LiveCity.LiveRoad realRoad = LiveCity.getRealRoad(rd);
        // TODO
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
     *
     * @param tl - the TrafficLight this Vehicle is waiting on
     */
    @Override
    public void waitOnTrafficLight(TrafficLight tl) {

    }

    /**
     * TODO not sure if possible because it is a single thread
     */
    @Override
    public boolean isWaitingForTrafficLight() {
        return false;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        /*TODO
        at the beginning of driving every Road, check if
        it is the destination Road, if not, drive from the location
        on the Road to the end of it, if the destination is on this Road,
        drive only to the needed ratio.
        when you finish a road you ask the Navigator the next Road
        and you wait on the needed TrafficLight accordingly.
        you wait on it until the same TrafficLight is GREEN
        and you are at the top of the lane.
        and back to the beginning of this pseudo - code
         */
        Road currRoad = this.navigator.getNextRoad();
        while (! currRoad.equals(destination)) {
            driveOnRoad(currRoad);
            // TODO: more...

        }
    }
}
