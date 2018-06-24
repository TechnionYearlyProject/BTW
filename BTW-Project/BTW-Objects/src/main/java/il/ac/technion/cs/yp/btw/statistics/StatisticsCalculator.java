package il.ac.technion.cs.yp.btw.statistics;

import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.StatisticalReport;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;

/**
 * @author Guy Rephaeli
 * @date 18-Apr-18.
 *
 * An interface for calculating statistics
 */
public interface StatisticsCalculator {
    /**
     * @author Guy Rephaeli
     * @date 18-Apr-18.
     *
     * @return StatisticsProvider object for querying about traffic statistics
     */
    StatisticsProvider getStatistics();

    /**
     * @author Guy Rephaeli
     * @date 18-Apr-18.
     *
     * @param rd - the road being reported
     * @param report - the report containing the traffic data about 'rd'
     * @return self
     */
    StatisticsCalculator addRoadReport(Road rd, StatisticalReport report);

    /**
     * @author Guy Rephaeli
     * @date 18-Apr-18.
     *
     * @param tl - the traffic-light being reported
     * @param report - the report containing the traffic data about 'tl'
     * @return self
     */
    StatisticsCalculator addTrafficLightReport(TrafficLight tl, StatisticalReport report);
}
