package il.ac.technion.cs.yp.btw.statistics;

import il.ac.technion.cs.yp.btw.classes.*;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Guy Rephaeli
 * @date 16-Jun-18.
 *
 * A class for smart statistics calculation
 */
public class SmartStatisticsCalculator extends AbstractStatisticCalculator {
    final static Logger logger = Logger.getLogger(SmartStatisticsCalculator.class);
    private Integer iterationNum;
    private final Double initialBias = 1.0;
    private final Integer numIterationsToDrop = 10;
    private final Double dropRate = 2.0;
    private Double bias;
    private boolean fresh;
    private StatisticsProvider statistics;

    private class CalculatingStatisticsProvider implements StatisticsProvider {
        private Long granularity;
        private StatisticsProvider previousStatistics;
        private StatisticsProvider currentStatistics;
        private Map<BTWTime, Map<Road, BTWWeight>> weightsOfRoadAtTime;
        private Map<BTWTime, Map<TrafficLight, BTWWeight>> weightsOfTrafficLightAtTime;
        private Double alpha;

        private CalculatingStatisticsProvider(StatisticsProvider previousStatistics, StatisticsProvider currentStatistics, Double alpha) {
            this.alpha = alpha;
            this.granularity = db.getStatisticsPeriod();
            this.previousStatistics = previousStatistics;
            this.currentStatistics = currentStatistics;
            this.weightsOfRoadAtTime = new HashMap<>();
            this.weightsOfTrafficLightAtTime = new HashMap<>();
        }

        @Override
        public Long granularity() {
            return this.granularity;
        }

        @Override
        public BTWWeight expectedTimeOnRoadAt(BTWTime time, Road rd) {
            BTWTime keyTime = time.startTimeWindow(this.granularity);
            if (! this.weightsOfRoadAtTime.containsKey(keyTime)) {
                this.weightsOfRoadAtTime.put(keyTime, new HashMap<>());
            }
            Map<Road, BTWWeight> weightsOfRoad = this.weightsOfRoadAtTime.get(keyTime);
            if (! weightsOfRoad.containsKey(rd)) {
                BTWWeight prev = this.previousStatistics.expectedTimeOnRoadAt(keyTime, rd);
                BTWWeight curr = this.currentStatistics.expectedTimeOnRoadAt(keyTime, rd);
                BTWWeight val = BTWWeight.weightedAverage(prev, curr, this.alpha);
                weightsOfRoad.put(rd, val);
            }
            return this.weightsOfRoadAtTime
                    .get(keyTime)
                    .get(rd);
        }

        @Override
        public BTWWeight expectedTimeOnTrafficLightAt(BTWTime time, TrafficLight tl) {
            BTWTime keyTime = time.startTimeWindow(this.granularity);
            if (! this.weightsOfTrafficLightAtTime.containsKey(keyTime)) {
                this.weightsOfTrafficLightAtTime.put(keyTime, new HashMap<>());
            }
            Map<TrafficLight, BTWWeight> weightsOfTrafficLight = this.weightsOfTrafficLightAtTime.get(keyTime);
            if (! weightsOfTrafficLight.containsKey(tl)) {
                BTWWeight prev = this.previousStatistics.expectedTimeOnTrafficLightAt(keyTime, tl);
                BTWWeight curr = this.currentStatistics.expectedTimeOnTrafficLightAt(keyTime, tl);
                BTWWeight val = BTWWeight.weightedAverage(prev, curr, this.alpha);
                weightsOfTrafficLight.put(tl, val);
            }
            return this.weightsOfTrafficLightAtTime
                    .get(keyTime)
                    .get(tl);
        }
    }

    private SmartStatisticsCalculator(BTWDataBase db, Integer iterationNUm) {
        super(db);
        this.fresh = true;
        this.iterationNum = iterationNUm;
        this.stepDecay();

    }

    public SmartStatisticsCalculator(BTWDataBase db) {
        this(db, 0);
    }

    private void stepDecay() {
        this.bias = this.initialBias / Math.pow(this.dropRate, Math.floor(this.iterationNum / this.numIterationsToDrop));
    }

    private StatisticsCalculator update() {
        this.iterationNum++;
        this.stepDecay();
        this.roadExpectedWeightOfTime = new HashMap<>();
        this.trafficLightExpectedWeightOfTime = new HashMap<>();
        this.fresh = true;
        return this;
    }

    @Override
    public StatisticsCalculator addRoadReport(Road rd, StatisticalReport report) {
        super.addRoadReport(rd, report);
        this.fresh = false;
        logger.debug("Calculator is no longer fresh");
        return this;
    }

    @Override
    public StatisticsCalculator addTrafficLightReport(TrafficLight tl, StatisticalReport report) {
        super.addTrafficLightReport(tl, report);
        this.fresh = false;
        logger.debug("Calculator is no longer fresh");
        return this;
    }

    @Override
    public StatisticsProvider getStatistics() {
        logger.debug("Calculating statistics");
        if (! this.fresh) {
            StatisticsProvider currentStatistics = this.getCurrentStatistics();
            StatisticsProvider previousStatistics = this.db.getStatisticsFromDB();
            this.statistics = new CalculatingStatisticsProvider(previousStatistics, currentStatistics, this.bias);
            this.update();
        }
        logger.debug("Statistics calculated successfully");
        return this.statistics;
    }
}
