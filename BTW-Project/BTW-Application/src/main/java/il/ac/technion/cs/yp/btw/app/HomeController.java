package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.*;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulator;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulatorImpl;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.db.BTWDataBaseImpl;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends SwitchToMapController implements Initializable {
    @FXML
    private ToggleGroup generate_city_toggle;
    @FXML
    private RadioButton grid_radio = new RadioButton("grid_radio");
    @FXML
    private RadioButton free_form_radio = new RadioButton("free_form_radio");

    @FXML private JFXTextField chooseMapTextBox;

    @FXML private JFXSpinner loadSpinner;

    @FXML private JFXButton load_button, generate_button;

//    @FXML
//    private Node anchor;

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
    }


    public void loadButtonClicked(ActionEvent actionEvent) {
        load_button.setDisable(true);
        generate_button.setDisable(true);
        loadSpinner.setVisible(true);
        new Thread(() -> {
            BTWDataBase dataBase = new BTWDataBaseImpl(chooseMapTextBox.getText());
            System.out.println("trying to load map: " + chooseMapTextBox.getText());
            boolean result = dataBase.loadMap();
            Platform.runLater(() -> {
                if(!result) {
                    load_button.setDisable(false);
                    generate_button.setDisable(false);
                    loadSpinner.setVisible(false);
                    showErrorDialog("Failed to load: Map name is not in the Database");
                } else {
                    new Thread(() -> {
                        CitySimulator citySimulator = new CitySimulatorImpl(dataBase);
                        Platform.runLater(() -> switchScreensToMap(actionEvent, citySimulator));
                    }).start();
                }
            });
        }).start();
    }

}
