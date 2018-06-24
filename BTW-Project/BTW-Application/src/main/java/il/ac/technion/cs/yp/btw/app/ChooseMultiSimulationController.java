package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.*;
import il.ac.technion.cs.yp.btw.citysimulation.*;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.classes.BTWTime;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
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
import javafx.fxml.FXMLLoader;
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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**@author: Orel
 * @date: 20/6/18
 * all methods are by Orel
 */
public class ChooseMultiSimulationController extends SwitchToMapController implements Initializable {

    final static Logger logger = Logger.getLogger("ChooseMultiSimulationController");


    private BTWDataBase mapDatabase;

    @FXML
    private HBox titleHBox, centerContent;
    @FXML
    private JFXButton back_button, attachButton, start_button;
    @FXML
    private
    JFXRadioButton naiveTrafficLight_radio1,simpleTrafficLight_radio1, naiveNavigation_radio1, statisticsNavigation_radio1, smartTrafficLight_radio1,
            naiveTrafficLight_radio2,simpleTrafficLight_radio2, naiveNavigation_radio2, statisticsNavigation_radio2, smartTrafficLight_radio2,
            vehicleFileRadio, generateVehiclesRadio;
    @FXML
    private ToggleGroup navigation_toggle1, trafficLight_toggle1, navigation_toggle2, trafficLight_toggle2, vehiclesToggle;
    @FXML private JFXSpinner loadSpinner;
    @FXML
    JFXTextField chooseVehicleFileTextField, numOfVehiclesTextField;

    private String simType1, simType2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Scene scene = new Scene((Parent)anchor, BTW.stage.getWidth(), BTW.stage.getHeight());
        BTW.stage.setScene(scene);
        back_button.setOnAction(this::BackClicked);
        start_button.setOnAction(this::StartClicked);
        initCenterPanes();
        initRadioButtons();
        Image buttonImage = new Image(getClass().getResourceAsStream("/icons8-attach-30.png"));
        attachButton.setGraphic(new ImageView(buttonImage));
        attachButton.setOnAction(this::attachButtonClicked);
        logger.debug("Initialized ChooseMultiSimulationController");
    }

    private void switchToStatisticsCompare(ActionEvent actionEvent, Evaluator eval1, Evaluator eval2,
                                           List<VehicleEntry> list, String simulationType1, String simulationType2) {
        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource("/fxml/statistics_comparison.fxml"));
        StatisticsComparisonController controller = new StatisticsComparisonController();
        controller.initParams(eval1, eval2, list, simulationType1, simulationType2, mapDatabase);
        loader.setController(controller);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private TrafficLightManager getTrafficLightManager(ToggleGroup trafficLight_toggle,
                                    JFXRadioButton naiveTrafficLight_radio, JFXRadioButton simpleTrafficLight_radio,
                                                                           JFXRadioButton smartTrafficLight_radio, StringBuilder simType) {
        TrafficLightManager trafficManager = null;
        JFXRadioButton selectedTrafficManagerRadio = (JFXRadioButton) trafficLight_toggle.getSelectedToggle();
        if (selectedTrafficManagerRadio.equals(naiveTrafficLight_radio)) {
            logger.debug("Setting up NaiveTrafficLightManager");
            trafficManager = new NaiveTrafficLightManager();
            simType.append("Naive TL + ");
        } else if (selectedTrafficManagerRadio.equals(simpleTrafficLight_radio)) {
            logger.debug("Setting up SimpleTrafficLightManager");
            trafficManager = new SimpleTrafficLightManager();
            simType.append("Simple TL + ");
        } else if (selectedTrafficManagerRadio.equals(smartTrafficLight_radio)) {
            logger.debug("Setting up SmartTrafficLightManager");
            trafficManager = new SmartTrafficLightManager();
            simType.append("Smart TL + ");
        }
        return trafficManager;
    }

    private NavigationManager getNavigationManager(ToggleGroup navigation_toggle, JFXRadioButton naiveNavigation_radio,
                                                  JFXRadioButton statisticsNavigation_radio, StringBuilder simType) {
        NavigationManager navigationManager = null;
        JFXRadioButton selectedNavigationManagerRadio = (JFXRadioButton) navigation_toggle.getSelectedToggle();
        if (selectedNavigationManagerRadio.equals(naiveNavigation_radio)) {
            logger.debug("Setting up NaiveNavigationManager");
            navigationManager = new NaiveNavigationManager(mapDatabase);
            simType.append("Naive Nav.");
        } else if(selectedNavigationManagerRadio.equals(statisticsNavigation_radio)) {
            logger.debug("Setting up StatisticalNavigationManager");
            navigationManager = new StatisticalNavigationManager(mapDatabase);
            simType.append("Statistics Nav.");
        }
        return navigationManager;
    }

    private void StartClicked(ActionEvent actionEvent) {
        //TODO: finish this function when i can make evaluators
        logger.debug("Start button clicked");
        JFXRadioButton selected = (JFXRadioButton) vehiclesToggle.getSelectedToggle();
        boolean fromFile;
        int vehiclesNum = 0;
        if(selected.equals(vehicleFileRadio)) {
            fromFile = true;
            if(chooseVehicleFileTextField.getText().isEmpty()) {
                showErrorDialog("You must select a vehicle file");
                return;
            }
        } else {
            fromFile = false;
            try {
                vehiclesNum = Integer.parseInt(numOfVehiclesTextField.getText());
                if(vehiclesNum < 2 || vehiclesNum > 10000) throw new NumberFormatException();
            } catch(NumberFormatException e) {
                showErrorDialog("Number of vehicles to generate is invalid. Make sure it's 2-10000");
                return;
            }
        }

        disableButtons();
        int finalVehiclesNum = vehiclesNum;
        new Thread(() -> {
            TrafficLightManager trafficManager1, trafficManager2;
            NavigationManager navigationManager1, navigationManager2;
            logger.debug("Getting the correct traffic light managers and navigation managers");
            StringBuilder builder1 = new StringBuilder(), builder2 = new StringBuilder();
            trafficManager1 = getTrafficLightManager(trafficLight_toggle1, naiveTrafficLight_radio1,
                    simpleTrafficLight_radio1, smartTrafficLight_radio1, builder1);
            trafficManager2 = getTrafficLightManager(trafficLight_toggle2, naiveTrafficLight_radio2,
                    simpleTrafficLight_radio2, smartTrafficLight_radio2, builder2);
            navigationManager1 = getNavigationManager(navigation_toggle1, naiveNavigation_radio1,
                    statisticsNavigation_radio1, builder1);
            navigationManager2 = getNavigationManager(navigation_toggle2, naiveNavigation_radio2,
                    statisticsNavigation_radio2, builder2);
            simType1 = builder1.toString();
            simType2 = builder2.toString();

            URL url;
            JsonVehiclesParser parser = new JsonVehiclesParser();
            List<VehicleEntry> entries;
            try {
                if(fromFile) {
                    File file = new File(chooseVehicleFileTextField.getText());
                    url = file.toURI().toURL();
                    entries = parser.parseVehiclesFromFile(url);
                } else {
                    entries = new VehiclesGenerator(mapDatabase.getAllRoads(),
                            finalVehiclesNum, BTWTime.of("09:00:00"), BTWTime.of("17:00:00")).generateList();
                }
                Evaluator eval1 = new EvaluatorImpl(entries, mapDatabase);
                Evaluator eval2 = new EvaluatorImpl(entries, mapDatabase);
                CitySimulator citySimulator1 = new CitySimulatorImpl(mapDatabase, navigationManager1, trafficManager1 ,eval1);
                citySimulator1.addVehiclesFromVehicleEntriesList(entries);
                trafficManager1.insertCrossroads(mapDatabase.getAllCrossroads()
                        .stream()
                        .map(citySimulator1::getRealCrossroad)
                        .collect(Collectors.toSet()));
                CitySimulator citySimulator2 = new CitySimulatorImpl(mapDatabase, navigationManager2, trafficManager2, eval2);
                citySimulator2.addVehiclesFromVehicleEntriesList(entries);
                trafficManager2.insertCrossroads(mapDatabase.getAllCrossroads()
                        .stream()
                        .map(citySimulator2::getRealCrossroad)
                        .collect(Collectors.toSet()));
                logger.debug("Attempting to run whole day for both simulations");
                logger.debug("Running whole day for simulation 1:");
                citySimulator1.runWholeDay();
                logger.debug("Done with simulation 1. Running whole day for simulation 2:");
                citySimulator2.runWholeDay();
                logger.debug("Done. Ran whole day for both simulations");
                Platform.runLater(() -> {
                    logger.debug("Moving to show statistics screen");
                    switchToStatisticsCompare(actionEvent, eval1, eval2, entries, simType1, simType2);
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    e.printStackTrace();
                    logger.debug("When trying to parse the string we got an exception of type " + e.getClass().toString());
                    showErrorDialog(e.getMessage());
                    enableButtons();
                });
            }


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

        vehicleFileRadio.setToggleGroup(vehiclesToggle);
        generateVehiclesRadio.setToggleGroup(vehiclesToggle);
    }

    private void BackClicked(ActionEvent actionEvent) {
        switchScreens(actionEvent, "/fxml/choose_running_config.fxml");
    }

    public void initCenterPanes() {
        titleHBox.translateXProperty()
                .bind(BTW.stage.widthProperty().subtract(titleHBox.widthProperty())
                        .divide(2));
        loadSpinner.translateXProperty()
                .bind(BTW.stage.widthProperty().subtract(loadSpinner.widthProperty())
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

        naiveNavigation_radio1.setDisable(true);
        statisticsNavigation_radio1.setDisable(true);
        naiveTrafficLight_radio1.setDisable(true);
        simpleTrafficLight_radio1.setDisable(true);
        smartTrafficLight_radio1.setDisable(true);

        naiveNavigation_radio2.setDisable(true);
        statisticsNavigation_radio2.setDisable(true);
        naiveTrafficLight_radio2.setDisable(true);
        simpleTrafficLight_radio2.setDisable(true);
        smartTrafficLight_radio2.setDisable(true);

        attachButton.setDisable(true);
        chooseVehicleFileTextField.setDisable(true);

        numOfVehiclesTextField.setDisable(true);
        vehicleFileRadio.setDisable(true);
        generateVehiclesRadio.setDisable(true);
    }

    private void enableButtons() {
        start_button.setDisable(false);
        back_button.setDisable(false);
        loadSpinner.setVisible(false);

        naiveNavigation_radio1.setDisable(false);
        statisticsNavigation_radio1.setDisable(false);
        naiveTrafficLight_radio1.setDisable(false);
        simpleTrafficLight_radio1.setDisable(false);
        smartTrafficLight_radio1.setDisable(false);

        naiveNavigation_radio2.setDisable(false);
        statisticsNavigation_radio2.setDisable(false);
        naiveTrafficLight_radio2.setDisable(false);
        simpleTrafficLight_radio2.setDisable(false);
        smartTrafficLight_radio2.setDisable(false);

        attachButton.setDisable(false);
        chooseVehicleFileTextField.setDisable(false);

        numOfVehiclesTextField.setDisable(false);
        vehicleFileRadio.setDisable(false);
        generateVehiclesRadio.setDisable(false);
    }

}
