package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.classes.BTWTime;
import il.ac.technion.cs.yp.btw.classes.Road;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * @author Guy Rephaeli
 * @date 26-May-18.
 */
abstract class AbstractNavigationManager implements NavigationManager {
    protected final BTWDataBase database;
    final static Logger logger = Logger.getLogger(AbstractNavigationManager.class);

    public AbstractNavigationManager(BTWDataBase db) {
        this.database = db.updateHeuristics();
    }


    /**
     * @author Guy Rephaeli
     * @date 26-May-18.
     *
     * A method to perform A* route computation from road 'src' to road 'dst' at time 'time'
     *
     * @param src - the road from which to navigate
     * @param dst -the road to navigate to
     * @param sourceRoadRatio - starting point in the source road
     * @param time - the time to start navigation
     * @return self
     * @throws PathNotFoundException - if a path from src to dst is not found
     */
    protected List<Road> staticAStar(Road src, Road dst, double sourceRoadRatio, BTWTime time) throws PathNotFoundException {
        logger.debug("Start A* computation from " + src.getName() + " to " + dst.getName() + " at time "
                + time.getHoursOnly().toString() + ":"
                + time.getMinutesOnly().toString() + ":"
                + time.getSecondsOnly().toString());
        PriorityQueue<RoadWrapper> open = new PriorityQueue<>();
        PriorityQueue<RoadWrapper> closed = new PriorityQueue<>();
        open.add(RoadWrapper.buildSourceRoad(src, dst, sourceRoadRatio, time));
        while (! open.isEmpty()) {
            RoadWrapper next = open.poll();
            closed.add(next);
            if (next.getRoad().equals(dst)) {
                LinkedList<RoadWrapper> path = new LinkedList<>();
                RoadWrapper currWrapper = next;
                while (currWrapper != null) {
                    path.addFirst(currWrapper);
                    currWrapper = currWrapper.getParent();
                }
                logger.debug("Finished A* computation from "
                        + src.getName() + " to "
                        + dst.getName() + " successfully");
                return path.stream()
                        .map(RoadWrapper::getRoad)
                        .collect(Collectors.toList());
            }
            for (Road r : next.getNeighbors()) {
                double dist = next.dist + next.getDistFromNeighbor(r);
                RoadWrapper newWrapper = RoadWrapper.buildRouteRoad(r, dst, dist, next, time);
                if (open.contains(newWrapper)) {
                    RoadWrapper oldWrapper = null;
                    for (RoadWrapper w : open) {
                        if (w.equals(newWrapper)) {
                            oldWrapper = w;
                            break;
                        }
                    }
                    if (oldWrapper.dist > newWrapper.dist) {
                        open.remove(oldWrapper);
                        open.add(newWrapper);
                    }
                } else if (closed.contains(newWrapper)) {
                    RoadWrapper oldWrapper = null;
                    for (RoadWrapper w : closed) {
                        if (w.equals(newWrapper)) {
                            oldWrapper = w;
                            break;
                        }
                    }
                    if (oldWrapper.dist > newWrapper.dist) {
                        closed.remove(oldWrapper);
                        open.add(newWrapper);
                    }
                } else {
                    open.add(newWrapper);
                }
            }
        }
        logger.error("No path found from " + src.getName() + " to " + dst.getName());
        throw new PathNotFoundException("No path from " + src.getName() + " to " + dst.getName());
    }
}
