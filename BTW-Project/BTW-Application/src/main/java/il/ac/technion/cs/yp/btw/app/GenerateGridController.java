package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import il.ac.technion.cs.yp.btw.mapgeneration.GridCityMapSimulator;
import il.ac.technion.cs.yp.btw.mapgeneration.MapSimulator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class GenerateGridController extends GenerateCityController implements Initializable{

    @FXML private JFXTextField NumberOfStreets;
    @FXML private JFXTextField NumberOfAvenues;
    @FXML private JFXTextField LengthOfStreets;
    @FXML private JFXTextField LengthOfAvenues;
    @FXML private JFXTextField mapNameTextField;
    @FXML private JFXToggleButton numStreetsToggle, numAvenuesToggle, legnthStreetsToggle, legnthAvenuesToggle, mapNameToggle;


    int Number_of_streets, Number_of_avenues, Length_of_streets, Length_of_avenues;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numStreetsToggle.selectedProperty().addListener((observable, oldValue, newValue) ->
                NumberOfStreets.setDisable(!NumberOfStreets.isDisabled()));
        numAvenuesToggle.selectedProperty().addListener((observable, oldValue, newValue) ->
                NumberOfAvenues.setDisable(!NumberOfAvenues.isDisabled()));
        legnthStreetsToggle.selectedProperty().addListener((observable, oldValue, newValue) ->
                LengthOfStreets.setDisable(!LengthOfStreets.isDisabled()));
        legnthAvenuesToggle.selectedProperty().addListener((observable, oldValue, newValue) ->
                LengthOfAvenues.setDisable(!LengthOfAvenues.isDisabled()));
        mapNameToggle.selectedProperty().addListener((observable, oldValue, newValue) ->
                mapNameTextField.setDisable(!mapNameTextField.isDisabled()));
    }


    @Override
    protected boolean getAndValidateUserInput(ActionEvent event) {
        String errorMessage = "";
        if(!NumberOfStreets.isDisabled()) {
            try{
                Number_of_streets = Integer.parseInt(NumberOfStreets.getText());
                //checking boundaries
                if(Number_of_streets < 2 || Number_of_streets > 50) throw new NumberFormatException();
            } catch(NumberFormatException e) {
                errorMessage += "Number of Streets input is invalid\n";
            }
        }
        if(!NumberOfAvenues.isDisabled()) {
            try{
                Number_of_avenues = Integer.parseInt(NumberOfAvenues.getText());
                //checking boundaries
                if(Number_of_avenues < 2 || Number_of_avenues > 50) throw new NumberFormatException();
            } catch(NumberFormatException e) {
                errorMessage += "Number of Avenues input is invalid\n";
            }
        }
        if(!LengthOfStreets.isDisabled()) {
            try{
                Length_of_streets = Integer.parseInt(LengthOfStreets.getText());
                //checking boundaries
                if(Length_of_streets < 50 || Length_of_streets > 500) throw new NumberFormatException();
            } catch(NumberFormatException e) {
                errorMessage += "Length of Streets input is invalid\n";
            }
        }
        if(!LengthOfAvenues.isDisabled()) {
            try{
                Length_of_avenues = Integer.parseInt(LengthOfAvenues.getText());
                //checking boundaries
                if(Length_of_avenues < 100 || Length_of_avenues > 1000) throw new NumberFormatException();
            } catch(NumberFormatException e) {
                errorMessage += "Length of Avenues input is invalid\n";
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
        GridCityMapSimulator gridCityMapSimulator = new GridCityMapSimulator();
        if(!NumberOfStreets.isDisabled()) gridCityMapSimulator.setNumOfStreets(Number_of_streets);
        if(!NumberOfAvenues.isDisabled()) gridCityMapSimulator.setNumOfAvenues(Number_of_avenues);
        if(!LengthOfAvenues.isDisabled()) gridCityMapSimulator.setAvenueLength(Length_of_avenues);
        if(!LengthOfStreets.isDisabled()) gridCityMapSimulator.setStreetLength(Length_of_streets);
        return gridCityMapSimulator;
    }
}
