package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulator;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulatorImpl;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.db.BTWDataBaseImpl;
import il.ac.technion.cs.yp.btw.geojson.GeoJsonParserImpl;
import il.ac.technion.cs.yp.btw.mapgeneration.GridCityMapSimulator;
import il.ac.technion.cs.yp.btw.mapgeneration.MapSimulator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;

/**
 * Created by orel on 21/01/18.
 */
public abstract class GenerateCityController extends SwitchToMapController {

    @FXML protected JFXButton generate_button, back_button;
    @FXML protected JFXSpinner progress_spinner;

    protected String mapName;

    @FXML
    protected void BackClicked(ActionEvent event) {
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
            System.out.println("input was valid");
            generate_button.setDisable(true);
            back_button.setDisable(true);
            progress_spinner.setVisible(true);
        }

        MapSimulator mapSimulator = createMapSimulator();

        new Thread(() -> {
            mapSimulator.build();

            String mapString = parseCitySimulationToGeoJsonString(mapSimulator);

            System.out.println(mapString);

            //Insert the new map to the database.
            if(mapName == null) mapName = "random_simulated_map";
            System.out.println("about to parse the map: " + mapName);
            BTWDataBase dataBase = new BTWDataBaseImpl(mapName);
            dataBase.parseMap(mapString);

            CitySimulator citySimulator = new CitySimulatorImpl(dataBase);
            Platform.runLater(() -> switchScreensToMap(event, citySimulator));
        }).start();


    }

    protected abstract MapSimulator createMapSimulator();

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
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return mapString;
    }

}
