package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulator;
import il.ac.technion.cs.yp.btw.citysimulation.CitySimulatorImpl;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.db.BTWDataBaseImpl;
import il.ac.technion.cs.yp.btw.geojson.GeoJsonParserImpl;
import il.ac.technion.cs.yp.btw.mapgeneration.GridCityMapSimulator;
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

public class GenerateGridController implements Initializable{
    @FXML private Node anchor;
    @FXML private JFXTextField NumberOfStreets;
    @FXML private JFXTextField NumberOfAvenues;
    @FXML private JFXTextField LengthOfStreets;
    @FXML private JFXTextField LengthOfAvenues;

    @FXML private JFXSpinner progress_spinner;
    @FXML private JFXButton generate_button, back_button;
    @FXML private JFXToggleButton numStreetsToggle, numAvenuesToggle, legnthStreetsToggle, legnthAvenuesToggle;

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
    }

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

    boolean getAndValidateUserInput(ActionEvent event) {
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
        if(!errorMessage.equals("")) {
            showErrorDialog(errorMessage, event);
            return false;
        }
        return true;
    }



    private void showErrorDialog(String errorMessage, ActionEvent event) {
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        JFXAlert<Void> alert = new JFXAlert<>(stage);
//        alert.setTitle("Invalid input");
//        alert.setHeaderText("Look, an Information Dialog");
//        alert.setContentText(errorMessage);
//
//        alert.show();
//
//        JFXDialog dialog = new JFXDialog();
//        dialog.setContent();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid input");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);

        alert.showAndWait();

//        alert.showAndWait();
    }

    //TODO:check input validity - exist & int
    @FXML protected void GenerateClicked(ActionEvent event) {
        if(!getAndValidateUserInput(event)) return; //if user input isn't valid there's nothing to do
        else {
            //TODO: for testing purposes
            System.out.println("input was valid");
            generate_button.setDisable(true);
            back_button.setDisable(true);
            progress_spinner.setVisible(true);
//            return;
        }

        //this is important code
        GridCityMapSimulator gridCityMapSimulator = new GridCityMapSimulator();
        if(!NumberOfStreets.isDisabled()) gridCityMapSimulator.setNumOfStreets(Number_of_streets);
        if(!NumberOfAvenues.isDisabled()) gridCityMapSimulator.setNumOfAvenues(Number_of_avenues);
        if(!LengthOfAvenues.isDisabled()) gridCityMapSimulator.setAvenueLength(Length_of_avenues);
        if(!LengthOfStreets.isDisabled()) gridCityMapSimulator.setStreetLength(Length_of_streets);


        new Thread(() -> {
            gridCityMapSimulator.build();

            String mapString = parseCitySimulationToGeoJsonString(gridCityMapSimulator);

            System.out.println(mapString);

            //Insert the new map to the database.
            BTWDataBase dataBase = new BTWDataBaseImpl("simulatedCity2_2");
            dataBase.saveMap(mapString);

            CitySimulator citySimulator = new CitySimulatorImpl(dataBase);
            Platform.runLater(() -> switchScreensToMap(event, citySimulator));
        }).start();


        /*gridCityMapSimulator.build();

        String mapString = parseCitySimulationToGeoJsonString(gridCityMapSimulator);

        System.out.println(mapString);

        //Insert the new map to the database.
        BTWDataBase dataBase = new BTWDataBaseImpl("simulatedCity2_2");
        dataBase.saveMap(mapString);

        CitySimulator citySimulator = new CitySimulatorImpl(dataBase);
        switchScreensToMap(event, citySimulator);*/

//        CityMap cityMap = citySimulator.saveMap();
//        switchScreensToMap(event, cityMap);


        //DrawMapController mapDrawer = new DrawMapController(cityMap);

//        FXMLLoader Loader = new FXMLLoader();
//        Loader.setLocation(getClass().getResource("/fxml/stageForDrawMap.fxml"));
//        try {
//            Loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        DrawMapController drawMapController = Loader.getController();
//        drawMapController.initCitySimulator(cityMap);
//
//        Parent p = Loader.getRoot();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(p));
//        stage.showAndWait();

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


    private String parseCitySimulationToGeoJsonString(GridCityMapSimulator gridCityMapSimulator) {
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
        return mapString;
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


//    private void switchScreensToMap(ActionEvent event, CityMap cityMap) {
    private void switchScreensToMap(ActionEvent event, CitySimulator citySimulator) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            //TODO: maybe remove resource
            URL resource = getClass().getResource("/fxml/stageForDrawMap.fxml");
//            transitionAndSwitchToMap(stageTheEventSourceNodeBelongs, resource, anchor, cityMap);
            transitionAndSwitchToMap(stageTheEventSourceNodeBelongs, resource, anchor, citySimulator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void transitionAnimationAndSwitch(String fxmlLocation, Stage stageTheEventSourceNodeBelongs,
                                                    URL resource, Node rootNode) throws IOException {
        Parent root = FXMLLoader.load(resource);
        transitionAndSwitchInner(stageTheEventSourceNodeBelongs, rootNode, root);
    }

    //getting borderPane (parent)
    private void transitionAndSwitchInner(Stage stageTheEventSourceNodeBelongs, Node rootNode, Parent root) {
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

    public void transitionAndSwitchToMap(Stage stageTheEventSourceNodeBelongs,
                                                    URL resource, Node rootNode, CitySimulator citySimulator) throws IOException {
//                                                    URL resource, Node rootNode, CityMap cityMap) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stageForDrawMap.fxml"));
        DrawMapController drawMapController = new DrawMapController();
//        drawMapController.initCitySimulator(cityMap);
        drawMapController.initCitySimulator(citySimulator);
        drawMapController.initStage(stageTheEventSourceNodeBelongs);
        loader.setController(drawMapController);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        DrawMapController drawMapController = loader.getController();
//        drawMapController.initCitySimulator(cityMap);
//        drawMapController.initStage(stageTheEventSourceNodeBelongs);
        //TODO: maybe remove the next 2 lines, depending on the DrawMapController init
//        Parent borderPane = loader.getRoot();
//        transitionAndSwitchInner(stageTheEventSourceNodeBelongs, rootNode, borderPane);
    }

}
