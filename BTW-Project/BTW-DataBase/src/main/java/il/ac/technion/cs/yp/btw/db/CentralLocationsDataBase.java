package il.ac.technion.cs.yp.btw.db;

import java.lang.String;
import java.util.Set;
import il.ac.technion.cs.yp.btw.classes.CentralLocation;
import il.ac.technion.cs.yp.btw.db.queries.QueryAllCentralLocations;
import il.ac.technion.cs.yp.btw.db.queries.QueryCentralLocation;
import il.ac.technion.cs.yp.btw.db.queries.Query;

/*
* this class is for handling fetching of centerall locations from the data base
* will be in use in the next semester
* */
public class CentralLocationsDataBase {
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * */
    public CentralLocationsDataBase(){

    }

    /*
    * @author Sharon Hadar
    * @Date 21/01/2018
    * get all centeral locations form the data base
    * */
    public static Set<CentralLocation> getAllCentralLocations(String mapName)
    {
        Query query = new QueryAllCentralLocations(mapName);
        return (Set<CentralLocation>) MainDataBase.queryDataBase(query);
    }

    /*
    * @author Sharon Hadar
    * @Date 21/01/2018
    * gets a name of centeral location and a name of a map and fetch the matching centeral location from the data base
    * */
    public static CentralLocation getCentralLocation(String nameID, String mapName){
        Query query = new QueryCentralLocation(mapName, nameID);
        return (CentralLocation) MainDataBase.queryDataBase(query);
    }

    public static void addCentralLocation(CentralLocation CentralLocation, String mapName){

    }
}