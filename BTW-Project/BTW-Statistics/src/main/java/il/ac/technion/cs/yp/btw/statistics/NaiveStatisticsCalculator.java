package il.ac.technion.cs.yp.btw.statistics;

import il.ac.technion.cs.yp.btw.classes.*;

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
    public StatisticsCalculator adRoadReport(Road rd, StatisticalReport report) {
        BTWTime time = report.getTimeOfReport().startTimeWindow(this.db.getStatisticsPeriod());
        if (! this.roadExpectedWeightOfTime.containsKey(time)) {
            this.roadExpectedWeightOfTime.put(time, new HashMap<Road, BTWWeight>());
        }
        Map<Road, BTWWeight> weightOfRoad = this.roadExpectedWeightOfTime.get(time);
        if (weightOfRoad.containsKey(rd)) {
            throw new BTWIllegalStatisticsException("Specific statistics can not be reported twice without resetting");
        } else {
            weightOfRoad.put(rd, report.timeTaken());
        }
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
        BTWTime time = report.getTimeOfReport().startTimeWindow(this.db.getStatisticsPeriod());
        if (! this.trafficLightExpectedWeightOfTime.containsKey(time)) {
            this.trafficLightExpectedWeightOfTime.put(time, new HashMap<TrafficLight, BTWWeight>());
        }
        Map<TrafficLight, BTWWeight> weightOfRoad = this.trafficLightExpectedWeightOfTime.get(time);
        if (weightOfRoad.containsKey(tl)) {
            throw new BTWIllegalStatisticsException("Specific statistics can not be reported twice without resetting");
        } else {
            weightOfRoad.put(tl, report.timeTaken());
        }
        return this;
    }
}
