package il.ac.technion.cs.yp.btw.mapgeneration.voronoi;

/**
 * class to describe the traversing
 * on points in Voronoi diagram
 * when the case is equal distances
 * from to site Points
 */
public class CircleEvent extends Event {
    public final Arc arc;
    public final VoronoiPoint vert;

    public CircleEvent(Arc a, VoronoiPoint p, VoronoiPoint vert) {
        super(p);
        this.arc = a;
        this.vert = vert;
    }
}
