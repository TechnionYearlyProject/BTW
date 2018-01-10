package il.ac.technion.cs.yp.btw.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    /**
     * Return true if the given point is contained inside the boundary.
     * See: http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
     * @param point The point to check
     * @return true if the point is inside the boundary, false otherwise
     *
     */
    default boolean containsPoint(Point point) {
        int i;
        int j;
        boolean result = false;
        List<Point> points = new ArrayList<Point>(this.getVertices());
        for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
            if ((points.get(i).getCoordinateY() > point.getCoordinateY()) != (points.get(j).getCoordinateY() > point.getCoordinateY()) &&
                    (point.getCoordinateX() <
                            (points.get(j).getCoordinateX() - points.get(i).getCoordinateX())
                                    * (point.getCoordinateY() - points.get(i).getCoordinateY())
                                    / (points.get(j).getCoordinateY()-points.get(i).getCoordinateY())
                                    + points.get(i).getCoordinateX())) {
                result = !result;
            }
        }
        return result;
    }
}
