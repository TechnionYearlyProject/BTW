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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    @FXML private HBox titleHBox;
    @FXML private VBox centerContent;
    @FXML private JFXButton back_button, generate_button;

    private Stage stage;
   // @FXML private JFXButton helpButton;

    int Number_of_blocks, radius_val;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        blocksToggle.selectedProperty().addListener((observable, oldValue, newValue) ->
                textToggleAction(NumberOfBlocks));
        radiusToggle.selectedProperty().addListener((observable, oldValue, newValue) ->
                textToggleAction(Radius));
//        mapNameToggle.selectedProperty().addListener((observable, oldValue, newValue) ->
//                textToggleAction(mapNameTextField));
        mapNameToggle.setVisible(false);
        if(acceptAction == DrawMapController.AcceptAction.SaveMap) {
            mapNameToggle.setSelected(true);
            mapNameTextField.setDisable(false);
        } else {
            mapNameToggle.setSelected(false);
            mapNameTextField.setVisible(false);
        }

        stage = BTW.stage;
        titleHBox.translateXProperty()
                .bind(stage.widthProperty().subtract(titleHBox.widthProperty())
                        .divide(2));
        centerContent.translateXProperty()
                .bind(stage.widthProperty().subtract(centerContent.widthProperty())
                        .divide(2));

        AnchorPane.setTopAnchor(titleHBox, 10.0);
        AnchorPane.setRightAnchor(back_button, 20.0);
        AnchorPane.setTopAnchor(back_button, 60.0);
        AnchorPane.setRightAnchor(generate_button, 30.0);


        logger.debug("Controller was initialized");
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
            errorMessage = mapNameSetValueAndGetMessage(errorMessage, mapNameTextField);
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
