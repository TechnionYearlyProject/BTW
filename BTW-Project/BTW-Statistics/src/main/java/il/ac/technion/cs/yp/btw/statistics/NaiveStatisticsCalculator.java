package il.ac.technion.cs.yp.btw.statistics;

import il.ac.technion.cs.yp.btw.classes.*;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Guy Rephaeli
 * @date 25-Apr-18.
 *
 * A class for a naive implementation for Statistics calculator
 * Built mainly to prevent the project from crushing
 */
public class NaiveStatisticsCalculator implements StatisticsCalculator {
    final static Logger logger = Logger.getLogger(NaiveStatisticsCalculator.class);

    private BTWDataBase db;
    private Map<BTWTime, Map<Road, BTWWeight>> roadExpectedWeightOfTime;
    private Map<BTWTime, Map<TrafficLight, BTWWeight>> trafficLightExpectedWeightOfTime;

    /**
     * @param db - the database to communicate with
     */
    public NaiveStatisticsCalculator(BTWDataBase db) {
        this.db = db;
        this.roadExpectedWeightOfTime = new HashMap<>();
        this.trafficLightExpectedWeightOfTime = new HashMap<>();
    }

    /**
     * @author Guy Rephaeli
     * @date 25-Apr-18.
     *
     * Compute new statistics according to the db and the newly acquired data
     *
     * @return A StatisticsProvider object that can be queried on the newly calculated statistics
     */
    @Override
    public StatisticsProvider getStatistics() {
        logger.debug("Sending new statistics (finished)");
        return new StatisticsProviderImpl(this.db.getStatisticsPeriod(), this.roadExpectedWeightOfTime, this.trafficLightExpectedWeightOfTime);
    }

    /**
     * @author Guy Rephaeli
     * @date 25-Apr-18.
     *
     * @param rd - the road being reported on
     * @param report - the report containing all the needed information on the road
     * @return self
     */
    @Override
    public StatisticsCalculator addRoadReport(Road rd, StatisticalReport report) {
        logger.debug("Adding new road report for road: " + rd.getRoadName());
        BTWTime time = report.getTimeOfReport().startTimeWindow(this.db.getStatisticsPeriod());
        if (! this.roadExpectedWeightOfTime.containsKey(time)) {
            this.roadExpectedWeightOfTime.put(time, new HashMap<Road, BTWWeight>());
        }
        Map<Road, BTWWeight> weightOfRoad = this.roadExpectedWeightOfTime.get(time);
        if (weightOfRoad.containsKey(rd)) {
            logger.error("Same statistics reported more than once");
            throw new BTWIllegalStatisticsException("Specific statistics can not be reported twice without resetting");
        } else {
            weightOfRoad.put(rd, report.timeTaken());
        }
        logger.debug("Road report added successfully");
        return this;
    }

    /**
     * @author Guy Rephaeli
     * @date 25-Apr-18.
     *
     * @param tl - the traffic-light being reported on
     * @param report - the report containing all the needed information on the traffic-light
     * @return self
     */
    @Override
    public StatisticsCalculator addTrafficLightReport(TrafficLight tl, StatisticalReport report) {
        logger.debug("Adding new traffic-light report for traffic-light: " + tl.getName());
        BTWTime time = report.getTimeOfReport().startTimeWindow(this.db.getStatisticsPeriod());
        if (! this.trafficLightExpectedWeightOfTime.containsKey(time)) {
            this.trafficLightExpectedWeightOfTime.put(time, new HashMap<TrafficLight, BTWWeight>());
        }
        Map<TrafficLight, BTWWeight> weightOfRoad = this.trafficLightExpectedWeightOfTime.get(time);
        if (weightOfRoad.containsKey(tl)) {
            logger.error("Same statistics reported more than once");
            throw new BTWIllegalStatisticsException("Specific statistics can not be reported twice without resetting");
        } else {
            weightOfRoad.put(tl, report.timeTaken());
        }
        logger.debug("Traffic-light report added successfully");
        return this;
    }
}
