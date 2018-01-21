package il.ac.technion.cs.yp.btw.db;

import java.util.HashSet;
import java.util.Set;
import il.ac.technion.cs.yp.btw.classes.Street;
import il.ac.technion.cs.yp.btw.db.queries.Query;
import il.ac.technion.cs.yp.btw.db.queries.QueryAllStreetNames;
import il.ac.technion.cs.yp.btw.db.queries.QueryStreet;
import java.util.Iterator;

/*
* this class is not in use in the current semester
* handles the fetching of street from the data base
* */
public class StreetsDataBase {

    public StreetsDataBase(String mapName){

    }

    /*
    * @author Sharon Hadar
    * @Date 21/01/2018
    * get from the data base all the traffic lights
    * */
    public static Set<Street> getAllStreets(String mapName){
        Query query = new QueryAllStreetNames(mapName);
        Set<String> streetNames =  (Set<String>) MainDataBase.queryDataBase(query);
        Set<Street> streets = new HashSet();
        Iterator<String> iterator = streetNames.iterator();
        while(iterator.hasNext()){
            streets.add(getStreet(iterator.next(), mapName));
        }
        return streets;
    }

    /*
    * @author Sharon Hadar
    * @Date 21/01/2018
    * get from the data base a street by its name
    * */
    public static Street getStreet(String streetName, String mapName){
        Query query = new QueryStreet(mapName, streetName);
        return (Street) MainDataBase.queryDataBase(query);
    }
}