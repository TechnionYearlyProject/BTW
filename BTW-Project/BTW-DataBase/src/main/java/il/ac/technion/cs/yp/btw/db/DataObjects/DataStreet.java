package il.ac.technion.cs.yp.btw.db.DataObjects;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import il.ac.technion.cs.yp.btw.classes.Street;
import il.ac.technion.cs.yp.btw.classes.Road;
import java.util.Iterator;

/**
 * default implementation for the interface Street
 */
public class DataStreet implements Street {
    private String mapName;
    private String name;
    private Set<Road> roads;

    public DataStreet (String name, String mapName){
        this(name,new HashSet<Road>(), mapName);
    }

    public DataStreet (String name, Set<Road> roads, String mapName){
        this.name = name;
        this.roads = roads;
        this.mapName = mapName;
    }

    @Override
    public Set<Road> getAllRoadsInStreet() {

        return roads;
    }

    @Override
    public String getStreetName() {
        return this.name;
    }

    /**
     * adds the given Road to this Street
     * @param rd - the added Road
     */
    public void addRoad(Road rd){

    }

    @Override
    public String toString(){
        String street = new String("");
        street += "street: ";
        street += "name = " + name;
        street += "\nthe roads are:\n";
        Iterator<Road> iterator = roads.iterator();
        while(iterator.hasNext()){
            street += "\t" + iterator.next().toString() + "\n";
        }
        return street;
    }
}
