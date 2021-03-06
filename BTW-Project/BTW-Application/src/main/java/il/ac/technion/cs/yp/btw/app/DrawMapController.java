package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import il.ac.technion.cs.yp.btw.citysimulation.*;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.navigation.PathNotFoundException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
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
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReentrantLock;

import static javafx.application.Application.launch;

/**@author: Orel
 * @date: 20/1/18
 * all methods that don't specify an other author are by Orel
 */
public class DrawMapController extends ShowErrorController implements Initializable {

    final static Logger logger = Logger.getLogger("DrawMapController");

    @FXML
    AnchorPane anchor;
    @FXML
    BorderPane borderPane;
    @FXML private HBox titleHBox;
    Set<Circle> circles;
    Set<Line> lines = new HashSet<>();
    CityMap cityMap;
    CitySimulator citySimulator;
    Stage stage;
    JFXButton playButton, tickButton, addVehiclesButton, backButton;
    JFXTextField numOfVehiclesTextField;
    VBox addVehiclesHbox;
    BTWDataBase mapDatabase;
    ReentrantLock lock = new ReentrantLock();
    double addVechilesBoxSpacing = 40;
//    @FXML
    Text timeText;
    @FXML
    Text verifyMapText;
    @FXML
    ImageView mapImage;
    boolean isPlayButton;
    Timeline playCityTimeline;
    CompletableFuture<Boolean> tickTask;
    int currentTicks;
    boolean isVerifyMap = true;
    private AcceptAction acceptAction = AcceptAction.ChooseSimulation;
    private Thread randomVehiclesThread;
    double startDragX,startDragY, endDragX, endDragY;

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

    void initIsVerifyMap(boolean verify) {
        isVerifyMap = verify;
    }

    void initAcceptAction(AcceptAction acceptAction) {
        this.acceptAction = acceptAction;
    }

    void initMapDatabase(BTWDataBase db) {
        mapDatabase = db;
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
        logger.debug("Initializing DrawMapController");
        AnchorPane root = initScenePanesAndGetRoot();

        HBox playAndTickHbox = new HBox();
        playAndTickHbox.setPadding(new Insets(15, 12, 15, 12));
        playAndTickHbox.setSpacing(30);

        initPlayAndTickButtons();
        initBackButton();

        initVehiclesButton();
        initVehiclesTextField();
        initTimeText();

        //inserting the buttons ot the HBox
        ImageView img = new ImageView("/icons8-time-50.png");
        HBox timeBox = new HBox();
        timeBox.getChildren().addAll(img,timeText);
        playAndTickHbox.getChildren().addAll(tickButton, playButton, backButton);

        initPlayActionTimeline();

        addVehiclesHbox = new VBox();
        addVehiclesHbox.setPadding(new Insets(15, 12, 15, 12));
        addVehiclesHbox.setSpacing(30);

        addVehiclesHbox.getChildren().addAll(addVehiclesButton, numOfVehiclesTextField);

        //add the hboxes to the screen AnchorPane and anchor them
        if(isVerifyMap) {
            setupVerifyScreen(root);
        } else {
            StackPane stack = new StackPane();
            stack.getChildren().add(timeBox);
            stack.translateXProperty()
                    .bind(stage.widthProperty().subtract(stack.widthProperty())
                            .divide(2));

            root.getChildren().addAll(playAndTickHbox, addVehiclesHbox, stack);
            AnchorPane.setTopAnchor(stack, 55.0);

            AnchorPane.setTopAnchor(playAndTickHbox, 40.0);
            AnchorPane.setRightAnchor(playAndTickHbox, 5.0);

            AnchorPane.setTopAnchor(addVehiclesHbox, addVechilesBoxSpacing);
            AnchorPane.setLeftAnchor(addVehiclesHbox, 200.0);
        }

        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight(), Color.GREY);

        drawNow(cityMap);

        stage.show();
        stage.setScene(scene);

        stage.setResizable(true);
        stage.setMinHeight(700);
        stage.setMinWidth(1200);
        logger.debug("Done initializing");
    }

    /**@author: Orel
     * @date: 20/6/18
     */
    private void setupVerifyScreen(AnchorPane root) {
        logger.debug("This is a verify map screen: setting it up");
        JFXButton acceptButton = createRaisedJFXButtonWithText("Accept Map");
        acceptButton.setPrefSize(250, 50);
        acceptButton.setOnAction(event -> {
            logger.debug("Accept button clicked: User verified the map");

            if(acceptAction.equals(AcceptAction.SaveMap)) {
                acceptButton.setDisable(true);
                backButton.setDisable(true);
                VBox savingBox = new VBox();
                JFXSpinner spinner = new JFXSpinner();
                Text text = new Text("Saving...");
                text.setFont(Font.font(18.0));
                savingBox.getChildren().addAll(spinner, text);
                savingBox.setStyle("-fx-background-color: white;");
                savingBox.setAlignment(Pos.CENTER);
                savingBox.setPrefWidth(200.0);
                savingBox.setPrefHeight(200.0);
                savingBox.setSpacing(20.0);
                anchor.getChildren().add(savingBox);
                savingBox.translateXProperty()
                        .bind(stage.widthProperty().subtract(savingBox.widthProperty())
                                .divide(2));
                savingBox.translateYProperty()
                        .bind(stage.heightProperty().subtract(savingBox.heightProperty())
                                .divide(2));
                new Thread(() -> {
                    mapDatabase.saveMap();
                    Platform.runLater(() -> { //switch to preapre screen when done
                        URL resource = getClass().getResource("/fxml/prepare_configs.fxml");
                        Parent parent = null;
                        try {
                            parent = FXMLLoader.load(resource);
                            Scene scene = new Scene(parent);
                            BTW.stage.setScene(scene);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }).start();
            } else {
                FXMLLoader loader = new FXMLLoader();
                Object controller = new Object();
                switch (acceptAction) {
                    case ChooseSimulation:
                        loader = new FXMLLoader(getClass().getResource("/fxml/choose_simulation.fxml"));
                        ChooseSimulationController choose_controller = new ChooseSimulationController();
                        choose_controller.initMapDatabase(mapDatabase);
                        choose_controller.initStage(stage);
                        controller = choose_controller;
                        break;
                    case ChooseMultiSimulation:
                        loader = new FXMLLoader(getClass().getResource("/fxml/choose_multi_simulation.fxml"));
                        ChooseMultiSimulationController choose_multi_controller = new ChooseMultiSimulationController();
                        choose_multi_controller.initMapDatabase(mapDatabase);
                        controller = choose_multi_controller;
                        break;
                    case LearningMode:
                        loader = new FXMLLoader(getClass().getResource("/fxml/set_params_for_learning.fxml"));
                        SetParamsForLearningController learning_controller = new SetParamsForLearningController();
                        learning_controller.initMapDatabase(mapDatabase);
                        controller = learning_controller;
                        break;
                }
                loader.setController(controller);
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        });
        verifyMapText.setVisible(true);
        mapImage.setVisible(true);
        titleHBox.translateXProperty()
                .bind(stage.widthProperty().subtract(titleHBox.widthProperty())
                        .divide(2));
        root.getChildren().addAll(acceptButton, backButton);
        AnchorPane.setBottomAnchor(acceptButton, 40.0);
        AnchorPane.setRightAnchor(acceptButton, 40.0);
        AnchorPane.setTopAnchor(backButton, 60.0);
        AnchorPane.setRightAnchor(backButton, 50.0);

    }

    private void initTimeText() {
        timeText = new Text("00:00:00");
        timeText.setStyle("-fx-font: 52 arial;");
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
        backButton = createRaisedJFXButtonWithIcon("/icons8-home-50.png");
        backButton.setOnAction(this::backButtonClicked);
        backButton.setMaxWidth(60);
        backButton.setMaxHeight(60);
        backButton.setStyle("-fx-background-color: transparent");
    }

    /**@author: Orel
     * @date: 3/4/18
     */
    private void backButtonClicked(ActionEvent event) {
        logger.debug("Back clicked - going back to home screen");
        playCityTimeline.stop();
        if(randomVehiclesThread != null) randomVehiclesThread.stop();
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        URL resource = getClass().getResource("/fxml/choose_running_config.fxml");
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
        AnchorPane root = anchor;

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



        /**@author: Anat
         * @date: 6/5/18
         */

        root.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
        /* drag was detected, start a drag-and-drop gesture*/
        /* allow any transfer mode */
                Dragboard db = root.startDragAndDrop(TransferMode.ANY);

        /* Put a string on a dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString("BLABLABLABLA");
                db.setContent(content);

                startDragX = event.getSceneX();
                startDragY = event.getSceneY();

                event.consume();
            }
        });

        /**@author: Anat
         * @date: 6/5/18
         */
        root.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
        /* data is dragged over the target */
                if (true) {
            /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        /**@author: Anat
         * @date: 6/5/18
         */
        root.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
        /* data dropped */
        /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;

                double sourceX = borderPane.getLayoutX();
                double sourceY = borderPane.getLayoutY();
                endDragX= event.getSceneX();
                endDragY = event.getSceneY();

                if((startDragX<endDragX)&&(startDragY<endDragY)){
                    borderPane.setLayoutX(borderPane.getLayoutX()+(endDragX-startDragX)*2);
                    borderPane.setLayoutY(borderPane.getLayoutY()+(endDragY-startDragY)*2);
                }

                else if((startDragX>endDragX)&&(startDragY<endDragY)){
                    borderPane.setLayoutX(borderPane.getLayoutX()-(startDragX-endDragX)*2);
                    borderPane.setLayoutY(borderPane.getLayoutY()+(endDragY-startDragY)*2);
                }

                else if((startDragX<endDragX)&&(startDragY>endDragY)){
                    borderPane.setLayoutX(borderPane.getLayoutX()+(endDragX-startDragX)*2);
                    borderPane.setLayoutY(borderPane.getLayoutY()-(startDragY-endDragY)*2);
                }

                else {
                    borderPane.setLayoutX(borderPane.getLayoutX()-(startDragX-endDragX)*2);
                    borderPane.setLayoutY(borderPane.getLayoutY()-(startDragY-endDragY)*2);
                }

                event.setDropCompleted(success);

                event.consume();
            }
        });

        return root;
    }
    /**@author: Orel
     * @date: 20/1/18
     */
    private void initVehiclesTextField() {
        numOfVehiclesTextField = new JFXTextField();
        numOfVehiclesTextField.setPromptText("Vehicles amount (1 - 200)");
        numOfVehiclesTextField.setFont(Font.font("Verdana", FontWeight.BOLD,13.5));
        numOfVehiclesTextField.setPrefSize(200, 50);
        numOfVehiclesTextField.setVisible(false);
    }


    /**@author: Orel
     * @date: 20/1/18
     */
    private void initVehiclesButton() {
        addVehiclesButton = createRaisedJFXButtonWithText("Choose Vehicles To Add");
//        addVehiclesButton = createRaisedJFXButtonWithText("Add Vehicles");
        addVehiclesButton.setPrefSize(200, 50);
        addVehiclesButton.setOnAction(event -> {
            boolean textFieldWasVisible = numOfVehiclesTextField.isVisible();
            if(!textFieldWasVisible) {
                numOfVehiclesTextField.setVisible(true);
                //numOfVehiclesTextField.setBackground();
//                numOfVehiclesTextField.setStyle("-fx-text-inner-color: red;");
                addVehiclesButton.setText("Add Vehicles");
                AnchorPane.setTopAnchor(addVehiclesHbox, 5.0);
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
                AnchorPane.setTopAnchor(addVehiclesHbox, addVechilesBoxSpacing);
                addVehiclesButton.setText("Choose Vehicles To Add");
                addRandomVehiclesToSimulation(numOfVehicles, addVehiclesButton);
            }
        });
    }

    /**@author: Orel
     * @date: 20/1/18
     */
    private void addRandomVehiclesToSimulation(int numOfVehicles, JFXButton addVehiclesButton) {
        //TODO: if we want to truly fix the concurent exception bug:
        // i need to disable all buttons (including current play action) until
        // adding vehicle action is complete. This may stall the user experience
        String previousText = addVehiclesButton.getText();
        addVehiclesButton.setText("Adding...");
        addVehiclesButton.setDisable(true);

        randomVehiclesThread = new Thread(() -> {
            try {
                citySimulator.addRandomVehicles(numOfVehicles);
            } catch (PathNotFoundException e) {
                e.printStackTrace();
            }
            cityMap = citySimulator.saveMap();
            Platform.runLater(() -> {
                    redrawMap();
                    addVehiclesButton.setDisable(false);
                    addVehiclesButton.setText(previousText);
                    randomVehiclesThread = null;
            });
        });
        randomVehiclesThread.start();
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
            logger.debug("Play button was clicked");
            buttonImage = new Image(getClass().getResourceAsStream("/icons8-pause-50.png"));
            tickButton.setDisable(true);
            //only fetch a new task if the previous task didn't finish
            if(!lastTickActionWasPause) resetTickTask();
            lastTickActionWasPause = false;
            playCityTimeline.play();
        } else {
            logger.debug("Pause button was clicked");
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
        button.setPrefSize(150, 50);
        button.setRipplerFill(Color.BLACK);
        return button;
    }

    private void tickButtonClicked(ActionEvent event) {
        logger.debug("Tick button was clicked");
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
            Thread thread = new Thread() {
                @Override
                public synchronized void run() {
                    try {
                        performMapTicks(tickButtonTickInterval);
                    } catch (Exception e) {
                        //TODO: try to find better solution
//                        logger.debug("Tick button thread caught exception of type " + e.getClass().toString());
                    }

                    Platform.runLater(() -> {
                        redrawMap();
                        tickButton.setDisable(false);
                    });
                }
            };
            thread.start();
        } catch (Exception e) {
            tickButton.setDisable(false);
            System.out.println("tick button caught exception " + e.getMessage());
        }

    }

    private void performMapTicks(int numberOfTicks) {
//        int numberOfTicks = 10;
        lock.lock();
        try {
            currentTicks += numberOfTicks;
            citySimulator.tick(numberOfTicks);
            cityMap = citySimulator.saveMap();
        } finally {
            lock.unlock();
        }
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

    public enum AcceptAction {
            ChooseSimulation, ChooseMultiSimulation, LearningMode, SaveMap;
    }

}