package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.db.queries.QueryAllWeights;
import il.ac.technion.cs.yp.btw.statistics.StatisticsProvider;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.Set;

/**
 * Created by shay on 26/04/2018.
 */
public class DBStatisticsProvider implements StatisticsProvider {

    final static Logger logger = Logger.getLogger("DBStatisticsProvider");
    private Map<String,BTWWeight[]> roadsMap;
    private Map<String,BTWWeight[]> trafficLightsMap;

    public DBStatisticsProvider(BTWDataBase db) {
        try {
            logger.debug("Start DBStatisticsProvider constructor");
            Set<Road> roads = db.getAllRoads();
            Set<TrafficLight> trafficLights = db.getAllTrafficLights();

            logger.debug("Insert all roads and their weights to map");
            for (Road road: roads) {
                String tableName = db.getMapName() + "Road" + road.getRoadName().replaceAll("\\s+","");
                BTWWeight[] weights;
                weights = (BTWWeight[])MainDataBase.queryDataBase(new QueryAllWeights(tableName));
                roadsMap.put(road.getRoadName(),weights);
                logger.trace("Insert Road: " + road.getRoadName());
            }

            logger.debug("Insert all traffic lights and their weights to map");
            for (TrafficLight trafficLight: trafficLights) {
                String tableName = db.getMapName() + "TL" + trafficLight.getName().replaceAll("\\s+","").replaceAll(":","");
                BTWWeight[] weights;
                weights = (BTWWeight[])MainDataBase.queryDataBase(new QueryAllWeights(tableName));
                trafficLightsMap.put(trafficLight.getName(),weights);
                logger.trace("Insert TrafficLight: " + trafficLight.getName());
            }

            logger.debug("End DBStatisticsProvider constructor");
        }
        catch (Exception e) {
            logger.error(e);
        }


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
