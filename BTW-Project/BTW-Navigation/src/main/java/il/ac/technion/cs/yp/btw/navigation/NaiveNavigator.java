package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.Road;

import java.util.Iterator;
import java.util.List;

/**
 * @author Guy Rephaeli
 *
 * A static Naive approach to the Navigation, as mostly
 * used today by drivers
 */
public class NaiveNavigator implements Navigator {
    private final List<Road> route;
    private Iterator<Road> routeIterator;
    private int nextRoadNum;
    private Road destination;

    NaiveNavigator(List<Road> route) {
        this.route = route;
        this.routeIterator = route.iterator();
        this.nextRoadNum = 0;
        this.destination = this.route.get(this.route.size() - 1);
    }

    /**
     * this method is called once for every Road,
     * so next time it is being called, you know
     * the Vehicle using it got to the end of that Road
     * @return the next Road for the Vehicle currently
     *         using this Navigator
     */
    @Override
    public Road getNextRoad() {
        this.nextRoadNum++;
        return this.routeIterator.next();
    }

    @Override
    public Road getDestination() {
        return this.destination;
    }

    @Override
    public boolean hasArrived() {
        return ! this.routeIterator.hasNext();
    }

    @Override
    public List<Road> showNextRoads(Integer roadsNumber) {
        return this.route.subList(this.nextRoadNum, Math.min(this.nextRoadNum + roadsNumber, this.route.size()));
    }
}
