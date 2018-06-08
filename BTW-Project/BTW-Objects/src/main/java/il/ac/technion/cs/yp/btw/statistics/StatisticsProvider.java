package il.ac.technion.cs.yp.btw.statistics;

import il.ac.technion.cs.yp.btw.classes.BTWTime;
import il.ac.technion.cs.yp.btw.classes.BTWWeight;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;

/**
 * @author Guy Rephaeli
 * @date 17-Apr-18.
 *
 * An interface for querying for statistical data
 */
public interface StatisticsProvider {
    /**
     * @author Guy Rephaeli
     * @date 17-Apr-18.
     * @return The time window used to save statistics in seconds.
     */
    Long granularity();

    /**
     * @author Guy Rephaeli
     * @date 17-Apr-18.
     * Get the time is takes to drive on road "rd" at time "time"
     *
     * @param time The time for which we would like to query the road
     * @param rd The subject road
     * @return
     */
    BTWWeight expectedTimeOnRoadAt(BTWTime time, Road rd);

    /**
     * @author Guy Rephaeli
     * @date 17-Apr-18.
     * Get the time is takes to drive on traffic-light "tl" at time "time"
     *
     * @param time The time for which we would like to query the traffic-light
     * @param tl The subject traffic-light
     * @return
     */
    BTWWeight expectedTimeOnTrafficLightAt(BTWTime time, TrafficLight tl);
}
