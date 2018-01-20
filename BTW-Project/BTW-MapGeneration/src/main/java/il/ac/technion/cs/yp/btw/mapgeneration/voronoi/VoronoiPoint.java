package il.ac.technion.cs.yp.btw.mapgeneration.voronoi;
/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * VoronoiPoint for Voronoi calculations
 */
public class VoronoiPoint implements Comparable<VoronoiPoint> {
    public double x, y;
    public VoronoiPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(VoronoiPoint o) {
        if ((this.x == o.x) || (Double.isNaN(this.x) && Double.isNaN(o.x))) {
            if (this.y == o.y) {
                return 0;
            }
            return (this.y < o.y) ? -1 : 1;
        }
        return (this.x < o.x) ? -1 : 1;
    }

    public static int minYOrderedCompareTo(VoronoiPoint p1, VoronoiPoint p2) {
        if (p1.y < p2.y) return 1;
        if (p1.y > p2.y) return -1;
        if (p1.x == p2.x) return 0;
        return (p1.x < p2.x) ? -1 : 1;
    }

    public static VoronoiPoint midpoint(VoronoiPoint p1, VoronoiPoint p2) {
        double x = (p1.x + p2.x) / 2;
        double y = (p1.y + p2.y) / 2;
        return new VoronoiPoint(x, y);
    }

    /**
     * Is a->b->c a counterclockwise turn?
     * @param a first point
     * @param b second point
     * @param c third point
     * @return { -1, 0, +1 } if a->b->c is a { clockwise, collinear; counterclocwise } turn.
     *
     * Copied directly from Point2D in Algs4 (Not taking credit for this guy)
     */
    public static int ccw(VoronoiPoint a, VoronoiPoint b, VoronoiPoint c) {
        double area2 = (b.x-a.x)*(c.y-a.y) - (b.y-a.y)*(c.x-a.x);
        if      (area2 < 0) return -1;
        else if (area2 > 0) return +1;
        else                return  0;
    }

    public String toString() {
        return String.format("(%.3f, %.3f)", this.x, this.y);
    }

    public double distanceTo(VoronoiPoint that) {
        return Math.sqrt((this.x - that.x)*(this.x - that.x) + (this.y - that.y)*(this.y - that.y));
    }
}
