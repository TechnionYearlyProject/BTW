package il.ac.technion.cs.yp.btw.mapgeneration.voronoi;

/**
 * class to describe the traversing
 * on points in Voronoi diagram
 */
public class Event implements Comparable<Event> {
    public final Point p;

    public Event(Point p) {
        this.p = p;
    }

    @Override
    public int compareTo(Event o) {
        return Point.minYOrderedCompareTo(this.p, o.p);
    }
}
