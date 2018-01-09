package il.ac.technion.cs.yp.btw.gui;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

public class DrawMap extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();
        Canvas canvas = new Canvas(640, 640);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas); // add plain canvas

        Set<TrafficLight> trafficLights = getTrafficLights();
        Set<Road> roads = getRoads();
        MapGraphics map = new MapGraphics(trafficLights,roads);

        // add all circles
        for (Circle circle: map.getCircles()) {
            root.getChildren().add(circle);
        }
        // add all lines
        for (Line line: map.getLines()) {
            root.getChildren().add(line);
        }

        primaryStage.setScene(new Scene(root, 640, 640, Color.GREY));
        primaryStage.show();
    }

    // should be implemented outside
    public Set<TrafficLight> getTrafficLights() {
        return new HashSet<TrafficLight>();
    }
    // should be implemented outside
    public Set<Road> getRoads() {
        return new HashSet<Road>();
    }
}
