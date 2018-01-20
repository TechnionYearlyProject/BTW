package il.ac.technion.cs.yp.btw.classes;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * Interface which represents a point in a
 * 2 dimensional plain as (x,y)
 */
public interface Point {
    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the x coordinate of this Point
     */
    double getCoordinateX();
    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the y coordinate of this Point
     */
    double getCoordinateY();

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return this Point
     */
    default Point getLocation(){
        return this;
    }
}
