package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.classes.BTWTime;
import il.ac.technion.cs.yp.btw.classes.BTWWeight;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.statistics.StatisticsProvider;

import java.util.Map;

/**
 * Created by shay on 26/04/2018.
 */
public class DBStatisticsProvider implements StatisticsProvider {

    private BTWWeight[] weights = new BTWWeight[48];
    private Map<String,BTWWeight[]> roadsMap;
    private Map<String,BTWWeight[]> trafficLightsMap;

    public DBStatisticsProvider(BTWDataBase db) {

    }

    /**
     * @Author: Shay
     * @Date: 26/4/18
     * granularity function
     * @return
     */
    @Override
    public Long granularity() {
        return null;
    }

    /**
     * @Author: Shay
     * @Date: 26/4/18
     * gets Time and one of the roads of the map and returns the weight of the specific road
     * on the specific time
     * @return BTWWeight object - the weight of the road.
     */
    @Override
    public BTWWeight expectedTimeOnRoadAt(BTWTime time, Road rd) {
        return null;
    }

    /**
     * @Author: Shay
     * @Date: 26/4/18
     * gets Time and one of the traffic lights of the map and returns the weight of the specific traffic light
     * on the specific time
     * @return BTWWeight object - the weight of the traffic light.
     */
    @Override
    public BTWWeight expectedTimeOnTrafficLightAt(BTWTime time, Road rd) {
        return null;
    }
}
