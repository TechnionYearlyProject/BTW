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
        circles = new ArrayList<>();
        lines = new ArrayList<Line>();

        createCircles(trafficLights);
        createLines(roads);
    }

    /**
     * creating the array of circles, each circle for a traffic light.
     * @param trafficLights - trafficlights in the map
     */
    private void createCircles(Set<TrafficLight> trafficLights) {
//        HashSet<TrafficLight> l_trafficLights = new HashSet<TrafficLight>();
//         insert into l_trafficLights one trafficLight for each source road
//        for (TrafficLight currTrafficLight: trafficLights) {
//            boolean existsFlag = false;
//            for (TrafficLight innerTrafficLight: l_trafficLights) {
//                if (currTrafficLight.getSourceRoad().getRoadName() == innerTrafficLight.getSourceRoad().getRoadName()){
//                    existsFlag = true;
//                    break;
//                }
//            }
//            if (!existsFlag)
//                l_trafficLights.add(currTrafficLight);
//        }
        // create circle for each of the traffic lights in l_trafficLights
        for (TrafficLight currTrafficLight: trafficLights) {
            double degrees = calculateLineSlopeInDegrees(currTrafficLight.getSourceRoad());
            Circle circle = new Circle(currTrafficLight.getCoordinateX(),currTrafficLight.getCoordinateY(),0.005);
            circle.setFill(Color.RED);
            circles.add(circle);
        }
    }

    /**
     * creating the array of lines to represent roads
     * @param roads - roads in the map
     */
    private void createLines(Set<Road> roads) {

        HashSet<Road> l_roads = new HashSet<Road>();
        // insert into l_roads only one road with the same name
//        for (Road currRoad: roads) {
//            boolean existsFlag = false;
//            if(l_roads.stream().anyMatch(road -> road.getDestinationCrossroad().getName()
//                    .equals(currRoad.getSourceCrossroad().getName())
//            && road.getSourceCrossroad().getName()
//                    .equals(currRoad.getDestinationCrossroad().getName())))
//            {
//                existsFlag = true;
//            }
//            if (!existsFlag)
//                l_roads.add(currRoad);
//        }

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
     */
    private double calculateLineSlopeInDegrees(Road road){
        if(road.getSourceCrossroad().getCoordinateX()==road.getDestinationCrossroad().getCoordinateX())
            return Math.PI/2;
        double slope = (road.getSourceCrossroad().getCoordinateY()-road.getDestinationCrossroad().getCoordinateY())/
                (road.getSourceCrossroad().getCoordinateX()-road.getDestinationCrossroad().getCoordinateX());
        return Math.atan(slope);
    }
}
