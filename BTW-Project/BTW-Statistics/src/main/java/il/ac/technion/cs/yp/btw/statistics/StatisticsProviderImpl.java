package il.ac.technion.cs.yp.btw.statistics;

import il.ac.technion.cs.yp.btw.classes.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    private <T extends TrafficObject> BTWWeight expectedTimeAt(BTWTime time, Map<BTWTime, Map<T, BTWWeight>> weightsAtTime, T e) {
        return weightsAtTime
                .getOrDefault(time.startTimeWindow(this.granularity), new HashMap<>())
                .getOrDefault(e, e.getMinimumWeight());
    }

    @Override
    public Long granularity() {
        return this.granularity;
    }

    @Override
    public BTWWeight expectedTimeOnRoadAt(BTWTime time, Road rd) {
        return this.expectedTimeAt(time, this.weightsOfRoadAtTime, rd);
    }

    @Override
    public BTWWeight expectedTimeOnTrafficLightAt(BTWTime time, TrafficLight tl) {
        return this.expectedTimeAt(time, this.weightsOfTrafficLightAtTime, tl);
    }
}
