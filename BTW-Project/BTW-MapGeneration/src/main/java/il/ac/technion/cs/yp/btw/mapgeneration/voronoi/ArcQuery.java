package il.ac.technion.cs.yp.btw.mapgeneration.voronoi;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * Query class for Arc
 */
public class ArcQuery extends ArcKey {
    private final VoronoiPoint p;
    public ArcQuery(VoronoiPoint p) {
        this.p = p;
    }

    protected VoronoiPoint getLeft() {
        return p;
    }

    protected VoronoiPoint getRight() {
        return p;
    }
}
