package il.ac.technion.cs.yp.btw.classes;

import java.util.HashSet;
import java.util.Set;

/**
 * a location on the map with high importance
 */
public class CentralLocation extends PolygonAbstract {
    private String name;

    CentralLocation(String name) {
        this(new HashSet<Point>(), name);
    }

    CentralLocation(Set<Point> vertices, String name) {
        super(vertices);
        this.name = name;
    }

    /**
     * @return the CentralLocation's unique name
     */
    public String getName() {
        return name;
    }

}
