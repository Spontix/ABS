package absController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import logic.UIInterfaceLogic;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    private UIInterfaceLogic bank;


    @FXML
    protected TabPane customerTablePane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
