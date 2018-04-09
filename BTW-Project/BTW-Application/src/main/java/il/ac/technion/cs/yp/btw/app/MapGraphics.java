package il.ac.technion.cs.yp.btw.app;

import il.ac.technion.cs.yp.btw.citysimulation.CityRoad;
import il.ac.technion.cs.yp.btw.citysimulation.CityTrafficLight;
import il.ac.technion.cs.yp.btw.citysimulation.RoadData;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Anat and Adam Elgressy
 * @Date 20-1-2018
 * Class for creation of map graphic object.
 */
public class MapGraphics {
    private List<Pair<Circle,String>> circles;
    private List<Pair<Line,String>> lines;

    /**
     * Constructor for map graphics to display graphic shapes from roads and traffic lights
     * @param trafficLights
     * @param roads
     */
    MapGraphics(Set<CityTrafficLight> trafficLights, Set<CityRoad> roads) {
        this.circles = new ArrayList<>();
        this.lines = new ArrayList<>();

        createCircles(trafficLights);
        createLines(roads);
    }

    /**
     * @author: shay
     * @date: 20/1/18
     * creating the array of circles, each circle for a traffic light.
     * @param trafficLights - trafficlights in the map
     */
    //TODO: change the color according to real time loads.
    private void createCircles(Set<CityTrafficLight> trafficLights) {
        int x=0;
        for (CityTrafficLight currTrafficLight: trafficLights) {
            Point point = calculateTrafficLightLocation(currTrafficLight);
            Circle circle = new Circle(point.getCoordinateX(), point.getCoordinateY(),0.0001);
            if (currTrafficLight.getState() == CityTrafficLight.TrafficLightState.GREEN)
                circle.setFill(Color.GREEN);
            else
                circle.setFill(Color.RED);
            circle.setOnMouseClicked(event -> {System.out.println(currTrafficLight.getName());});
            circles.add(new Pair(circle,currTrafficLight.getName()));
            x++;
        }
    }

    /**
     * @author Shay and Anat and Adam Elgressy
     * @Date 20-1-2018
     * creating the array of lines to represent roads
     * @param roads - roads in the map
     */
    private void createLines(Set<CityRoad> roads) {

        HashSet<CityRoad> l_roads = new HashSet<>();
        for (CityRoad currRoad: roads) {

            double deviationAngle = 0.0;
            double deviationDistance = -0.00003;
            Point newSource = getDeviationFromVectorEnd(currRoad.getDestinationCrossroad(), currRoad.getSourceCrossroad(),deviationAngle , deviationDistance);
            Point newDestination = getDeviationFromVectorEnd(currRoad.getSourceCrossroad(), currRoad.getDestinationCrossroad(), deviationAngle , deviationDistance);

            deviationAngle = 0.5;
            deviationDistance = 0.000125;

            newSource = getDeviationFromVectorEnd(newDestination, newSource,-deviationAngle , deviationDistance);
            newDestination = getDeviationFromVectorEnd(newSource, newDestination, deviationAngle , deviationDistance);

            double xroad1 = newSource.getCoordinateX();
            double yroad1 = newSource.getCoordinateY();
            double xroad2 = newDestination.getCoordinateX();
            double yroad2 = newDestination.getCoordinateY();


            Line roadLine = new Line(xroad1,yroad1,xroad2,yroad2);
            //roadLine.setStroke(Color.BLACK);
            chooseRoadColor(roadLine,currRoad.getCurrentWeight().seconds());
            roadLine.setStrokeWidth(0.00025);
            roadLine.toBack();

            deviationAngle = 0.0;
            deviationDistance = -0.0003;
            newSource = getDeviationFromVectorEnd(currRoad.getDestinationCrossroad(), currRoad.getSourceCrossroad(),deviationAngle , deviationDistance);
            newDestination = getDeviationFromVectorEnd(currRoad.getSourceCrossroad(), currRoad.getDestinationCrossroad(), deviationAngle , deviationDistance);

            double xSeperateLine1 = newSource.getCoordinateX();
            double ySeperateLine1 = newSource.getCoordinateY();
            double xSeperateLine2 = newDestination.getCoordinateX();
            double ySeperateLine2 = newDestination.getCoordinateY();

            Line separateline = new Line(xSeperateLine1,ySeperateLine1,xSeperateLine2,ySeperateLine2);
            separateline.setStroke(Color.WHITE);
            separateline.setStrokeWidth(0.00005);
            lines.add(new Pair(roadLine,currRoad.getRoadName()));
            lines.add(new Pair(separateline,currRoad.getRoadName()));

            roadLine.setOnMouseClicked(event -> {
                RoadData roadData = currRoad.getStatisticalData();
                int length = roadData.getRoadLength();
                double averageSpeed = roadData.getAverageSpeed();
                int numOfVehicles = roadData.getNumOfVehicles();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/road_real_time_statistics.fxml"));
                try {
                    Parent root = fxmlLoader.load();
                    RoadRealTimeStatisticsController roadRealTimeStatisticsController = fxmlLoader.getController();
                    roadRealTimeStatisticsController.generateView(length,averageSpeed,numOfVehicles, root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }
    }


    public List<Pair<Circle,String>> getCircles() {
        return circles;
    }

    public List<Pair<Line,String>> getLines() {
        return lines;
    }

    /**@author: Anat & Sharon
     * @date: 20/1/18
     * returns the degree value in radians
     * of the given line on, from the x-axis
     * @param trafficLight - the line we check is its source road
     * @return the degree value in radians
     *         of the given line on, from the x-axis
     *         The functions:
     *         y1 = ax+b1
     *         y2 = -(1/a)*x+b2
     */

    private Point calculateTrafficLightLocation(TrafficLight trafficLight) {
        Road sourceRoad = trafficLight.getSourceRoad();
        double deviationAngle = 0.85;
        double deviationDistance = 0.0003;
        return getDeviationFromVectorEnd(sourceRoad.getSourceCrossroad(), sourceRoad.getDestinationCrossroad(), deviationAngle , deviationDistance);
    }

    /**
     * @author: shay
     * @date: 20/1/18
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
     * @author: shay
     * @date: 20/1/18
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

    /**
     * @author: Anat & Sharon
     * @date: 20/1/18
     * Create GeoJson full string for Road.
     * @Param: road- the road that we want geoJson string for.
     * @return: string in geoJson full format for the given road.
     */
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

    /**
     * @author: shay
     * Need to determine colors by weight
     */
    private static final long ORANGE_THRESHOLD = 7000;
    private static final long RED_THRESHOLD = 700000;
    private static final long DEEPRED_THRESHOLD = 7000000;

    /**
     * @author: shay
     * @date: 09/04/2018
     * determines color to the road by its load
     * @param roadLine the line we need to color
     * @param overload long number, the overload of the road
     */
    private void chooseRoadColor(Line roadLine, long overload) {
        roadLine.setStroke(Color.BLACK);
        if (overload > ORANGE_THRESHOLD)
            roadLine.setStroke(Color.ORANGE);
        if (overload > RED_THRESHOLD)
            roadLine.setStroke(Color.ORANGERED);
        if (overload > DEEPRED_THRESHOLD)
            roadLine.setStroke(Color.DARKRED);

    }
}
