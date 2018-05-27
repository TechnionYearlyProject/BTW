package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import il.ac.technion.cs.yp.btw.citysimulation.*;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.geojson.GeoJsonParserImpl;
import il.ac.technion.cs.yp.btw.navigation.NaiveNavigationManager;
import il.ac.technion.cs.yp.btw.navigation.NavigationManager;
import il.ac.technion.cs.yp.btw.navigation.PathNotFoundException;
import il.ac.technion.cs.yp.btw.statistics.NaiveStatisticsCalculator;
import il.ac.technion.cs.yp.btw.statistics.StatisticsCalculator;
import il.ac.technion.cs.yp.btw.trafficlights.NaiveTrafficLightManager;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by orel on 26/05/18.
 */
public class ChooseSimulationController extends SwitchToMapController implements Initializable {

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
    }

    private void StartClicked(ActionEvent actionEvent) {
        start_button.setDisable(true);
        loadSpinner.setVisible(true);
        new Thread(() -> {
            JFXRadioButton selectedTrafficManagerRadio = (JFXRadioButton) trafficLight_toggle.getSelectedToggle();
            TrafficLightManager trafficManager;
            if (selectedTrafficManagerRadio.equals(naiveTrafficLight_radio)) {
                trafficManager = new NaiveTrafficLightManager();
            } else if(selectedTrafficManagerRadio.equals(simpleTrafficLight_radio)) {
                //TODO: should be SimpleTrafficLightManager
                trafficManager = new NaiveTrafficLightManager();
            }else{ //can't happen, radio only has these two buttons
                return;
            }

            JFXRadioButton selectedNavigationManagerRadio = (JFXRadioButton) navigation_toggle.getSelectedToggle();
            NavigationManager navigationManager;
            if (selectedNavigationManagerRadio.equals(naiveNavigation_radio)) {
                navigationManager = new NaiveNavigationManager(mapDatabase);
            } else if(selectedNavigationManagerRadio.equals(statisticsNavigation_radio)) {
                //TODO: should be StatisticsNavigationManager
                navigationManager = new NaiveNavigationManager(mapDatabase);
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
                    Platform.runLater(() -> {
                        e.printStackTrace();
                        showErrorDialog(e.getMessage());
                        start_button.setDisable(false);
                        loadSpinner.setVisible(false);
                    });
                    return;
                }
            }
            trafficManager.insertCrossroads(mapDatabase.getAllCrossroads()
                    .stream()
                    .map(citySimulator::getRealCrossroad)
                    .collect(Collectors.toSet()));
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

    public void attachButtonClicked(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(anchor.getScene().getWindow());
        chooseVehicleFileTextField.setText(selectedFile.getAbsolutePath());
    }

}
