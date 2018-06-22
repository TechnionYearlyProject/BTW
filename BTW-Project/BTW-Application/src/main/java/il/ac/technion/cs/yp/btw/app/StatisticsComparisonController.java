package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Scene scene = new Scene((Parent)anchor, BTW.stage.getWidth(), BTW.stage.getHeight());
        BTW.stage.setScene(scene);
        initCenterPanes();
        back_button.setOnAction(this::BackClicked);
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
