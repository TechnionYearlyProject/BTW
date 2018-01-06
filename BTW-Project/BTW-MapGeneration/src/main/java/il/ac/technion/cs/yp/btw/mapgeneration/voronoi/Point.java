package il.ac.technion.cs.yp.btw.mapgeneration.voronoi;
/**
 * Point for Voronoi calculations
 */
public class Point implements Comparable<Point> {
    public final double x, y;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point o) {
        if ((this.x == o.x) || (Double.isNaN(this.x) && Double.isNaN(o.x))) {
            if (this.y == o.y) {
                return 0;
            }
            return (this.y < o.y) ? -1 : 1;
        }
        return (this.x < o.x) ? -1 : 1;
    }

    public static int minYOrderedCompareTo(Point p1, Point p2) {
        if (p1.y < p2.y) return 1;
        if (p1.y > p2.y) return -1;
        if (p1.x == p2.x) return 0;
        return (p1.x < p2.x) ? -1 : 1;
    }

    public static Point midpoint(Point p1, Point p2) {
        double x = (p1.x + p2.x) / 2;
        double y = (p1.y + p2.y) / 2;
        return new Point(x, y);
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
    public static int ccw(Point a, Point b, Point c) {
        double area2 = (b.x-a.x)*(c.y-a.y) - (b.y-a.y)*(c.x-a.x);
        if      (area2 < 0) return -1;
        else if (area2 > 0) return +1;
        else                return  0;
    }

    public String toString() {
        return String.format("(%.3f, %.3f)", this.x, this.y);
    }

    public double distanceTo(Point that) {
        return Math.sqrt((this.x - that.x)*(this.x - that.x) + (this.y - that.y)*(this.y - that.y));
    }
}
