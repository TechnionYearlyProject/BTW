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

public class MapGraphics {
    private ArrayList<Circle> circles;
    private ArrayList<Line> lines;

    MapGraphics(ArrayList<TrafficLight> trafficLights, ArrayList<Road> roads) {
        circles = new ArrayList<Circle>();
        lines = new ArrayList<Line>();

        createCircles(trafficLights);
        createLines(roads);
    }

    /**
     * creating the array of circles, each circle for a traffic light.
     * @param trafficLights - trafficlights in the map
     */
    private void createCircles(ArrayList<TrafficLight> trafficLights) {
        for (int i = 0; i < trafficLights.size(); i++) {
            Circle circle = new Circle(trafficLights.get(i).getCoordinateX(),trafficLights.get(i).getCoordinateY(),5);
            circle.setFill(Color.RED);
            circles.add(circle);
        }
    }

    /**
     * creating the array of lines to represent roads
     * @param roads - roads in the map
     */
    private void createLines(ArrayList<Road> roads) {
        for (int i = 0; i < roads.size(); i++) {
            double x1 = roads.get(i).getSourceCrossroad().getCoordinateX();
            double y1 = roads.get(i).getSourceCrossroad().getCoordinateY();
            double x2 = roads.get(i).getDestinationCrossroad().getCoordinateX();
            double y2 = roads.get(i).getDestinationCrossroad().getCoordinateY();
            Line roadLine = new Line(x1,y1,x2,y2);
            roadLine.setFill(Color.BLACK);
            roadLine.setStrokeWidth(25);
            Line separateline = new Line(x1,y1,x2,y2);
            separateline.setFill(Color.WHITE);
            separateline.setStrokeWidth(2);
            lines.add(roadLine);
            lines.add(separateline);
        }
    }

    public ArrayList<Circle> getCircles() {
        return circles;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }
}
