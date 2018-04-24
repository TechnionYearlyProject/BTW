package il.ac.technion.cs.yp.btw.statistics;

import il.ac.technion.cs.yp.btw.classes.*;

import java.util.Map;

/**
 * @author Guy Rephaeli
 * @date 25-Apr-18.
 *
 * A class for a naive implementation for Statistics calculator
 * Built mainly to prevent the project from crushing
 */
public class NaiveStatisticsCalculator implements StatisticsCalculator {
    private BTWDataBase db;
    private Map<BTWTime, Map<Road, BTWWeight>> roadExpectedWeightOfTime;
    private Map<BTWTime, Map<TrafficLight, BTWWeight>> trafficLightExpectedWeightOfTime;

    /**
     * @param db - the database to communicate with
     */
    public NaiveStatisticsCalculator(BTWDataBase db) {
        this.db = db;
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
        // TODO
        return null;
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
    public StatisticsCalculator adRoadReport(Road rd, StatisticalReport report) {
        // TODO
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
    public StatisticsCalculator adTrafficLightReport(TrafficLight tl, StatisticalReport report) {
        // TODO
        return this;
    }
}
