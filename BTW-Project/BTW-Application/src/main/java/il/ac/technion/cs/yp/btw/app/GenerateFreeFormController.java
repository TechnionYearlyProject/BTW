package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import il.ac.technion.cs.yp.btw.citysimulation.CityMap;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulator;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulatorImpl;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.db.BTWDataBaseImpl;
import il.ac.technion.cs.yp.btw.geojson.GeoJsonParserImpl;
import il.ac.technion.cs.yp.btw.mapgeneration.FreeFormMapSimulator;
import il.ac.technion.cs.yp.btw.mapgeneration.GridCityMapSimulator;
import il.ac.technion.cs.yp.btw.mapgeneration.MapSimulator;
import javafx.animation.FadeTransition;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class GenerateFreeFormController implements Initializable{
    @FXML private Node anchor;
    @FXML private JFXTextField NumberOfBlocks;
    @FXML private JFXTextField Radius;

    @FXML private JFXToggleButton blocksToggle, radiusToggle;

    int Number_of_blocks, radius_val;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        blocksToggle.selectedProperty().addListener((observable, oldValue, newValue) ->
                NumberOfBlocks.setDisable(!NumberOfBlocks.isDisabled()));
        radiusToggle.selectedProperty().addListener((observable, oldValue, newValue) ->
                Radius.setDisable(!Radius.isDisabled()));
    }

    @FXML protected void BackClicked(ActionEvent event) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            String fxmlLocation = "/fxml/home_layout.fxml";
            URL resource = getClass().getResource(fxmlLocation);
            transitionAnimationAndSwitch(fxmlLocation, stageTheEventSourceNodeBelongs, resource, anchor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean getAndValidateUserInput(ActionEvent event) {
        String errorMessage = "";
        if(!NumberOfBlocks.isDisabled()) {
            try{
                Number_of_blocks = Integer.parseInt(NumberOfBlocks.getText());
                //checking boundaries
                if(Number_of_blocks < 50 || Number_of_blocks > 250) throw new NumberFormatException();
            } catch(NumberFormatException e) {
                errorMessage += "Number of Blocks input is invalid\n";
            }
        }
        if(!Radius.isDisabled()) {
            try{
                radius_val = Integer.parseInt(Radius.getText());
                //checking boundaries
                if(radius_val < 2000 || radius_val > 7000) throw new NumberFormatException();
            } catch(NumberFormatException e) {
                errorMessage += "Radius input is invalid\n";
            }
        }
        if(!errorMessage.equals("")) {
            showErrorDialog(errorMessage, event);
            return false;
        }
        return true;
    }



    private void showErrorDialog(String errorMessage, ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid input");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);

        alert.showAndWait();
    }


    @FXML protected void GenerateClicked(ActionEvent event) {
        if(!getAndValidateUserInput(event)) return; //if user input isn't valid there's nothing to do
        else {
            //TODO: for testing purposes
            System.out.println("input was valid");
//            return;
        }

        FreeFormMapSimulator freeFormMapSimulator = new FreeFormMapSimulator();
        //TODO: add input to map simulator when Adam is done
//        if(!NumberOfBlocks.isDisabled())
        freeFormMapSimulator.build();

        String mapString = parseCitySimulationToGeoJsonString(freeFormMapSimulator);

        //Insert the new map to the database.
        BTWDataBase dataBase = new BTWDataBaseImpl("simulatedCity897");
        dataBase.saveMap(mapString);

        CitySimulator citySimulator = new CitySimulatorImpl(dataBase);
        CityMap cityMap = citySimulator.saveMap();
        switchScreensToMap(event, cityMap);


    }


    private String parseCitySimulationToGeoJsonString(MapSimulator mapSimulator) {
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


    private void switchScreensToMap(ActionEvent event, CityMap cityMap) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            //TODO: maybe remove resource
            URL resource = getClass().getResource("/fxml/stageForDrawMap.fxml");
            transitionAndSwitchToMap(stageTheEventSourceNodeBelongs, resource, anchor, cityMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void transitionAnimationAndSwitch(String fxmlLocation, Stage stageTheEventSourceNodeBelongs,
                                             URL resource, Node rootNode) throws IOException {
        Parent root = FXMLLoader.load(resource);
        transitionAndSwitchInner(stageTheEventSourceNodeBelongs, rootNode, root);
    }

    private void transitionAndSwitchInner(Stage stageTheEventSourceNodeBelongs, Node rootNode, Parent root) {
        int length = 300;
        FadeTransition fadeOut = new FadeTransition(Duration.millis(length), rootNode);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event1 -> {
                    FadeTransition fadeIn = new FadeTransition(Duration.millis(length), root);
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);
                    fadeIn.play();
                    DoubleProperty opacity = root.opacityProperty();
                    opacity.set(0);
                    Scene scene = new Scene(root);
                    stageTheEventSourceNodeBelongs.setScene(scene);
                }
        );
        fadeOut.play();
    }

    public void transitionAndSwitchToMap(Stage stageTheEventSourceNodeBelongs,
                                         URL resource, Node rootNode, CityMap cityMap) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stageForDrawMap.fxml"));
        DrawMapController drawMapController = new DrawMapController();
        drawMapController.initCityMap(cityMap);
        drawMapController.initStage(stageTheEventSourceNodeBelongs);
        loader.setController(drawMapController);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
