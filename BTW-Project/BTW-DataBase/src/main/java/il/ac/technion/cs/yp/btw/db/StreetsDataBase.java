package il.ac.technion.cs.yp.btw.db;

import java.util.HashSet;
import java.util.Set;
import il.ac.technion.cs.yp.btw.classes.Street;
import il.ac.technion.cs.yp.btw.db.queries.Query;
import il.ac.technion.cs.yp.btw.db.queries.QueryAllStreetNames;
import il.ac.technion.cs.yp.btw.db.queries.QueryStreet;
import java.util.Iterator;


public class StreetsDataBase {

    private String mapName;

    public StreetsDataBase(String mapName){
        this.mapName = mapName;
    }

    public Set<Street> getAllStreets(){
        Query query = new QueryAllStreetNames(mapName);
        Set<String> streetNames =  (Set<String>) MainDataBase.queryDataBase(query);
        Set<Street> streets = new HashSet();
        Iterator<String> iterator = streetNames.iterator();
        while(iterator.hasNext()){
            streets.add(getStreet(iterator.next()));
        }
        return streets;
    }

    public Street getStreet(String streetName){
        Query query = new QueryStreet(mapName, streetName);
        return (Street) MainDataBase.queryDataBase(query);
    }
}