package il.ac.technion.cs.yp.btw;

/**
 * Hello world!
 *
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BTW extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/layout.fxml"));

        Scene scene = new Scene(root, 300, 275);

        stage.setTitle("BTW Welcome");
        stage.setScene(scene);
        stage.show();
    }
}
