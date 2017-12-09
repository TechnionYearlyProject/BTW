package il.ac.technion.cs.yp;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Adam on 03/12/2017.
 */
public class CentralLocation extends Polygon {
    private String name;
    CentralLocation(String name) {
        this(new HashSet<Point>(),name);
    }

    CentralLocation(Set<Point> vertices, String name) {
        super(vertices);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
