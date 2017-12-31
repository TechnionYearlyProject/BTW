package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.Road;
//import il.ac.technion.cs.yp.btw.db.BTWDataBase;

import java.sql.Wrapper;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

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
    public List<Road> navigate(Road src, Road dst) throws PathNotFoundException{
        //TODO - maybe update the graph in some way
        // naive implementation
//        return new AStarShortestPath<>(this.heuristicGraph,
//                this.heuristics).getPath(src, dst).getVertexList();
        return staticAStar(src, dst);
    }
}
