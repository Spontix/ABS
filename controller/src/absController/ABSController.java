package absController;


import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.BorderPane;
import  absController.CustomerController;
import javax.swing.border.Border;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ABSController implements Initializable {

    private CustomerController customerController;

    @FXML
    private MenuButton viewBy;

    @FXML
    private MenuItem Admin;

    @FXML
    private MenuItem Customer;

    @FXML
    private Label filePath;

    @FXML
    private Label currentYaz;

    @FXML
    private BorderPane myBorderPane;

    @FXML
    void contextMenuRequested(ContextMenuEvent event) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/application/desktop/MyCustomerView.fxml");
        fxmlLoader.setLocation(url);
        try {
            fxmlLoader.load(fxmlLoader.getLocation().openStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        customerController = fxmlLoader.getController();
        Customer.setOnAction(e -> myBorderPane.setCenter(customerController.customerTablePane));
    }
}

