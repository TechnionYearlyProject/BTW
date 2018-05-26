package il.ac.technion.cs.yp.btw.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

/**
 * @author: Orel
 * @date: 20/1/18
 */
public class BTW extends Application {
//    final static Logger logger = Logger.getLogger(BTW.class);
    final static Logger logger = Logger.getLogger("BTW");
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/home_layout.fxml"));
        logger.debug("BTW Begins Here");
        Scene scene = new Scene(root);

        stage.setTitle("By The Way");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setHeight(700);
        stage.show();
    }
}
