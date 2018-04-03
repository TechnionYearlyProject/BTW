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
     * @return all roads in the DB
     */
    Set<Road> getAllRoads();

    /**
     * @return all crossroads in the DB
     */
    Set<Crossroad> getAllCrossroads();

    /**
     * for future use, implementation not yet decided
     */
    BTWDataBase updateWeight();

    /**
     * parse from geoJson to java classes
     * @param geoJson - string containing the map represented in Json
     * @return
     */
    BTWDataBase parseMap(String geoJson);

    /**
     * save map from geoJson to DB tables
     * @param geoJson - string containing the map represented in Json
     *        mapName - string representing the name of the map
     */
    BTWDataBase saveMap(String geoJson);

    /**
     * update the heuristics
     */
    BTWDataBase updateHeuristics();

    /**
     * load existing map
     */
    public boolean loadMap();

    /**
     * gets all table names existing in DB
     * @return set of table names
     */
    public Set<String> getTablesNames();
}
