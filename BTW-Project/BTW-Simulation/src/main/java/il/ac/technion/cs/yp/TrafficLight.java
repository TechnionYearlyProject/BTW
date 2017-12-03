package il.ac.technion.cs.yp;

import java.util.*;

/**
 * traffic light object representation in the
 * map simulation
 */
public class TrafficLight extends Point {
    public static final int DEFAULT_LOAD = 0;
    private Map<Road,Set<Road>> possibleTransitions;
    private int load;//amount of cars in the intersection
    private String identifier;
    TrafficLight(int coordinateX, int coordinateY,
                 String identifier){
        this(coordinateX, coordinateY,identifier,
                DEFAULT_LOAD,new HashMap<Road, Set<Road>>());
    }
    TrafficLight(int coordinateX, int coordinateY,
                 String identifier, int load,
                 Map<Road,Set<Road>> possibleTransitions) {
        super(coordinateX, coordinateY);
        this.identifier = identifier;
        this.load = load;
        this.possibleTransitions = possibleTransitions;
    }

    public Map<Road,Set<Road>> getPossibleTransitions() {
        return this.possibleTransitions;
    }

    public int getLoad() {
        return load;
    }

    public String getIdentifier() {
        return identifier;
    }

    void addTransition(Road origin, Road destination){
        Set<Road> set = this.possibleTransitions.getOrDefault(origin, new HashSet<Road>());
        set.add(destination);
        this.possibleTransitions.put(origin,set);
    }
}
