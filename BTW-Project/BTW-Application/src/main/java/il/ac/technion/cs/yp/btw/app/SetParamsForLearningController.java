package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulator;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulatorImpl;
import il.ac.technion.cs.yp.btw.citysimulation.VehicleEntry;
import il.ac.technion.cs.yp.btw.citysimulation.VehiclesGenerator;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.classes.BTWTime;
import il.ac.technion.cs.yp.btw.db.BTWDataBaseImpl;
import il.ac.technion.cs.yp.btw.evaluation.Evaluator;
import il.ac.technion.cs.yp.btw.evaluation.EvaluatorImpl;
import il.ac.technion.cs.yp.btw.navigation.NaiveNavigationManager;
import il.ac.technion.cs.yp.btw.navigation.NavigationManager;
import il.ac.technion.cs.yp.btw.navigation.StatisticalNavigationManager;
import il.ac.technion.cs.yp.btw.statistics.NaiveStatisticsCalculator;
import il.ac.technion.cs.yp.btw.statistics.SmartStatisticsCalculator;
import il.ac.technion.cs.yp.btw.statistics.StatisticsCalculator;
import il.ac.technion.cs.yp.btw.trafficlights.NaiveTrafficLightManager;
import il.ac.technion.cs.yp.btw.trafficlights.SmartTrafficLightManager;
import il.ac.technion.cs.yp.btw.trafficlights.TrafficLightManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author: Anat Tetroashvili
 * @date: 2/6/18
 * A class for setting parameters for learning simulation mode.
 */
public class SetParamsForLearningController extends SwitchToMapController implements Initializable {

    final static Logger logger = Logger.getLogger("setParamsForLearningController");

    /*db that contains the map to run the learning mode on it.*/
    private BTWDataBase db;

    private StatisticsCalculator calculator;

    /*nimber of vehicles to generate during the run*/
    private int numberOfVehicles = 0;

    @FXML
    protected JFXRadioButton days_radio = new JFXRadioButton("days_radio");
    @FXML
    protected JFXRadioButton weeks_radio = new JFXRadioButton("weeks_radio");
    @FXML
    protected JFXRadioButton months_radio = new JFXRadioButton("months_radio");
    @FXML
    private ToggleGroup unitType;
    @FXML
    protected JFXTextField duration = new JFXTextField("duration");
    @FXML
    protected JFXButton run_simulation = new JFXButton("run_simulation");
    @FXML
    protected JFXTextField chooseNumOfVehiclesField = new JFXTextField("chooseNumOfVehiclesField");
    @FXML
    protected JFXButton back_button = new JFXButton("back_button");
    @FXML
    protected HBox titleHBox;
    @FXML
    protected JFXButton duration_helper = new JFXButton("duration_helper");
    @FXML
    protected JFXSpinner progress_spinner;
    @FXML private VBox centerContent;
    private long durationValue;


    public SetParamsForLearningController(){
        this.unitType = new ToggleGroup();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Scene scene = new Scene((Parent)anchor, BTW.stage.getWidth(), BTW.stage.getHeight());
        BTW.stage.setScene(scene);
        days_radio.setToggleGroup(unitType);
        weeks_radio.setToggleGroup(unitType);
        months_radio.setToggleGroup(unitType);
        days_radio.setSelected(true);
        run_simulation.setOnAction(this::runLearningSimulation);
//        load_vehicles_button.setOnAction(this::loadVehiclesButtonClicked);
        back_button.setOnAction(this::BackClicked);
        calculator = new SmartStatisticsCalculator(db);
        initCenterPanes();
        duration_helper.setOnAction(this::showHelpDialogForDuration);
    }

    /*Returns to the main page of the system.*/
    @FXML
    protected void BackClicked(ActionEvent event) {
        logger.debug("Going back to home screen");
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            String fxmlLocation = "/fxml/choose_running_config.fxml";
            URL resource = getClass().getResource(fxmlLocation);
            transitionAnimationAndSwitch(fxmlLocation, stageTheEventSourceNodeBelongs, resource, anchor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*@Author:Anat Tetroasvili
    * @Date:19/6/18
    * This function activates the learning mode, by running each day,
    * and save it to the database.
    * The minimum duration for a run is 1 day.
    * The maximum duration for a run is 1 year(12 months).*/
    @FXML
    protected void runLearningSimulation(ActionEvent actionEvent){
        if(!getAndValidateUserInput()){
            return;
        }

        disableAllButtons();
        new Thread(() -> {
            long numberOfDays = durationValue;
            while(numberOfDays!=0){
                List<VehicleEntry> vehicleEntries = new VehiclesGenerator(db.getAllRoads(),numberOfVehicles,
                        BTWTime.of("09:00:00"),BTWTime.of("17:00:00")).generateList();
                CitySimulator citySimulator = new CitySimulatorImpl(db, new StatisticalNavigationManager(db),
                        new SmartTrafficLightManager(), calculator);
                citySimulator.addVehiclesFromVehicleEntriesList(vehicleEntries);
                citySimulator.runWholeDay();
                db.updateStatisticsTables(calculator.getStatistics());
                numberOfDays--;
            }
            enableAllButtons();
        }).start();


    }

    private void disableAllButtons() {
//        load_vehicles_button.setDisable(true);
        days_radio.setDisable(true);
        weeks_radio.setDisable(true);
        months_radio.setDisable(true);
        run_simulation.setDisable(true);
        progress_spinner.setVisible(true);
        back_button.setDisable(true);
    }
    private void enableAllButtons() {
//        load_vehicles_button.setDisable(false);
        days_radio.setDisable(false);
        weeks_radio.setDisable(false);
        months_radio.setDisable(false);
        run_simulation.setDisable(false);
        progress_spinner.setVisible(false);
        back_button.setDisable(false);
    }

    public void initMapDatabase(BTWDataBase mapDatabase) {
        this.db = mapDatabase;
    }

    /**@author: Anat
     * @date: 23/6/2018
     */
    public void initCenterPanes() {
        titleHBox.translateXProperty()
                .bind(BTW.stage.widthProperty().subtract(titleHBox.widthProperty())
                        .divide(2));
        centerContent.translateXProperty()
                .bind(BTW.stage.widthProperty().subtract(centerContent.widthProperty())
                        .divide(2));
        progress_spinner.translateXProperty()
                .bind(BTW.stage.widthProperty().subtract(progress_spinner.widthProperty())
                        .divide(2));
        AnchorPane.setTopAnchor(titleHBox, 30.0);
        AnchorPane.setRightAnchor(back_button, 20.0);
        AnchorPane.setTopAnchor(back_button, 60.0);
    }

    /**@author: Anat
     * @date: 27/4/2018
     */
    @FXML
    public void showHelpDialogForDuration(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText(null);
        alert.setContentText("Enter the amount of days/weeks/months you \n"+
                "want to run the learning mode.\n" +
                "Days - up to 6\n"+
                "Weeks - up to 8\n"+
                "Months - up to 12");
        alert.showAndWait();
    }


    /*@Author: Anat Tetroashvili
    * @Date: 23/6/18
    * Checking input validity.*/
    protected boolean getAndValidateUserInput() {
        String errorMessage = "";
        try{
            //checking number of vehicles
            numberOfVehicles = Integer.parseInt(chooseNumOfVehiclesField.getText());
            if(numberOfVehicles < 1 || numberOfVehicles > 200) {
                throw new NumberFormatException();
            }
        } catch(NumberFormatException e) {
            errorMessage += "Number of Vehicles input is invalid\n";
        }
        if(duration.getText().equals("")){
            errorMessage += "Insert Duration To Run\n";
        } else {
            try {
                durationValue = Long.parseLong(duration.getText());
                JFXRadioButton units = (JFXRadioButton) unitType.getSelectedToggle();
                long dur = durationValue;
                if(units.equals(days_radio)){
                    if((dur<1)||(dur>6))
                        errorMessage += "Number Of Days Invalid\n";
                }
                if(units.equals(weeks_radio)){
                    if((dur<1)||(dur>8))
                        errorMessage += "Number Of Weeks Invalid\n";
                    else durationValue *= 7;
                }
                if(units.equals(months_radio)){
                    if((dur<1)||(dur>12))
                        errorMessage += "Number Of Months Invalid\n";
                    else durationValue *= 30;
                }

            } catch (NumberFormatException e){
                errorMessage += "Duration input invalid: isn't a number\n";
            }
        }
        if(!errorMessage.equals("")) {
            showErrorDialog(errorMessage);
            return false;
        }
        return true;
    }

}
