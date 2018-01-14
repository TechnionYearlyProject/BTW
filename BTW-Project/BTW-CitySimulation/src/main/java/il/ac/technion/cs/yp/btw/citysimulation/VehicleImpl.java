package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.navigation.Navigator;
import il.ac.technion.cs.yp.btw.navigation.PathNotFoundException;

import java.util.Set;

public class VehicleImpl implements Vehicle {

    private VehicleDescriptor descriptor;
    private Road currentRoad;
    private Road nextRoad;
    private Road destination;
//    private Double sourceRoadRatio;
//    private Double destinationRoadRatio;
    private Navigator navigator;
    private Long remainingTimeOnRoad;
    private boolean isWaitingOnTrafficLight;

    public VehicleImpl(VehicleDescriptor descriptor,
                       Road source, double sourceRoadRatio,
                       Road destination, double destinationRoadRatio,
                       Navigator navigator) throws PathNotFoundException{
        this.descriptor = descriptor;
        this.destination = destination;
//        this.sourceRoadRatio = sourceRoadRatio;
//        this.destinationRoadRatio = destinationRoadRatio;
        this.navigator = navigator;
        this.currentRoad = null;
        this.nextRoad = navigator.getNextRoad();
        this.remainingTimeOnRoad = 0L;
        this.isWaitingOnTrafficLight = false;
    }

    private Vehicle leaveRoad(Road rd) {
        if (rd != null) {
            CityRoad realRoad = LiveCity.getRealRoad(rd);
            realRoad.removeVehicle(this);
        }
        return this;
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
    public Vehicle driveOnRoad(Road rd, double ratioStart, double ratioEnd) {
        CityRoad realRoad = LiveCity.getRealRoad(rd);
        realRoad.addVehicle(this);
        return this;
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
     * @param crossroad - the Crossroad containing the TrafficLight this Vehicle is waiting on
     */
    @Override
    public Vehicle waitOnTrafficLight(Crossroad crossroad) {
        Set<TrafficLight> possibleTrafficLights = crossroad.getTrafficLightsFromRoad(this.currentRoad);
        TrafficLight toWaitOn = null;
        for (TrafficLight trafficLight : possibleTrafficLights) {
            if (trafficLight.getDestinationRoad().equals(this.nextRoad)) {
                toWaitOn = trafficLight;
                break;
            }
        }
        this.isWaitingOnTrafficLight = true;
        CityTrafficLight realTL = LiveCity.getRealTrafficLight(toWaitOn);
        realTL.addVehicle(this);
        return this;
    }

    /**
     * TODO not sure if possible because it is a single thread
     */
    @Override
    public boolean isWaitingForTrafficLight() {
        return this.isWaitingOnTrafficLight;
    }

    @Override
    public Vehicle progressRoad() {
        Road prev = this.currentRoad;
        this.currentRoad = this.nextRoad;
        if (this.currentRoad.equals(this.destination)) {
            this.nextRoad = null;
        } else {
            this.nextRoad = this.navigator.getNextRoad();
        }
        this.leaveRoad(prev);
        this.isWaitingOnTrafficLight = false;
        this.driveOnRoad(this.currentRoad);
        return this;
    }

    @Override
    public BTWWeight getRemainingTimeOnRoad() {
        try {
            return BTWWeight.of(this.remainingTimeOnRoad);
        } catch(BTWIllegalTimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Vehicle setRemainingTimeOnRoad(BTWWeight timeOnRoad) {
        this.remainingTimeOnRoad = timeOnRoad.seconds();
        return this;
    }

    @Override
    public Vehicle progressOnRoad() {
        this.remainingTimeOnRoad--;
        if (this.remainingTimeOnRoad <= 0) {
            waitOnTrafficLight(currentRoad.getDestinationCrossroad());
        }
        return this;
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
        // may not be useful anymore
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
//        Road currRoad = this.navigator.getNextRoad();
//        while (! currRoad.equals(destination)) {
//            driveOnRoad(currRoad);
//            // TODO: more...
//
//        }
    }
}
