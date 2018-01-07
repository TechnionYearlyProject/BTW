package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.Road;

import java.util.Iterator;
import java.util.List;

/**
 * a static Naive approach to the Navigation, as mostly
 * used today by drivers
 */
public class NaiveNavigator implements Navigator {
    private final List<Road> route;
    private Iterator<Road> routeIterator;

    NaiveNavigator(List<Road> route) {
        this.route = route;
        this.routeIterator = route.iterator();
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
        return routeIterator.next();
    }

    @Override
    public Road getDestination() {
        return route.get(route.size() - 1);
    }

    @Override
    public boolean hasArrived() {
        return ! routeIterator.hasNext();
    }
}
