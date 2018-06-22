package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.*;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulator;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulatorImpl;
import il.ac.technion.cs.yp.btw.citysimulation.JsonVehiclesParser;
import il.ac.technion.cs.yp.btw.citysimulation.VehicleEntry;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.evaluation.DumbEvaluator;
import il.ac.technion.cs.yp.btw.evaluation.Evaluator;
import il.ac.technion.cs.yp.btw.evaluation.EvaluatorImpl;
import il.ac.technion.cs.yp.btw.navigation.NaiveNavigationManager;
import il.ac.technion.cs.yp.btw.navigation.NavigationManager;
import il.ac.technion.cs.yp.btw.navigation.StatisticalNavigationManager;
import il.ac.technion.cs.yp.btw.statistics.NaiveStatisticsCalculator;
import il.ac.technion.cs.yp.btw.statistics.StatisticsCalculator;
import il.ac.technion.cs.yp.btw.trafficlights.NaiveTrafficLightManager;
import il.ac.technion.cs.yp.btw.trafficlights.SimpleTrafficLightManager;
import il.ac.technion.cs.yp.btw.trafficlights.SmartTrafficLightManager;
import il.ac.technion.cs.yp.btw.trafficlights.TrafficLightManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by orel on 22/06/18.
 */
public class ChooseMultiSimulationController  extends SwitchToMapController implements Initializable {

    final static Logger logger = Logger.getLogger("ChooseMultiSimulationController");


    private BTWDataBase mapDatabase;

    @FXML
    private HBox titleHBox, centerContent;
    @FXML
    private JFXButton back_button, attachButton, start_button;
    @FXML
    private
    JFXRadioButton naiveTrafficLight_radio1,simpleTrafficLight_radio1, naiveNavigation_radio1, statisticsNavigation_radio1, smartTrafficLight_radio1,
            naiveTrafficLight_radio2,simpleTrafficLight_radio2, naiveNavigation_radio2, statisticsNavigation_radio2, smartTrafficLight_radio2;
    @FXML
    private ToggleGroup navigation_toggle1, trafficLight_toggle1, navigation_toggle2, trafficLight_toggle2;
    @FXML private JFXSpinner loadSpinner;
    @FXML
    JFXTextField chooseVehicleFileTextField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Scene scene = new Scene((Parent)anchor, BTW.stage.getWidth(), BTW.stage.getHeight());
        BTW.stage.setScene(scene);
        back_button.setOnAction(this::BackClicked);
//        start_button.setOnAction(this::StartClicked);
        initCenterPanes();
        initRadioButtons();
        Image buttonImage = new Image(getClass().getResourceAsStream("/icons8-attach-30.png"));
        attachButton.setGraphic(new ImageView(buttonImage));
        attachButton.setOnAction(this::attachButtonClicked);
    }


    private TrafficLightManager getTrafficLightManager(ToggleGroup trafficLight_toggle,
                                    JFXRadioButton naiveTrafficLight_radio, JFXRadioButton simpleTrafficLight_radio,
                                                                           JFXRadioButton smartTrafficLight_radio) {
        TrafficLightManager trafficManager = null;
        JFXRadioButton selectedTrafficManagerRadio = (JFXRadioButton) trafficLight_toggle.getSelectedToggle();
        if (selectedTrafficManagerRadio.equals(naiveTrafficLight_radio)) {
            logger.debug("Setting up NaiveTrafficLightManager");
            trafficManager = new NaiveTrafficLightManager();
        } else if (selectedTrafficManagerRadio.equals(simpleTrafficLight_radio)) {
            logger.debug("Setting up SimpleTrafficLightManager");
            trafficManager = new SimpleTrafficLightManager();
        } else if (selectedTrafficManagerRadio.equals(smartTrafficLight_radio)) {
            logger.debug("Setting up SmartTrafficLightManager");
            trafficManager = new SmartTrafficLightManager();
        }
        return trafficManager;
    }

    private NavigationManager getNavigationManager(ToggleGroup navigation_toggle, JFXRadioButton naiveNavigation_radio,
                                                  JFXRadioButton statisticsNavigation_radio) {
        NavigationManager navigationManager = null;
        JFXRadioButton selectedNavigationManagerRadio = (JFXRadioButton) navigation_toggle.getSelectedToggle();
        if (selectedNavigationManagerRadio.equals(naiveNavigation_radio)) {
            logger.debug("Setting up NaiveNavigationManager");
            navigationManager = new NaiveNavigationManager(mapDatabase);
        } else if(selectedNavigationManagerRadio.equals(statisticsNavigation_radio)) {
            logger.debug("Setting up StatisticalNavigationManager");
            navigationManager = new StatisticalNavigationManager(mapDatabase);
        }
        return navigationManager;
    }

    private void StartClicked(ActionEvent actionEvent) {
        logger.debug("Start button clicked");
        disableButtons();
        new Thread(() -> {
            TrafficLightManager trafficManager1, trafficManager2;
            NavigationManager navigationManager1, navigationManager2;
            trafficManager1 = getTrafficLightManager(trafficLight_toggle1, naiveTrafficLight_radio1,
                    simpleTrafficLight_radio1, smartTrafficLight_radio1);
            trafficManager2 = getTrafficLightManager(trafficLight_toggle2, naiveTrafficLight_radio2,
                    simpleTrafficLight_radio2, smartTrafficLight_radio2);
            navigationManager1 = getNavigationManager(navigation_toggle1, naiveNavigation_radio1,
                    statisticsNavigation_radio1);
            navigationManager2 = getNavigationManager(navigation_toggle2, naiveNavigation_radio2,
                    statisticsNavigation_radio2);


            StatisticsCalculator calculator1 = new NaiveStatisticsCalculator(mapDatabase);
            CitySimulator citySimulator1 = new CitySimulatorImpl(mapDatabase, navigationManager1, trafficManager1, calculator1, new DumbEvaluator());
            if(!chooseVehicleFileTextField.getText().isEmpty()) {
                URL url;
                JsonVehiclesParser parser = new JsonVehiclesParser();
                List<VehicleEntry> entries;
                try {
                    File file = new File(chooseVehicleFileTextField.getText());
                    url = file.toURI().toURL();
                    entries = parser.parseVehiclesFromFile(url);
                    citySimulator1.addVehiclesFromVehicleEntriesList(entries);
                } catch (Exception e) {
                    e.printStackTrace();
                    Platform.runLater(() -> {
//                        e.printStackTrace();
                        showErrorDialog(e.getMessage());
                        enableButtons();
                        logger.debug("When trying to parse the string we got an exception of type " + e.getClass().toString());
                    });
                    return;
                }
            }
            trafficManager1.insertCrossroads(mapDatabase.getAllCrossroads()
                    .stream()
                    .map(citySimulator1::getRealCrossroad)
                    .collect(Collectors.toSet()));
            logger.debug("Done making the simulation - moving to map screen");
//            Platform.runLater(() -> switchScreensToMap(actionEvent, citySimulator, mapDatabase, false));
        }).start();
    }

    private void attachButtonClicked(ActionEvent actionEvent) {
        logger.debug("Attach vehicles file clicked");
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(anchor.getScene().getWindow());
        if(selectedFile != null)
            chooseVehicleFileTextField.setText(selectedFile.getAbsolutePath());
    }

    private void initRadioButtons() {
        naiveNavigation_radio1.setToggleGroup(navigation_toggle1);
        statisticsNavigation_radio1.setToggleGroup(navigation_toggle1);
        naiveTrafficLight_radio1.setToggleGroup(trafficLight_toggle1);
        simpleTrafficLight_radio1.setToggleGroup(trafficLight_toggle1);
        smartTrafficLight_radio1.setToggleGroup(trafficLight_toggle1);
        naiveTrafficLight_radio1.setSelected(true);
        naiveNavigation_radio1.setSelected(true);

        naiveNavigation_radio2.setToggleGroup(navigation_toggle2);
        statisticsNavigation_radio2.setToggleGroup(navigation_toggle2);
        naiveTrafficLight_radio2.setToggleGroup(trafficLight_toggle2);
        simpleTrafficLight_radio2.setToggleGroup(trafficLight_toggle2);
        smartTrafficLight_radio2.setToggleGroup(trafficLight_toggle2);
        naiveTrafficLight_radio2.setSelected(true);
        naiveNavigation_radio2.setSelected(true);
    }

    private void BackClicked(ActionEvent actionEvent) {
        switchScreens(actionEvent, "/fxml/choose_running_config.fxml");
    }

    public void initCenterPanes() {
        titleHBox.translateXProperty()
                .bind(BTW.stage.widthProperty().subtract(titleHBox.widthProperty())
                        .divide(2));
        AnchorPane.setTopAnchor(titleHBox, 40.0);
        AnchorPane.setRightAnchor(back_button, 20.0);
        AnchorPane.setTopAnchor(back_button, 60.0);

        centerContent.translateXProperty()
                .bind(BTW.stage.widthProperty().subtract(centerContent.widthProperty())
                        .divide(2));

        AnchorPane.setRightAnchor(start_button, 30.0);
        AnchorPane.setBottomAnchor(start_button, 30.0);
    }

    public void initMapDatabase(BTWDataBase mapDatabase) {
        this.mapDatabase = mapDatabase;
    }

    private void disableButtons() {
        start_button.setDisable(true);
        back_button.setDisable(true);
        loadSpinner.setVisible(true);
    }

    private void enableButtons() {
        start_button.setDisable(false);
        back_button.setDisable(false);
        loadSpinner.setVisible(false);
    }

}
