package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.classes.Road;
//import il.ac.technion.cs.yp.btw.db.BTWDataBase;
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Graph Calculations
 */
public class BTWGraphInfo {

    static Graph<Road, DefaultWeightedEdge> calculateMinimumGraph(BTWDataBase db) {
        SimpleDirectedWeightedGraph<Road, DefaultWeightedEdge> minimunGraph
                = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        db.getAllTrafficLights()
                .forEach(trafficLight -> {
                    Road src = trafficLight.getSourceRoad();
                    Road dst = trafficLight.getDestinationRoad();
                    if (! minimunGraph.containsVertex(src)) {
                        minimunGraph.addVertex(src);
                    }
                    if (! minimunGraph.containsVertex(dst)) {
                        minimunGraph.addVertex(dst);
                    }
                    DefaultWeightedEdge edge = minimunGraph.addEdge(src, dst);
                    minimunGraph.setEdgeWeight(edge,
                            trafficLight.getMinimumWeight().seconds() + src.getMinimumWeight().seconds());
                });
        return minimunGraph;
    }

    public static Map<String, Map<String, Long>> calculateHeuristics(BTWDataBase db) {
        Graph<Road, DefaultWeightedEdge> heuristicGraph = calculateMinimumGraph(db);
        FloydWarshallShortestPaths<Road, DefaultWeightedEdge> sp = new FloydWarshallShortestPaths<>(heuristicGraph);
        return heuristicGraph.vertexSet()
                .stream()
                .collect(Collectors.toMap(Road::getRoadName, src -> heuristicGraph.vertexSet()
                        .stream()
                        .collect(Collectors.toMap(Road::getRoadName,
                                dst -> new Double(sp.getPathWeight(src, dst)).longValue()))));
    }
}
