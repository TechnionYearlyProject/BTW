package il.ac.technion.cs.yp.btw.mapgeneration.voronoi;

/**
 * class to describe the traversing
 * on points in Voronoi diagram
 * when the case is equal distances
 * from to site Points
 */
public class CircleEvent extends Event {
    public final Arc arc;
    public final Point vert;

    public CircleEvent(Arc a, Point p, Point vert) {
        super(p);
        this.arc = a;
        this.vert = vert;
    }
}
