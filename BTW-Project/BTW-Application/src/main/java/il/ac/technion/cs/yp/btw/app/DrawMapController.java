package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import il.ac.technion.cs.yp.btw.citysimulation.*;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.db.BTWDataBaseImpl;
import il.ac.technion.cs.yp.btw.geojson.GeoJsonParserImpl;
import il.ac.technion.cs.yp.btw.mapgeneration.GridCityMapSimulator;
import il.ac.technion.cs.yp.btw.mapgeneration.MapSimulator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.beans.EventHandler;
import java.io.*;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import static javafx.application.Application.launch;

public class DrawMapController implements Initializable {
    private static final double MAX_SCALE = 10.0d;
    private static final double MIN_SCALE = .1d;
    Canvas canvas;
    BorderPane borderPane;
    Set<Circle> circles;
    Set<Line> lines = new HashSet<Line>();
    CityMap cityMap;
    CitySimulator citySimulator;
    Stage stage;
    JFXButton playButton, tickButton, addVehiclesButton;
    JFXTextField numOfVehiclesTextField;
    boolean isPlayButton;
    Timeline playCityTimeline;
    CompletableFuture<Boolean> tickTask;

//    void initCityMap(CityMap cityMap){
    void initCitySimulator(CitySimulator citySimulator){
        this.citySimulator = citySimulator;
        cityMap = citySimulator.saveMap();
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
        AnchorPane root = new AnchorPane();
        borderPane = new BorderPane();

        borderPane.setStyle("-fx-background-color: transparent;");
        root.setStyle("-fx-background-color: transparent;");

//        canvas = new Canvas(stage.getWidth(), stage.getHeight());
//        canvas = new Canvas(640, 640);
//        borderPane.getChildren().add(canvas); // add plain canvas

        final Affine accumulatedScales = new Affine();
        accumulatedScales.appendScale(100,100);
        borderPane.getTransforms().add(accumulatedScales);
        //borderPane.autoSizeChildrenProperty();
        borderPane.setOnScroll(event -> {
            double dy = event.getDeltaY();
            double delta = dy>0.0 ? 1.2 : 0.8;
            accumulatedScales.appendScale(delta,delta
                    ,event.getX(), event.getY());
        });

//        insertRandomMap();
        HBox playAndTickHbox = new HBox();
        playAndTickHbox.setPadding(new Insets(15, 12, 15, 12));
        playAndTickHbox.setSpacing(10);

        tickButton = createRaisedJFXButtonWithIcon("/icons8-arrow-50.png");
        tickButton.setOnAction(event -> {
            tickButtonClicked(event);
        });
        playButton = createRaisedJFXButtonWithIcon("/icons8-play-50.png");
        playButton.setOnAction(event -> {
            playButtonClicked(event);
        });
        isPlayButton = true;
        playAndTickHbox.getChildren().addAll(tickButton, playButton);

        playCityTimeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            getTickTask();
            redrawMap();
            resetTickTask();
//            tickTask = tickTask.thenApply(val -> {redrawMap(); return true;})
//                    .thenApply(val -> {performMapTicks();
//                        return true;});

        }));
        playCityTimeline.setCycleCount(Timeline.INDEFINITE);


        HBox addVehiclesHbox = new HBox();
        addVehiclesHbox.setPadding(new Insets(15, 12, 15, 12));
        addVehiclesHbox.setSpacing(10);

        //set up the button
        addVehiclesButton = createRaisedJFXButtonWithText("Choose Vehicles To Add");
        addVehiclesButton.setOnAction(event -> {
            boolean textFieldWasVisible = numOfVehiclesTextField.isVisible();
            if(!textFieldWasVisible) {
                numOfVehiclesTextField.setVisible(true);
                addVehiclesButton.setText("Add Vehicles");
            } else {
                int numOfVehicles;
                try {
                    numOfVehicles = Integer.parseInt(numOfVehiclesTextField.getText());
                    if(numOfVehicles < 1) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    showErrorDialog("Number of vehicles needs to be a number greater than 0");
                    return;
                }
                numOfVehiclesTextField.setVisible(false);
                numOfVehiclesTextField.setText("");
                addVehiclesButton.setText("Choose Vehicles To Add");
                addRandomVehiclesToSimulation(numOfVehicles);
            }
        });

        //setting up the text field
        numOfVehiclesTextField = new JFXTextField();
        numOfVehiclesTextField.setPromptText("Enter amount of vehicles");
        numOfVehiclesTextField.setPrefSize(200, 50);
        numOfVehiclesTextField.setVisible(false);


        addVehiclesHbox.getChildren().addAll(addVehiclesButton, numOfVehiclesTextField);

//        root.getChildren().addAll(borderPane, tickButton);
        root.getChildren().addAll(borderPane, playAndTickHbox, addVehiclesHbox);
        AnchorPane.setTopAnchor(borderPane, 0.0);

        AnchorPane.setBottomAnchor(playAndTickHbox, 5.0);
//        AnchorPane.setLeftAnchor(playAndTickHbox, 5.0);
        AnchorPane.setRightAnchor(playAndTickHbox, 5.0);


        AnchorPane.setBottomAnchor(addVehiclesHbox, 5.0);
        AnchorPane.setLeftAnchor(addVehiclesHbox, 5.0);


//        AnchorPane.setBottomAnchor(tickButton, 5.0);
//        AnchorPane.setLeftAnchor(tickButton, 5.0);
//        AnchorPane.setRightAnchor(tickButton, 5.0);

//        Scene scene = new Scene(borderPane, 640, 640, Color.GREY);
//        Scene scene = new Scene(borderPane, stage.getWidth(), stage.getHeight(), Color.GREY);
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight(), Color.GREY);
        draw(cityMap);


        stage.show();
        stage.setScene(scene);


    }

    private void addRandomVehiclesToSimulation(int numOfVehicles) {
        //TODO: fill up with Guy's code
    }

    private void showErrorDialog(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid input");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);

        alert.showAndWait();
    }

    private void resetTickTask() {
        tickTask = CompletableFuture.supplyAsync(() -> {
            performMapTicks();
            return true;
        });
    }

    private void getTickTask() {
        try {
            tickTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void playButtonClicked(ActionEvent event) {
        Image buttonImage;
        if(isPlayButton) {
            buttonImage = new Image(getClass().getResourceAsStream("/icons8-pause-50.png"));
            tickButton.setDisable(true);
            resetTickTask();
            playCityTimeline.play();
        } else {
            buttonImage = new Image(getClass().getResourceAsStream("/icons8-play-50.png"));
            tickButton.setDisable(false);
            playCityTimeline.stop();
        }
        isPlayButton = !isPlayButton;
        playButton.setGraphic(new ImageView(buttonImage));
    }

    private JFXButton createRaisedJFXButtonWithIcon(String iconResourceLocation) {
        JFXButton button = new JFXButton("");
        Image buttonImage = new Image(getClass().getResourceAsStream(iconResourceLocation));
        button.setButtonType(JFXButton.ButtonType.RAISED);
        button.setStyle("-fx-background-color: #ffffff");
        button.setPrefSize(200, 50);
        button.setRipplerFill(Color.BLACK);
        button.setGraphic(new ImageView(buttonImage));
        return button;
    }

    private JFXButton createRaisedJFXButtonWithText(String buttonText) {
        JFXButton button = new JFXButton(buttonText);
        button.setButtonType(JFXButton.ButtonType.RAISED);
        button.setStyle("-fx-background-color: #ffffff");
        button.setPrefSize(200, 50);
        button.setRipplerFill(Color.BLACK);
        return button;
    }

    private void tickButtonClicked(ActionEvent event) {
//        if(isPlayButton) { //can only do a tick when play isn't enabled
//            performMapTicks();
//        }

//        performMapTicks();
//        redrawMap();
        tickButton.setDisable(true);
        try {
            Thread thread = new Thread(() -> {
                performMapTicks();
                Platform.runLater(() -> {
                    redrawMap();
                    tickButton.setDisable(false);
                });
            });
            thread.start();
        } catch (Exception e) {
            tickButton.setDisable(false);
            System.out.println("tick button caught exception " + e.getMessage());
        }
//        try {
//            CompletableFuture.supplyAsync(() -> {performMapTicks(); return true;}).
//                    thenAccept(val -> redrawMap()).get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
    }

    private void performMapTicks() {
        int numberOfTicks = 10;
        citySimulator.tick(numberOfTicks);
        cityMap = citySimulator.saveMap();
    }

    private void redrawMap() {
        borderPane.getChildren().clear();
        draw(cityMap);
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

    public DrawMapController draw(CityMap cityMap) {
        circles = new HashSet<Circle>();
        lines = new HashSet<Line>();
        Set<CityRoad> cityRoads = cityMap.getAllRoads();
        Set<CityTrafficLight> cityTrafficLights = cityMap.getAllTrafficLights();
        MapGraphics map = new MapGraphics(cityTrafficLights,cityRoads);
        for (Pair<Line,String> line: map.getLines()) {
//            lines.add(line.getKey());
            borderPane.getChildren().add(line.getKey());
        }
        // add all circles
        for (Pair<Circle,String> circle: map.getCircles()) {
//            circles.add(circle.getKey());
            borderPane.getChildren().add(circle.getKey());
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