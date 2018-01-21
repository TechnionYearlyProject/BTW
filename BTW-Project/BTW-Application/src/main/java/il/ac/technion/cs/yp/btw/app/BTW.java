package il.ac.technion.cs.yp.btw.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author: Orel
 * @date: 20/1/18
 */
public class BTW extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/home_layout.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("By The Way");
        stage.setScene(scene);
        stage.show();

    }
}
