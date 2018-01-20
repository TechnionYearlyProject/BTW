package il.ac.technion.cs.yp.btw.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by anat ana on 19/01/2018.
 */

public class RoadRealTimeStatisticsController {
    @FXML private Text vehiclesNum, averageSpeed, lengthInKm;

    public void generateView(int length, double averageSpeed, int numOfVehicles, Parent root) throws IOException {

        Scene scene = new Scene(root);

        Stage stage = new Stage();

        stage.setScene(scene);
        stage.show();

        this.vehiclesNum.setText(String.valueOf(numOfVehicles));
        DecimalFormat df = new DecimalFormat("#.000");
        String formattedSpeed = df.format(averageSpeed);
        this.averageSpeed.setText(formattedSpeed);
//        this.averageSpeed.setText(String.valueOf(averageSpeed));
        this.lengthInKm.setText(String.valueOf(length));

    }
}
