package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import il.ac.technion.cs.yp.btw.citysimulation.Vehicle;
import il.ac.technion.cs.yp.btw.citysimulation.VehicleDescriptor;
import il.ac.technion.cs.yp.btw.citysimulation.VehicleEntry;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.classes.TrafficLight;
import il.ac.technion.cs.yp.btw.evaluation.EvaluationComparator;
import il.ac.technion.cs.yp.btw.evaluation.Evaluator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

/**
 * Created by orel on 22/06/18.
 */
public class StatisticsComparisonController extends SwitchToMapController implements Initializable {

    @FXML
    private AnchorPane tableAnchor;
    @FXML
    private HBox titleHBox;
    @FXML private JFXButton back_button;

    private Evaluator eval1, eval2;
    private List<VehicleEntry> vehiclesList;
    private String simulationType1, simulationType2;
    @FXML private Text simulation1Text, simulation2Text, avgWaitingTime1, avgWaitingTime2, avgDrivingTime1, avgDrivingTime2,
            avgRoadDriving1, avgRoadDriving2, avgDrivingTimeDiff, avgDrivingTimePercent, avgWaitingTimeDiff, avgWaitingTimePercent,
            avgRoadDrivingDiff, avgRoadDrivingPercent,waitingTime1, waitingTime2, waitingTimeDiff, waitingTimePercent,
            drivingTime1, drivingTime2,drivingTimeDiff, drivingTimePercent, roadDriving1, roadDriving2, roadDrivingDiff, roadDrivingPercent;
    @FXML private JFXComboBox<Label> vehicleComboBox, trafficLightComboBox, roadComboBox;
    private Paint defaultFill;
    private BTWDataBase mapDatabase;

    private Map<Integer, VehicleDescriptor> vehiclesMap = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Scene scene = new Scene((Parent)anchor, BTW.stage.getWidth(), BTW.stage.getHeight());
        BTW.stage.setScene(scene);
        initCenterPanes();
        back_button.setOnAction(this::BackClicked);
        initTextFields();
    }

    private void initTextFields() {
        defaultFill = avgDrivingTime1.getFill();
        simulation1Text.setText(simulationType1);
        simulation2Text.setText(simulationType2);
        EvaluationComparator comparator = new EvaluationComparator(eval1, eval2);
        setTimeText(avgDrivingTime1,eval1.averageTotalDrivingTime().seconds() );
        setTimeText(avgDrivingTime2,eval2.averageTotalDrivingTime().seconds() );
        setTimeText(avgRoadDriving1, eval1.averageDrivingTimeOnAllRoads().seconds());
        setTimeText(avgRoadDriving2, eval2.averageDrivingTimeOnAllRoads().seconds());
        setTimeText(avgWaitingTime1, eval1.averageWaitingTimeOnAllTrafficLights().seconds());
        setTimeText(avgWaitingTime2, eval2.averageWaitingTimeOnAllTrafficLights().seconds());
        setColoredTimeText(avgDrivingTimeDiff, comparator.compareAverageDrivingTimeOfVehicles());
        setColoredTimeText(avgRoadDrivingDiff, comparator.compareAverageDrivingTimeOnRoads());
        setColoredTimeText(avgWaitingTimeDiff, comparator.compareAverageWaitingTimeOnTrafficLights());
        setColoredTimePercent(avgDrivingTimePercent, comparator.compareAverageDrivingTimeOfVehiclesPercent());
        setColoredTimePercent(avgRoadDrivingPercent, comparator.compareAverageDrivingTimeOnRoadsPercent());
        setColoredTimePercent(avgWaitingTimePercent, comparator.compareAverageWaitingTimeOnTrafficLightsPercent());
        //now for the combo boxes
        new Thread(() -> {
            for(TrafficLight f : mapDatabase.getAllTrafficLights()) {
                Label l = new Label(f.getName());
                trafficLightComboBox.getItems().add(l);
            }
            for(Road r : mapDatabase.getAllRoads()) {
                Label l = new Label(r.getName());
                roadComboBox.getItems().add(l);
            }
            for(VehicleEntry v : vehiclesList) {
                Optional<VehicleDescriptor> descriptor = v.getDescriptor();
                if(descriptor.isPresent()) {
                    Label l = new Label(Integer.toString(descriptor.get().getID()));
                    vehicleComboBox.getItems().add(l);
                    vehiclesMap.put(descriptor.get().getID(), descriptor.get());
                }
            }
        }).start();
        vehicleComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                Integer id = Integer.parseInt(newValue.getText());
                VehicleDescriptor vec = vehiclesMap.get(id);
                setTimeText(drivingTime1, eval1.totalDrivingTime(vec).seconds());
                setTimeText(drivingTime2, eval2.totalDrivingTime(vec).seconds());
                setColoredTimeText(drivingTimeDiff, comparator.compareDrivingTimeOfVehicle(vec));
                setColoredTimePercent(drivingTimePercent, comparator.compareDrivingTimeOfVehiclePercent(vec));
            }
        });
        trafficLightComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                String id = newValue.getText();
                TrafficLight tl = mapDatabase.getTrafficLight(id);
                setTimeText(waitingTime1, eval1.averageWaitingTimeOnTrafficLight(tl).seconds());
                setTimeText(waitingTime2, eval2.averageWaitingTimeOnTrafficLight(tl).seconds());
                setColoredTimeText(waitingTimeDiff, comparator.compareWaitingTimeOnTrafficLight(tl));
                setColoredTimePercent(waitingTimePercent, comparator.compareWaitingTimeOnTrafficLightPercent(tl));
            }
        });
        roadComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                String id = newValue.getText();
                Road r = mapDatabase.getRoad(id);
                setTimeText(roadDriving1, eval1.averageDrivingTimeOnRoad(r).seconds());
                setTimeText(roadDriving2, eval2.averageDrivingTimeOnRoad(r).seconds());
                setColoredTimeText(roadDrivingDiff, comparator.compareDrivingTimeOnRoad(r));
                setColoredTimePercent(roadDrivingPercent, comparator.compareDrivingTimeOnRoadPercent(r));
            }
        });
    }

    private void setTimeText(Text text, Long seconds) {
        String timeString = getTimeString(seconds);
        text.setText(timeString);
    }

    private void setColoredTimeText(Text text, Long seconds) {
        if(seconds > 0) text.setFill(Color.GREEN);
        else if(seconds < 0){
            seconds = -seconds;
            text.setFill(Color.RED);
        } else {
            text.setFill(defaultFill);
        }
        String timeString = getTimeString(seconds);
        text.setText(timeString);
    }

    private void setColoredTimePercent(Text text, Double percent) {
        if(percent > 0) text.setFill(Color.GREEN);
        else if(percent < 0) {
            percent = -percent;
            text.setFill(Color.RED);
        } else {
            text.setFill(defaultFill);
        }
        String timeString = String.format ("%.2f", percent);
        timeString += "%";
        text.setText(timeString);
    }

    private String getTimeString(Long ticks) {
        int seconds = (int) (ticks % 60);
        int minutes = (int) ((ticks / (60)) % 60);
        int hours = (int) ((ticks / (60*60)) % 24);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public void initParams(Evaluator eval1, Evaluator eval2, List<VehicleEntry> list,
                           String simulationType1, String simulationType2, BTWDataBase mapDatabase) {
        this.eval1 = eval1;
        this.eval2 = eval2;
        this.vehiclesList = list;
        this.simulationType1 = simulationType1;
        this.simulationType2 = simulationType2;
        this.mapDatabase = mapDatabase;
    }

    private void BackClicked(ActionEvent actionEvent) {
        switchScreens(actionEvent, "/fxml/choose_running_config.fxml");
    }

    private void initCenterPanes() {
        titleHBox.translateXProperty()
                .bind(BTW.stage.widthProperty().subtract(titleHBox.widthProperty())
                        .divide(2));
        AnchorPane.setTopAnchor(titleHBox, 20.0);
        AnchorPane.setRightAnchor(back_button, 20.0);
        AnchorPane.setTopAnchor(back_button, 50.0);

        tableAnchor.translateXProperty()
                .bind(BTW.stage.widthProperty().subtract(tableAnchor.widthProperty())
                        .divide(2));
    }
}
