package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import il.ac.technion.cs.yp.btw.citysimulation.*;
import il.ac.technion.cs.yp.btw.navigation.PathNotFoundException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static javafx.application.Application.launch;

/**@author: Orel
 * @date: 20/1/18
 * all methods that don't specify an other author are by Orel
 */
public class DrawMapController extends ShowErrorController implements Initializable {

    BorderPane borderPane;
    Set<Circle> circles;
    Set<Line> lines = new HashSet<>();
    CityMap cityMap;
    CitySimulator citySimulator;
    Stage stage;
    JFXButton playButton, tickButton, addVehiclesButton, backButton;
    JFXTextField numOfVehiclesTextField;
    Text timeText;
    boolean isPlayButton;
    Timeline playCityTimeline;
    CompletableFuture<Boolean> tickTask;
    int currentTicks;

    //the interval of ticks for the action of each button
    private int playButtonTicksInterval = 2;
    private int tickButtonTickInterval = 10;

    boolean lastTickActionWasPause = false;

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

    /**
     * @author Anat and Orel and Adam Elgressy
     * @Date 20-1-2018
     */
    public void start() {
        AnchorPane root = initScenePanesAndGetRoot();

        HBox playAndTickHbox = new HBox();
        playAndTickHbox.setPadding(new Insets(15, 12, 15, 12));
        playAndTickHbox.setSpacing(10);

        initPlayAndTickButtons();
        initBackButton();

        //inserting the buttons ot the HBox
        playAndTickHbox.getChildren().addAll(tickButton, playButton);

        initPlayActionTimeline();

        HBox addVehiclesHbox = new HBox();
        addVehiclesHbox.setPadding(new Insets(15, 12, 15, 12));
        addVehiclesHbox.setSpacing(10);

        initVehiclesButton();
        initVehiclesTextField();
        initTimeText();

        addVehiclesHbox.getChildren().addAll(addVehiclesButton, numOfVehiclesTextField);

        //add the hboxes to the screen AnchorPane and anchor them
        root.getChildren().addAll(borderPane, playAndTickHbox, addVehiclesHbox, timeText, backButton);
        AnchorPane.setTopAnchor(borderPane, 0.0);

        AnchorPane.setBottomAnchor(playAndTickHbox, 5.0);
        AnchorPane.setRightAnchor(playAndTickHbox, 5.0);

        AnchorPane.setBottomAnchor(addVehiclesHbox, 5.0);
        AnchorPane.setLeftAnchor(addVehiclesHbox, 5.0);

        //for now it's in the top right
        AnchorPane.setTopAnchor(timeText, 5.0);
        AnchorPane.setRightAnchor(timeText, 5.0);

        //back button alignment
        AnchorPane.setTopAnchor(backButton, 5.0);
        AnchorPane.setLeftAnchor(backButton, 5.0);

        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight(), Color.GREY);

        drawNow(cityMap);

        stage.show();
        stage.setScene(scene);
    }

    private void initTimeText() {
        timeText = new Text("00:00:00");
        timeText.setFill(Color.WHITE);
        timeText.setStyle("-fx-font: 30 arial;");
    }

    /**@author: Orel
     * @date: 20/1/18
     */
    private void initPlayAndTickButtons() {
        tickButton = createRaisedJFXButtonWithIcon("/icons8-arrow-50.png");
        tickButton.setOnAction(event -> {
            tickButtonClicked(event);
        });
        playButton = createRaisedJFXButtonWithIcon("/icons8-play-50.png");
        playButton.setOnAction(event -> {
            playButtonClicked(event);
        });
        isPlayButton = true;
    }

    /**@author: Orel
     * @date: 3/4/18
     */
    private void initBackButton() {
        backButton = createRaisedJFXButtonWithIcon("/icons8-back-filled-50.png");
        backButton.setOnAction(this::backButtonClicked);
        backButton.setMaxWidth(60);
        backButton.setMaxHeight(60);
        backButton.setStyle("-fx-background-color: transparent");
    }

    /**@author: Orel
     * @date: 3/4/18
     */
    private void backButtonClicked(ActionEvent event) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        URL resource = getClass().getResource("/fxml/home_layout.fxml");
        try {
            Parent root = FXMLLoader.load(resource);
            Scene scene = new Scene(root);
            stageTheEventSourceNodeBelongs.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**@author: Orel
     * @date: 20/1/18
     */
    private void initPlayActionTimeline() {
        playCityTimeline = new Timeline(new KeyFrame(Duration.seconds(playButtonTicksInterval), event -> {

            new Thread(() -> {
                getTickTask();
                Platform.runLater(() -> {
                    redrawMap();
                    resetTickTask();
                });
            }).start();

        }));
        playCityTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**@author: Orel
     * @date: 1/4/18
     */
    private String getTimeString() {
        int seconds = currentTicks % 60 ;
        int minutes = (currentTicks / (60)) % 60;
        int hours = (currentTicks / (60*60)) % 24;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**@author: Anat
     * @date: 20/1/18
     */
    private AnchorPane initScenePanesAndGetRoot() {
        AnchorPane root = new AnchorPane();
        borderPane = new BorderPane();

        borderPane.setStyle("-fx-background-color: transparent;");
        root.setStyle("-fx-background-color: transparent;");

        final Affine accumulatedScales = new Affine();
        accumulatedScales.appendScale(100000,100000);
        borderPane.getTransforms().add(accumulatedScales);
        borderPane.setOnScroll(event -> {
            double dy = event.getDeltaY();
            double delta = dy>0.0 ? 1.4 : 0.6;
            accumulatedScales.appendScale(delta,delta
                    ,event.getX(), event.getY());
        });
        return root;
    }
    /**@author: Orel
     * @date: 20/1/18
     */
    private void initVehiclesTextField() {
        numOfVehiclesTextField = new JFXTextField();
        numOfVehiclesTextField.setPromptText("Vehicles amount (1 - 200)");
        numOfVehiclesTextField.setPrefSize(200, 50);
        numOfVehiclesTextField.setVisible(false);
    }


    /**@author: Orel
     * @date: 20/1/18
     */
    private void initVehiclesButton() {
        addVehiclesButton = createRaisedJFXButtonWithText("Choose Vehicles To Add");
        addVehiclesButton.setOnAction(event -> {
            boolean textFieldWasVisible = numOfVehiclesTextField.isVisible();
            if(!textFieldWasVisible) {
                numOfVehiclesTextField.setVisible(true);
                //numOfVehiclesTextField.setBackground();
                numOfVehiclesTextField.setFont(Font.font("Verdana", FontWeight.BOLD,13.5));
                numOfVehiclesTextField.setStyle("-fx-text-inner-color: red;");
                addVehiclesButton.setText("Add Vehicles");
            } else {
                int numOfVehicles;
                try {
                    numOfVehicles = Integer.parseInt(numOfVehiclesTextField.getText());
                    if(numOfVehicles < 1 || numOfVehicles > 200) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    showErrorDialog("Number of vehicles needs to be a number between 1 and 200");
                    return;
                }
                numOfVehiclesTextField.setVisible(false);
                numOfVehiclesTextField.setText("");
                addVehiclesButton.setText("Choose Vehicles To Add");
                addRandomVehiclesToSimulation(numOfVehicles);
            }
        });
    }

    /**@author: Orel
     * @date: 20/1/18
     */
    private void addRandomVehiclesToSimulation(int numOfVehicles) {
        Thread thread = new Thread(() -> {
            try {
                citySimulator.addRandomVehicles(numOfVehicles);
            } catch (PathNotFoundException e) {
                e.printStackTrace();
            }
            cityMap = citySimulator.saveMap();
            Platform.runLater(this::redrawMap);
        });
        thread.start();
    }

    /**@author: Orel
     * @date: 20/1/18
     */
    private void resetTickTask() {
        tickTask = CompletableFuture.supplyAsync(() -> {
            performMapTicks(playButtonTicksInterval);
            return true;
        });
    }
    /**@author: Orel
     * @date: 20/1/18
     */
    private void getTickTask() {
        try {
            tickTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    /**@author: Orel
     * @date: 20/1/18
     */
    private void playButtonClicked(ActionEvent event) {
        Image buttonImage;
        if(isPlayButton) {
            buttonImage = new Image(getClass().getResourceAsStream("/icons8-pause-50.png"));
            tickButton.setDisable(true);
            //only fetch a new task if the previous task didn't finish
            if(!lastTickActionWasPause) resetTickTask();
            lastTickActionWasPause = false;
            playCityTimeline.play();
        } else {
            buttonImage = new Image(getClass().getResourceAsStream("/icons8-play-50.png"));
            tickButton.setDisable(false);
            lastTickActionWasPause = true;
            playCityTimeline.stop();
        }
        isPlayButton = !isPlayButton;
        playButton.setGraphic(new ImageView(buttonImage));
    }

    private JFXButton createRaisedJFXButtonWithIcon(String iconResourceLocation) {
        JFXButton button = createRaisedJFXButtonWithText("");
        Image buttonImage = new Image(getClass().getResourceAsStream(iconResourceLocation));

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
        tickButton.setDisable(true);

        //if the last action was pause, we started a task but didn't draw it yet. finish that task and draw it.
        if(lastTickActionWasPause) {
            lastTickActionWasPause = false;
            new Thread(() -> {
                getTickTask();
                Platform.runLater(() -> {
                    redrawMap();
                    tickButton.setDisable(false);
                });
            }).start();
            return;
        }
        try {
            Thread thread = new Thread(() -> {
                performMapTicks(tickButtonTickInterval);
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

    }

    private void performMapTicks(int numberOfTicks) {
//        int numberOfTicks = 10;
        citySimulator.tick(numberOfTicks);
        cityMap = citySimulator.saveMap();
        currentTicks += numberOfTicks;
    }

    private void redrawMap() {
        timeText.setText(getTimeString());
        draw(cityMap);
    }


    /**
     * @author: shay & Orel (work in threads)
     * @date: 20/1/18
     * gets the logical object and draw all the corcles and lines from it
     * @param cityMap - the citymap object we want to draw
     * @return DrawMapController
     */
    public DrawMapController draw(CityMap cityMap) {
        circles = new HashSet<>();
        lines = new HashSet<>();
        new Thread(() -> {
            Set<CityRoad> cityRoads = cityMap.getAllRoads();
            Set<CityTrafficLight> cityTrafficLights = cityMap.getAllTrafficLights();
            MapGraphics map = new MapGraphics(cityTrafficLights,cityRoads);
            Platform.runLater(() -> {
                extractMapToBorderPane(map);
            });
        }).start();
        return this;
    }

    /**
     * @author: shay
     * @date: 20/1/18
     * gets the logical object and draw all the corcles and lines from it
     * @param cityMap - the citymap object we want to draw
     * @return DrawMapController
     */
    public DrawMapController drawNow(CityMap cityMap) {
        circles = new HashSet<>();
        lines = new HashSet<>();

        Set<CityRoad> cityRoads = cityMap.getAllRoads();
        Set<CityTrafficLight> cityTrafficLights = cityMap.getAllTrafficLights();
        MapGraphics map = new MapGraphics(cityTrafficLights,cityRoads);

        extractMapToBorderPane(map);

        return this;
    }

    private void extractMapToBorderPane(MapGraphics map) {
        borderPane.getChildren().clear();
        for (Pair<Line,String> line: map.getLines()) {
            borderPane.getChildren().add(line.getKey());
        }
        // add all circles
        for (Pair<Circle,String> circle: map.getCircles()) {
            borderPane.getChildren().add(circle.getKey());
        }
    }

}