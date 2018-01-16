package il.ac.technion.cs.yp.btw.db.DataObjects;

import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.CentralLocation;
import java.util.HashSet;
import java.util.Set;

/**
 * a location on the map with high importance
 */
public class DataCentralLocation extends  CentralLocation{

    private String name;
    private String street;
    private String mapName;

    /*DataCentralLocation(String name, String street, String mapName) {     WHY NEEDED?

        this(new HashSet<Point>(), name, street, mapName);
        this.mapName = mapName;
        this.street = street;
    }*/

    public DataCentralLocation(Set<Point> vertices, String name, String street, String mapName) {
        super(vertices, name);
        this.name = name;
        this.street = street;
        this.mapName = mapName;
    }

    /**
     * @return the CentralLocation's unique name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        String centralLocation = new String("");
        centralLocation += "centeral location: ";
        centralLocation += "name = " + name + " ";
        centralLocation += "points = " + super.toString();
        return centralLocation;
    }

}