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
import javafx.stage.Stage;

import java.util.ArrayList;


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

        ArrayList<TrafficLight> trafficLights = getTrafficLights();
        ArrayList<Road> roads = getRoads();
        MapGraphics map = new MapGraphics(trafficLights,roads);

        // add all circles
        for (int i = 0; i < map.getCircles().size(); i++) {
            root.getChildren().add( map.getCircles().get(i));
        }
        // add all lines
        for (int i = 0; i < map.getLines().size(); i++) {
            root.getChildren().add( map.getLines().get(i));
        }

        primaryStage.setScene(new Scene(root, 640, 640, Color.GREY));
        primaryStage.show();
    }

    // should be implemented outside
    public ArrayList<TrafficLight> getTrafficLights() {
        return new ArrayList<TrafficLight>();
    }
    // should be implemented outside
    public ArrayList<Road> getRoads() {
        return new ArrayList<Road>();
    }
}
