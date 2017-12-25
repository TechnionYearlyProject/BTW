package il.ac.technion.cs.yp.btw.db;

import java.lang.String;
import java.util.Set;
import il.ac.technion.cs.yp.btw.classes.CentralLocation;
import il.ac.technion.cs.yp.btw.db.queries.QueryAllCentralLocations;
import il.ac.technion.cs.yp.btw.db.queries.QueryCentralLocation;
import il.ac.technion.cs.yp.btw.db.queries.Query;

public class CentralLocationsDataBase {

    public CentralLocationsDataBase(){

    }

    public static Set<CentralLocation> getAllCentralLocations(String mapName)
    {
        Query query = new QueryAllCentralLocations(mapName);
        return (Set<CentralLocation>) MainDataBase.queryDataBase(query);
    }

    public static CentralLocation getCentralLocation(String nameID, String mapName){
        Query query = new QueryCentralLocation(mapName, nameID);
        return (CentralLocation) MainDataBase.queryDataBase(query);
    }

    public static void addCentralLocation(CentralLocation CentralLocation, String mapName){

    }
}