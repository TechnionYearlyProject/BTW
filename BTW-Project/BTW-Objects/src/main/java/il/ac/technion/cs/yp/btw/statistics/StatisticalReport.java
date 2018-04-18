package il.ac.technion.cs.yp.btw.statistics;

import il.ac.technion.cs.yp.btw.classes.BTWTime;
import il.ac.technion.cs.yp.btw.classes.BTWWeight;

/**
 * Created by Guy Rephaeli on 18-Apr-18.
 *
 * Class for temporal reports about traffic loads
 */
public class StatisticalReport {
    private BTWTime timeOfReport;
    private Integer numOfReporters;
    private Long timeWindow;
    private Long timeSum;


    /**
     * @author Guy Rephaeli
     * @date 18.4.18
     *
     * @param timeOfReport - the time of the report
     * @param timeWindow - the length of the time window reported
     */
    public StatisticalReport(BTWTime timeOfReport, Long timeWindow) {
        this.timeOfReport = timeOfReport;
        this.numOfReporters = 0;
        this.timeWindow = timeWindow;
        this.timeSum = 0L;
    }

    /**
     * @author Guy Rephaeli
     * @date 18.4.18
     *
     * @param timeOfReport - the time of the report
     */
    public StatisticalReport(BTWTime timeOfReport) {
        this(timeOfReport, 15 * 60L);
    }

    /**
     * @author Guy Rephaeli
     * @date 18.4.18
     *
     * Update the current report with new vehicle reporting
     *
     * @param weight - the time of the report
     * @return - self
     */
    public StatisticalReport update(BTWWeight weight) {
        this.timeSum += weight.seconds();
        this.numOfReporters++;
        return this;
    }

    /**
     * @author Guy Rephaeli
     * @date 18.4.18
     *
     * @return - the time taken according to the report
     */
    public BTWWeight timeTaken() {
        return BTWWeight.of(Double.valueOf(this.timeSum / this.numOfReporters).longValue());
    }

    /**
     * @author Guy Rephaeli
     * @date 18.4.18
     *
     * @return - the time referred by the report
     */
    public BTWTime getTimeOfReport() {
        return this.timeOfReport;
    }

    /**
     * @author Guy Rephaeli
     * @date 18.4.18
     *
     * @return - the number of reporters
     */
    public Integer getNumOfReporters() {
        return this.numOfReporters;
    }
}
