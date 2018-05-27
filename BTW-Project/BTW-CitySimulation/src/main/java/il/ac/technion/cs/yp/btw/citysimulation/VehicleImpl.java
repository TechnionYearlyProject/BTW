package il.ac.technion.cs.yp.btw.citysimulation;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.navigation.Navigator;
import il.ac.technion.cs.yp.btw.navigation.PathNotFoundException;

import java.util.Optional;

/**
 * @author Guy Rephaeli on 15-Jan-18.
 *
 * implementation of Vehicle
 */
public class VehicleImpl implements Vehicle {
    private VehicleDescriptor descriptor;
    private Road currentRoad;
    private Optional<TrafficLight> currentTrafficLight;
    private Road nextRoad;
    private Road destination;
    private Double sourceRoadRatio;
    private Double destinationRoadRatio;
    private Navigator navigator;
    private Long timeOnCurrentRoad;
    private Long remainingTimeOnRoad;
    private boolean isWaitingOnTrafficLight;
    private CitySimulator simulator;
    private long startTime;
    private long currentTrafficLightStartingTime;
    private long currentRoadStartingTime;

    /**
     * @author Guy Rephaeli
     *
     * @param descriptor - the descriptor of the vehicle
     * @param source - the source road from which the vehicle should start driving
     * @param sourceRoadRatio - the place on the source road from which the vehicle should start driving
     * @param destination - the destination road to which the vehicle should eventually arrive
     * @param destinationRoadRatio - the place on the destination road to which the vehicle should eventually arrive
     * @param navigator - the navigator the calculates the route for the vehicle
     * @param simulator - the simulator in which the vehicle is driving
     * @param startTime - the global start time to start driving
     * @throws PathNotFoundException
     */
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
        this.currentTrafficLight = Optional.empty();
        this.nextRoad = navigator.getNextRoad();
        this.remainingTimeOnRoad = 0L;
        this.timeOnCurrentRoad = 0L;
        this.isWaitingOnTrafficLight = false;
        this.simulator = simulator;
        this.startTime = startTime;
        this.currentTrafficLightStartingTime = 0L;
        this.currentRoadStartingTime = 0L;
    }

    /**
     * @author Guy Rephaeli
     *
     * @param rd - the road to leave
     * @return self
     */
    private Vehicle leaveRoad(Road rd) {
        if (rd != null) {
            CityRoad realRoad = this.simulator.getRealRoad(rd);
            realRoad.removeVehicle(this);
        }
        return this;
    }

    /**
     * @author Guy Rephaeli
     *
     * @return VehicleDescriptor of this Vehicle,
     * which contain technical information
     * about the driven Vehicle
     */
    @Override
    public VehicleDescriptor getVehicleDescriptor() {
        return this.descriptor;
    }

    /**
     * @author Guy Rephaeli
     *
     * @return the Road this Vehicle is currently located in
     */
    @Override
    public Road getCurrentRoad() {
        return this.currentRoad;
    }

    /**
     * @author Guy Rephaeli
     *
     * @return the next road on the route
     */
    @Override
    public Road getNextRoad() {
        return this.nextRoad;
    }

    /**
     * @author Guy Rephaeli
     *
     * @return the destination Road of this Vehicle
     */
    @Override
    public Road getDestinationRoad() {
        return this.destination;
    }

    /**
     * @author Guy Rephaeli
     *
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
        this.timeOnCurrentRoad =  realRoad.getCurrentWeight().seconds();
        this.remainingTimeOnRoad = Double.valueOf((ratioEnd - ratioStart) * this.timeOnCurrentRoad).longValue();
        realRoad.addVehicle(this);
        return this;
    }

    /**
     * @author Guy Rephaeli
     *
     * commit a drive on the first road in the route
     *
     * @return self
     */
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

    /**
     * @author Guy Rephaeli
     *
     * commit a drive on the last road in the route
     *
     * @return self
     */
    public Vehicle driveOnLastRoad() {
        return driveOnRoad(this.destination, 0.0, this.destinationRoadRatio);
    }

    /**
     * @author Guy Rephaeli
     *
     * puts this Vehicle in wait on the given TrafficLight
     * until the TrafficLight is GREEN and this Vehicle
     * is at the top of the TrafficLight's lane
     * @param crossroad - the Crossroad containing the TrafficLight this Vehicle is waiting on
     */
    @Override
    public Vehicle waitOnTrafficLight(Crossroad crossroad) {
        long timeTakenOnRoad = this.simulator.getCurrentTime() - this.currentRoadStartingTime;
        this.simulator.reportOnRoad(this.currentRoad, timeTakenOnRoad);

        CityCrossroad realCrossroad = this.simulator.getRealCrossroad(crossroad);
        this.isWaitingOnTrafficLight = true;
        CityTrafficLight tl = realCrossroad.addVehicleOnTrafficLight(this);
        this.currentTrafficLight = Optional.of(tl);
        this.currentTrafficLightStartingTime = this.simulator.getCurrentTime();
        return this;
    }

    /**
     * @author Guy Rephaeli
     *
     * tell if the vehicle is waiting on a trafficlight
     *
     * @return self
     */
    @Override
    public boolean isWaitingForTrafficLight() {
        return this.isWaitingOnTrafficLight;
    }

    /**
     * @author Guy Rephaeli
     *
     * move to the next road on the route
     *
     * @return self
     */
    @Override
    public Vehicle driveToNextRoad() {
        long timeTakenOnTrafficLight = this.simulator.getCurrentTime() - this.currentTrafficLightStartingTime;
        this.simulator.reportOnTrafficLight(this.currentTrafficLight.get(), timeTakenOnTrafficLight);
        this.currentTrafficLight = Optional.empty();

        Road prev = this.currentRoad;
        this.leaveRoad(prev);
        this.isWaitingOnTrafficLight = false;
        this.currentRoad = this.nextRoad;
        this.currentRoadStartingTime = this.simulator.getCurrentTime();
        if (this.currentRoad.equals(this.destination)) {
            this.nextRoad = null;
            this.driveOnLastRoad();
        } else {
            this.nextRoad = this.navigator.getNextRoad();
            this.driveOnRoad(this.currentRoad);
        }
        return this;
    }

    /**
     * @author Guy Rephaeli
     *
     * @return the remaining time on the current road
     */
    @Override
    public BTWWeight getRemainingTimeOnRoad() {
        try {
            return BTWWeight.of(this.remainingTimeOnRoad);
        } catch(BTWIllegalTimeException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @author Guy Rephaeli
     *
     * progress on the current road in one second
     *
     * @return self
     */
    @Override
    public Vehicle progressOnRoad() {
        long prevRemainingTime = this.remainingTimeOnRoad;
        if (this.remainingTimeOnRoad > 0) {
            this.remainingTimeOnRoad--;
        }
        if (this.remainingTimeOnRoad <= 0) {
            if (this.currentRoad.equals(this.destination)) {
                this.leaveRoad(this.destination);
                return this;
            }
            if (prevRemainingTime > 0) {
                this.waitOnTrafficLight(this.currentRoad.getDestinationCrossroad());
            }
        }
        return this;
    }

    /**
     * @author Guy Rephaeli
     *
     * start driving if drive time has arrived
     *
     * @param now - current time in the system
     * @return indicator if drive time has arrived
     */
    @Override
    public boolean driveOnTime(long now) {
        if (this.startTime > now) {
            return false;
        }
        driveOnFirstRoad();
        return true;
    }

    /**
     * @author Guy Rephaeli
     * @date 25.4.2018
     * return the time in which the driving starts
     *
     * @return the time in which the driving starts
     */
    @Override
    public BTWTime getStartingTime() {
        return BTWTime.of(this.startTime);
    }
}
