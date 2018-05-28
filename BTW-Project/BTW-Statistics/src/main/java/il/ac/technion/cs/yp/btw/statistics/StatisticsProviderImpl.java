package il.ac.technion.cs.yp.btw.statistics;

import il.ac.technion.cs.yp.btw.classes.*;

import java.util.Map;

/**
 * @author Guy Rephaeli
 * @date 07-May-18.
 * Implementation for the StatisticsProvider interface
 */
public class StatisticsProviderImpl implements StatisticsProvider {
    private long granularity;
    private Map<BTWTime, Map<Road, BTWWeight>> weightsOfRoadAtTime;
    private Map<BTWTime, Map<TrafficLight, BTWWeight>> weightsOfTrafficLightAtTime;

    StatisticsProviderImpl(long granularity, Map<BTWTime, Map<Road, BTWWeight>> weightsOfRoadAtTime, Map<BTWTime,
            Map<TrafficLight, BTWWeight>> weightsOfTrafficLightAtTime) {
        this.granularity = granularity;
        this.weightsOfRoadAtTime = weightsOfRoadAtTime;
        this.weightsOfTrafficLightAtTime = weightsOfTrafficLightAtTime;
    }

    @Override
    public Long granularity() {
        return this.granularity;
    }

    @Override
    public BTWWeight expectedTimeOnRoadAt(BTWTime time, Road rd) {
        return this.weightsOfRoadAtTime
                .get(time.startTimeWindow(this.granularity))
                .get(rd);
    }

    @Override
    public BTWWeight expectedTimeOnTrafficLightAt(BTWTime time, TrafficLight tl) {
        return this.weightsOfTrafficLightAtTime
                .get(time.startTimeWindow(this.granularity))
                .get(tl);
    }
}
