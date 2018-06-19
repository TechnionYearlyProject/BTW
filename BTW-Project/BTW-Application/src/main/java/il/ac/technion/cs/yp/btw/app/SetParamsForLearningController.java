package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulator;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulatorImpl;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.evaluation.DumbEvaluator;
import il.ac.technion.cs.yp.btw.evaluation.Evaluator;
import il.ac.technion.cs.yp.btw.navigation.NaiveNavigationManager;
import il.ac.technion.cs.yp.btw.statistics.SmartStatisticsCalculator;
import il.ac.technion.cs.yp.btw.statistics.StatisticsCalculator;
import il.ac.technion.cs.yp.btw.trafficlights.SmartTrafficLightManager;
import il.ac.technion.cs.yp.btw.trafficlights.TrafficLightManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author:Anat Tetroashvili
 * @Date: 2/6/18
 */
public class SetParamsForLearningController extends SwitchToMapController implements Initializable {

    final static Logger logger = Logger.getLogger("setParamsForLearningController");

    BTWDataBase db;

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

    public SetParamsForLearningController(){
        this.unitType = new ToggleGroup();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        days_radio.setToggleGroup(unitType);
        weeks_radio.setToggleGroup(unitType);
        months_radio.setToggleGroup(unitType);
    }

    @FXML
    protected void BackClicked(ActionEvent event) {
        logger.debug("Going back to home screen");
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            String fxmlLocation = "/fxml/home_layout.fxml";
            URL resource = getClass().getResource(fxmlLocation);
            transitionAnimationAndSwitch(fxmlLocation, stageTheEventSourceNodeBelongs, resource, anchor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*@Author:Anat Tetroasvili
    * @Date:19/6/18
    * This function runs activates the learning mode, by running each day,
    * and save it to the database.*/
    @FXML
    protected void runLearningSimulation(){
        long dur = 0;
        JFXRadioButton units = null;
        long numberOfDays = 0;
        units = (JFXRadioButton) unitType.getSelectedToggle();
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

        /*while(numberOfDays!=0){
            CitySimulator citySimulator = new CitySimulatorImpl(db,new NaiveNavigationManager(db),
                    new SmartTrafficLightManager(), new SmartStatisticsCalculator(db),
                    new DumbEvaluator());
            citySimulator.runWholeDay();
            db.updateHeuristics();
            numberOfDays--;
        }*/





    }

}
