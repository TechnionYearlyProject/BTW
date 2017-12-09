package il.ac.technion.cs.yp.btw.classes;

/**
 * default abstract implementation for the interface Point
 */
public abstract class PointAbstract implements Point {
    private int coordinateX;
    private int coordinateY;
    PointAbstract(int coordinateX, int coordinateY){
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }
    PointAbstract(Point p){
        this(p.getCoordinateX(),p.getCoordinateY());
    }
    @Override
    public int getCoordinateX() {
        return coordinateX;
    }
    @Override
    public int getCoordinateY() {
        return coordinateY;
    }
}
