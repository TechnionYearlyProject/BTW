package il.ac.technion.cs.yp.btw.mapgeneration.voronoi;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * Arc key class to compare
 */
public abstract class ArcKey implements Comparable<ArcKey> {
    protected abstract VoronoiPoint getLeft();
    protected abstract VoronoiPoint getRight();

    public int compareTo(ArcKey that) {
        VoronoiPoint myLeft = this.getLeft();
        VoronoiPoint myRight = this.getRight();
        VoronoiPoint yourLeft = that.getLeft();
        VoronoiPoint yourRight = that.getRight();

        // If one arc contains the query then we'll say that they're the same
        if (((that.getClass() == ArcQuery.class) || (this.getClass() == ArcQuery.class)) &&
            ((myLeft.x <= yourLeft.x && myRight.x >= yourRight.x) ||
                (yourLeft.x <= myLeft.x && yourRight.x >= myRight.x ))) {
            return 0;
        }

        if (myLeft.x == yourLeft.x && myRight.x == yourRight.x) return 0;
        if (myLeft.x >= yourRight.x) return 1;
        if (myRight.x <= yourLeft.x) return -1;

        return VoronoiPoint.midpoint(myLeft, myRight).compareTo(VoronoiPoint.midpoint(yourLeft, yourRight));
    }
}
