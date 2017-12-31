package il.ac.technion.cs.yp.btw.classes;

import java.util.Set;

/**
 * DataBase layer interface for the BTW project
 */
public interface BTWDataBase {

    /**
     * @return Set of all TrafficLights in the system
     */
    Set<TrafficLight> getAllTrafficLights();

    /**
     * @param streetName - name of a street
     * @return the Street with the given name
     * TODO thinks of an error if doesn't exist, maybe throw new IllegalArgument()
     */
    Street getStreetByName(String streetName);

    /**
     * @param locationName - the name of the central location
     *                     we are looking for
     * @return CentralLocation according to the given name
     */
    CentralLocation getCentralLocationByName(String locationName);

    /**
     * returns all Roads which are next to the
     * given CenralLocation
     * @param centralLocation - the location we
     *                          are looking for
     * @return Set of Roads, which have the location
     *         on them
     */
    Set<Road> getAllRoadsNextToCentralLocation(CentralLocation centralLocation);

    /**
     * for future use, implementation not yet decided
     */
    void updateWeight();

    /**
     * save map from geoJson to DB tables
     * @param geoJson - string containing the map represented in Json
     *        mapName - string representing the name of the map
     */
    void saveMap(String geoJson, String mapName);

    /**
     * update the heuristics
     */
    BTWDataBase updateHeuristics();
}
