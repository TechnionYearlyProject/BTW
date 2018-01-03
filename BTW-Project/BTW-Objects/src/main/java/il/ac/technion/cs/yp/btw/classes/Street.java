package il.ac.technion.cs.yp.btw.classes;

import java.util.Set;

/**
 * interface which represents Streets
 * on a road map
 */
public interface Street {

    /**
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
     * @return Set of all Roads in this Street
     */
    Set<Road> getAllRoadsInStreet();

    /**
     * @return this Street's unique name
     */
    String getStreetName();

    /**
     * adds the given Road to this Street
     * @param rd - the added Road
     */
    Street addRoad(Road rd);
}
