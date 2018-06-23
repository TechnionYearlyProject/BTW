package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulator;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulatorImpl;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author: Anat Tetroashvili
 * @date: 2/6/18
 * A class for setting parameters for learning simulation mode.
 */
public class SetParamsForLearningController extends SwitchToMapController implements Initializable {

    final static Logger logger = Logger.getLogger("setParamsForLearningController");

    private BTWDataBase db;

    private StatisticsCalculator calculator = new SmartStatisticsCalculator(db);

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
    protected JFXButton load_button = new JFXButton("load_button");
    @FXML
    protected JFXTextField chooseVFileTextField = new JFXTextField("chooseVFileTextField");
    @FXML
    protected JFXButton back_button = new JFXButton("back_button");

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
        run_simulation.setOnAction(this::runLearningSimulation);
        load_button.setOnAction(this::loadButtonClicked);
        back_button.setOnAction(this::BackClicked);
    }

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
        long dur = 0;
        JFXRadioButton units = null;
        long numberOfDays = 0;
        units = (JFXRadioButton) unitType.getSelectedToggle();
        if(duration.getText().equals("")){
            return;
        }
        dur = Long.valueOf(duration.getText());
        if(units.equals(days_radio)){
            if((dur<1)||(dur>6)){
                return;
            }
            numberOfDays = dur;
        }
        else if(units.equals(weeks_radio)){
            if((dur<1)||(dur>8)){
                return;
            }
            numberOfDays = dur*7;
        }
        else if(units.equals(months_radio)){
            if((dur<1)||(dur>12)){
                return;
            }
            numberOfDays = dur*28;
        }
//TODO: change the naive traffic light manager to smart one, and insert valid parameters to the evaluator,
// TODO: (CONTINUE) then run it and check that the statistics are saved to in the DB.
        /*while(numberOfDays!=0){
            CitySimulator citySimulator = new CitySimulatorImpl(db, new StatisticalNavigationManager(db),
                    new NaiveTrafficLightManager(), calculator, new EvaluatorImpl(db, db.));
            citySimulator.runWholeDay();
            db.updateStatisticsTables(calculator.getStatistics());
            numberOfDays--;
        }*/

    }

    /*@Author:Anat Tetroasvili
    * @Date:20/6/18
    * This function loads an existing map from the db,according to the name that have inserted,
     * or announce that there no such map.*/
    public void loadButtonClicked(ActionEvent actionEvent) {
        disableAllButtons();
        new Thread(() -> {
            db = new BTWDataBaseImpl(chooseVFileTextField.getText());
            logger.debug("Trying to load map: " + chooseVFileTextField.getText());
            boolean result = db.loadMap();
            Platform.runLater(() -> {
                if(!result) {
                    enableAllButtons();
                    showErrorDialog("Failed to load: Map name is not in the Database");
                } else {
                    logger.debug("Loading map from Database");
                    new Thread(() -> {
                        //TODO: CHANGE TO SMART
                        NavigationManager navigationManager = new NaiveNavigationManager(db);
                        TrafficLightManager trafficLightManager = new NaiveTrafficLightManager();
                        calculator = new NaiveStatisticsCalculator(db);
                        CitySimulator citySimulator = new CitySimulatorImpl(db, navigationManager, trafficLightManager, calculator);
                        trafficLightManager.insertCrossroads(db.getAllCrossroads()
                                .stream()
                                .map(citySimulator::getRealCrossroad)
                                .collect(Collectors.toSet()));
                        //Platform.runLater(() -> switchScreensToMap(actionEvent, citySimulator, db));
                        Platform.runLater(this::enableAllButtons);
                    }).start();
                }});
        }).start();
    }

    private void disableAllButtons() {
        load_button.setDisable(true);
        days_radio.setDisable(true);
        weeks_radio.setDisable(true);
        months_radio.setDisable(true);
        run_simulation.setDisable(true);
    }
    private void enableAllButtons() {
        load_button.setDisable(false);
        days_radio.setDisable(false);
        weeks_radio.setDisable(false);
        months_radio.setDisable(false);
        run_simulation.setDisable(false);
    }

    public void initMapDB(BTWDataBase mapDatabase) {
        this.db = mapDatabase;
    }

}
