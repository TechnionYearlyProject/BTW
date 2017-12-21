package il.ac.technion.cs.yp.btw.classes;

/**
 * default implementation for the abstract class PointAbstract
 */
public class PointImpl extends PointAbstract {
    public PointImpl(double coordinateX, double coordinateY) {
        super(coordinateX, coordinateY);
    }
    public PointImpl(Point p) {
        super(p.getCoordinateX(), p.getCoordinateY());
    }
}
