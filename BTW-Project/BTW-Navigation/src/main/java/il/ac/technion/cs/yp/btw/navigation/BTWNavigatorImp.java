package il.ac.technion.cs.yp.btw.navigation;

import com.google.common.graph.ImmutableValueGraph;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.db.BTWDataBase;
import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.ALTAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.List;

/**
 * The main navigation logic
 */
public class BTWNavigatorImp implements BTWNavigator {
    private final BTWDataBase Database;
    private Graph<Road, DefaultWeightedEdge> heuristicGraph;
    private AStarAdmissibleHeuristic<Road> heuristics;

    @Override
    public long calculateRouteTime(List<Road> route, double ratioSourceRoad, double ratioTargetRoad) {
        return 0;
    }

    public BTWNavigatorImp(BTWDataBase db) {
        this.Database = db;
        this.heuristicGraph = BTWGraph.calculateMinimumGraph(db);
        this.heuristics = new ALTAdmissibleHeuristic<>(this.heuristicGraph, this.heuristicGraph.vertexSet());
    }

    @Override
    public List<Road> navigate(Road src, Road dst) {
        //TODO - maybe update the graph in some way
        // naive implementation
        return new AStarShortestPath<>(this.heuristicGraph,
                this.heuristics).getPath(src, dst).getVertexList();
    }
}
