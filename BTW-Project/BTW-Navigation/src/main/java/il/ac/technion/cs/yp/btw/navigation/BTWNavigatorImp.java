package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.Road;
//import il.ac.technion.cs.yp.btw.db.BTWDataBase;

import java.sql.Wrapper;
import java.util.List;
import java.util.PriorityQueue;

/**
 * The main navigation logic
 */
public class BTWNavigatorImp implements BTWNavigator {
    private final BTWDataBase database;
//    private Graph<Road, DefaultWeightedEdge> heuristicGraph;
//    private AStarAdmissibleHeuristic<Road> heuristics;

    @Override
    public long calculateRouteTime(List<Road> route, double ratioSourceRoad, double ratioTargetRoad) {
        return 0;
    }

    public BTWNavigatorImp(BTWDataBase db) {
        this.database = db.updateHeuristics();
//        this.heuristicGraph = BTWGraphInfo.calculateMinimumGraph(database);
//        this.heuristics = new ALTAdmissibleHeuristic<>(this.heuristicGraph, this.heuristicGraph.vertexSet());
    }

    private List<Road> staticAStar(Road src, Road dst) {
        //TODO: adjust to equal also road
        PriorityQueue<RoadWrapper> open = new PriorityQueue<>();
        PriorityQueue<RoadWrapper> closed = new PriorityQueue<>();
        open.add(new RoadWrapper(src, dst, 0.0));
        while (! open.isEmpty()) {
            RoadWrapper next = open.poll();
            closed.add(next);
            if (next.getRoad().equals(dst)) {
                // finish
            }
            for (Road r : next.getNeighbors()) {
                double dist = next.dist + next.getDistFromNeighbor(r);
                RoadWrapper currWrapper = new RoadWrapper(r, dst, dist);
                if (open.contains(currWrapper)) {
                    RoadWrapper oldWrapper = null;
                    for (RoadWrapper w : open) {
                        if (w.equals(currWrapper)) {
                            oldWrapper = w;
                            break;
                        }
                    }
                    if (oldWrapper.dist > currWrapper.dist) {
                        // TODO: handle parents
                        open.remove(oldWrapper);
                        open.add(currWrapper);
                    }
                } else if (closed.contains(currWrapper)) {
                    RoadWrapper oldWrapper = null;
                    for (RoadWrapper w : closed) {
                        if (w.equals(currWrapper)) {
                            oldWrapper = w;
                            break;
                        }
                    }
                    if (oldWrapper.dist > currWrapper.dist) {
                        // TODO: handle parents
                        closed.remove(oldWrapper);
                        open.add(currWrapper);
                    }
                } else {
                    open.add(currWrapper);
                }
            }
        }
        // somethings wrong
    }

    @Override
    public List<Road> navigate(Road src, Road dst) {
        //TODO - maybe update the graph in some way
        // naive implementation
//        return new AStarShortestPath<>(this.heuristicGraph,
//                this.heuristics).getPath(src, dst).getVertexList();
        return staticAStar(src, dst);
    }
}
