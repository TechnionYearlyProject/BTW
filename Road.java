import java.util.Collection;

/**
 * Created by orelk_000 on 26/11/2017.
 */
public interface Road {
    String getName();
    Weight getWeight(); //distance of road
    Weight getWeightByTime(int hour, int minute); //maybe do this with speeds
    void setWeight(Weight w); //for the future
    double getRoadLength(); //in km
    Collection<Place> getPlacesInRoad();
    Crossroad getSourceCrossroad();
    Crossroad getTargetCrossroad();
}
