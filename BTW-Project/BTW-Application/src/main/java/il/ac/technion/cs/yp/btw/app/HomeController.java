package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulator;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulatorImpl;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.db.BTWDataBaseImpl;
import il.ac.technion.cs.yp.btw.geojson.GeoJsonParserImpl;
import il.ac.technion.cs.yp.btw.geojson.MapParsingException;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.net.MalformedURLException;
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

    @FXML private JFXTextField chooseMapTextBox, mapFromFileTextBox;

    @FXML private JFXSpinner loadSpinner;

    @FXML private JFXButton load_button, generate_button, load_file_button, attachButton;

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
        Image buttonImage = new Image(getClass().getResourceAsStream("/icons8-attach-30.png"));
        attachButton.setGraphic(new ImageView(buttonImage));

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
        disableAllButtons();
//        loadSpinner.setVisible(true);
        new Thread(() -> {
            BTWDataBase dataBase = new BTWDataBaseImpl(chooseMapTextBox.getText());
            System.out.println("trying to load map: " + chooseMapTextBox.getText());
            boolean result = dataBase.loadMap();
            Platform.runLater(() -> {
                if(!result) {
                    enableAllButtons();
//                    loadSpinner.setVisible(false);
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

    public void attachButtonClicked(ActionEvent actionEvent) {
//        disableAllButtons();
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(anchor.getScene().getWindow());
        mapFromFileTextBox.setText(selectedFile.getAbsolutePath());
//        enableAllButtons();
    }

    public void loadFileButtonClicked(ActionEvent actionEvent) {
        URL url;
        try {
            File file = new File(mapFromFileTextBox.getText());
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            showErrorDialog("Map URL isn't a valid file URL");
            return;
        }

        GeoJsonParserImpl parser = new GeoJsonParserImpl();
        String mapString;
        try {
            mapString = parser.getDataFromFile(url);
        } catch(MapParsingException e) {
            showErrorDialog(e.getMessage());
            return;
        }

        //getting the file name
        String fileName = url.getPath().substring( url.getPath().lastIndexOf('/')+1, url.getPath().length() );
        String mapName = fileName.substring(0, fileName.lastIndexOf('.'));
        disableAllButtons();
        new Thread(() -> {

            BTWDataBase dataBase;
            try{
                dataBase = new BTWDataBaseImpl(mapName);
                dataBase.parseMap(mapString);
            } catch (Exception e) {
                Platform.runLater(() -> {
                    showErrorDialog("Failed to parse file");
                    enableAllButtons();
                });
                return;
            }
            dataBase.getTablesNames();
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

    private void disableAllButtons() {
        load_button.setDisable(true);
        load_file_button.setDisable(true);
        generate_button.setDisable(true);
        attachButton.setDisable(true);
        loadSpinner.setVisible(true);
    }
    private void enableAllButtons() {
        load_button.setDisable(false);
        load_file_button.setDisable(false);
        generate_button.setDisable(false);
        attachButton.setDisable(false);
        loadSpinner.setVisible(false);
    }

}
