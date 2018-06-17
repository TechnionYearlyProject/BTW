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
public class NaiveStatisticsCalculator extends AbstractStatisticCalculator {
    final static Logger logger = Logger.getLogger(NaiveStatisticsCalculator.class);

    /**
     * @param db - the database to communicate with
     */
    public NaiveStatisticsCalculator(BTWDataBase db) {
        super(db);
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
        logger.debug("Sending new statistics");
        StatisticsProvider current = this.getCurrentStatistics();
        this.roadExpectedWeightOfTime = new HashMap<>();
        this.trafficLightExpectedWeightOfTime = new HashMap<>();
        return current;
    }
}
