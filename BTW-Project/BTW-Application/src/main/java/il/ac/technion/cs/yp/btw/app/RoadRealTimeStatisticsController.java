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
 * Class for control the show of road statistics.
 */

public class RoadRealTimeStatisticsController {
    @FXML private Text vehiclesNum, averageSpeed, lengthInKm;
    /**@author: Orel, Anat.
     *  - Orel implemented actually showing the function input on screen
     * @date: 20/1/18
     */
    public void generateView(int length, double averageSpeed, int numOfVehicles, Parent root) throws IOException {
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        this.vehiclesNum.setText(String.valueOf(numOfVehicles));
        DecimalFormat df = new DecimalFormat("#.000");
        String formattedSpeed = df.format(averageSpeed);
        if(numOfVehicles == 0) formattedSpeed = "0";
        this.averageSpeed.setText(formattedSpeed);
        this.lengthInKm.setText(String.valueOf(length));

    }
}
