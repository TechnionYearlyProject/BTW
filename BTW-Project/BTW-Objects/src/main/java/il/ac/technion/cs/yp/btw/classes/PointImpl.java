package il.ac.technion.cs.yp.btw.classes;

/**
 * default implementation for the abstract class PointAbstract
 */
public class PointImpl extends PointAbstract {
    public PointImpl(double coordinateX, double coordinateY) {
        super(coordinateX, coordinateY);
    }
    public PointImpl(Point p) {
        super(p);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Point))
            return false;
        Point pt = (Point)obj;
        return this.getCoordinateX()==pt.getCoordinateX()
                && this.getCoordinateY()==pt.getCoordinateY();
    }

    @Override
    public int hashCode() {
        return Double.hashCode(this.getCoordinateX()*this.getCoordinateY());
    }
}
