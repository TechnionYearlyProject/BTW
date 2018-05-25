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
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

/**@author: Orel
 * @date: 20/1/18
 * all methods that don't specify an other author are by Orel
 */
public class HomeController extends SwitchToMapController implements Initializable {
    @FXML
    private ToggleGroup generate_city_toggle;
    @FXML
    private RadioButton grid_radio = new RadioButton("grid_radio");
    @FXML
    private RadioButton free_form_radio = new RadioButton("free_form_radio");

    @FXML private JFXTextField chooseMapTextBox;

    @FXML private JFXSpinner loadSpinner;

    @FXML private JFXButton load_button, generate_button;

    final static Logger logger = Logger.getLogger("HomeController");

    public HomeController(){
        this.generate_city_toggle = new ToggleGroup();
    }
    @FXML
    protected void generateButtonClick(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) generate_city_toggle.getSelectedToggle();
        if(selectedRadioButton == null) return;
        String switchTo;
        if (selectedRadioButton.equals(grid_radio)) {
            switchTo = "/fxml/generate_grid.fxml";
        } else if(selectedRadioButton.equals(free_form_radio)) {
            switchTo = "/fxml/generate_free_form.fxml";
        }else{
            return;
        }
        switchScreens(event, switchTo);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        grid_radio.setToggleGroup(generate_city_toggle);
        grid_radio.setUserData("grid_radio");
        grid_radio.setToggleGroup(generate_city_toggle);
        grid_radio.setUserData("free_form_radio");
        new Thread(() -> {
            BTWDataBase dbForTables = new BTWDataBaseImpl("dbForTables");   // Shay - TO DO: separate tables names from the constructor
            Set<String> tablesNames = dbForTables.getTablesNames();
            Platform.runLater(() -> {
                if (tablesNames != null) {
                    logger.debug("Tables names are loaded, should bind to auto complete");
                    TextFields.bindAutoCompletion(chooseMapTextBox, tablesNames);
                }
            });
        }).start();
    }


    public void loadButtonClicked(ActionEvent actionEvent) {
        load_button.setDisable(true);
        generate_button.setDisable(true);
        loadSpinner.setVisible(true);
        new Thread(() -> {
            BTWDataBase dataBase = new BTWDataBaseImpl(chooseMapTextBox.getText());
            System.out.println("trying to load map: " + chooseMapTextBox.getText());
            boolean result = dataBase.loadMap();
            Platform.runLater(() -> {
                if(!result) {
                    load_button.setDisable(false);
                    generate_button.setDisable(false);
                    loadSpinner.setVisible(false);
                    showErrorDialog("Failed to load: Map name is not in the Database");
                } else {
                    new Thread(() -> {
                        NavigationManager navigationManager = new NaiveNavigationManager(dataBase);
                        TrafficLightManager trafficLightManager = new NaiveTrafficLightManager();
                        StatisticsCalculator calculator = new NaiveStatisticsCalculator(dataBase);
                        CitySimulator citySimulator = new CitySimulatorImpl(dataBase, navigationManager, trafficLightManager, calculator);
                        trafficLightManager.insertCrossroads(dataBase.getAllCrossroads()
                                .stream()
                                .map(citySimulator::getRealCrossroad)
                                .collect(Collectors.toSet()));
                        Platform.runLater(() -> switchScreensToMap(actionEvent, citySimulator));
                    }).start();
                }
            });
        }).start();
    }

}
