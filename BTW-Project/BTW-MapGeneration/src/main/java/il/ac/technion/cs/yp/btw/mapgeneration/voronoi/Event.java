package il.ac.technion.cs.yp.btw.mapgeneration.voronoi;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * class to describe the traversing
 * on points in Voronoi diagram
 */
class Event implements Comparable<Event> {
    public final VoronoiPoint p;

    public Event(VoronoiPoint p) {
        this.p = p;
    }

    @Override
    public int compareTo(Event o) {
        return VoronoiPoint.minYOrderedCompareTo(this.p, o.p);
    }
}
