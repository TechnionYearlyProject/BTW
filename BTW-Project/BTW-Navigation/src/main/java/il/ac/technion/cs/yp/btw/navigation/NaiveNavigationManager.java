package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.VehicleDescriptor;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;


/**
 * Implementation of the navigation manager
 */
public class NaiveNavigationManager implements NavigationManager {
    private final BTWDataBase database;

    public NaiveNavigationManager(BTWDataBase db) {
        this.database = db.updateHeuristics();
    }

    private List<Road> staticAStar(Road src, Road dst) throws PathNotFoundException{
        PriorityQueue<RoadWrapper> open = new PriorityQueue<>();
        PriorityQueue<RoadWrapper> closed = new PriorityQueue<>();
        open.add(new RoadWrapper(src, dst, 0.0, null));
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
                return path.stream()
                        .map(RoadWrapper::getRoad)
                        .collect(Collectors.toList());
            }
            for (Road r : next.getNeighbors()) {
                double dist = next.dist + next.getDistFromNeighbor(r);
                RoadWrapper newWrapper = new RoadWrapper(r, dst, dist, next);
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
        throw new PathNotFoundException("No path from " + src.getRoadName() + " to " + dst.getRoadName());
    }

    @Override
    public Navigator getNavigator(VehicleDescriptor vehicleDescriptor, Road source, double ratioSource, Road destination, double ratioDestination) throws PathNotFoundException{
        return new NaiveNavigator(staticAStar(source, destination));
    }
}
