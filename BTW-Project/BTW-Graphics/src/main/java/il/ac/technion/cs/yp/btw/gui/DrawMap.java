package il.ac.technion.cs.yp.btw.gui;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.mapgeneration.FreeFormMapSimulator;
import il.ac.technion.cs.yp.btw.mapgeneration.GridCityMapSimulator;
import il.ac.technion.cs.yp.btw.mapgeneration.MapSimulator;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;

public class DrawMap extends Application {
    private static final double MAX_SCALE = 10.0d;
    private static final double MIN_SCALE = .1d;
    Canvas canvas;
    Group root;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drawing Operations Test");
        root = new Group();
        canvas = new Canvas(640, 640);
        root.getChildren().add(canvas); // add plain canvas
        FreeFormMapSimulator k = new FreeFormMapSimulator();
        k.build();
        Set<TrafficLight> trafficLights = k.getTrafficLights();
        Set<Road> roads = k.getRoads();
        MapGraphics map = new MapGraphics(trafficLights,roads);

        // add all lines
        for (Pair<Line,String> line: map.getLines()) {
            root.getChildren().add(line.getKey());
        }
        // add all circles
        for (Pair<Circle,String> circle: map.getCircles()) {
            root.getChildren().add(circle.getKey());
        }
        Button btn = new Button("> Forward");
        btn.setMaxHeight(10);
        btn.setMaxWidth(30);
        root.getChildren().add(btn);

        final Affine accumulatedScales = new Affine();
        accumulatedScales.appendScale(100,100);
        root.getTransforms().add(accumulatedScales);
        root.autoSizeChildrenProperty();
        root.setOnScroll(event -> {
            double dy = event.getDeltaY();
            double delta = dy>0.0 ? 1.1 : 0.9;
            accumulatedScales.appendScale(delta,delta
                    ,event.getX(), event.getY());
        });

        Scene scene = new Scene(root, 640, 640, Color.GREY);
        primaryStage.setScene(scene);
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