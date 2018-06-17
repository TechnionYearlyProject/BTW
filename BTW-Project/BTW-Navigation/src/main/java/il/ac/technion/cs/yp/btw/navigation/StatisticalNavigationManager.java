package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.citysimulation.VehicleDescriptor;
import il.ac.technion.cs.yp.btw.classes.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Guy Rephaeli
 * @date 24-May-18.
 */
public class StatisticalNavigationManager extends AbstractNavigationManager {
    final static Logger logger = Logger.getLogger(StatisticalNavigationManager.class);

    public StatisticalNavigationManager(BTWDataBase db) {
        super(db);
    }

    @Override
    public Navigator getNavigator(VehicleDescriptor vehicleDescriptor, Road source, double sourceRoadRatio, Road destination, double destinationRoadRatio, BTWTime time) throws PathNotFoundException {
        logger.debug("Create new navigator");
        List<Road> path = new ArrayList<>();
        Road curr = source;
        path.add(curr);
        while (! curr.equals(destination)) {
            List<Road> tmpRoute = this.staticAStar(curr, destination, sourceRoadRatio, time);
            Road last = tmpRoute.get(1);
            if (path.contains(last)) {
                logger.error("Encountered cycle on the route from " + source.getName() + " to " + destination.getName());
                throw new PathNotFoundException("A cycle was encountered");
            }
            time.progressBy(curr.getWeightByTime(time))
                    .progressBy(
                            curr
                            .getDestinationCrossroad()
                            .getTrafficLightsFromRoad(curr)
                            .stream()
                            .filter(tl -> tl.getDestinationRoad().equals(last))
                            .findFirst()
                            .get()
                            .getWeightByTime(time)
                    );
            curr = last;
            path.add(curr);
        }
        Navigator navigator = new NaiveNavigator(path);
        logger.debug("Navigator created successfully");
        return navigator;
    }
}
