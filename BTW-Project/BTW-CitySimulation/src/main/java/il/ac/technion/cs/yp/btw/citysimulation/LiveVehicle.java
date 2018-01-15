package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.navigation.Navigator;
import il.ac.technion.cs.yp.btw.navigation.PathNotFoundException;

import java.util.Set;

public class LiveVehicle implements Vehicle {

    private VehicleDescriptor descriptor;
    private Road currentRoad;
    private Road nextRoad;
    private Road destination;
//    private Double sourceRoadRatio;
//    private Double destinationRoadRatio;
    private Navigator navigator;
    private Long remainingTimeOnRoad;
    private boolean isWaitingOnTrafficLight;

    public LiveVehicle(VehicleDescriptor descriptor,
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

    @Override
    public Road getNextRoad() {
        return this.nextRoad;
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

        CityCrossroad realCrossroad = LiveCity.getRealCrossroad(crossroad);
        this.isWaitingOnTrafficLight = true;
        realCrossroad.addVehicle(this);
//        Set<TrafficLight> possibleTrafficLights = crossroad.getTrafficLightsFromRoad(this.currentRoad);
//        TrafficLight toWaitOn = null;
//        for (TrafficLight trafficLight : possibleTrafficLights) {
//            if (trafficLight.getDestinationRoad().equals(this.nextRoad)) {
//                toWaitOn = trafficLight;
//                break;
//            }
//        }
//        CityTrafficLight realTL = LiveCity.getRealTrafficLight(toWaitOn);
//        realTL.addVehicle(this);
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
    public Vehicle driveToNextRoad() {
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

    @Override
    public boolean driveOnTime(long now) {
        return false;
    }
}
