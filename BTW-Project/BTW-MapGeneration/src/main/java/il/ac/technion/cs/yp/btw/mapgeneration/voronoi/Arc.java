package il.ac.technion.cs.yp.btw.mapgeneration.voronoi;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * Arc on Voronoi diagram
 */
class Arc extends ArcKey {
    private final Voronoi v;
    public BreakPoint left, right;
    public final VoronoiPoint site;

    public Arc(BreakPoint left, BreakPoint right, Voronoi v) {
        this.v = v;
        if (left == null && right == null) {
            throw new RuntimeException("cannot make arc with null breakpoints");
        }
        this.left = left;
        this.right = right;
        this.site = (left != null) ? left.s2 : right.s1;
    }

    public Arc(VoronoiPoint site, Voronoi v) {
        // Only for creating the first Arc
        this.v = v;
        this.left = null;
        this.right = null;
        this.site = site;
    }

    protected VoronoiPoint getRight() {
        if (right != null) return right.getPoint();
        return new VoronoiPoint(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    protected VoronoiPoint getLeft() {
        if (left != null) return left.getPoint();
        return new VoronoiPoint(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    public String toString() {
        VoronoiPoint l = getLeft();
        VoronoiPoint r = getRight();

        return String.format("{%.4f, %.4f}", l.x, r.x);
    }

    public VoronoiPoint checkCircle() {
        if ((this.left == null) || (this.right == null)) return null;
        if (VoronoiPoint.ccw(this.left.s1, this.site, this.right.s2) != -1) return null;
        return (this.left.getEdge().intersection(this.right.getEdge()));
    }
}