package il.ac.technion.cs.yp.btw.statistics;

import il.ac.technion.cs.yp.btw.classes.BTWTime;
import il.ac.technion.cs.yp.btw.classes.BTWWeight;
import il.ac.technion.cs.yp.btw.classes.Road;

/**
 * @author Guy Rephaeli
 * @date 17-Apr-18.
 *
 * An interface for querying for statistical data
 */
public interface StatisticsProvider {
    Long granularity();

    BTWWeight expectedTimeOnRoadAt(BTWTime time, Road rd);

    BTWWeight expectedTimeOnTrafficLightAt(BTWTime time, Road rd);
}
