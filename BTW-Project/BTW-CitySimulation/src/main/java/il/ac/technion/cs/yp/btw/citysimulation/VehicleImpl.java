package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.BTWIllegalTimeException;
import il.ac.technion.cs.yp.btw.classes.BTWWeight;
import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.navigation.Navigator;
import il.ac.technion.cs.yp.btw.navigation.PathNotFoundException;

/**
 * Created by Guy Rephaeli on 15-Jan-18.
 * implementation of Vehicle
 */
public class VehicleImpl implements Vehicle {
    private VehicleDescriptor descriptor;
    private Road currentRoad;
    private Road nextRoad;
    private Road destination;
    private Double sourceRoadRatio;
    private Double destinationRoadRatio;
    private Navigator navigator;
    private Long remainingTimeOnRoad;
    private boolean isWaitingOnTrafficLight;
    private CitySimulator simulator;
    private long startTime;

    public VehicleImpl(VehicleDescriptor descriptor,
                       Road source, double sourceRoadRatio,
                       Road destination, double destinationRoadRatio,
                       Navigator navigator, CitySimulator simulator, long startTime) throws PathNotFoundException {
        this.descriptor = descriptor;
        this.destination = destination;
        this.sourceRoadRatio = sourceRoadRatio;
        this.destinationRoadRatio = destinationRoadRatio;
        this.navigator = navigator;
        this.currentRoad = null;
        this.nextRoad = navigator.getNextRoad();
        this.remainingTimeOnRoad = 0L;
        this.isWaitingOnTrafficLight = false;
        this.simulator = simulator;
        this.startTime = startTime;
    }

    private Vehicle leaveRoad(Road rd) {
        if (rd != null) {
            CityRoad realRoad = this.simulator.getRealRoad(rd);
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
        CityRoad realRoad = this.simulator.getRealRoad(rd);
        BTWWeight weight = realRoad.getCurrentWeight();
        this.remainingTimeOnRoad = Double.valueOf((ratioEnd - ratioStart) * weight.seconds()).longValue();
        realRoad.addVehicle(this);
        return this;
    }

    public Vehicle driveOnFirstRoad() {
        Road prev = this.currentRoad;
        this.leaveRoad(prev);
        this.isWaitingOnTrafficLight = false;
        this.currentRoad = this.nextRoad;
        if (this.currentRoad.equals(this.destination)) {
            this.nextRoad = null;
            return driveOnRoad(currentRoad, this.sourceRoadRatio, this.destinationRoadRatio);
        }
        this.nextRoad = this.navigator.getNextRoad();
        return driveOnRoad(currentRoad, this.sourceRoadRatio, 1.0);
    }

    public Vehicle driveOnLastRoad() {
        return driveOnRoad(this.destination, 0.0, this.destinationRoadRatio);
    }

    /**
     * puts this Vehicle in wait on the given TrafficLight
     * until the TrafficLight is GREEN and this Vehicle
     * is at the top of the TrafficLight's lane
     * @param crossroad - the Crossroad containing the TrafficLight this Vehicle is waiting on
     */
    @Override
    public Vehicle waitOnTrafficLight(Crossroad crossroad) {
        CityCrossroad realCrossroad = this.simulator.getRealCrossroad(crossroad);
        this.isWaitingOnTrafficLight = true;
        realCrossroad.addVehicle(this);
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
        this.leaveRoad(prev);
        this.isWaitingOnTrafficLight = false;
        this.currentRoad = this.nextRoad;
        if (this.currentRoad.equals(this.destination)) {
            this.nextRoad = null;
            this.driveOnLastRoad();
        } else {
            this.nextRoad = this.navigator.getNextRoad();
            this.driveOnRoad(this.currentRoad);
        }
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
    public Vehicle progressOnRoad() {
        long prevRemainingTime = this.remainingTimeOnRoad;
        if (this.remainingTimeOnRoad > 0) {
            this.remainingTimeOnRoad--;
        }
        if (this.remainingTimeOnRoad <= 0) {
            if (this.currentRoad.equals(this.destination)) {
                return this;
            }
            if (prevRemainingTime > 0) {
                waitOnTrafficLight(currentRoad.getDestinationCrossroad());
            }
        }
        return this;
    }

    @Override
    public boolean driveOnTime(long now) {
        if (this.startTime > now) {
            return false;
        }
        driveOnFirstRoad();
        return true;
    }
}
