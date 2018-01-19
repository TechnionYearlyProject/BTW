package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXTextField;
import il.ac.technion.cs.yp.btw.citysimulation.CityMap;
import il.ac.technion.cs.yp.btw.citysimulation.CityMapImpl;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulator;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulatorImpl;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.db.BTWDataBaseImpl;
import il.ac.technion.cs.yp.btw.geojson.GeoJsonParserImpl;
import il.ac.technion.cs.yp.btw.mapgeneration.GridCityMapSimulator;
import javafx.animation.FadeTransition;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;

import static il.ac.technion.cs.yp.btw.app.HomeController.transitionAnimationAndSwitch;

public class GenerateGridController {
    @FXML private Node anchor;
    @FXML private JFXTextField NumberOfStreets;
    @FXML private JFXTextField NumberOfAvenues;
    @FXML private JFXTextField LengthOfStreets;
    @FXML private JFXTextField LengthOfAvenues;
    @FXML private JFXTextField StartLongtitude;
    @FXML private JFXTextField StartLatitude;

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

    //TODO:check input validity - exist & int
    @FXML protected void GenerateClicked(ActionEvent event) {
        //int Number_of_streets = Integer.parseInt(NumberOfStreets.getText());
//        int Number_of_avenues = Integer.parseInt(NumberOfAvenues.getText());
//        int Length_of_streets = Integer.parseInt(LengthOfStreets.getText());
//        int Length_of_avenues = Integer.parseInt(LengthOfAvenues.getText());
//        double Start_longtitude = Integer.parseInt(StartLongtitude.getText());
//        double Start_latitude = Integer.parseInt(StartLatitude.getText());

        GridCityMapSimulator gridCityMapSimulator = new GridCityMapSimulator();
//        gridCityMapSimulator.setNumOfStreets(Number_of_streets);
//        gridCityMapSimulator.setAvenueLength(Length_of_avenues);
//        gridCityMapSimulator.setNumOfAvenues(Number_of_avenues);
//        gridCityMapSimulator.setStreetLength(Length_of_streets);
//        gridCityMapSimulator.setStartXCoordinate(Start_longtitude);
//        gridCityMapSimulator.setStartYCoordinate(Start_latitude);
        gridCityMapSimulator.build();

        GeoJsonParserImpl geoJsonParser = new GeoJsonParserImpl();
        File mapFile = geoJsonParser.buildGeoJsonFromSimulation(gridCityMapSimulator);
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

        System.out.println(mapString);

        //Insert the new map to the database.
        BTWDataBase dataBase = new BTWDataBaseImpl("simulatedCity");
        dataBase.saveMap(mapString);

        CitySimulator citySimulator = new CitySimulatorImpl(dataBase);
        CityMap cityMap = citySimulator.saveMap();

        //DrawMapController mapDrawer = new DrawMapController(cityMap);

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/fxml/stageForDrawMap.fxml"));
        try {
            Loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DrawMapController drawMapController = Loader.getController();
        drawMapController.initCityMap(cityMap);

        Parent p = Loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.showAndWait();

//
//        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        String fxmlLocation = "/fxml/stageForDrawMap.fxml";
//        URL resource = getClass().getResource(fxmlLocation);
//        try {
//            transitionAnimationAndSwitch(fxmlLocation, stageTheEventSourceNodeBelongs, resource, anchor);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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



        //BTWDataBase db = new BTWDataBaseImpl("exampleMap");
        //db.saveMap();
        //CitySimulator simulator = new CitySimulatorImpl(db);
        //DrawMapController mapDrawer = new DrawMapController();
        //CityMap map = simulator.saveMap();
        //mapDrawer.draw(map);


        fadeOut.play();
    }
}
