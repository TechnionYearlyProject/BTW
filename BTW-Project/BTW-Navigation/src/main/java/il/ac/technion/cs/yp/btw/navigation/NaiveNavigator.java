package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.Road;

import java.util.List;

/**
 * a static Naive approach to the Navigation, as mostly
 * used today by drivers
 */
public class NaiveNavigator implements Navigator {
    public NaiveNavigator() {
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
        //TODO
        return null;
    }

    //TODO delete as an overridden method
    @Override
    public List<Road> navigate(Road source, Road target) throws PathNotFoundException {
        return null;
    }

    //TODO delete as an overridden method
    @Override
    public long calculateRouteTime(List<Road> route, double ratioSourceRoad, double ratioTargetRoad) {
        return 0;
    }
}
