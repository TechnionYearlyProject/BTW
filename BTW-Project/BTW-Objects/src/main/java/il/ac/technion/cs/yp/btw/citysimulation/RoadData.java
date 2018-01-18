package il.ac.technion.cs.yp.btw.citysimulation;

/**
 * Created by Guy Rephaeli on 18-Jan-18.
 * Class for transferring statistical data about roads
 */
public class RoadData extends StatisticalData {
    private int length;
    private double speed;
    private int numOfVehicles;

    public RoadData(int length, double speed, int numOfVehicles) {
        this.length = length;
        this.speed = speed;
        this.numOfVehicles = numOfVehicles;
    }

    /**
     * @return the length of the road in meters
     */
    int getRoadLength() {
        return this.length;
    }

    /**
     * @return the average speed on the road in km/h
     */
    double getAverageSpeed() {
        return this.speed;
    }

    /**
     * @return the current number of vehicles on the road
     */
    int getNumOfVehicles() {
        return this.numOfVehicles;
    }
}
