package absController;



import dataObjects.dtoCustomer.DTOCustomer;
import dataObjects.dtoBank.dtoAccount.DTOLoan;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import logic.UIInterfaceLogic;
import logic.YazLogicDesktop;
import logic.bank.Bank;
import logic.bank.XmlSerialization;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ABSController implements Initializable {

    private CustomerController customerController;

    private SecondAdminController secondAdminController;
    private LoansListController loansListController;
    private CustomersListController customersListController;

    /////////////public static = just for to get the bank example that I create here to customerController
    ///////////// new Bank() = for this line I had to do Bank.Bank public////////////
    public static UIInterfaceLogic bank = new Bank();




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

    /*public void createBankExample() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        DTOCustomer customer0 = bank.customerBuild("Menash", 5000);
        DTOCustomer customer1 = bank.customerBuild("Avrum", 1000);
        DTOCustomer customer2 = bank.customerBuild("Tikva", 10000);

        DTOLoan loan1 = bank.loanBuilder("build a room", "Avrum", "Renovate", 3000, 10, 2,3);
        DTOLoan loan2 = bank.loanBuilder("Bar Mitzva", "Tikva", "OverdraftCover", 4000, 10, 1,3);
        DTOLoan loan3 = bank.loanBuilder("Wedding", "Menash", "Setup a business", 42000, 21, 7,20);

        String a =new String("Renovate");
        bank.getCategoriesGroup().add(a);
        bank.getCategoriesGroup().add("OverdraftCover");
        bank.getCategoriesGroup().add("Setup a business");

        bank.getLoansList().add(loan1);
        bank.getLoansList().add(loan2);
        bank.getLoansList().add(loan3);

        bank.getCustomers().add(customer0);
        bank.getCustomers().add(customer1);
        bank.getCustomers().add(customer2);
    }*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerController = myFXMLLoader("/application/desktop/MyCustomerView.fxml");
        secondAdminController=myFXMLLoader("/application/desktop/SecondMyAdminView.fxml");
        loansListController=myFXMLLoader("/application/desktop/LoansListViewer.fxml");
        customersListController=myFXMLLoader("/application/desktop/CustomerListViewer.fxml");

        //C:\Users\Eliran\IdeaProjects\ABS\model\src\resources
        loansListController.LoansListView.getSelectionModel().selectedItemProperty().addListener(e -> {
            if(!loansListController.LoansListView.getItems().isEmpty()) {
                loansListController.loansAccordionInformation.setVisible(true);
                DTOLoan localLoan = loansListController.LoansListView.getSelectionModel().getSelectedItem();
                loansListController.lendersTableView.setItems(FXCollections.observableArrayList(localLoan.getListOfInlays()));
            }
        });

        customersListController.customersListView.getSelectionModel().selectedItemProperty().addListener(e -> {
            if(! customersListController.customersListView.getItems().isEmpty()) {
                customersListController.customersAccordionInformation.setVisible(true);
                DTOCustomer localCustomer = customersListController.customersListView.getSelectionModel().getSelectedItem();
                customersListController.movementsTableView.setItems(FXCollections.observableArrayList(localCustomer.getMovements()));
                customersListController.loansListLoanerView.getItems().clear();
                for (DTOLoan dtoLoan:bank.getCustomerLoanersList(localCustomer.getCustomerName()))
                     customersListController.loansListLoanerView.getItems().add(dtoLoan);

            }
        });


        viewBy.onContextMenuRequestedProperty().addListener(e->viewBy.setText(viewBy.getContextMenu().toString()));

        Customer.setOnAction(e -> {
            myBorderPane.setCenter(customerController.customerTablePane);
        });


        Admin.setOnAction(e->{
            myBorderPane.setCenter(secondAdminController.adminGridPane);
            viewBy.setText("Admin");
        });


        secondAdminController.increaseYazButton.setOnAction(e->
        YazLogicDesktop.currentYazUnitProperty.setValue(YazLogicDesktop.currentYazUnitProperty.getValue()+1));
        YazLogicDesktop.currentYazUnitProperty.addListener(((observable, oldValue, newValue) -> currentYaz.setText("Current Yaz : "+newValue)));

        YazLogicDesktop.currentYazUnitProperty.addListener(((observable, oldValue, newValue) -> {
            try {
                bank.yazProgressLogic(true);
                showLoanInformationInAdminView();
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }));


       secondAdminController.loadFileButton.setOnAction(e-> {
            String file=fileChooserImplementation(e);
            try {
                bank=XmlSerialization.buildBank(file.trim());
                filePath.setText("File Path : "+file);
                YazLogicDesktop.currentYazUnitProperty.setValue(1);
                showLoanInformationInAdminView();
                showCustomerInformationAdminView();
                secondAdminController.LoansBoardPane.setCenter(loansListController.LoansMainGridPane);
                secondAdminController.CustomerBoardPane.setCenter(customersListController.CustomersMainGridPane);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //ToDo: Function


    }

    private void showLoanInformationInAdminView() {
        loansListController.LoansListView.getItems().clear();
        for (DTOLoan dtoLoan:bank.getLoansList()) {
            loansListController.LoansListView.getItems().add(dtoLoan);
        }
    }

    private void showCustomerInformationAdminView(){
        customersListController.customersListView.getItems().clear();
        for (DTOCustomer dtoCustomer:bank.getCustomers()) {
            customersListController.customersListView.getItems().add(dtoCustomer);
        }

    }

    /*private void showLoanInformationInAdminView()
    {
        for (DTOLoan dtoLoan:bank.getLoansList()) {
            LoanTitleTableController loanTitleTableController=myFXMLLoader("/application/desktop/LoanTitleTableView.fxml");
            loanTitleTableControllerList.add(loanTitleTableController);
            adminController.loansAccordion.getPanes().add(loanTitleTableController.loanTitledPane);
            List<DTOLoan> dtoLoans=new ArrayList<>();
            dtoLoans.add(dtoLoan);
            loanTitleTableController.lendersTableView.setItems(FXCollections.observableArrayList(dtoLoan.getListOfInlays()));
            loanTitleTableController.loansTableView.setItems(FXCollections.observableArrayList(dtoLoans));
            loanTitleTableController.loanTitledPane.setText("ID:"+dtoLoan.getId()+"-"+"Owner:"+dtoLoan.getOwner());

        }
    }*/

    private String fileChooserImplementation(javafx.event.ActionEvent e){
        Node node = (Node) e.getSource();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML","*.xml"));
        fileChooser.setTitle("Load File");
        return fileChooser.showOpenDialog(node.getScene().getWindow()).getPath();
    }

    private <T> T myFXMLLoader(String resource){
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url =  getClass().getResource(resource);
        fxmlLoader.setLocation(url);
        try {
            fxmlLoader.load(fxmlLoader.getLocation().openStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return fxmlLoader.getController();
    }
}

