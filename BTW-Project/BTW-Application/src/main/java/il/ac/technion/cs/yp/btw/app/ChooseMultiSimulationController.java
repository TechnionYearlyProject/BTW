package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
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
public class ChooseMultiSimulationController  extends SwitchToMapController implements Initializable {

    private BTWDataBase mapDatabase;

    @FXML
    private HBox titleHBox, centerContent;
    @FXML private JFXButton back_button, start_button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Scene scene = new Scene((Parent)anchor, BTW.stage.getWidth(), BTW.stage.getHeight());
        BTW.stage.setScene(scene);
        back_button.setOnAction(this::BackClicked);
        back_button.setDisable(false);
        initCenterPanes();
    }

    private void BackClicked(ActionEvent actionEvent) {
        switchScreens(actionEvent, "/fxml/choose_running_config.fxml");
    }

    public void initCenterPanes() {
        titleHBox.translateXProperty()
                .bind(BTW.stage.widthProperty().subtract(titleHBox.widthProperty())
                        .divide(2));
        AnchorPane.setTopAnchor(titleHBox, 40.0);
        AnchorPane.setRightAnchor(back_button, 20.0);
        AnchorPane.setTopAnchor(back_button, 60.0);

        centerContent.translateXProperty()
                .bind(BTW.stage.widthProperty().subtract(centerContent.widthProperty())
                        .divide(2));

        AnchorPane.setRightAnchor(start_button, 30.0);
        AnchorPane.setBottomAnchor(start_button, 30.0);
    }

    public void initMapDatabase(BTWDataBase mapDatabase) {
        this.mapDatabase = mapDatabase;
    }

}
