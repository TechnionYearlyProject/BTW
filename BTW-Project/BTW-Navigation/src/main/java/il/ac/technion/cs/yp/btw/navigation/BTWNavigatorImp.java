package il.ac.technion.cs.yp.btw.navigation;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;

import java.util.List;
import java.util.Set;

/**
 * Created by orelk_000 on 10/12/2017.
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
        Set<TrafficLight> edges = Database.getAllTrafficLights();
        for(TrafficLight edge : edges) {
            graph.putEdgeValue(edge.getSourceRoad(), edge.getDestinationRoad(),
                    edge.getCurrentWeight().getWeightValue());
        }
    }

    @Override
    public List<Road> navigate(Road source, Road target) {
        //TODO - maybe update the graph in some way
        return null;
    }


}
