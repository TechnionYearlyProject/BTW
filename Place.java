import java.util.Collection;

/**
 * Created by orelk_000 on 26/11/2017.
 */
public interface Place {
    Collection<Road> getRoadsInPlace();
    Collection<Crossroad> getCrossroadsInPlace();
    String getName();
}
