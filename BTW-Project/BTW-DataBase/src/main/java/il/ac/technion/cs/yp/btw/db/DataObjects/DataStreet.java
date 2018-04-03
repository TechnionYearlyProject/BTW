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

    /** @author Sharon Hadarn
     * @Date 21/01/2018*/
    public DataStreet (String name, String mapName){
        this(name,new HashSet<>(), mapName);
    }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    public DataStreet (String name, Set<Road> roads, String mapName){
        this.name = name;
        this.roads = roads;
        this.mapName = mapName;
    }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    @Override
    public Set<Road> getAllRoadsInStreet() {

        return roads;
    }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    @Override
    public String getStreetName() {
        return this.name;
    }

    /**
     * adds the given Road to this Street
     * @param rd - the added Road
     */
    public Street addRoad(Road rd){
        roads.add(rd);
        return this;
    }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * get a string represents the street*/
    @Override
    public String toString(){
        String street = "";
        street += "street: ";
        street += "name = " + name;
        street += "\nthe roads are:\n";
        for (Road road : roads) {
            street += "\t" + road.toString() + "\n";
        }
        return street;
    }
}
