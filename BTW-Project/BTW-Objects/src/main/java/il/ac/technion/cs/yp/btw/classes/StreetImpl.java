package il.ac.technion.cs.yp.btw.classes;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * default implementation for the interface Street
 */
public class StreetImpl implements Street {
    private String name;
    private Set<Road> roads;

    public StreetImpl (String name){
        this(name,new HashSet<Road>());
    }

    public StreetImpl (String name, Set<Road> roads){
        this.name = name;
        this.roads = roads;
    }

    @Override
    public Set<Road> getAllRoadsInStreet() {
        return this.roads;
    }

    @Override
    public String getStreetName() {
        return this.name;
    }
}
