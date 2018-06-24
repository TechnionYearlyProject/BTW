package il.ac.technion.cs.yp.btw.app;

import il.ac.technion.cs.yp.btw.citysimulation.CitySimulator;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import javafx.animation.FadeTransition;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

/**
 * Created by orel on 21/01/18.
 */
/**@author: Orel
 * @date: 20/1/18
 * all methods are by Orel
 */
public abstract class SwitchToMapController extends ShowErrorController {

    @FXML
    protected Node anchor;

    protected void switchScreens(ActionEvent event, String fxmlLocation) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            URL resource = getClass().getResource(fxmlLocation);
            transitionAnimationAndSwitch(fxmlLocation, stageTheEventSourceNodeBelongs, resource, anchor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void switchScreensToMap(ActionEvent event, CitySimulator citySimulator, BTWDataBase db) {
        switchScreensToMap(event, citySimulator, db, true, DrawMapController.AcceptAction.ChooseSimulation);
    }

    protected void switchScreensToMap(ActionEvent event, CitySimulator citySimulator, BTWDataBase db,
                                      boolean isVerify) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            transitionAndSwitchToMap(stageTheEventSourceNodeBelongs, citySimulator, db, isVerify, DrawMapController.AcceptAction.ChooseSimulation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void switchScreensToMap(ActionEvent event, CitySimulator citySimulator, BTWDataBase db,
                                      boolean isVerify, DrawMapController.AcceptAction acceptAction) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            transitionAndSwitchToMap(stageTheEventSourceNodeBelongs, citySimulator, db, isVerify, acceptAction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void transitionAnimationAndSwitch(String fxmlLocation, Stage stageTheEventSourceNodeBelongs,
                                                    URL resource, Node rootNode) throws IOException {
        Parent root;
        root = FXMLLoader.load(resource);
        transitionToParent(stageTheEventSourceNodeBelongs, rootNode, root);
    }

    public static void transitionToParent(Stage stageTheEventSourceNodeBelongs, Node rootNode, Parent targetParent) {
        int length = 300;
        FadeTransition fadeOut = new FadeTransition(Duration.millis(length), rootNode);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event1 -> {
            FadeTransition fadeIn = new FadeTransition(Duration.millis(length), targetParent);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
            DoubleProperty opacity = targetParent.opacityProperty();
            opacity.set(0);
            Scene scene = new Scene(targetParent);
            stageTheEventSourceNodeBelongs.setScene(scene);
        });
        fadeOut.play();
    }

    public void transitionAndSwitchToMap(Stage stageTheEventSourceNodeBelongs,
                                         CitySimulator citySimulator, BTWDataBase db, boolean isVerify,
                                         DrawMapController.AcceptAction acceptAction) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stageForDrawMap.fxml"));
        DrawMapController drawMapController = new DrawMapController();
        drawMapController.initCitySimulator(citySimulator);
        drawMapController.initStage(stageTheEventSourceNodeBelongs);
        drawMapController.initMapDatabase(db);
        drawMapController.initIsVerifyMap(isVerify);
        drawMapController.initAcceptAction(acceptAction);
        loader.setController(drawMapController);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
