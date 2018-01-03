package il.ac.technion.cs.yp.btw.classes;

/**
 * Interface which represents a point in a
 * 2 dimensional plain as (x,y)
 */
public interface Point {
    /**
     * @return the x coordinate of this Point
     */
    double getCoordinateX();
    /**
     * @return the y coordinate of this Point
     */
    double getCoordinateY();

    /**
     * @return this Point
     */
    default Point getLocation(){
        return this;
    }
}
