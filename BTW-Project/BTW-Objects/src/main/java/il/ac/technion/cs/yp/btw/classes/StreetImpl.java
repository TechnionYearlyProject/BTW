package il.ac.technion.cs.yp.btw.classes;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * default implementation for the interface Street
 */
public class StreetImpl implements Street {
    private String name;
    private Set<String> roadIds;

    public StreetImpl (String name){
        this(name,new HashSet<String>());
    }

    public StreetImpl (String name, Set<String> roadIds){
        this.name = name;
        this.roadIds = roadIds;
    }

    @Override
    public Set<Road> getAllRoadsInStreet() {
        return null;
    }

    @Override
    public String getStreetName() {
        return this.name;
    }
    /**
     * @return the Range of this Street
     */
    @Override
    public Range getStreetRange(){
        return null;
    }
}
