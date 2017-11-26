/**
 * Created by orelk_000 on 26/11/2017.
 */
public interface Stoplight { //this is essentially an edge in the graph
    Road getSourceRoad();
    Road getTargetRoad();
    Weight getStoplightWeightByHour(int hour, int minute);
    Weight getCurrentStoplightWeight();
}
