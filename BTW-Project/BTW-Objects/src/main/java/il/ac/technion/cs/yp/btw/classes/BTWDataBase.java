package il.ac.technion.cs.yp.btw.classes;

import il.ac.technion.cs.yp.btw.statistics.StatisticsProvider;

import java.util.Set;

/**
 * DataBase layer interface for the BTW project
 */
public interface BTWDataBase {

    /**
     * @Author: Shay
     * Date: 25/4/18
     * @return Set of all TrafficLights in the system
     */
    Set<TrafficLight> getAllTrafficLights();

    /**
     * @Author: Sharon
     * Date: 25/4/18
     * @param streetName - name of a street
     * @return the Street with the given name
     * TODO thinks of an error if doesn't exist, maybe throw new IllegalArgument()
     */
    Street getStreetByName(String streetName);

    /**
     * @Author: Shay
     * Date: 25/4/18
     * @param locationName - the name of the central location
     *                     we are looking for
     * @return CentralLocation according to the given name
     */
    CentralLocation getCentralLocationByName(String locationName);

    /**
     * @Author: Sharon
     * Date: 25/4/18
     * @return all roads in the DB
     */
    Set<Road> getAllRoads();

    /**
     * @return all crossroads in the DB
     */
    Set<Crossroad> getAllCrossroads();

    /**
     * @Author: Shay
     * Date: 25/4/18
     * for future use, implementation not yet decided
     */
    BTWDataBase updateWeight();

    /**
     * @Author: Sharon
     * Date: 25/4/18
     * parse from geoJson to java classes
     * @param geoJson - string containing the map represented in Json
     * @return
     */
    BTWDataBase parseMap(String geoJson);

    /**
     * @Author: Shay
     * Date: 25/4/18
     * save map from geoJson to DB tables
     * @param geoJson - string containing the map represented in Json
     *        mapName - string representing the name of the map
     */
    BTWDataBase saveMap(String geoJson);

    /**
     * @Author: Shay
     * Date: 25/4/18
     * update the heuristics in DB
     */
    BTWDataBase updateHeuristics();

    /**
     * @Author: Shay
     * Date: 25/4/18
     * creates tables in DB to hold statistics for roads and traffic lights
     */
    BTWDataBase createStatisticsTables(Set<Road> roads, Set<TrafficLight> trafficLights);

    /**
     * @Author: Shay
     * Date: 25/4/18
     * updates the statistics tables by StatisticsProvider
     */
    BTWDataBase updateStatisticsTables(StatisticsProvider provider);

    /**
     * @Author: Shay
     * Date: 25/4/18
     * Get statistics from Data Base.
     * @return statistics object
     */
    StatisticsProvider getStatisticsFromDB();

    /**
     * @Author: Sharon
     * Date: 25/4/18
     * load existing map
     */
    public boolean loadMap();

    /**
     * gets all table names existing in DB
     * @return set of table names
     */
    public Set<String> getTablesNames();
}
