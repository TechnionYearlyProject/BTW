package il.ac.technion.cs.yp.btw.mapgeneration.objects;

import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.Street;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * default implementation for the interface Street
 */
public class MapSimulationStreetImpl implements Street {
    private String name;
    private Set<Road> roads;

    public MapSimulationStreetImpl(String name){
        this(name,new HashSet<>());
    }

    public MapSimulationStreetImpl(String name, Set<Road> roads){
        this.name = name;
        this.roads = roads;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return this Street's Roads in a Set
     */
    @Override
    public Set<Road> getAllRoadsInStreet() {
        return this.roads;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return this Street's name
     */
    @Override
    public String getStreetName() {
        return this.name;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * adds the given Road to this Street
     * @param rd - the added Road
     */
    @Override
    public Street addRoad(Road rd) {
        this.roads.add(rd);
        return this;
    }
}
