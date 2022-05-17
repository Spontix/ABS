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
import logic.UIInterfaceLogic;
import logic.YazLogicDesktop;
import logic.bank.Bank;
import logic.bank.XmlSerialization;

import javax.swing.border.Border;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ABSController implements Initializable {

    private CustomerController customerController;
    private LoanTitleTableController loanTitleTableController;
    private AdminController adminController;
    private UIInterfaceLogic bank;

    @FXML
    protected MenuButton viewBy;

    @FXML
    protected MenuItem Admin;

    @FXML
    protected MenuItem Customer;

    @FXML
    protected Label filePath;

    @FXML
    protected Label currentYaz;

    @FXML
    protected BorderPane myBorderPane;

    @FXML
    void contextMenuRequested(ContextMenuEvent event) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //ToDo: Function
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/application/desktop/MyCustomerView.fxml");
        fxmlLoader.setLocation(url);
        try {
            fxmlLoader.load(fxmlLoader.getLocation().openStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        customerController = fxmlLoader.getController();


        fxmlLoader = new FXMLLoader();
        url = getClass().getResource("/application/desktop/MyAdminView.fxml");
        fxmlLoader.setLocation(url);
        try {
            fxmlLoader.load(fxmlLoader.getLocation().openStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        adminController=fxmlLoader.getController();
        adminController.setBank(bank);

        //ToDo: Function
        Customer.setOnAction(e -> myBorderPane.setCenter(customerController.customerTablePane));
        Admin.setOnAction(e->myBorderPane.setCenter(adminController.adminGridPane));
        //ToDo: Function
        adminController.increaseYazButton.setOnAction(e-> YazLogicDesktop.currentYazUnitProperty.setValue(YazLogicDesktop.currentYazUnitProperty.getValue()+1));
        YazLogicDesktop.currentYazUnitProperty.addListener(((observable, oldValue, newValue) -> currentYaz.setText("Current Yaz : "+newValue)));


    }
}

