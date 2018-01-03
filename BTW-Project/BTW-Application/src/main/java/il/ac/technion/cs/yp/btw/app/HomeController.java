package il.ac.technion.cs.yp.btw.app;

import javafx.animation.FadeTransition;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class HomeController {
    @FXML
    private ToggleGroup generate_city_toggle;
    @FXML
    private RadioButton grid_radio;
    @FXML
    private Node anchor;

    @FXML
    protected void generateButtonClick(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) generate_city_toggle.getSelectedToggle();
        if(selectedRadioButton == null) return;
        String switchTo;
        if (selectedRadioButton.equals(grid_radio)) {
            switchTo = "/fxml/generate_grid.fxml";
        } else {
            switchTo = "/fxml/generate_free_form.fxml";
        }
        switchScreens(event, switchTo);
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


}
