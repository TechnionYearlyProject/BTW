package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulator;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulatorImpl;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.db.BTWDataBaseImpl;
import il.ac.technion.cs.yp.btw.navigation.NaiveNavigationManager;
import il.ac.technion.cs.yp.btw.navigation.NavigationManager;
import il.ac.technion.cs.yp.btw.statistics.NaiveStatisticsCalculator;
import il.ac.technion.cs.yp.btw.statistics.StatisticsCalculator;
import il.ac.technion.cs.yp.btw.trafficlights.NaiveTrafficLightManager;
import il.ac.technion.cs.yp.btw.trafficlights.TrafficLightManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class ChooseRunningConfigController extends SwitchToMapController implements Initializable {

    final static Logger logger = Logger.getLogger("ChooseRunningConfigController");

    @FXML
    private HBox titleHBox, centerContent;
    @FXML
    private StackPane lineSeperator, learningText;
    @FXML private JFXTextField chooseMapTextBox;
    @FXML private JFXButton learning_mode_button, compare_button, live_simulation_button, prepare_button;
    @FXML private JFXSpinner loadSpinner;
    private Set<String> tablesNames;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDynamicPosition();
        new Thread(() -> {
            BTWDataBase dbForTables = new BTWDataBaseImpl("dbForTables");   // Shay - TO DO: separate tables names from the constructor
            tablesNames = dbForTables.getTablesNames();
            Platform.runLater(() -> {
                if (tablesNames != null) {
                    logger.debug("Tables names are loaded, should bind to auto complete");
                    TextFields.bindAutoCompletion(chooseMapTextBox, tablesNames);
                }
            });
        }).start();
        logger.debug("Home Screen Initialized");
    }

    private void initDynamicPosition() {
        titleHBox.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                // scene is set for the first time. Now its the time to listen stage changes.
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow == null && newWindow != null) {
                        // stage is set. now is the right time to do whatever we need to the stage in the controller.
                        Stage stage = (Stage) newWindow;
                        titleHBox.translateXProperty()
                                .bind(stage.widthProperty().subtract(titleHBox.widthProperty())
                                        .divide(2));
                        centerContent.translateXProperty()
                                .bind(stage.widthProperty().subtract(centerContent.widthProperty())
                                        .divide(2));
                        learningText.translateXProperty()
                                .bind(stage.widthProperty().subtract(learningText.widthProperty())
                                        .divide(2));
                        loadSpinner.translateXProperty()
                                .bind(stage.widthProperty().subtract(loadSpinner.widthProperty())
                                        .divide(2));
                    }
                });
            }
        });
//        AnchorPane.setBottomAnchor(learningText, 40.0);
//        AnchorPane.setLeftAnchor(learningText, 80.0);
    }

    private boolean mapNameIsInDatabase() {
        String mapName = chooseMapTextBox.getText();
        if(tablesNames == null) {
            showErrorDialog("No connection to Database yet");
            return false;
        }
        if(tablesNames.contains(mapName)) return true;
        else {
            showErrorDialog("Map name isn't in Database");
            return false;
        }
    }

    public void learningButtonClicked(ActionEvent actionEvent) {
        if(mapNameIsInDatabase()) {
            switchToVerifyAndThenTo(DrawMapController.AcceptAction.LearningMode, actionEvent);
        }
    }

    public void compareButtonClicked(ActionEvent actionEvent) {
        if(mapNameIsInDatabase()) {
            switchToVerifyAndThenTo(DrawMapController.AcceptAction.ChooseMultiSimulation, actionEvent);
        }
    }

    private void switchToVerifyAndThenTo(DrawMapController.AcceptAction acceptAction, ActionEvent actionEvent) {
        disableAllButtons();
        new Thread(() -> {
            BTWDataBase dataBase = new BTWDataBaseImpl(chooseMapTextBox.getText());
            dataBase.loadMap();
            NavigationManager navigationManager = new NaiveNavigationManager(dataBase);
            TrafficLightManager trafficLightManager = new NaiveTrafficLightManager();
//            StatisticsCalculator calculator = new NaiveStatisticsCalculator(dataBase);
            CitySimulator citySimulator = new CitySimulatorImpl(dataBase, navigationManager, trafficLightManager);
            trafficLightManager.insertCrossroads(dataBase.getAllCrossroads()
                    .stream()
                    .map(citySimulator::getRealCrossroad)
                    .collect(Collectors.toSet()));
            Platform.runLater(() -> switchScreensToMap(actionEvent, citySimulator, dataBase, true, acceptAction));
        }).start();
    }

    private void disableAllButtons() {
        learning_mode_button.setDisable(true);
        compare_button.setDisable(true);
        live_simulation_button.setDisable(true);
        prepare_button.setDisable(true);
        loadSpinner.setVisible(true);
        chooseMapTextBox.setDisable(true);
    }

    private void enableAllButtons() {
        learning_mode_button.setDisable(false);
        compare_button.setDisable(false);
        live_simulation_button.setDisable(false);
        prepare_button.setDisable(false);
        loadSpinner.setVisible(false);
    }

    public void liveSimulationButtonClicked(ActionEvent actionEvent) {
        switchScreens(actionEvent,  "/fxml/home_layout.fxml");
    }

    public void prepareButtonClicked(ActionEvent actionEvent) {
        switchScreens(actionEvent, "/fxml/prepare_configs.fxml");
    }
}
