package il.ac.technion.cs.yp.btw.gui;
import il.ac.technion.cs.yp.btw.citysimulation.CityMap;
import il.ac.technion.cs.yp.btw.citysimulation.CityRoad;
import il.ac.technion.cs.yp.btw.citysimulation.CityTrafficLight;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.mapgeneration.FreeFormMapSimulator;
import il.ac.technion.cs.yp.btw.mapgeneration.GridCityMapSimulator;
import il.ac.technion.cs.yp.btw.mapgeneration.MapSimulator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Pair;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;


import java.util.HashSet;
import java.util.Set;

public class DrawMap extends Application {
    private static final double MAX_SCALE = 10.0d;
    private static final double MIN_SCALE = .1d;
    Canvas canvas;
    BorderPane root;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drawing Operations Test");
        root = new BorderPane();
        canvas = new Canvas(640, 640);
        //root.getChildren().add(canvas); // add plain canvas

        //GridCityMapSimulator k = new GridCityMapSimulator();
        //FreeFormMapSimulator k = new FreeFormMapSimulator();
        //k.build();

        final Affine accumulatedScales = new Affine();
        accumulatedScales.appendScale(100,100);
        root.getTransforms().add(accumulatedScales);
        //root.autoSizeChildrenProperty();
        root.setOnScroll(event -> {
            double dy = event.getDeltaY();
            double delta = dy>0.0 ? 1.2 : 0.9;
            accumulatedScales.appendScale(delta,delta
                    ,event.getX(), event.getY());
        });


        Scene scene = new Scene(root, 640, 640, Color.GREY);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public DrawMap draw(CityMap cityMap) {
        Set<Circle> circles = new HashSet<Circle>();
        Set<Line> lines = new HashSet<Line>();
        Set<CityRoad> cityRoads = cityMap.getAllRoads();
        Set<CityTrafficLight> cityTrafficLights = cityMap.getAllTrafficLights();
        MapGraphics map = new MapGraphics(cityTrafficLights,cityRoads);
        for (Pair<Line,String> line: map.getLines()) {
            lines.add(line.getKey());
        }
        // add all circles
        for (Pair<Circle,String> circle: map.getCircles()) {
            circles.add(circle.getKey());
        }
        return this;
    }

    // should be implemented outside
    public Set<TrafficLight> getTrafficLights() {
        return new HashSet<TrafficLight>();
    }
    // should be implemented outside
    public Set<Road> getRoads() {
        return new HashSet<Road>();
    }

    /**
     * finding minimum coordinate in order to zoom in map
     * @param trafficLights - set of traffic light
     * @return min coordinate x in set
     */
    public double findMinX(Set<TrafficLight> trafficLights) {
        double min = trafficLights.iterator().next().getCoordinateX();
        for (TrafficLight tl: trafficLights) {
            if (tl.getCoordinateX() < min)
                min = tl.getCoordinateX();
        }
        return min;
    }

    /**
     * finding maximum coordinate in order to zoom in map
     * @param trafficLights - set of traffic light
     * @return max coordinate x in set
     */
    public double findMaxX(Set<TrafficLight> trafficLights) {
        double max = trafficLights.iterator().next().getCoordinateX();
        for (TrafficLight tl: trafficLights) {
            if (tl.getCoordinateX() > max)
                max = tl.getCoordinateX();
        }
        return max;
    }

    /**
     * finding minimum coordinate in order to zoom in map
     * @param trafficLights - set of traffic light
     * @return min coordinate y in set
     */
    public double findMinY(Set<TrafficLight> trafficLights) {
        double min = trafficLights.iterator().next().getCoordinateY();
        for (TrafficLight tl: trafficLights) {
            if (tl.getCoordinateY() < min)
                min = tl.getCoordinateY();
        }
        return min;
    }

    /**
     * finding maximum coordinate in order to zoom in map
     * @param trafficLights - set of traffic light
     * @return max coordinate x in set
     */
    public double findMaxY(Set<TrafficLight> trafficLights) {
        double max = trafficLights.iterator().next().getCoordinateY();
        for (TrafficLight tl: trafficLights) {
            if (tl.getCoordinateY() > max)
                max = tl.getCoordinateY();
        }
        return max;
    }
}