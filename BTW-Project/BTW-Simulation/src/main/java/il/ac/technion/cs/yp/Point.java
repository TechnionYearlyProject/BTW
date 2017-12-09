package il.ac.technion.cs.yp;

/**
 * abstract class to represent a point on a simulated map
 */
public class Point {
    private int coordinateX;
    private int coordinateY;
    Point(int coordinateX, int coordinateY){
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }
}
