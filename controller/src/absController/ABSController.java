package absController;


import dataObjects.dtoBank.DTOBank;
import dataObjects.dtoBank.dtoAccount.DTOLoan;
import dataObjects.dtoCustomer.DTOCustomer;
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
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

public class ABSController implements Initializable {

    private CustomerController customerController;
    private LoanTitleTableController loanTitleTableController;
    private AdminController adminController;
    /////////////public static = just for to get the bank example that I create here to customerController
    ///////////// new Bank() = for this line I had to do Bank.Bank public////////////
    public static UIInterfaceLogic bank = new Bank();

    ////////////// this ctor and the two blocks is for the example/////////////////////
    public ABSController(){
        try {
            createBankExample();
        } catch (InvocationTargetException | InstantiationException|IllegalAccessException e) {
        }
    }

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

    public void createBankExample() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        DTOCustomer customer0 = bank.customerBuild("Menash", 5000);
        DTOCustomer customer1 = bank.customerBuild("Avrum", 1000);
        DTOCustomer customer2 = bank.customerBuild("Tikva", 10000);

        DTOLoan loan1 = bank.loanBuilder("build a room", "Avrum", "Renovate", 3000, 10, 2,3);
        DTOLoan loan2 = bank.loanBuilder("Bar Mitzva", "Tikva", "OverdraftCover", 4000, 10, 1,3);
        DTOLoan loan3 = bank.loanBuilder("Wedding", "Menash", "Setup a business", 42000, 21, 7,20);

        bank.getCategoriesGroup().add("Renovate");
        bank.getCategoriesGroup().add("OverdraftCover");
        bank.getCategoriesGroup().add("Setup a business");

        bank.getLoansList().add(loan1);
        bank.getLoansList().add(loan2);
        bank.getLoansList().add(loan3);

        bank.getCustomers().add(customer0);
        bank.getCustomers().add(customer1);
        bank.getCustomers().add(customer2);
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

