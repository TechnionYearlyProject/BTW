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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapGraphics {
    private List<Circle> circles;
    private List<Line> lines;

    MapGraphics(Set<TrafficLight> trafficLights, Set<Road> roads) {
        this.circles = new ArrayList<>();
        this.lines = new ArrayList<Line>();

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
            circles.add(circle);
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
            double x1 = currRoad.getSourceCrossroad().getCoordinateX();
            double y1 = currRoad.getSourceCrossroad().getCoordinateY();
            double x2 = currRoad.getDestinationCrossroad().getCoordinateX();
            double y2 = currRoad.getDestinationCrossroad().getCoordinateY();
            Line roadLine = new Line(x1,y1,x2,y2);
            roadLine.setStroke(Color.BLACK);
            roadLine.setStrokeWidth(0.05);
            roadLine.toBack();
            Line separateline = new Line(x1,y1,x2,y2);
            separateline.setStroke(Color.WHITE);
            separateline.setStrokeWidth(0.005);
            lines.add(roadLine);
            lines.add(separateline);
        }
    }

    public List<Circle> getCircles() {
        return circles;
    }

    public List<Line> getLines() {
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
        double x1 = road.getSourceCrossroad().getCoordinateX();
        double y1 = road.getSourceCrossroad().getCoordinateY();
        double x2 = road.getDestinationCrossroad().getCoordinateX();
        double y2 = road.getDestinationCrossroad().getCoordinateY();

        double movement = 0.02;

        if (road.getSourceCrossroad().getCoordinateX() == road.getDestinationCrossroad().getCoordinateX())
            return new PointImpl(x1, y1);

        double slope = (y2 - y1) / (x2 - x1);
        double verticalSlope = -1 / slope;

        double degree = Math.atan(slope)+Math.PI/2;

        if((x2<x1)||((x2==x1)&&(y2<y1))){
            degree = degree + Math.PI;
        }

        if ((degree > Math.PI/2) || (degree < -Math.PI/2)) {
            degree = Math.atan(slope)-Math.PI/2;
        }

        double newX = x2+movement*Math.cos(degree);
        double newY = y2+movement*Math.sin(degree);

        return new PointImpl(newX,newY);


        /*double b1 = y2 - slope * x2;
        double b2 = y2 - verticalSlope * x2;
        double b3 = slope * x2 + b1;
        double movement = 0.03;
        if (y1 == y2) {
            if (x1 < x2) {
                return new PointImpl(x2, y2 + movement);
            } else {
                return new PointImpl(x2, y2 - movement);
            }
        } else {
            if (y1 > y2) {
                double newY = ((-verticalSlope) * b1 + b2*slope + movement*slope)/(slope-verticalSlope);
                double newX = (newY-b1)/slope;
                return new PointImpl(newX,newY);
            } else {
                double newY = ((-verticalSlope) * b1 + b2*slope - movement*slope)/(slope-verticalSlope);
                double newX = (newY-b1)/slope;
                return new PointImpl(newX,newY);
            }
        }*/
    }
}
