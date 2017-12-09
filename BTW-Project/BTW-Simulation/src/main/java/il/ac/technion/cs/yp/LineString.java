package il.ac.technion.cs.yp;

/**
 * abstract class to represent a connection
 * between to Points on a simulated map
 */
public abstract class LineString {
    private Point startPoint;
    private Point endPoint;
    LineString(Point startPoint, Point endPoint){
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }
}
