package absController;



import dataObjects.dtoCustomer.DTOCustomer;
import dataObjects.dtoBank.dtoAccount.DTOLoan;
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
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ABSController extends HelperFunction implements Initializable {

    private List<CustomerController> customersController;
    private AdminController adminController;
    private LoansListController loansListController;
    private CustomersListController customersListController;
    public static UIInterfaceLogic bank;


    @FXML
    protected MenuButton viewBy;

    @FXML
    protected MenuItem Admin;

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
        adminController =myFXMLLoader("/application/desktop/MyAdminView.fxml");
        loansListController=myFXMLLoader("/application/desktop/LoansListViewer.fxml");
        customersListController=myFXMLLoader("/application/desktop/CustomerListViewer.fxml");
        customersController=new ArrayList<>();
        Admin.setOnAction(ie->{
            myBorderPane.setCenter(adminController.adminGridPane);
            viewBy.setText("Admin");
            if(bank!=null) {
            showLoanInformationInAdminView(loansListController.LoansListView,bank.getLoansList());
            showCustomerInformationAdminView( customersListController.customersListView,bank.getCustomers());
            }
        });
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
                customersListController.loansListLenderView.getItems().clear();
                for (DTOLoan dtoLoan:bank.getCustomerBorrowersList(localCustomer.getCustomerName()))
                    customersListController.loansListLenderView.getItems().add(dtoLoan);


            }
        });

        adminController.increaseYazButton.setOnAction(e->
        YazLogicDesktop.currentYazUnitProperty.setValue(YazLogicDesktop.currentYazUnitProperty.getValue()+1));
        YazLogicDesktop.currentYazUnitProperty.addListener(((observable, oldValue, newValue) -> currentYaz.setText("Current Yaz : "+newValue)));

        YazLogicDesktop.currentYazUnitProperty.addListener(((observable, oldValue, newValue) -> {
            try {
                List<DTOLoan> loansThatShouldPay=bank.yazProgressLogicDesktop();
                clearAllLoansPayListView();
                addTheLoansThatShouldPayToAllTheLoansPayListView(loansThatShouldPay);
                showLoanInformationInAdminView(loansListController.LoansListView,bank.getLoansList());
                showCustomerInformationAdminView( customersListController.customersListView,bank.getCustomers());
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }));


       adminController.loadFileButton.setOnAction(e-> {
            String file=fileChooserImplementation(e);
            try {
                bank=XmlSerialization.buildBank(file.trim());
                filePath.setText("File Path : "+file);
                YazLogicDesktop.currentYazUnitProperty.setValue(1);
                showLoanInformationInAdminView(loansListController.LoansListView,bank.getLoansList());
                showCustomerInformationAdminView( customersListController.customersListView,bank.getCustomers());
                adminController.LoansBoardPane.setCenter(loansListController.LoansMainGridPane);
                adminController.CustomerBoardPane.setCenter(customersListController.CustomersMainGridPane);

                setTheAdminAndCustomersAsMenuItems();



            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //ToDo: Function


    }

    private void setTheAdminAndCustomersAsMenuItems(){
        MenuItem customerAsMenuItem;
        for (DTOCustomer dtoCustomer: bank.getCustomers()) {
            customerAsMenuItem=new MenuItem(dtoCustomer.getCustomerName());
            viewBy.getItems().add(customerAsMenuItem);
            CustomerController customerController =myFXMLLoader("/application/desktop/MyCustomerView.fxml");
            customersController.add(customerController);
            customerController.setCurrentCustomer(dtoCustomer);
            customerController.setBankInCustomerController(bank);
            customerController.setAbsControllerRef(this);
            List<DTOLoan> dtoLoan=bank.getLoansList().stream().filter(l-> Objects.equals(l.getOwner(), dtoCustomer.getCustomerName())).collect(Collectors.toList());
            if(!dtoLoan.isEmpty())
                bank.myAddListenerToStringPropertyLoans(customerController.notificationAreaListView,dtoLoan.get(0));
            customerAsMenuItem.setOnAction(e->{
                viewBy.setText(dtoCustomer.getCustomerName());
                try {
                    myBorderPane.setCenter(customerController.customerTablePane);
                    final ObservableList<String> categories = FXCollections.observableArrayList();
                    categories.addAll(bank.getCategoriesGroup());
                    customerController.categoriesList.getItems().clear();
                    customerController.categoriesList.getItems().addAll(categories);
                    showLoanInformationInAdminView(loansListController.LoansListView,bank.getLoansList());
                    showCustomerInformationAdminView( customersListController.customersListView,bank.getCustomers());
                    customerController.allInlayListView.getItems().clear();
                    customerController.chosenInlayListView.getItems().clear();
                    customerController.allInlayListView.setVisible(false);
                    customerController.chosenInlayListView.setVisible(false);
                    customerController.chooseLoanButton.setVisible(false);
                    customerController.unChosenLoanButton.setVisible(false);
                    customerController.doneChosenLoanButton.setVisible(false);
                }
                catch (Exception exception)
                {
                    //ToDo popup
                }
            });
        }

    }

    /*private void setTheAdminAndCustomersAsMenuItems(){
        MenuItem customerAsMenuItem;

        for (DTOCustomer dtoCustomer1: bank.getCustomers()) {
            customerAsMenuItem=new MenuItem(dtoCustomer1.getCustomerName());
            viewBy.getItems().add(customerAsMenuItem);
            viewBy.setText(dtoCustomer1.getCustomerName());
        }

        for (DTOCustomer dtoCustomer: bank.getCustomers()) {
            CustomerController customerController =myFXMLLoader("/application/desktop/MyCustomerView.fxml");
            List<CustomerController> customerC=customersController.stream().filter(c->c.dtoCustomer.getCustomerName()== dtoCustomer.getCustomerName()).collect(Collectors.toList());
            if(!customerC.isEmpty())
            {
                customersController.stream().
            customersController.add(customerController);
            customerController.setCurrentCustomer(dtoCustomer);
            customerController.setBankInCustomerController(bank);

                try {
                    myBorderPane.setCenter(customerController.customerTablePane);
                    final ObservableList<String> categories = FXCollections.observableArrayList();
                    categories.addAll(bank.getCategoriesGroup());
                    customerController.categoriesList.getItems().clear();
                    customerController.categoriesList.getItems().addAll(categories);

                }
                catch (Exception exception)
                {
                    //ToDo popup
                }
            });
        }

    }*/

    private String fileChooserImplementation(javafx.event.ActionEvent e){
        Node node = (Node) e.getSource();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML","*.xml"));
        fileChooser.setTitle("Load File");
        return fileChooser.showOpenDialog(node.getScene().getWindow()).getPath();
    }

    protected void  addTheLoansThatShouldPayToAllTheLoansPayListView(List<DTOLoan> loansThatShouldPay){
        for (CustomerController customerController: customersController) {
            for (DTOLoan dtoLoan: loansThatShouldPay) {
                if(Objects.equals(customerController.dtoCustomer.getCustomerName(), dtoLoan.getOwner())) {
                    customerController.loansThatShouldBePaidListView.getItems().add(dtoLoan);
                }
            }
        }
    }

    protected void clearAllLoansPayListView(){
        for (CustomerController customerController: customersController) {
            customerController.loansThatShouldBePaidListView.getItems().clear();
        }
    }

}

