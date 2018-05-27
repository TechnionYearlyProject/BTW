package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import il.ac.technion.cs.yp.btw.mapgeneration.GridCityMapSimulator;
import il.ac.technion.cs.yp.btw.mapgeneration.MapSimulator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**@author: Orel
 * @date: 20/1/18
 * all methods that don't specify an other author are by Orel
 */
public class GenerateGridController extends GenerateCityController implements Initializable{

    public JFXButton numStreetsHelper;
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
        logger.debug("Controller was initialized");
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
                if(Length_of_streets < 100 || Length_of_streets > 2000) throw new NumberFormatException();
            } catch(NumberFormatException e) {
                errorMessage += "Length of Streets input is invalid\n";
            }
        }
        if(!LengthOfAvenues.isDisabled()) {
            try{
                Length_of_avenues = Integer.parseInt(LengthOfAvenues.getText());
                //checking boundaries
                if(Length_of_avenues < 100 || Length_of_avenues > 2000) throw new NumberFormatException();
            } catch(NumberFormatException e) {
                errorMessage += "Length of Avenues input is invalid\n";
            }
        }
        if(!mapNameTextField.isDisabled()) {
            errorMessage = mapNameSetValueAndGetMessage(errorMessage, mapNameTextField);
        }
        if(!errorMessage.equals("")) {
            showErrorDialog(errorMessage);
            return false;
        }
        return true;
    }



    /**@author: Anat
     * @date: 01/05/2018
     */
  /*  @FXML
    protected void showHelpDialog(String helpMessage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText(null);
        alert.setContentText(helpMessage);
        alert.showAndWait();
    }
    */


    @Override
    protected MapSimulator createMapSimulator() {
        GridCityMapSimulator gridCityMapSimulator = new GridCityMapSimulator();
        if(!NumberOfStreets.isDisabled()) gridCityMapSimulator.setNumOfStreets(Number_of_streets);
        if(!NumberOfAvenues.isDisabled()) gridCityMapSimulator.setNumOfAvenues(Number_of_avenues);
        if(!LengthOfAvenues.isDisabled()) gridCityMapSimulator.setAvenueLength(Length_of_avenues);
        if(!LengthOfStreets.isDisabled()) gridCityMapSimulator.setStreetLength(Length_of_streets);
        return gridCityMapSimulator;
    }

    /**@author: Anat
     * @date: 27/4/2018
     */
    @FXML
    public void showHelpDialogForNumAvenues(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText(null);
        alert.setContentText("Enter the number of avenues you \nwant to have on your map.\n" +
                "Avenues are Located vertically in a grid city");
        alert.showAndWait();
    }

    /**@author: Anat
     * @date: 27/4/2018
     */
    @FXML
    public void showHelpDialogForLengthAvenues(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText(null);
        alert.setContentText("Enter the length of avenues you \n"+
                "want to have in your map.");
        alert.showAndWait();
    }

    /**@author: Anat
     * @date: 27/4/2018
     */
    @FXML
    public void showHelpDialogFornumStreets(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText(null);
        alert.setContentText("Enter the number of streets you \nwant to have on your map.\n" +
                "Streets are Located horizontally in a grid city");
        alert.showAndWait();
    }

    /**@author: Anat
     * @date: 27/4/2018
     */
    @FXML
    public void showHelpDialogForLengthStreets(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText(null);
        alert.setContentText("Enter the length of streets you \n"+
                "want to have in your map.");
        alert.showAndWait();
    }


}
