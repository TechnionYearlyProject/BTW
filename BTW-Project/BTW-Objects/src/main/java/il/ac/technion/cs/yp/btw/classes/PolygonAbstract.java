package il.ac.technion.cs.yp.btw.classes;

import java.util.HashSet;
import java.util.Set;

/**
 * default abstract implementation for the interface Polygon
 */
public abstract class PolygonAbstract implements Polygon {
    private Set<Point> vertices;
    public PolygonAbstract(){
        this(new HashSet<Point>());
    }
    public PolygonAbstract(Set<Point> vertices){
        this.vertices = vertices;
    }

    @Override
    public Set<Point> getVertices(){return this.vertices;}
}
