package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.classes.CentralLocation;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.Street;
import il.ac.technion.cs.yp.btw.db.MainDataBase;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;

import java.util.Set;

public class BTWDataBaseImpl implements BTWDataBase{

    private String mapName;

    public BTWDataBaseImpl(String mapName){
        this.mapName = mapName;
    }

    /**
     * @return Set of all TrafficLights in the system
     */
    @Override
    public Set<TrafficLight> getAllTrafficLights(){
        return TrafficLightsDataBase.getAllTrafficLights(mapName);
    }

    /**
     * @param streetName - name of a street
     * @return the Street with the given name
     * TODO thinks of an error if doesn't exist, maybe throw new IllegalArgument()
     */
    @Override
    public Street getStreetByName(String streetName){
        return StreetsDataBase.getStreet(streetName, mapName);
    }

    /**
     * @param locationName - the name of the central location
     *                     we are looking for
     * @return CentralLocation according to the given name
     */
    @Override
    public CentralLocation getCentralLocationByName(String locationName){
        return CentralLocationsDataBase.getCentralLocation(locationName, mapName);
    }

    /**
     * returns all Roads which are next to the
     * given CenralLocation
     * @param centralLocation - the location we
     *                          are looking for
     * @return Set of Roads, which have the location
     *         on them
     */
    @Override
    public Set<Road> getAllRoadsNextToCentralLocation(CentralLocation centralLocation){
        //return centralLocationsDataBase.getAllCentralLocations();
        return null;
    }

    /**
     * for future use, implementation not yet decided
     */
    @Override
    public void updateWeight(){

    }

    public void saveMap(String geoJson, String mapName) {
        String createTraffic = "CREATE TABLE " + mapName
                +"(nameID varchar(50) NOT NULL,\n" +
                "cordx smallint NOT NULL,\n" +
                "cordy smallint NOT NULL,\n" +
                "overload varchar(50),\n" +
                "PRIMARY KEY(nameID));\n";
        String createPlace = "CREATE TABLE " + mapName
                + "Place(\n" +
                "nameID varchar(50) NOT NULL,\n" +
                "street varchar(50) NOT NULL,\n" +
                "cord1x smallint NOT NULL,\n" +
                "cord2x smallint NOT NULL,\n" +
                "cord3x smallint NOT NULL,\n" +
                "cord4x smallint NOT NULL,\n" +
                "cord1y smallint NOT NULL,\n" +
                "cord2y smallint NOT NULL,\n" +
                "cord3y smallint NOT NULL,\n" +
                "cord4y smallint NOT NULL,\n" +
                "PRIMARY KEY(nameID));\n";
        String createRoad = "CREATE TABLE " + mapName
                + "Road(\n" +
                "nameID varchar(50) NOT NULL,\n" +
                "cord1x smallint NOT NULL,\n" +
                "cord1y smallint NOT NULL,\n" +
                "cord2x smallint NOT NULL,\n" +
                "cord2y smallint NOT NULL,\n" +
                "length int,\n" +
                "secStart smallint,\n" +
                "secEnd smallint,\n" +
                "overload varchar(50),\n" +
                "PRIMARY KEY(nameID));\n";
        String createJson = "DECLARE @json NVARCHAR(max)\n" +
                "SET @json = \n'" + geoJson + "'\n"
                + "INSERT INTO dbo." + mapName
                + "TrafficLight (nameID, cordx,cordy, overload) SELECT nameID,cordx,cordy,overload FROM OPENJSON(@json, '$.features')\n" +
                "WITH (\n" +
                "\ttypeoftoken varchar(50) '$.geometry.type',\n" +
                "\tnameID varchar(50) '$.geometry.name',\n" +
                "\tcordx smallint '$.geometry.coordinates[0]',\n" +
                "\tcordy smallint '$.geometry.coordinates[1]',\n" +
                "\toverload varchar(5) '$.geometry.overload'\n" +
                "\t) WHERE (typeoftoken = 'Point');\n"
                + "INSERT INTO dbo." + mapName
                + "Place (nameID, street, cord1x, cord2x, cord3x, cord4x, cord1y, cord2y, cord3y, cord4y) SELECT nameID, street, cord1x, cord2x, cord3x, cord4x, cord1y, cord2y, cord3y, cord4y FROM OPENJSON(@json, '$.features')\n" +
                "WITH (\n" +
                "\ttypeoftoken varchar(50) '$.geometry.type',\n" +
                "\tnameID varchar(50) '$.properties.name',\n" +
                "\tstreet varchar(50) '$.properties.street',\n" +
                "\tcord1x smallint '$.geometry.coordinates[0][0]',\n" +
                "\tcord2x smallint '$.geometry.coordinates[1][0]',\n" +
                "\tcord3x smallint '$.geometry.coordinates[2][0]',\n" +
                "\tcord4x smallint '$.geometry.coordinates[3][0]',\n" +
                "\tcord1y smallint '$.geometry.coordinates[0][1]',\n" +
                "\tcord2y smallint '$.geometry.coordinates[1][1]',\n" +
                "\tcord3y smallint '$.geometry.coordinates[2][1]',\n" +
                "\tcord4y smallint '$.geometry.coordinates[3][1]'\n" +
                "\t) WHERE (typeoftoken = 'Poligon');\n"
                + "INSERT INTO dbo." + mapName
                + "Road (nameID,cord1x,cord1y,cord2x,cord2y,length,secStart,secEnd,overload) SELECT nameID, cord1x, cord1y, cord2x, cord2y, length, secStart, secEnd, overload FROM OPENJSON(@json, '$.features')\n" +
                "WITH (\n" +
                "\ttypeoftoken varchar(50) '$.geometry.type',\n" +
                "\tnameID varchar(30) '$.properties.name',\n" +
                "\tcord1x smallint '$.geometry.coordinates[0][0]',\n" +
                "\tcord1y smallint '$.geometry.coordinates[0][1]',\n" +
                "\tcord2x smallint '$.geometry.coordinates[1][0]',\n" +
                "\tcord2y smallint '$.geometry.coordinates[1][1]',\t\n" +
                "\tlength int '$.properties.length',\n" +
                "\tsecStart smallint '$.properties.secStart',\n" +
                "\tsecEnd smallint '$.properties.secEnd',\n" +
                "\toverload varchar(50) '$.properties.overload'\n" +
                "\t) WHERE (typeoftoken = 'LineString');\n";
        String sqlQuery = createTraffic + createPlace + createRoad + createJson;
        MainDataBase.saveDataFromQuery(sqlQuery);
    }
}