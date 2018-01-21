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

public class HomeController implements Initializable {
    @FXML
    private ToggleGroup generate_city_toggle;
    @FXML
    private RadioButton grid_radio = new RadioButton("grid_radio");
    @FXML
    private RadioButton free_form_radio = new RadioButton("free_form_radio");

//    @FXML
//    private JFXComboBox chooseMapCombo;

    @FXML private JFXTextField chooseMapTextBox;

    @FXML private JFXSpinner loadSpinner;

    @FXML private JFXButton load_button, generate_button;

    @FXML
    private Node anchor;

    public HomeController(){
        this.generate_city_toggle = new ToggleGroup();
    }
    @FXML
    protected void generateButtonClick(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) generate_city_toggle.getSelectedToggle();
        if(selectedRadioButton == null) return;
        String switchTo = null;
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
        generate_city_toggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle){
                if(generate_city_toggle.getSelectedToggle() != null){
                    if(generate_city_toggle.getSelectedToggle().toString().equalsIgnoreCase("grid_radio")){
                    }else if(generate_city_toggle.getSelectedToggle().toString().equalsIgnoreCase("free_form_radio")){

                    }
                }
            }
        });
//        chooseMapCombo.setStyle("-fx-font: 15px \"Arial\";");
    }

    private void switchScreens(ActionEvent event, String fxmlLocation) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            URL resource = getClass().getResource(fxmlLocation);
            transitionAnimationAndSwitch(fxmlLocation, stageTheEventSourceNodeBelongs, resource, anchor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void transitionAnimationAndSwitch(String fxmlLocation, Stage stageTheEventSourceNodeBelongs,
                                                    URL resource, Node rootNode) throws IOException {
        Parent root;
        root = FXMLLoader.load(resource);
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
                    //TODO: switch screen to map
                    new Thread(() -> {
                        CitySimulator citySimulator = new CitySimulatorImpl(dataBase);
                        Platform.runLater(() -> switchScreensToMap(actionEvent, citySimulator));
                    }).start();
                }
            });
        }).start();
    }

    private void switchScreensToMap(ActionEvent event, CitySimulator citySimulator) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            transitionAndSwitchToMap(stageTheEventSourceNodeBelongs, citySimulator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void transitionAndSwitchToMap(Stage stageTheEventSourceNodeBelongs,
                                         CitySimulator citySimulator) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stageForDrawMap.fxml"));
        DrawMapController drawMapController = new DrawMapController();
        drawMapController.initCitySimulator(citySimulator);
        drawMapController.initStage(stageTheEventSourceNodeBelongs);
        loader.setController(drawMapController);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showErrorDialog(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid input");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);

        alert.showAndWait();
    }

}
