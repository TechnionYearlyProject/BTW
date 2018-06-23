package il.ac.technion.cs.yp.btw.app;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.db.BTWDataBaseImpl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by orel on 23/06/18.
 */
public class PrepareConfigsController extends SwitchToMapController implements Initializable {

    @FXML
    private ToggleGroup generate_city_toggle;
    @FXML
    private JFXRadioButton grid_radio, free_form_radio;

    @FXML private JFXTextField chooseMapTextBox, mapFromFileTextBox;

    @FXML private JFXSpinner loadSpinner;

    @FXML private JFXButton load_button, generate_button, load_file_button, attachButton, back_button;

    @FXML private HBox titleHBox, centerContent;

    final static Logger logger = Logger.getLogger("PrepareConfigsController");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grid_radio.setToggleGroup(generate_city_toggle);
        free_form_radio.setToggleGroup(generate_city_toggle);
        grid_radio.setSelected(true);
        Image buttonImage = new Image(getClass().getResourceAsStream("/icons8-attach-30.png"));
        attachButton.setGraphic(new ImageView(buttonImage));

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
        logger.debug("PrepareConfigsController Initialized");
    }


    private void initDynamicPosition() {
        titleHBox.translateXProperty()
                .bind(BTW.stage.widthProperty().subtract(titleHBox.widthProperty())
                        .divide(2));
        centerContent.translateXProperty()
                .bind(BTW.stage.widthProperty().subtract(centerContent.widthProperty())
                        .divide(2));
        AnchorPane.setRightAnchor(back_button, 20.0);
        AnchorPane.setTopAnchor(back_button, 60.0);
    }

    public void BackClicked(ActionEvent actionEvent) {
        switchScreens(actionEvent, "/fxml/choose_running_config.fxml");
    }

    public void attachButtonClicked(ActionEvent actionEvent) {
    }

    public void generateButtonClick(ActionEvent actionEvent) {
        RadioButton selectedRadioButton = (RadioButton) generate_city_toggle.getSelectedToggle();
        if(selectedRadioButton == null) return;
        String switchTo;
        if (selectedRadioButton.equals(grid_radio)) {
            switchTo = "/fxml/generate_grid.fxml";
        } else if(selectedRadioButton.equals(free_form_radio)) {
            switchTo = "/fxml/generate_free_form.fxml";
        }else{
            return;
        }
        GenerateCityController.acceptAction = DrawMapController.AcceptAction.SaveMap;
        switchScreens(actionEvent, switchTo);
    }

    public void loadFileButtonClicked(ActionEvent actionEvent) {
    }

    public void generateVehiclesButtonClicked(ActionEvent actionEvent) {
    }
}
