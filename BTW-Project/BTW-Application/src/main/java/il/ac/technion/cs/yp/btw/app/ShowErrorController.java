package il.ac.technion.cs.yp.btw.app;

import javafx.scene.control.Alert;

/**
 * Created by orel on 21/01/18.
 */
/**@author: Orel
 * @date: 20/1/18
 * all methods that don't specify an other author are by Orel
 */
public abstract class ShowErrorController {
    protected void showErrorDialog(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid input");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);

        alert.showAndWait();
    }
}
