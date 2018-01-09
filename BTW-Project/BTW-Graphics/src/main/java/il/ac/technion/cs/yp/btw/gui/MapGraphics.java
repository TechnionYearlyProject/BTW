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

import java.util.HashSet;
import java.util.Set;

public class MapGraphics {
    private Set<Circle> circles;
    private Set<Line> lines;

    MapGraphics(Set<TrafficLight> trafficLights, Set<Road> roads) {
        circles = new HashSet<Circle>();
        lines = new HashSet<Line>();

        createCircles(trafficLights);
        createLines(roads);
    }

    /**
     * creating the array of circles, each circle for a traffic light.
     * @param trafficLights - trafficlights in the map
     */
    private void createCircles(Set<TrafficLight> trafficLights) {
        for (TrafficLight currTrafficLight: trafficLights) {
            Circle circle = new Circle(currTrafficLight.getCoordinateX(),currTrafficLight.getCoordinateY(),5);
            circle.setFill(Color.RED);
            circles.add(circle);
        }
    }

    /**
     * creating the array of lines to represent roads
     * @param roads - roads in the map
     */
    private void createLines(Set<Road> roads) {
        for (Road currRoad: roads) {
            double x1 = currRoad.getSourceCrossroad().getCoordinateX();
            double y1 = currRoad.getSourceCrossroad().getCoordinateY();
            double x2 = currRoad.getDestinationCrossroad().getCoordinateX();
            double y2 = currRoad.getDestinationCrossroad().getCoordinateY();
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

    public Set<Circle> getCircles() {
        return circles;
    }

    public Set<Line> getLines() {
        return lines;
    }
}
