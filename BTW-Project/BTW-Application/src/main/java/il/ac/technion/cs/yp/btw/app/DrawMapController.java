package il.ac.technion.cs.yp.btw.app;

import il.ac.technion.cs.yp.btw.citysimulation.*;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.db.BTWDataBaseImpl;
import il.ac.technion.cs.yp.btw.geojson.GeoJsonParserImpl;
import il.ac.technion.cs.yp.btw.mapgeneration.FreeFormMapSimulator;
import il.ac.technion.cs.yp.btw.mapgeneration.GridCityMapSimulator;
import il.ac.technion.cs.yp.btw.mapgeneration.MapSimulator;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.*;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import static javafx.application.Application.launch;

public class DrawMapController implements Initializable {
    private static final double MAX_SCALE = 10.0d;
    private static final double MIN_SCALE = .1d;
    Canvas canvas;
    BorderPane root;
    Set<Circle> circles;
    Set<Line> lines = new HashSet<Line>();
    CityMap cityMap;
    Stage stage;

    void initCityMap(CityMap cityMap){
        this.cityMap = cityMap;
    }

    void initStage(Stage stage) {
        this.stage = stage;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start();
    }

    public void start() {
//        stage.setTitle("Drawing Operations Test");
        root = new BorderPane();

        root.setStyle("-fx-background-color: transparent;");
//        canvas = new Canvas(stage.getWidth(), stage.getHeight());
//        canvas = new Canvas(640, 640);
//        root.getChildren().add(canvas); // add plain canvas

        final Affine accumulatedScales = new Affine();
        accumulatedScales.appendScale(1000,1000);
        root.getTransforms().add(accumulatedScales);
        //root.autoSizeChildrenProperty();
        root.setOnScroll(event -> {
            double dy = event.getDeltaY();
            double delta = dy>0.0 ? 1.2 : 0.8;
            accumulatedScales.appendScale(delta,delta
                    ,event.getX(), event.getY());
        });

//        insertRandomMap();

//        Scene scene = new Scene(root, 640, 640, Color.GREY);
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight(), Color.GREY);
        draw(cityMap);

        stage.show();
        stage.setScene(scene);


    }

    private String parseCitySimulationToGeoJsonString(MapSimulator gridCityMapSimulator) {
        GeoJsonParserImpl geoJsonParser = new GeoJsonParserImpl();
        File mapFile = geoJsonParser.buildGeoJsonFromSimulation(gridCityMapSimulator);
        String mapString = "";
        FileReader fileReader = null;
        try {
            String line;
            fileReader = new FileReader(mapFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                mapString = mapString+line;
            }
            // Always close files.
            bufferedReader.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return mapString;
    }

    //TODO - this is for testing purposes
    private void insertRandomMap() {
        GridCityMapSimulator mapSimulator = new GridCityMapSimulator();
        mapSimulator.setNumOfStreets(3);
        mapSimulator.setNumOfAvenues(3);
        mapSimulator.build();
//        FreeFormMapSimulator mapSimulator = new FreeFormMapSimulator();
//        mapSimulator.build();

        String mapString = parseCitySimulationToGeoJsonString(mapSimulator);

        System.out.println(mapString);

//        Insert the new map to the database.
        BTWDataBase dataBase = new BTWDataBaseImpl("simulatedCity33");
        dataBase.saveMap(mapString);

//        BTWDataBase dataBase = new BTWDataBaseImpl("simulatedCity12");

        CitySimulator citySimulator = new CitySimulatorImpl(dataBase);
        cityMap = citySimulator.saveMap();
    }


    /**
     * @author: shay
     * @date: 20/1/18
     * gets the logical object and draw all the corcles and lines from it
     * @param cityMap - the citymap object we want to draw
     * @return DrawMapController
     */
    public DrawMapController draw(CityMap cityMap) {
        circles = new HashSet<Circle>();
        lines = new HashSet<Line>();
        Set<CityRoad> cityRoads = cityMap.getAllRoads();
        Set<CityTrafficLight> cityTrafficLights = cityMap.getAllTrafficLights();
        MapGraphics map = new MapGraphics(cityTrafficLights,cityRoads);
        for (Pair<Line,String> line: map.getLines()) {
            root.getChildren().add(line.getKey());
        }
        // add all circles
        for (Pair<Circle,String> circle: map.getCircles()) {
            root.getChildren().add(circle.getKey());
        }
        return this;
    }

    /**
     * @author: shay
     * @date: 20/1/18
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
     * @author: shay
     * @date: 20/1/18
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
     * @author: shay
     * @date: 20/1/18
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
     * @author: shay
     * @date: 20/1/18
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