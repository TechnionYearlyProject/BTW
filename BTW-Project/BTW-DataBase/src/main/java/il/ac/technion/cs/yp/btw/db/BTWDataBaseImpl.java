package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.*;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataCrossRoad;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataRoad;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataStreet;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataTrafficLight;
import il.ac.technion.cs.yp.btw.db.queries.Query;
import il.ac.technion.cs.yp.btw.db.queries.QueryAllTables;
import il.ac.technion.cs.yp.btw.navigation.BTWGraphInfo;
import il.ac.technion.cs.yp.btw.statistics.StatisticsProvider;
import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;

public class BTWDataBaseImpl implements BTWDataBase {

    final static Logger logger = Logger.getLogger("BTWDataBaseImpl");

    private String mapName;
    private Connection connection;
    private Set<TrafficLight> trafficLights;
    private boolean trafficLightsLoaded;
    private Set<Crossroad> crossRoads;
    private boolean crossRoadsLoaded;
    private Set<Road> roads;
    private boolean roadsLoaded;
    private Map<String, Map<String,Long>> heuristics;
    private boolean updatedHeuristics;

    /**
     * @author Sharon Hadar
     * @Date 21/01/2018
     * a constructor to data bas3e object
     */
    public BTWDataBaseImpl(String mapName){

        logger.debug("BTWDataBase Constructor");
        this.mapName = mapName;
        MainDataBase.openConnection();
        logger.debug("BTWDataBase open connection");
        this.updatedHeuristics = false;

        roadsLoaded = false;
        trafficLightsLoaded = false;
        crossRoadsLoaded = false;
        roads = null;
        trafficLights = null;
        crossRoads = null;
    }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * close the connection to the cloud DB. need to close on end of run.
     */
    public void closeDataBaseConnection(){
        MainDataBase.closeConnection();
    }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * @return Set of all TrafficLights in the system
     */
    @Override
    public Set<TrafficLight> getAllTrafficLights(){
        if (!trafficLightsLoaded) {
            trafficLightsLoaded = true;
            this.trafficLights = TrafficLightsDataBase.getAllTrafficLights(mapName);
            return this.trafficLights;
        }
        else {
            return this.trafficLights;
        }

    }

    /**
     * @author Sharon Hadar
     * @Date 21/01/2018
     * @param streetName - name of a street
     * @return the Street with the given name
     * TODO thinks of an error if doesn't exist, maybe throw new IllegalArgument()
     */
    @Override
    public Street getStreetByName(String streetName){
        return null;
    }

    /**
     * @author Sharon Hadar
     * @Date 21/01/2018
     * @param locationName - the name of the central location
     *                     we are looking for
     * @return CentralLocation according to the given name
     */
    @Override
    public CentralLocation getCentralLocationByName(String locationName){
        return null;
    }

    /**
     * @author Sharon Hadar
     * @Date 21/01/2018
     * @author: shay
     * @date: 20/1/18
     * get all roads in data base
     * @return set of all roads in data base
     */
    @Override
    public Set<Road> getAllRoads() {
        // TODO
        if(this.roadsLoaded == true){
            return this.roads;
        }else {
            this.roadsLoaded = true;
            this.roads = RoadsDataBase.getAllRoads(mapName);
            return this.roads;
        }
    }

    /**
     * @Author: shay
     * @Data: 13/04/2018
     * get all crossroads in data base
     * @return set of all crossroads in data base
     */
    @Override
    public Set<Crossroad> getAllCrossroads() {

        if(this.crossRoadsLoaded == true){
            return this.crossRoads;
        }
        this.crossRoadsLoaded = true;
        Set<Crossroad> crossRoads = new HashSet<>();
        String name = "cross ";
        Map<Pair<Double, Double>, List<TrafficLight>> trafficLightsOfLocation = this.getAllTrafficLights()
                .stream()
                .collect(Collectors.groupingBy(trafficLight ->
                        new Pair<>(trafficLight.getCoordinateX(), trafficLight.getCoordinateY())));

        for (Map.Entry<Pair<Double, Double>, List<TrafficLight>> entry: trafficLightsOfLocation.entrySet()) {
            Point p = new PointImpl(entry.getKey().getKey(),entry.getKey().getValue());
            Set<TrafficLight> tlsOfCrossRoad = new HashSet<TrafficLight>(entry.getValue());
            String crossRoadName = name+p.toString();
            Crossroad crossRoad = new DataCrossRoad(p,tlsOfCrossRoad,crossRoadName,mapName);
            crossRoads.add(crossRoad);
        }
        this.crossRoads = crossRoads;
        return this.crossRoads;
    }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    private void insertCrossRoadsToRoads(){
        Map<Point, Crossroad> crossRoadsOfLocation = new HashMap<>();
        this.crossRoads.
                forEach(crossRoad -> crossRoadsOfLocation.put(new PointImpl(crossRoad.getCoordinateX(), crossRoad.getCoordinateY()),crossRoad ));
        for (Road road : this.roads) {
            Point sourcePosition = ((DataRoad)road).getSourceCrossroadPosition();
            ((DataRoad)road).setSourceCrossRoad(crossRoadsOfLocation.get(sourcePosition));
            Point destinationPosition = ((DataRoad)road).getDestinationCrossroadPosition();
            ((DataRoad)road).setDestinationCrossRoad(crossRoadsOfLocation.get(destinationPosition));
        }

    }
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    private void insertRoadsToTrafficLights(){
        Map<String, Road> roadsLightsOfName = new HashMap<>();
        this.roads.
                forEach(road -> roadsLightsOfName.put(road.getRoadName(),road ));
        for (TrafficLight trafficLight : this.trafficLights) {

            String sourceRoadName = ((DataTrafficLight)trafficLight).getSourceRoadName();
            ((DataTrafficLight)trafficLight).setSourceRoad(roadsLightsOfName.get(sourceRoadName));

            String destinationRoadName = ((DataTrafficLight)trafficLight).getDestinationRoadName();
            ((DataTrafficLight)trafficLight).setDestinationRoad(roadsLightsOfName.get(destinationRoadName));

        }

    }
    /**
     ** @author: shay
     * @date: 20/1/18
     *  for future use, implementation not yet decided
     * NOT FOR THIS SEMESTER
     */
    @Override
    public BTWDataBase updateWeight(){
        // TODO: ???
        return null;
    }

    /**
     * @Author: Sharon Hadar
    *@Date: 30/3/2018
    *gets a map represented as a json string and parse it to java classes of crossroad, trafficlight and road
    * returns the BTWDataBase itself updated
    **/
    @Override
    public BTWDataBase parseMap(String geoJson){

        GeoJasonToJavaParser parser = new GeoJasonToJavaParser(mapName, geoJson);
        this.trafficLights = parser.getTrafficLights();
        this.trafficLightsLoaded = true;
        this.crossRoads = parser.getCrossRoads();
        this.crossRoadsLoaded = true;
        this.roads = parser.getRoads();
        this.roadsLoaded = true;
        insertCrossRoadsToRoads();
        insertRoadsToTrafficLights();
        insertStreetsToRoads();
        /*the trafficlights are inserted to the crossroads in the parser constructor*/
        //saveHeuristics();
        //createStatisticsTables(roads,trafficLights);

        new Thread("DB save") {
            public void run() {
                logger.debug("BTWDataBase New Thread Saving Map Information...");
                saveMap(geoJson);
                createStatisticsTables(roads,trafficLights);
            }
        }.start();
        return this;
    }


    /**
     * @author: shay
     * @date: 20/1/18
     * Save map to DataBase from geoJson
     * @param geoJson - string containing the map represented in Json
     * @return this object
     */
    @Override
    public BTWDataBase saveMap(String geoJson) {
        String addMapName = "INSERT INTO dbo.AdminTables(mapName) VALUES('"+ mapName +"');\n";
        String createTraffic = "DROP TABLE IF EXISTS "+ mapName + "TrafficLight;\n"+
                "CREATE TABLE " + mapName + "TrafficLight"
                +"(nameID varchar(100) NOT NULL,\n" +
                "cordx float NOT NULL,\n" +
                "cordy float NOT NULL,\n" +
                "overload bigint,\n" +
                "PRIMARY KEY(nameID));\n";
        String createPlace = "DROP TABLE IF EXISTS "+ mapName + "Place;\n"+
                "CREATE TABLE " + mapName
                + "Place(\n" +
                "nameID varchar(100) NOT NULL,\n" +
                "street varchar(100) NOT NULL,\n" +
                "cord1x float NOT NULL,\n" +
                "cord2x float NOT NULL,\n" +
                "cord3x float NOT NULL,\n" +
                "cord4x float NOT NULL,\n" +
                "cord1y float NOT NULL,\n" +
                "cord2y float NOT NULL,\n" +
                "cord3y float NOT NULL,\n" +
                "cord4y float NOT NULL,\n" +
                "PRIMARY KEY(nameID));\n";
        String createRoad = "DROP TABLE IF EXISTS "+ mapName + "Road;\n"+
                "CREATE TABLE " + mapName
                + "Road(\n" +
                "nameID varchar(100) NOT NULL,\n" +
                "cord1x float NOT NULL,\n" +
                "cord1y float NOT NULL,\n" +
                "cord2x float NOT NULL,\n" +
                "cord2y float NOT NULL,\n" +
                "length int,\n" +
                "secStart smallint,\n" +
                "secEnd smallint,\n" +
                "overload bigint,\n" +
                "PRIMARY KEY(nameID));\n";
        String createJson = "DECLARE @json NVARCHAR(max)\n" +
                "SET @json = \n'" + geoJson + "'\n"
                + "INSERT INTO dbo." + mapName
                + "TrafficLight (nameID, cordx,cordy, overload) SELECT nameID,cordx,cordy,overload FROM OPENJSON(@json, '$.features')\n" +
                "WITH (\n" +
                "\ttypeoftoken varchar(100) '$.geometry.type',\n" +
                "\tnameID varchar(100) '$.properties.name',\n" +
                "\tcordx float '$.geometry.coordinates[0]',\n" +
                "\tcordy float '$.geometry.coordinates[1]',\n" +
                "\toverload bigint '$.geometry.overload'\n" +
                "\t) WHERE (typeoftoken = 'Point');\n"
                + "INSERT INTO dbo." + mapName
                + "Place (nameID, street, cord1x, cord2x, cord3x, cord4x, cord1y, cord2y, cord3y, cord4y) SELECT nameID, street, cord1x, cord2x, cord3x, cord4x, cord1y, cord2y, cord3y, cord4y FROM OPENJSON(@json, '$.features')\n" +
                "WITH (\n" +
                "\ttypeoftoken varchar(100) '$.geometry.type',\n" +
                "\tnameID varchar(100) '$.properties.name',\n" +
                "\tstreet varchar(100) '$.properties.street',\n" +
                "\tcord1x float '$.geometry.coordinates[0][0]',\n" +
                "\tcord2x float '$.geometry.coordinates[1][0]',\n" +
                "\tcord3x float '$.geometry.coordinates[2][0]',\n" +
                "\tcord4x float '$.geometry.coordinates[3][0]',\n" +
                "\tcord1y float '$.geometry.coordinates[0][1]',\n" +
                "\tcord2y float '$.geometry.coordinates[1][1]',\n" +
                "\tcord3y float '$.geometry.coordinates[2][1]',\n" +
                "\tcord4y float '$.geometry.coordinates[3][1]'\n" +
                "\t) WHERE (typeoftoken = 'Poligon');\n"
                + "INSERT INTO dbo." + mapName
                + "Road (nameID,cord1x,cord1y,cord2x,cord2y,length,secStart,secEnd,overload) SELECT nameID, cord1x, cord1y, cord2x, cord2y, length, secStart, secEnd, overload FROM OPENJSON(@json, '$.features')\n" +
                "WITH (\n" +
                "\ttypeoftoken varchar(100) '$.geometry.type',\n" +
                "\tnameID varchar(100) '$.properties.name',\n" +
                "\tcord1x float '$.geometry.coordinates[0][0]',\n" +
                "\tcord1y float '$.geometry.coordinates[0][1]',\n" +
                "\tcord2x float '$.geometry.coordinates[1][0]',\n" +
                "\tcord2y float '$.geometry.coordinates[1][1]',\n" +
                "\tlength int '$.properties.length',\n" +
                "\tsecStart smallint '$.properties.secStart',\n" +
                "\tsecEnd smallint '$.properties.secEnd',\n" +
                "\toverload bigint '$.properties.overload'\n" +
                "\t) WHERE (typeoftoken = 'LineString');\n";
        String sqlQuery = addMapName + createTraffic + createPlace + createRoad + createJson;
        MainDataBase.saveDataFromQuery(sqlQuery);
        logger.debug("BTWDataBase Complete Saving Map Information ");
        saveHeuristics();
        return this;
    }

    /**
     * @author: shay
     * @date: 20/1/18
     * update the heuristics table for the specific map in DB
     * @return this object
     */
    @Override
    public BTWDataBase updateHeuristics() {
        logger.debug("BTWDataBase Start Updating Heuristics");
        if (this.updatedHeuristics)
            return this;
        this.heuristics = BTWGraphInfo.calculateHeuristics(this);
        this.updatedHeuristics = true;
        logger.debug("BTWDataBase Complete Updating Heuristics");
        return this;
    }

    /**
     * @author: shay
     * @date: 11/4/18
     * creates tables in DB to hold statistics for roads and traffic lights
     * each road has and each traffic light have table
     * every table should save overloads by time
     * @return this object
     */
    @Override
    public BTWDataBase createStatisticsTables(Set<Road> roads, Set<TrafficLight> trafficLights) {
        logger.debug("BTWDataBase Start Statistics Tables");
        String queryCreate = "";
        String queryInsert = "";
        for (Road road: roads) {
            queryCreate += "CREATE TABLE " + mapName + "Road" + road.getRoadName().replaceAll("\\s+","") + "(time integer NOT NULL, " +
                    "overload bigint NOT NULL, PRIMARY KEY(time));\n";
            Integer time = 0;
            while (time < 86400) {
                queryInsert += "INSERT INTO dbo." + mapName + "Road" + road.getRoadName().replaceAll("\\s+","") + "(time,overload)" +
                        " VALUES (" + time.toString() +", " + road.getMinimumWeight().seconds() + ");\n";
                time += 1800;
            }
        }
        for (TrafficLight trafficLight: trafficLights) {
            queryCreate += "CREATE TABLE " + mapName + "TL" + trafficLight.getName().replaceAll("\\s+","").replaceAll(":","") +
                    "(time integer NOT NULL, overload bigint NOT NULL, PRIMARY KEY(time));\n";
            Integer time = 0;
            while (time < 86400) {
                queryInsert += "INSERT INTO dbo." + mapName + "TL" + trafficLight.getName().replaceAll("\\s+","").replaceAll(":","") +
                        "(time,overload) VALUES (" + time.toString() + ", " + trafficLight.getMinimumWeight().seconds() + ");\n";
                time += 1800;
            }
        }
        logger.debug(queryCreate+queryInsert);
        MainDataBase.saveDataFromQuery(queryCreate+queryInsert);
        logger.debug("BTWDataBase Complete Statistics Tables");
        return this;
    }

    /**
     * @author: shay
     * @date: 25/4/18
     * update the statistics in DB
     * the function will get the StatisticsProvider object
     * and will save the new overloads to every road and every traffic light.
     * @return this object
     */
    @Override
    public BTWDataBase updateStatisticsTables(StatisticsProvider provider) {
        return null;
    }

    /**
     * @author: shay
     * @date: 25/4/18
     * get the statistics from DB to statistics provider
     * the function will get the current information from DB to the provider.
     * @return StatisticsProvider object
     */
    @Override
    public StatisticsProvider getStatisticsFromDB() {
        return null;
    }

    /**
     * @author: shay
     * @date: 20/1/18
     * update the heuristics table for the specific map in DB
     * @return this object
     */
    private void saveHeuristics(){
        logger.debug("BTWDataBase Start Saving Heuristics");
        String mapName = this.mapName;  // need to know the name of the map...
        String sql1 = "DROP TABLE IF EXISTS dbo." + mapName + "Heuristics;";
        String sql2 = "CREATE TABLE " + mapName + "Heuristics(sourceID varchar(50) NOT NULL, " +
                "targetID varchar(50) NOT NULL, overload bigint, PRIMARY KEY(sourceID,targetID));";
        MainDataBase.saveDataFromQuery(sql1);
        MainDataBase.saveDataFromQuery(sql2);
        String sql3 = "";
        for (Map.Entry<String,Map<String,Long>> firstEntry: this.heuristics.entrySet())
        {
            for (Map.Entry<String,Long> secondEntry: firstEntry.getValue().entrySet())
            {
//                System.out.println(firstEntry.getKey() + " " + secondEntry.getKey() + " " + secondEntry.getValue());
                String sql3do = "INSERT INTO dbo." + mapName + "Heuristics(sourceID,targetID,overload)" +
                        " VALUES (" + "'" + firstEntry.getKey()+ "', " + "'" +secondEntry.getKey() + "', "
                        + secondEntry.getValue().toString() + ");\n";
                sql3 += sql3do;
            }
        }
        MainDataBase.saveDataFromQuery(sql3);
        logger.debug("BTWDataBase Complete Saving Heuristics");
        return;
    }

    /**
     * @author: shay
     * @date: 20/1/18
     * update the heuristics table for the specific map in DB
     * @return this object
     */
    @Override
    public Set<String> getTablesNames() {
        Query query = new QueryAllTables("AdminTables");
        Set<String> tables = (Set<String>) MainDataBase.queryDataBase(query);
        return tables;
    }

    /**
    * @author Sharon Hadar
    * @Date 21/01/2018
    * fetch an existing map from the data base
    * */
    @Override
    public boolean loadMap(){
        logger.debug("BTWDataBase Start loadMap()");
        roads = getAllRoads();
        if(roads == null){
            return false;
        }
        trafficLights = getAllTrafficLights();
        if(trafficLights == null){
            return false;
        }
        crossRoads = getAllCrossroads();//traffic light inserted to crss roads here.
        if(crossRoads == null){
            return false;
        }
        insertCrossRoadsToRoads();
        insertRoadsToTrafficLights();
        insertStreetsToRoads();
        logger.debug("BTWDataBase End loadMap()");
        return true;
    }

    /**
     * @Author: Sharon
     * @Date: 13/04/2018
     *
     */
    private void insertStreetsToRoads(){
        Map<String, Street> streets = new HashMap<>();
        Iterator<Road> dataRoads = this.roads.iterator();
        while(dataRoads.hasNext()){
            DataRoad dataRoad = (DataRoad)dataRoads.next();
            String streetName = dataRoad.getStreetName();
            Street street = null;
            if(streets.containsKey(streetName)){
                street = streets.get(streetName);

            }else{
                street = new DataStreet(streetName, mapName);
            }
            street.addRoad(dataRoad);
            dataRoad.setStreet(street);
        }
    }


}