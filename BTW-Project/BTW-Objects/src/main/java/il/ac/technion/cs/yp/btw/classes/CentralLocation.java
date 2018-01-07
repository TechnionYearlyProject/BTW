package il.ac.technion.cs.yp.btw.classes;

import java.util.HashSet;
import java.util.Set;

/**
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
     * @return the CentralLocation's unique name
     */
    public String getName() {
        return name;
    }

//    public String toStringLocation() {
//        Point p1,p2,p3,p4;
//        Set<Point> vertices = this.getVertices();
//        p1 = (Point)vertices.toArray()[0];
//        p2 = (Point)vertices.toArray()[1];
//        p3 = (Point)vertices.toArray()[2];
//        p4 = (Point)vertices.toArray()[3];
//
//        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"Point\","+"\"coordinates\""+":"+
//                "[["+p1.getCoordinateX()+","+p1.getCoordinateY()+"],"+"["+p2.getCoordinateX()+","+p2.getCoordinateY()+"],"+
//                "["+p3.getCoordinateX()+","+p3.getCoordinateY()+"],"+"["+p4.getCoordinateX()+","+p4.getCoordinateY()+"]]},"+
//                "\"properties\":{"+"\"name\":"+"\""+this.getName()+"\"},\n";
//    }

}
