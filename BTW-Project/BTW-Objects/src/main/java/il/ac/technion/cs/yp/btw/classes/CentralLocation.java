package il.ac.technion.cs.yp.btw.classes;

import java.util.HashSet;
import java.util.Set;

/**
 * a location on the map with high importance
 */
public class CentralLocation extends PolygonAbstract {
    private String name;
    private String street;

    public CentralLocation(String name) {
        this(new HashSet<Point>(), name);
    }

    public CentralLocation(Set<Point> vertices, String name) {
        super(vertices);
        this.name = name;
    }

    /**
     * @return the CentralLocation's unique name
     */
    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    //@Override
    public String toStringLocationFull() {
        Point p1 = (Point)this.getVertices().toArray()[0];
        Point p2 = (Point)this.getVertices().toArray()[1];
        Point p3 = (Point)this.getVertices().toArray()[2];
        Point p4 = (Point)this.getVertices().toArray()[3];
        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"Point\","+"\"coordinates\""+":"+
                "[["+p1.getCoordinateX()+","+p1.getCoordinateY()+"],"+"["+p2.getCoordinateX()+","+p2.getCoordinateY()+"],"+
                "["+p3.getCoordinateX()+","+p3.getCoordinateY()+"],"+"["+p4.getCoordinateX()+","+p4.getCoordinateY()+"]]},"+
                "\"properties\":{"+"\"name\":"+"\""+this.getName()+"\","+"\"street\":"+"\""+this.getStreet()+"\"},";
    }

    //@Override
    public String toStringLocation() {
        Point p1 = (Point)this.getVertices().toArray()[0];
        Point p2 = (Point)this.getVertices().toArray()[1];
        Point p3 = (Point)this.getVertices().toArray()[2];
        Point p4 = (Point)this.getVertices().toArray()[3];
        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"Point\","+"\"coordinates\""+":"+
                "[["+p1.getCoordinateX()+","+p1.getCoordinateY()+"],"+"["+p2.getCoordinateX()+","+p2.getCoordinateY()+"],"+
                "["+p3.getCoordinateX()+","+p3.getCoordinateY()+"],"+"["+p4.getCoordinateX()+","+p4.getCoordinateY()+"]]},"+
                "\"properties\":{"+"\"name\":"+"\""+this.getName()+"\"},";
    }

}
