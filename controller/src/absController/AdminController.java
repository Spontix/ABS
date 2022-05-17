package absController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import logic.UIInterfaceLogic;
import logic.YazLogicDesktop;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    private UIInterfaceLogic bank;


    @FXML
    protected GridPane adminGridPane;

    @FXML
    protected Button increaseYazButton;

    @FXML
    protected Button loadFileButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setBank(UIInterfaceLogic bank){
     this.bank=bank;
    }
}

