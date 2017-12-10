package il.ac.technion.cs.yp.btw.navigation;

import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.Street;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.db.BTWDataBase;

import java.util.List;
import java.util.Set;

/**
 * Created by orelk_000 on 10/12/2017.
 */
public class BTWNavigatorImp extends BTWNavigator {

    private final BTWDataBase Database;

    public BTWNavigatorImpl(BTWDataBase db) {
        this.Database = db;
    }

    private int parseStringToStreetNumber(String str) {
        //TODO: find out the format and parse accordingly
        return str.length();
    }

    private Road getRoadFromString(String str) {
        Street s = Database.getStreetByName(str);
        return s.getRoadByStreetNumber(parseStringToStreetNumber(str));
    }

    @Override
    public List<Road> navigate(String source, String target) {
        Set<TrafficLight> edges = Database.getAllTrafficLights();
        Road sourceRoad = getRoadFromString(source);
        Road targetRoad = getRoadFromString(target);
        MutableValueGraph<Road, Long> graph = ValueGraphBuilder.directed().build();
        for(TrafficLight edge : edges) {
            graph.putEdgeValue(edge.getSourceRoad(), edge.getDestinationRoad(), edge.getCurrentWeight().getWeight());
        }
        return navigationAlgorithm(MutableValueGraph graph, sourceRoad, targetRoad);
    }

    private List<Road> navigationAlgorithm(MutableValueGraph graph, Road sourceRoad, Road targetRoad) {
        //TODO: implement navigation algorithm
    }

}
