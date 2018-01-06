package il.ac.technion.cs.yp.btw.classes;

import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

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

    @Override
    public String toString(){
        StringBuilder polygonAbstract = new StringBuilder("");
        for (Point vertice : vertices) {
            polygonAbstract.append(vertice.toString()).append(" ");
        }
        return polygonAbstract.toString();
    }
}
