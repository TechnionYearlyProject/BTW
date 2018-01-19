package il.ac.technion.cs.yp.btw.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by anat ana on 19/01/2018.
 */

public class RoadRealTimeStatisticsController {
 public void generateView(int length, double averageSpeed, int numOfVehicles) throws IOException {
     Parent root = FXMLLoader.load(getClass().getResource("fxml/RoadRealTimeStatistics.fxml"));

     Scene scene = new Scene(root);

     Stage stage = new Stage();

     stage.setScene(scene);
     stage.show();

 }
}
