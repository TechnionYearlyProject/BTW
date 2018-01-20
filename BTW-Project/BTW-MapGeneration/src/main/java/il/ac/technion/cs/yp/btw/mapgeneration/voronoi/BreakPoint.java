package il.ac.technion.cs.yp.btw.mapgeneration.voronoi;

/**
 * "BreakPoint" class to describe
 * a snapshot during creation of the
 * diagram
 */
public class BreakPoint {
    private final Voronoi v;
    protected final VoronoiPoint s1, s2;
    private VoronoiEdge e;
    private boolean isEdgeLeft;
    public final VoronoiPoint edgeBegin;

    private double cacheSweepLoc;
    private VoronoiPoint cachePoint;

    public BreakPoint(VoronoiPoint left, VoronoiPoint right, VoronoiEdge e, boolean isEdgeLeft, Voronoi v) {
        this.v = v;
        this.s1 = left;
        this.s2 = right;
        this.e = e;
        this.isEdgeLeft = isEdgeLeft;
        this.edgeBegin = this.getPoint();
    }

    private static double sq(double d) {
        return d * d;
    }


    public void finish(VoronoiPoint vert) {
        if (isEdgeLeft) {
            this.e.p1 = vert;
        }
        else {
            this.e.p2 = vert;
        }
    }

    public void finish() {
        VoronoiPoint p = this.getPoint();
        if (isEdgeLeft) {
            this.e.p1 = p;
        }
        else {
            this.e.p2 = p;
        }
    }

    public VoronoiPoint getPoint() {
        double l = v.getSweepLoc();
        if (l == cacheSweepLoc) {
            return cachePoint;
        }
        cacheSweepLoc = l;

        double x,y;
        // Handle the vertical line case
        if (s1.y == s2.y) {
            x = (s1.x + s2.x) / 2; // x coordinate is between the two sites
            // comes from parabola focus-directrix definition:
            y = (sq(x - s1.x) + sq(s1.y) - sq(l)) / (2* (s1.y - l));
        }
        else {
            // This method works by intersecting the line of the edge with the parabola of the higher point
            // I'm not sure why I chose the higher point, either should work
            double px = (s1.y > s2.y) ? s1.x : s2.x;
            double py = (s1.y > s2.y) ? s1.y : s2.y;
            double m = e.m;
            double b = e.b;

            double d = 2*(py - l);

            // Straight up quadratic formula
            double A = 1;
            double B = -2*px - d*m;
            double C = sq(px) + sq(py) - sq(l) - d*b;
            int sign = (s1.y > s2.y) ? -1 : 1;
            double det = sq(B) - 4 * A * C;
            // When rounding leads to a very very small negative determinant, fix it
            if (det <= 0) {
                x = -B / (2 * A);
            }
            else {
                x = (-B + sign * Math.sqrt(det)) / (2 * A);
            }
            y = m*x + b;
        }
        cachePoint = new VoronoiPoint(x, y);
        return cachePoint;
    }

    public String toString() {
        return String.format("%s \ts1: %s\ts2: %s", this.getPoint(), this.s1, this.s2);
    }

    public VoronoiEdge getEdge() {
        return this.e;
    }
}
