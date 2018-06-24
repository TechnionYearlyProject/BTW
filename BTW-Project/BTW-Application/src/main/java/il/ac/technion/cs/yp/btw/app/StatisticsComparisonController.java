package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import il.ac.technion.cs.yp.btw.citysimulation.VehicleDescriptor;
import il.ac.technion.cs.yp.btw.citysimulation.VehicleEntry;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.evaluation.EvaluationComparator;
import il.ac.technion.cs.yp.btw.evaluation.Evaluator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
            avgRoadDrivingDiff, avgRoadDrivingPercent;
    private BTWDataBase mapDatabase;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Scene scene = new Scene((Parent)anchor, BTW.stage.getWidth(), BTW.stage.getHeight());
        BTW.stage.setScene(scene);
        initCenterPanes();
        back_button.setOnAction(this::BackClicked);
        initTextFields();
    }

    private void initTextFields() {
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
    }

    private void setTimeText(Text text, Long seconds) {
        String timeString = getTimeString(seconds);
        text.setText(timeString);
    }

    private void setColoredTimeText(Text text, Long seconds) {
        if(seconds > 0) text.setFill(Color.GREEN);
        else {
            seconds = -seconds;
            text.setFill(Color.RED);
        }
        String timeString = getTimeString(seconds);
        text.setText(timeString);
    }

    private void setColoredTimePercent(Text text, Double percent) {
        if(percent > 0) text.setFill(Color.GREEN);
        else {
            percent = -percent;
            text.setFill(Color.RED);
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
