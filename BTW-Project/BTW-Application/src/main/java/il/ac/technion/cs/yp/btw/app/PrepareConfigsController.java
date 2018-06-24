package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.*;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulator;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulatorImpl;
import il.ac.technion.cs.yp.btw.citysimulation.VehicleEntry;
import il.ac.technion.cs.yp.btw.citysimulation.VehiclesGenerator;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.classes.BTWTime;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

/**@author: Orel
 * @date: 20/6/18
 * all methods are by Orel
 */
public class PrepareConfigsController extends SwitchToMapController implements Initializable {

    @FXML
    private ToggleGroup generate_city_toggle;
    @FXML
    private JFXRadioButton grid_radio, free_form_radio;

    @FXML private JFXTextField chooseMapTextBox, mapFromFileTextBox, nameInDBFromFileTextBox, vehicleFileURL, numberOfVehiclesTextField;

    @FXML private JFXSpinner loadSpinner;

    @FXML private JFXButton load_button, generate_button, load_file_button, attachButton, attachVehicleButton, back_button;

    @FXML private HBox titleHBox, centerContent;

    @FXML private JFXTimePicker timePicker1, timePicker2;

    private Set<String> tablesNames;

    final static Logger logger = Logger.getLogger("PrepareConfigsController");

    private int numOfVehicles;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grid_radio.setToggleGroup(generate_city_toggle);
        free_form_radio.setToggleGroup(generate_city_toggle);
        grid_radio.setSelected(true);
        Image buttonImage = new Image(getClass().getResourceAsStream("/icons8-attach-30.png"));
        attachButton.setGraphic(new ImageView(buttonImage));
        attachVehicleButton.setGraphic(new ImageView(buttonImage));
        timePicker1.setIs24HourView(true);
        timePicker2.setIs24HourView(true);
        initDynamicPosition();

        new Thread(() -> {
            BTWDataBase dbForTables = new BTWDataBaseImpl("dbForTables");
            tablesNames = dbForTables.getTablesNames();
            Platform.runLater(() -> {
                if (tablesNames != null) {
                    logger.debug("Tables names are loaded");
                    TextFields.bindAutoCompletion(chooseMapTextBox, tablesNames);
                }
            });
        }).start();
        logger.debug("PrepareConfigsController Initialized");
    }


    private void initDynamicPosition() {
        titleHBox.translateXProperty()
                .bind(BTW.stage.widthProperty().subtract(titleHBox.widthProperty())
                        .divide(2));
        centerContent.translateXProperty()
                .bind(BTW.stage.widthProperty().subtract(centerContent.widthProperty())
                        .divide(2));
        loadSpinner.translateXProperty()
                .bind(BTW.stage.widthProperty().subtract(loadSpinner.widthProperty())
                        .divide(2));
        AnchorPane.setRightAnchor(back_button, 20.0);
        AnchorPane.setTopAnchor(back_button, 60.0);
    }

    public void BackClicked(ActionEvent actionEvent) {
        switchScreens(actionEvent, "/fxml/choose_running_config.fxml");
    }

    public void attachButtonClicked(ActionEvent actionEvent) {
        logger.debug("Attaching file");
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(anchor.getScene().getWindow());
        if(selectedFile != null) mapFromFileTextBox.setText(selectedFile.getAbsolutePath());
    }


    public void attachVehicleButtonClicked(ActionEvent actionEvent) {
        logger.debug("Attaching file");
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Json files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showSaveDialog(anchor.getScene().getWindow());
        if(selectedFile != null) vehicleFileURL.setText(selectedFile.getAbsolutePath());
    }



    public void generateButtonClick(ActionEvent actionEvent) {
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
        GenerateCityController.acceptAction = DrawMapController.AcceptAction.SaveMap;
        switchScreens(actionEvent, switchTo);
    }

    protected String validateMapName(String text) {
        String errorMessage = "";
        if(text.equals("")) {
            errorMessage += "Map name can't be empty\n";
        } if(!text.matches("[a-zA-Z0-9_]+")) {
            errorMessage += "Map name must be alphanumeric\n" + "and without spaces";
        }
        if(errorMessage.equals("")) {
            if(tablesNames == null) errorMessage = "No connection to Database yet";
            else if(tablesNames.contains(text)) errorMessage = "Map name already in Database";
        }
        return errorMessage;
    }

    public void loadFileButtonClicked(ActionEvent actionEvent) {
        String mapName = nameInDBFromFileTextBox.getText();
        String errorMessage = validateMapName(mapName);
        if(!errorMessage.equals("")) {
            showErrorDialog(errorMessage);
            return;
        }
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

        disableAllButtons();
        logger.debug("Trying to load map from attached file");
        new Thread(() -> {

            BTWDataBase dataBase;
            try{
                dataBase = new BTWDataBaseImpl(mapName);
                dataBase.parseMap(mapString);
            } catch (Exception e) {
                Platform.runLater(() -> {
                    showErrorDialog("Failed to parse file");
                    logger.debug("Failed to load map from file: invalid file");
                    enableAllButtons();
                });
                return;
            }
            dataBase.getTablesNames();
            dataBase.updateHeuristics();
            NavigationManager navigationManager = new NaiveNavigationManager(dataBase);
            TrafficLightManager trafficLightManager = new NaiveTrafficLightManager();
            CitySimulator citySimulator = new CitySimulatorImpl(dataBase, navigationManager, trafficLightManager);
            Platform.runLater(() -> switchScreensToMap(actionEvent, citySimulator, dataBase, true, DrawMapController.AcceptAction.SaveMap));
        }).start();
    }

    private void disableAllButtons() {
        load_button.setDisable(true);
        generate_button.setDisable(true);
        load_file_button.setDisable(true);
        attachButton.setDisable(true);
        back_button.setDisable(true);
        loadSpinner.setVisible(true);
    }

    private void enableAllButtons() {
        load_button.setDisable(false);
        generate_button.setDisable(false);
        load_file_button.setDisable(false);
        attachButton.setDisable(false);
        back_button.setDisable(false);
        loadSpinner.setVisible(false);
    }


    private boolean getAndValidateUserInput() {
        if(!mapNameIsInDatabase()) return false;
        String errorMessage = "";
        if(timePicker1.getValue() == null || timePicker2.getValue() == null) {
            errorMessage += "You must choose both heavy traffic hours\n";
        }
        if(chooseMapTextBox.getText().equals("")) {
            errorMessage +="You must choose a valid map from the Database\n";
        }
        if(vehicleFileURL.getText().equals("")) {
            errorMessage += "You must choose Vehicle file save location\n";
        }

        if(!vehicleFileURL.getText().endsWith(".json")) {
            errorMessage += "File must have .json extension\n";
        }

        try{
            numOfVehicles = Integer.parseInt(numberOfVehiclesTextField.getText());
            //checking boundaries
            if(numOfVehicles < 2 || numOfVehicles > 10000) throw new NumberFormatException();
        } catch(NumberFormatException e) {
            errorMessage += "Number of vehicles input is invalid\n";
        }

        if(errorMessage.equals("")) return true;
        else {
            showErrorDialog(errorMessage);
            return false;
        }
    }

    public void generateVehiclesButtonClicked(ActionEvent actionEvent) {
        if(!getAndValidateUserInput()) return;
        disableAllButtons();
        LocalTime time1 = timePicker1.getValue();
        LocalTime time2 = timePicker2.getValue();
        new Thread(() -> {
            BTWDataBase db = new BTWDataBaseImpl(chooseMapTextBox.getText());
            db.loadMap();
            File file = new File(vehicleFileURL.getText());
            VehiclesGenerator gen = new VehiclesGenerator(db.getAllRoads(),10,
                    BTWTime.of(time1.getHour(), time1.getMinute(), time1.getSecond()),
                    BTWTime.of(time2.getHour(), time2.getMinute(), time2.getSecond()));
            try {
                gen.generateToFile(file);
            } catch (IOException e) {
                Platform.runLater(() -> showErrorDialog("Invalid file path. Failed to save"));
            }
            Platform.runLater(this::enableAllButtons);
        }).start();

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
}
