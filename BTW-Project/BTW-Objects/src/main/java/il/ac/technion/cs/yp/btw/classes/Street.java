package il.ac.technion.cs.yp.btw.classes;

import java.util.Set;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * interface which represents Streets
 * on a road map
 */
public interface Street {

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * returns the Road in this street which
     * has the given streetNumber in his
     * range
     * @param streetNumber - the wanted street number
     * @return - the Road, null if not in range of all
     *           this Street's Roads
     */
    default Road getRoadByStreetNumber(int streetNumber){
        return getAllRoadsInStreet().stream()
                .filter(road -> road.isStreetNumberInRange(streetNumber))
                .findAny()
                .orElse(null);
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return Set of all Roads in this Street
     */
    Set<Road> getAllRoadsInStreet();

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return this Street's unique name
     */
    String getStreetName();

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * adds the given Road to this Street
     * @param rd - the added Road
     */
    Street addRoad(Road rd);
}
