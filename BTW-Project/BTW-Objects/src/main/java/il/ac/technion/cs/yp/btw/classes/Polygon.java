package il.ac.technion.cs.yp.btw.classes;

import java.util.Set;

/**
 * Interface which represents a Polygon
 * on a road map
 */
public interface Polygon {
    /**
     * Adds a Point to define this Polygon
     * @param p - the Point to add
     */
    default void addPoint(Point p){
        getVertices().add(p);
    }

    /**
     * @return Set of all the Points
     *         which represent this Polygon
     */
    Set<Point> getVertices();
}
