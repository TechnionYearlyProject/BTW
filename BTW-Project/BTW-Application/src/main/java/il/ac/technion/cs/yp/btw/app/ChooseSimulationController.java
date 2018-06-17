package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import il.ac.technion.cs.yp.btw.citysimulation.*;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.navigation.NaiveNavigationManager;
import il.ac.technion.cs.yp.btw.navigation.NavigationManager;
import il.ac.technion.cs.yp.btw.navigation.StatisticalNavigationManager;
import il.ac.technion.cs.yp.btw.statistics.NaiveStatisticsCalculator;
import il.ac.technion.cs.yp.btw.statistics.StatisticsCalculator;
import il.ac.technion.cs.yp.btw.trafficlights.NaiveTrafficLightManager;
import il.ac.technion.cs.yp.btw.trafficlights.SimpleTrafficLightManager;
import il.ac.technion.cs.yp.btw.trafficlights.TrafficLightManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by orel on 26/05/18.
 */
public class ChooseSimulationController extends SwitchToMapController implements Initializable {

    final static Logger logger = Logger.getLogger("ChooseSimulationController");

    BTWDataBase mapDatabase;
    Stage stage;
    @FXML
    private JFXButton back_button, attachButton, start_button;
    @FXML
    private
    JFXRadioButton naiveTrafficLight_radio,simpleTrafficLight_radio, naiveNavigation_radio, statisticsNavigation_radio;
    @FXML
    private ToggleGroup navigation_toggle, trafficLight_toggle;
    @FXML
    private JFXSpinner loadSpinner;
    @FXML JFXTextField chooseVehicleFileTextField;
    @FXML StackPane title_stack_pane;
    @FXML HBox title_hbox, centerHBox;

    void initMapDatabase(BTWDataBase db) {
        mapDatabase = db;
    }
    public void initStage(Stage s) {
        stage = s;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Scene scene = new Scene((Parent)anchor, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        back_button.setOnAction(this::BackClicked);
        start_button.setOnAction(this::StartClicked);
        initRadioButtons();
        Image buttonImage = new Image(getClass().getResourceAsStream("/icons8-attach-30.png"));
        attachButton.setGraphic(new ImageView(buttonImage));
        attachButton.setOnAction(this::attachButtonClicked);
        initCenterPanes();
        logger.debug("Initialized controller");
    }

    public void initCenterPanes() {
        title_stack_pane.translateXProperty()
                .bind(stage.widthProperty().subtract(title_stack_pane.widthProperty())
                        .divide(2));
        AnchorPane.setTopAnchor(title_stack_pane, 50.0);
        title_hbox.setSpacing(10);
        AnchorPane.setRightAnchor(back_button, 20.0);
        AnchorPane.setTopAnchor(back_button, 60.0);

        centerHBox.translateXProperty()
                .bind(stage.widthProperty().subtract(centerHBox.widthProperty())
                        .divide(2));

        AnchorPane.setRightAnchor(start_button, 30.0);
        AnchorPane.setBottomAnchor(start_button, 30.0);
    }

    private void StartClicked(ActionEvent actionEvent) {
        logger.debug("Start button clicked");
        start_button.setDisable(true);
        loadSpinner.setVisible(true);
        new Thread(() -> {
            JFXRadioButton selectedTrafficManagerRadio = (JFXRadioButton) trafficLight_toggle.getSelectedToggle();
            TrafficLightManager trafficManager;
            if (selectedTrafficManagerRadio.equals(naiveTrafficLight_radio)) {
                logger.debug("Setting up NaiveTrafficLightManager");
                trafficManager = new NaiveTrafficLightManager();
            } else if(selectedTrafficManagerRadio.equals(simpleTrafficLight_radio)) {
                logger.debug("Setting up SimpleTrafficLightManager");
                trafficManager = new SimpleTrafficLightManager();
            }else{ //can't happen, radio only has these two buttons
                return;
            }

            JFXRadioButton selectedNavigationManagerRadio = (JFXRadioButton) navigation_toggle.getSelectedToggle();
            NavigationManager navigationManager;
            if (selectedNavigationManagerRadio.equals(naiveNavigation_radio)) {
                logger.debug("Setting up NaiveNavigationManager");
                navigationManager = new NaiveNavigationManager(mapDatabase);
            } else if(selectedNavigationManagerRadio.equals(statisticsNavigation_radio)) {
//                navigationManager = new NaiveNavigationManager(mapDatabase);
                logger.debug("Setting up StatisticalNavigationManager");
                navigationManager = new StatisticalNavigationManager(mapDatabase);
            }else{ //can't happen, radio only has these two buttons
                return;
            }

            StatisticsCalculator calculator = new NaiveStatisticsCalculator(mapDatabase);
            CitySimulator citySimulator = new CitySimulatorImpl(mapDatabase, navigationManager, trafficManager, calculator);
            if(!chooseVehicleFileTextField.getText().isEmpty()) {
                URL url;
                JsonVehiclesParser parser = new JsonVehiclesParser();
                List<VehicleEntry> entries;
                try {
                    File file = new File(chooseVehicleFileTextField.getText());
                    url = file.toURI().toURL();
                    entries = parser.parseVehiclesFromFile(url);
                    citySimulator.addVehiclesFromVehicleEntriesList(entries);
                } catch (Exception e) {
                    e.printStackTrace();
                    Platform.runLater(() -> {
//                        e.printStackTrace();
                        showErrorDialog(e.getMessage());
                        start_button.setDisable(false);
                        loadSpinner.setVisible(false);
                        logger.debug("When trying to parse the string we got an exception of type " + e.getClass().toString());
                    });
                    return;
                }
            }
            trafficManager.insertCrossroads(mapDatabase.getAllCrossroads()
                    .stream()
                    .map(citySimulator::getRealCrossroad)
                    .collect(Collectors.toSet()));
            logger.debug("Done making the simulation - moving to map screen");
            Platform.runLater(() -> switchScreensToMap(actionEvent, citySimulator, mapDatabase, false));
        }).start();

    }

    public void initRadioButtons() {
        naiveNavigation_radio.setToggleGroup(navigation_toggle);
        statisticsNavigation_radio.setToggleGroup(navigation_toggle);
        naiveTrafficLight_radio.setToggleGroup(trafficLight_toggle);
        simpleTrafficLight_radio.setToggleGroup(trafficLight_toggle);
        naiveTrafficLight_radio.setSelected(true);
        naiveNavigation_radio.setSelected(true);
    }

    private void BackClicked(ActionEvent event) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            String fxmlLocation = "/fxml/home_layout.fxml";
            URL resource = getClass().getResource(fxmlLocation);
            transitionAnimationAndSwitch(fxmlLocation, stageTheEventSourceNodeBelongs, resource, anchor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void attachButtonClicked(ActionEvent actionEvent) {
        logger.debug("Attach vehicles file clicked");
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(anchor.getScene().getWindow());
        if(selectedFile != null)
            chooseVehicleFileTextField.setText(selectedFile.getAbsolutePath());
    }

}
