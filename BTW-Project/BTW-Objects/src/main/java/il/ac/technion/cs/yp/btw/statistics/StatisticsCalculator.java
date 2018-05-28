package il.ac.technion.cs.yp.btw.statistics;

import il.ac.technion.cs.yp.btw.classes.BTWTime;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;

/**
 * @author Guy Rephaeli
 * @date 18-Apr-18.
 *
 * An interface for calculating statistics
 */
public interface StatisticsCalculator {
    StatisticsProvider getStatistics();

    StatisticsCalculator addRoadReport(Road rd, StatisticalReport report);

    StatisticsCalculator addTrafficLightReport(TrafficLight tl, StatisticalReport report);
}
