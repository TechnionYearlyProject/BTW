package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import il.ac.technion.cs.yp.btw.mapgeneration.FreeFormMapSimulator;
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
public class GenerateFreeFormController extends GenerateCityController implements Initializable{
    @FXML private JFXTextField NumberOfBlocks;
    @FXML private JFXTextField Radius;
    @FXML private JFXToggleButton blocksToggle, radiusToggle, mapNameToggle;
    @FXML private JFXTextField mapNameTextField;
   // @FXML private JFXButton helpButton;

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
            errorMessage += validateMapName(mapNameTextField.getText());
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

    /**@author: Anat
     * @date: 01/5/2018
     */
    @FXML
    public void showHelpDialogForRadius(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText(null);
        alert.setContentText("The map is built within a circle.\nInsert the radius size you want the circle to have");
        alert.showAndWait();
    }

    /**@author: Anat
     * @date: 01/5/2018
     */
    @FXML
    public void showHelpDialogForNumBlocks(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText(null);
        alert.setContentText("The map is built from a collection of blocks\ncontaining several streets and avenues.\n" +
                "Enter the number of blocks you want to be on the map");
        alert.showAndWait();
    }
}
