package il.ac.technion.cs.yp.btw.db;

import java.lang.String;
import java.util.Set;
import il.ac.technion.cs.yp.btw.classes.CentralLocation;
import il.ac.technion.cs.yp.btw.db.queries.QueryAllCentralLocations;
import il.ac.technion.cs.yp.btw.db.queries.QueryCentralLocation;
import il.ac.technion.cs.yp.btw.db.queries.Query;

public class CentralLocationsDataBase {

    String mapName;
    public CentralLocationsDataBase(String mapName){
        this.mapName = mapName;
    }

    public Set<CentralLocation> getAllCentralLocations()
    {
        Query query = new QueryAllCentralLocations(mapName);
        return (Set<CentralLocation>) MainDataBase.queryDataBase(query);
    }

    public CentralLocation getCentralLocation(String nameID){
        Query query = new QueryCentralLocation(mapName, nameID);
        return (CentralLocation) MainDataBase.queryDataBase(query);
    }

    public void addCentralLocation(CentralLocation CentralLocation){

    }
}