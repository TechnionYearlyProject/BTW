package il.ac.technion.cs.yp.btw.citysimulation;

/**
 * Created by Guy Rephaeli on 18-Jan-18.
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

    double getAverageSpeed() {
        return this.speed;
    }

    double getRoadLength() {
        return this.length;
    }

    double getNumOfVehicles() {
        return this.numOfVehicles;
    }
}
