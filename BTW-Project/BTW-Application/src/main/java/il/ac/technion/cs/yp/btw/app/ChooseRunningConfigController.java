package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXTextField;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.db.BTWDataBaseImpl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class ChooseRunningConfigController extends SwitchToMapController implements Initializable {

    final static Logger logger = Logger.getLogger("ChooseRunningConfigController");

    @FXML
    private HBox titleHBox, centerContent;
    @FXML
    private StackPane lineSeperator, learningText;
    @FXML private JFXTextField chooseMapTextBox;
//    @FXML private Text learningText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDynamicPosition();
        new Thread(() -> {
            BTWDataBase dbForTables = new BTWDataBaseImpl("dbForTables");   // Shay - TO DO: separate tables names from the constructor
            Set<String> tablesNames = dbForTables.getTablesNames();
            Platform.runLater(() -> {
                if (tablesNames != null) {
                    logger.debug("Tables names are loaded, should bind to auto complete");
                    TextFields.bindAutoCompletion(chooseMapTextBox, tablesNames);
                }
            });
        }).start();
        logger.debug("Home Screen Initialized");
    }

    private void initDynamicPosition() {
        titleHBox.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                // scene is set for the first time. Now its the time to listen stage changes.
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow == null && newWindow != null) {
                        // stage is set. now is the right time to do whatever we need to the stage in the controller.
                        Stage stage = (Stage) newWindow;
                        titleHBox.translateXProperty()
                                .bind(stage.widthProperty().subtract(titleHBox.widthProperty())
                                        .divide(2));
                        centerContent.translateXProperty()
                                .bind(stage.widthProperty().subtract(centerContent.widthProperty())
                                        .divide(2));
                        learningText.translateXProperty()
                                .bind(stage.widthProperty().subtract(learningText.widthProperty())
                                        .divide(2));
                    }
                });
            }
        });
//        AnchorPane.setBottomAnchor(learningText, 40.0);
//        AnchorPane.setLeftAnchor(learningText, 80.0);
    }

    public void learningButtonClicked(ActionEvent actionEvent) {
    }

    public void compareButtonClicked(ActionEvent actionEvent) {
    }

    public void liveSimulationButtonClicked(ActionEvent actionEvent) {
        switchScreens(actionEvent,  "/fxml/home_layout.fxml");
    }

    public void prepareButtonClicked(ActionEvent actionEvent) {
    }
}
