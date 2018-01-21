package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
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
import javafx.application.Platform;
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

public class GenerateFreeFormController extends GenerateCityController implements Initializable{
    @FXML private JFXTextField NumberOfBlocks;
    @FXML private JFXTextField Radius;
    @FXML private JFXToggleButton blocksToggle, radiusToggle, mapNameToggle;
    @FXML private JFXTextField mapNameTextField;

    int Number_of_blocks, radius_val;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        blocksToggle.selectedProperty().addListener((observable, oldValue, newValue) ->
                NumberOfBlocks.setDisable(!NumberOfBlocks.isDisabled()));
        radiusToggle.selectedProperty().addListener((observable, oldValue, newValue) ->
                Radius.setDisable(!Radius.isDisabled()));
        mapNameToggle.selectedProperty().addListener((observable, oldValue, newValue) ->
                mapNameTextField.setDisable(!mapNameTextField.isDisabled()));
    }

    @Override
    protected boolean getAndValidateUserInput(ActionEvent event) {
        String errorMessage = "";
        if(!NumberOfBlocks.isDisabled()) {
            try{
                Number_of_blocks = Integer.parseInt(NumberOfBlocks.getText());
                //checking boundaries
                if(Number_of_blocks < 30 || Number_of_blocks > 250) throw new NumberFormatException();
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
        if(!mapNameTextField.isDisabled()) {
            mapName = mapNameTextField.getText();
            if(mapName.equals("")) {
                errorMessage += "Map name can't be empty\n";
            }
        }
        if(!errorMessage.equals("")) {
            showErrorDialog(errorMessage);
            return false;
        }
        return true;
    }

    @Override
    protected MapSimulator createMapSimulator() {
        FreeFormMapSimulator freeFormMapSimulator = new FreeFormMapSimulator();
        if(!NumberOfBlocks.isDisabled()) freeFormMapSimulator.setNumOfCityBlocks(Number_of_blocks);
        if(!Radius.isDisabled()) freeFormMapSimulator.setCityRadius(radius_val);
        return freeFormMapSimulator;
    }

}
