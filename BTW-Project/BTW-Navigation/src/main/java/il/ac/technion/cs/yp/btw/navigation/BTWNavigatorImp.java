package il.ac.technion.cs.yp.btw.navigation;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;

import java.util.List;
import java.util.Set;

/**
 * The main navigation logic
 */
public class BTWNavigatorImp implements BTWNavigator {

    private final BTWDataBase Database;
    private MutableValueGraph<Road, Long> graph;

    @Override
    public long calculateRouteTime(List<Road> route, double ratioSourceRoad, double ratioTargetRoad) {
        return 0;
    }

    public BTWNavigatorImp(BTWDataBase db) {
        this.Database = db;
        initGraph();
    }

    private void initGraph() {
        graph = ValueGraphBuilder.directed().build();
        Database.getAllTrafficLights()
                .forEach(edge -> {
                    Road src = edge.getSourceRoad();
                    graph.putEdgeValue(src,
                            edge.getDestinationRoad(),
                            edge.getCurrentWeight().getWeightValue() + src.getRoadLength());
                });
    }

    @Override
    public List<Road> navigate(Road source, Road target) {
        //TODO - maybe update the graph in some way
        return null;
    }


}
