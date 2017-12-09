package il.ac.technion.cs.yp;

import java.util.HashSet;
import java.util.Set;

/**
 * abstract class to represent polygonal shapes
 * of a simulated map
 */
public abstract class Polygon {
    private Set<Point> vertices;
    Polygon(){
        this(new HashSet<Point>());
    }
    Polygon(Set<Point> vertices){
        this.vertices = vertices;
    }
    void addPoint(Point p){
        vertices.add(p);
    }
}
