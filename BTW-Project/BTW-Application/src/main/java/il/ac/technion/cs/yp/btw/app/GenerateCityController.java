package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulator;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulatorImpl;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.db.BTWDataBaseImpl;
import il.ac.technion.cs.yp.btw.geojson.GeoJsonParserImpl;
import il.ac.technion.cs.yp.btw.mapgeneration.GridCityMapSimulator;
import il.ac.technion.cs.yp.btw.mapgeneration.MapSimulator;
import il.ac.technion.cs.yp.btw.navigation.NaiveNavigationManager;
import il.ac.technion.cs.yp.btw.navigation.NavigationManager;
import il.ac.technion.cs.yp.btw.statistics.NaiveStatisticsCalculator;
import il.ac.technion.cs.yp.btw.statistics.StatisticsCalculator;
import il.ac.technion.cs.yp.btw.trafficlights.NaiveTrafficLightManager;
import il.ac.technion.cs.yp.btw.trafficlights.TrafficLightManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sun.rmi.runtime.Log;

import java.io.*;
import java.net.URL;
import java.util.stream.Collectors;

/**
 * Created by orel on 21/01/18.
 */
/**@author: Orel
 * @date: 20/1/18
 * all methods that don't specify an other author are by Orel
 */
public abstract class GenerateCityController extends SwitchToMapController {

    @FXML protected JFXButton generate_button, back_button;
    @FXML protected JFXSpinner progress_spinner;
    protected String mapName;
    final static Logger logger = Logger.getLogger("GenerateCityController");

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

    @FXML protected void GenerateClicked(ActionEvent event) {
        if(!getAndValidateUserInput(event)) return; //if user input isn't valid there's nothing to do
        else {
            logger.debug("Input was valid");
            generate_button.setDisable(true);
            back_button.setDisable(true);
            progress_spinner.setVisible(true);
        }
        logger.debug("Creating simulation");
        MapSimulator mapSimulator = createMapSimulator();

        new Thread(() -> {
            mapSimulator.build();

            String mapString = parseCitySimulationToGeoJsonString(mapSimulator);

//            System.out.println(mapString);

            //Insert the new map to the database.
            if(mapName == null) mapName = "random_simulated_map";
            logger.debug("About to parse the map: " + mapName);
            BTWDataBase dataBase = new BTWDataBaseImpl(mapName);
            dataBase.parseMap(mapString);

            dataBase.getTablesNames();

            NavigationManager navigationManager = new NaiveNavigationManager(dataBase);
            TrafficLightManager trafficLightManager = new NaiveTrafficLightManager();
            StatisticsCalculator calculator = new NaiveStatisticsCalculator(dataBase);
            CitySimulator citySimulator = new CitySimulatorImpl(dataBase, navigationManager, trafficLightManager, calculator);
            trafficLightManager.insertCrossroads(dataBase.getAllCrossroads()
                    .stream()
                    .map(citySimulator::getRealCrossroad)
                    .collect(Collectors.toSet()));
            logger.debug("Switching to draw map screen");
            Platform.runLater(() -> switchScreensToMap(event, citySimulator, dataBase));
        }).start();


    }

    protected abstract MapSimulator createMapSimulator();

    protected String mapNameSetValueAndGetMessage(String errorMessage, JFXTextField mapNameTextField) {
        String mapNameErrorString = validateMapName(mapNameTextField.getText());
        errorMessage += mapNameErrorString;
        if(mapNameErrorString.equals("")) mapName = mapNameTextField.getText();
        else mapName = null;
        return errorMessage;
    }

    protected abstract boolean getAndValidateUserInput(ActionEvent event);

    /**@author: Anat
     * @date: 20/1/18
     * @param mapSimulator - simulator for parsing
     * @return GeoJson string format for the given simulator
     */
    protected String parseCitySimulationToGeoJsonString(MapSimulator mapSimulator) {
        GeoJsonParserImpl geoJsonParser = new GeoJsonParserImpl();
        File mapFile = geoJsonParser.buildGeoJsonFromSimulation(mapSimulator);
        String mapString = "";
        FileReader fileReader = null;
        try {
            String line;
            fileReader = new FileReader(mapFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                mapString = mapString+line;
            }
            // Always close files.
            bufferedReader.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        mapFile.delete();
        return mapString;
    }

    protected String validateMapName(String text) {
        String errorMessage = "";
        mapName = text;
        if(mapName.equals("")) {
            errorMessage += "Map name can't be empty\n";
        } if(!mapName.matches("[a-zA-Z0-9_]+")) {
            errorMessage += "Map name must be alphanumeric\n" + "and without spaces";
        }
        return errorMessage;
    }

    protected void textToggleAction(JFXTextField textField) {
        textField.setDisable(!textField.isDisabled());
        textField.setText("");
    }
}
