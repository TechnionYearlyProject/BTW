package il.ac.technion.cs.yp.btw.classes;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * a location on the map with high importance
 */
public class CentralLocation extends PolygonAbstract {
    private String name;

    public CentralLocation(String name) {
        this(new HashSet<>(), name);
    }

    public CentralLocation(Set<Point> vertices, String name) {
        super(vertices);
        this.name = name;
    }

    /**
     * @author Adam Elgressy
     * @Date 20-1-2018
     * @return the CentralLocation's unique name
     */
    public String getName() {
        return name;
    }

}
