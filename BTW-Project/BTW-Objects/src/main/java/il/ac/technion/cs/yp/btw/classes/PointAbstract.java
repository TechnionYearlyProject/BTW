package il.ac.technion.cs.yp.btw.classes;

/**
 * @author Adam Elgressy
 * @Date 20-1-2018
 * default abstract implementation for the interface Point
 */
public abstract class PointAbstract implements Point {
    private double coordinateX;
    private double coordinateY;
    public PointAbstract(double coordinateX, double coordinateY){
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }
    public PointAbstract(Point p){
        this(p.getCoordinateX(),p.getCoordinateY());
    }
    @Override
    public double getCoordinateX() {
        return coordinateX;
    }
    @Override
    public double getCoordinateY() {
        return coordinateY;
    }

    /*@Author: Sharon Hadar
     *@Date: 02/04/2018
     *return a string representing the point with the pattern: (X, Y)
     */
    @Override
    public String toString(){return "(" + this.coordinateX + ", " + this.coordinateY + ")";}
}
