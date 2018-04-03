package il.ac.technion.cs.yp.btw.mapgeneration.voronoi;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * class to describe the traversing
 * on points in Voronoi diagram
 * when the case is equal distances
 * from to site Points
 */
class CircleEvent extends Event {
    public final Arc arc;
    public final VoronoiPoint vert;

    public CircleEvent(Arc a, VoronoiPoint p, VoronoiPoint vert) {
        super(p);
        this.arc = a;
        this.vert = vert;
    }
}
