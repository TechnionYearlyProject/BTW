package il.ac.technion.cs.yp.btw.gui;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import il.ac.technion.cs.yp.btw.classes.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapGraphics {
    private List<Pair<Circle,String>> circles;
    private List<Pair<Line,String>> lines;

    MapGraphics(Set<TrafficLight> trafficLights, Set<Road> roads) {
        this.circles = new ArrayList<>();
        this.lines = new ArrayList<>();

        createCircles(trafficLights);
        createLines(roads);
    }

    /**
     * creating the array of circles, each circle for a traffic light.
     * @param trafficLights - trafficlights in the map
     */
    //TODO: change the color according to real time loads.
    private void createCircles(Set<TrafficLight> trafficLights) {
        int x=0;
        for (TrafficLight currTrafficLight: trafficLights) {
            Point point = calculateTrafficLightLocation(currTrafficLight.getSourceRoad());
            Circle circle = new Circle(point.getCoordinateX(), point.getCoordinateY(),0.01);
            System.out.println("original X : "+currTrafficLight.getCoordinateX()+"original Y : "+currTrafficLight.getCoordinateY());
            System.out.println("X : "+point.getCoordinateX()+"Y : "+point.getCoordinateY());
            if(x%2==0){
                circle.setFill(Color.RED);
            }
            else {
                circle.setFill(Color.GREEN);
            }
            circle.setFill(Color.RED);
            circles.add(new Pair(circle,currTrafficLight.getName()));
            x++;
        }
    }

    /**
     * creating the array of lines to represent roads
     * @param roads - roads in the map
     */
    private void createLines(Set<Road> roads) {

        HashSet<Road> l_roads = new HashSet<Road>();
        for (Road currRoad: roads) {

            double deviationAngle = 0.0;
            double deviationDistance = -0.03;
            Point newSource = getDeviationFromVectorEnd(currRoad.getDestinationCrossroad(), currRoad.getSourceCrossroad(),deviationAngle , deviationDistance);
            Point newDestination = getDeviationFromVectorEnd(currRoad.getSourceCrossroad(), currRoad.getDestinationCrossroad(), deviationAngle , deviationDistance);
            double x1 = newSource.getCoordinateX();
            double y1 = newSource.getCoordinateY();
            double x2 = newDestination.getCoordinateX();
            double y2 = newDestination.getCoordinateY();

            Line roadLine = new Line(x1,y1,x2,y2);
            roadLine.setStroke(Color.BLACK);
            roadLine.setStrokeWidth(0.05);
            roadLine.toBack();
            Line separateline = new Line(x1,y1,x2,y2);
            separateline.setStroke(Color.WHITE);
            separateline.setStrokeWidth(0.005);
            lines.add(new Pair(roadLine,currRoad.getRoadName()));
            lines.add(new Pair(separateline,currRoad.getRoadName()));
        }
    }

    public List<Pair<Circle,String>> getCircles() {
        return circles;
    }

    public List<Pair<Line,String>> getLines() {
        return lines;
    }

    /**
     * returns the degree value in radians
     * of the given line on, from the x-axis
     * @param road - the line we check
     * @return the degree value in radians
     *         of the given line on, from the x-axis
     *         The functions:
     *         y1 = ax+b1
     *         y2 = -(1/a)*x+b2
     */
    private Point calculateTrafficLightLocation(Road road) {

        double deviationAngle = 0.85;
        double deviationDistance = 0.035;
        return getDeviationFromVectorEnd(road.getSourceCrossroad(), road.getDestinationCrossroad(), deviationAngle , deviationDistance);
    }

    /**
     * changes the color of the traffic light in the map to RED
     * @param trafficlight - the traffic light we want to turn red
     * @return
     */
    public MapGraphics TurnTrafficLightRed(TrafficLight trafficlight) {
        for (Pair<Circle,String> currCircle: circles) {
            if (currCircle.getValue() == trafficlight.getName()) {
                currCircle.getKey().setFill(Color.RED);
                return this;
            }
        }
        return this;
    }

    /**
     * changes the color of the traffic light in the map to GREEN
     * @param trafficlight - the traffic light we want to turn green
     * @return
     */
    public MapGraphics TurnTrafficLightGreen(TrafficLight trafficlight) {
        for (Pair<Circle,String> currCircle: circles) {
            if (currCircle.getValue() == trafficlight.getName()) {
                currCircle.getKey().setFill(Color.GREEN);
                return this;
            }
        }
        return this;
    }

    //the angle is always  to the vector angle.
    private Point getDeviationFromVectorEnd(Point source, Point destination, double deviationAngle , double deviationDistance){
        double x1 = source.getCoordinateX();
        double y1 = source.getCoordinateY();
        double x2 = destination.getCoordinateX();
        double y2 = destination.getCoordinateY();

        double vectorAngle = 0;
        if(x2==x1){
            if(y2>y1){
                vectorAngle = (Math.PI)/2;
            }else{
                vectorAngle = -(Math.PI)/2;
            }

        }else {
            double slope = (y2 - y1) / (x2 - x1);
            vectorAngle = Math.atan(slope);
            if (x2 < x1) {
                vectorAngle += Math.PI;
            }
        }
        vectorAngle += (Math.PI)*deviationAngle;
        double newX = x2+deviationDistance*Math.cos(vectorAngle);
        double newY = y2+deviationDistance*Math.sin(vectorAngle);
        return new PointImpl(newX,newY);
    }
}
