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
    private Map<BTWTime, Map<Road, BTWWeight>> weithsOfRoadAtTime;
    private Map<BTWTime, Map<TrafficLight, BTWWeight>> weithsOfTrafficLightAtTime;

    StatisticsProviderImpl(long granularity, Map<BTWTime, Map<Road, BTWWeight>> weithsOfRoadAtTime, Map<BTWTime,
            Map<TrafficLight, BTWWeight>> weithsOfTrafficLightAtTime) {
        this.granularity = granularity;
        this.weithsOfRoadAtTime = weithsOfRoadAtTime;
        this.weithsOfTrafficLightAtTime = weithsOfTrafficLightAtTime;
    }

    @Override
    public Long granularity() {
        return this.granularity;
    }

    @Override
    public BTWWeight expectedTimeOnRoadAt(BTWTime time, Road rd) {
        return this.weithsOfRoadAtTime
                .get(time.startTimeWindow(this.granularity))
                .get(rd);
    }

    @Override
    public BTWWeight expectedTimeOnTrafficLightAt(BTWTime time, TrafficLight tl) {
        return this.weithsOfTrafficLightAtTime
                .get(time.startTimeWindow(this.granularity))
                .get(tl);
    }
}
