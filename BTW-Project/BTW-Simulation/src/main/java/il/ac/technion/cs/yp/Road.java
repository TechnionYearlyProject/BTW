package il.ac.technion.cs.yp;

/**
 * road representation in a simulated map
 */
public class Road extends LineString {
    private String name;
    Road(Point startPoint, Point endPoint,String name) {
        super(startPoint, endPoint);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
