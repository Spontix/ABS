package absController;



import dataObjects.dtoBank.dtoAccount.DTOLoanStatus;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import logic.UIInterfaceLogic;
import logic.YazLogicDesktop;
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
    private MenuItem defaultSkinMenuButton;

    @FXML
    protected MenuItem skinMenuButton;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminController =myFXMLLoader("MyAdminView.fxml");
        loansListController=myFXMLLoader("LoansListViewer.fxml");
        customersListController=myFXMLLoader("CustomerListViewer.fxml");
        customersController=new ArrayList<>();
        adminController.loadFileButton.setOnAction(e-> {
            try {
                String file=fileChooserImplementation(e);
                bank=XmlSerialization.buildBank(file.trim());
                filePath.setText("File Path : "+file);
                YazLogicDesktop.currentYazUnitProperty.setValue(1);
                showLoanInformationInAdminAndCustomerView(loansListController.LoansListView,bank.getLoansList(),false);
                showCustomerInformationAdminView( customersListController.customersListView,bank.getCustomers());
                adminController.LoansBoardPane.setCenter(loansListController.LoansMainGridPane);
                adminController.CustomerBoardPane.setCenter(customersListController.CustomersMainGridPane);

                setTheAdminAndCustomersAsMenuItems();
            }
            catch (NullPointerException ex){
                popupMessage("Error","Please be sure to choose a file...");
            }
            catch (Exception ex) {
                popupMessage("Error",ex.getMessage());
            }
        });
        onAdminMenuItemClick();
        //C:\Users\Eliran\IdeaProjects\ABS\model\src\resources
        onLoanClickProperty(loansListController,null);
        onCustomerClickProperty();
        adminController.increaseYazButton.setOnAction(e-> {
            YazLogicDesktop.currentYazUnitProperty.setValue(YazLogicDesktop.currentYazUnitProperty.getValue() + 1);
            loansListControllerHandler(loansListController);
        });
        YazLogicDesktop.currentYazUnitProperty.addListener(((observable, oldValue, newValue) -> {
            if(bank!=null)
               currentYaz.setText("Current Yaz : "+newValue);
        }));

        YazLogicDesktop.currentYazUnitProperty.addListener(((observable, oldValue, newValue) -> {
            try {
                List<DTOLoan> loansThatShouldPay=bank.yazProgressLogicDesktop();
                clearAllLoansPayListView();
                addTheLoansThatShouldPayToAllTheLoansPayListView(loansThatShouldPay);
                showLoanInformationInAdminAndCustomerView(loansListController.LoansListView,bank.getLoansList(),false);
                showCustomerInformationAdminView( customersListController.customersListView,bank.getCustomers());
            } catch (Exception e) {
                popupMessage("Error","Please be sure to choose a file...");
            }
        }));




        //ToDo: Function


    }

   public MenuItem getSkinMenuButton(){
        return skinMenuButton;
   }

   public MenuItem getDefaultSkinMenuButton(){
        return defaultSkinMenuButton;
   }

    private void setTheAdminAndCustomersAsMenuItems() {

        if( viewBy.getItems().size()>1)
             viewBy.getItems().remove(1,viewBy.getItems().size());
        for (DTOCustomer dtoCustomer : bank.getCustomers()) {
            MenuItem customerAsMenuItem = new MenuItem(dtoCustomer.getCustomerName());
            viewBy.getItems().add(customerAsMenuItem);
            CustomerController customerController = myFXMLLoader("MyCustomerView.fxml");
            customersController.add(customerController);
            onLoanClickProperty(customerController.loansListController,customerController);
            customerController.setCurrentCustomer(dtoCustomer);
            customerController.setBankInCustomerController(bank);
            customerController.setAbsControllerRef(this);
            List<DTOLoan> dtoLoan = bank.getLoansList().stream().filter(l -> Objects.equals(l.getOwner(), dtoCustomer.getCustomerName())).collect(Collectors.toList());
            if (!dtoLoan.isEmpty())
                bank.myAddListenerToStringPropertyLoans(customerController.notificationAreaListView, dtoLoan.get(0));
            customerAsMenuItem.setOnAction(e -> {
                    //viewBy.setText(dtoCustomer.getCustomerName());
                    myBorderPane.setCenter(customerController.customerTablePane);
                    final ObservableList<String> categories = FXCollections.observableArrayList();
                    categories.addAll(bank.getCategoriesGroup());
                    customerController.categoriesList.getItems().clear();
                    customerController.categoriesList.getItems().addAll(categories);
                  
                    showLoanInformationInAdminAndCustomerView(loansListController.LoansListView, bank.getLoansList(),false);
                    showLoanInformationInAdminAndCustomerView(customerController.loanerLoansListView, bank.getCustomerLoanersList(dtoCustomer.getCustomerName()),false);
                    showLoanInformationInAdminAndCustomerView(customerController.LenderLoansTableListView, bank.getCustomerBorrowersList(dtoCustomer.getCustomerName()),false);
                    showLoanInformationInAdminAndCustomerView(customerController.loansListController.LoansListView, bank.getCustomerLoanersList(dtoCustomer.getCustomerName()), true);
                    customerController.customerMovments.setItems(FXCollections.observableArrayList(dtoCustomer.getMovements()));
                    showCustomerInformationAdminView(customersListController.customersListView, bank.getCustomers());
                  
                    customerController.errorTextArea.setVisible(false);
                    customerController.allInlayListView.getItems().clear();
                    customerController.chosenInlayListView.getItems().clear();
                    customerController.allInlayListView.setVisible(false);
                    customerController.chosenInlayListView.setVisible(false);
                    customerController.chooseLoanButton.setVisible(false);
                    customerController.unChosenLoanButton.setVisible(false);
                    customerController.doneChosenLoanButton.setVisible(false);
                    loansListControllerHandler(customerController.loansListController);
            });
        }

    }

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
                    customerController.loansListController.LoansListView.getItems().add(dtoLoan);
                }
            }
        }
    }

    protected void clearAllLoansPayListView(){
        for (CustomerController customerController: customersController) {
            customerController.loansListController.LoansListView.getItems().clear();
        }
    }

    private void onLoanClickProperty(LoansListController loansListController,CustomerController customerController){
        loansListController.LoansListView.getSelectionModel().selectedItemProperty().addListener(e -> {
            if(!loansListController.LoansListView.getItems().isEmpty()) {
               DTOLoan selectedLoan= loansListControllerHandler(loansListController);
                if(customerController!=null) {
                    if (selectedLoan.getLoanStatus() == DTOLoanStatus.RISK || (!selectedLoan.getIsPaid() && selectedLoan.numberOfYazTillNextPulseWithoutTheIncOfWindowOfPaymentCounterDK() == 0)) {
                        customerController.payButton.setDisable(false);
                        customerController.payButton.setOnAction(e1 -> {
                         try {
                            if (selectedLoan.getLoanStatus() == DTOLoanStatus.RISK) {
                                TextInputDialog paymentDialog = new TextInputDialog();
                                paymentDialog.setTitle("Loan In Risk");
                                paymentDialog.setContentText("Please enter the amount of money you would like to pay:");
                                paymentDialog.setHeaderText("Current Balance: " + bank.getCustomerByName(selectedLoan.getOwner()).getAmount() + "\nDebt Amount: " + (selectedLoan.getDebt()));
                                paymentDialog.showAndWait();

                                if (paymentDialog.getResult() != null && (Integer.parseInt(paymentDialog.getResult()) <= selectedLoan.getDebt())) {
                                        bank.operateThePaymentOfTheLoanDesktop(selectedLoan, Integer.parseInt(paymentDialog.getResult()));
                                } else {
                                    throw new Exception("The payment was not preformed!");
                                }
                            } else if (selectedLoan.numberOfYazTillNextPulseWithoutTheIncOfWindowOfPaymentCounterDK() == 0) {
                                    bank.operateThePaymentOfTheLoanDesktop(selectedLoan, selectedLoan.paymentPerPulse());
                            }
                         } catch (Exception ex ) {
                             popupMessage("Error",ex.getMessage());
                         }
                            showLoanInformationInAdminAndCustomerView(loansListController.LoansListView, bank.getCustomerLoanersList(selectedLoan.getOwner()), true);
                            loansListControllerHandler(loansListController);
                            customerController.payButton.setDisable(true);
                            loansListController.loansAccordionInformation.setVisible(false);
                        });
                    }
                    if (selectedLoan.getLoanStatus() == DTOLoanStatus.ACTIVE) {
                        customerController.closeLoanButton.setDisable(false);
                        customerController.closeLoanButton.setOnAction(e2 -> {
                            try {
                                bank.operateThePaymentOfTheLoanDesktop(selectedLoan, selectedLoan.getTotalCapitalPayTillEnd());
                            } catch (InvocationTargetException | InstantiationException | IllegalAccessException ex) {
                                popupMessage("System Error!",ex.getMessage());
                            }
                            showLoanInformationInAdminAndCustomerView(loansListController.LoansListView, bank.getCustomerLoanersList(selectedLoan.getOwner()), true);
                            loansListControllerHandler(loansListController);
                            customerController.closeLoanButton.setDisable(true);
                            loansListController.loansAccordionInformation.setVisible(false);

                        });
                    }
                }
            }
        });
    }

    private void onCustomerClickProperty(){
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

    }

    private void onAdminMenuItemClick(){
        Admin.setOnAction(ie->{
            myBorderPane.setCenter(adminController.adminGridPane);
            viewBy.setText("Admin");
            if(bank!=null) {
                customersListController.customersAccordionInformation.setVisible(false);
                showLoanInformationInAdminAndCustomerView(loansListController.LoansListView,bank.getLoansList(),false);
                showCustomerInformationAdminView( customersListController.customersListView,bank.getCustomers());
                loansListControllerHandler(loansListController);
            }
        });
    }

    private DTOLoan loansListControllerHandler(LoansListController loansListController){
        clearComponents(loansListController);
        DTOLoan selectedLoan = loansListController.LoansListView.getSelectionModel().getSelectedItem();
        if(selectedLoan!=null) {
            loansListController.loansAccordionInformation.setVisible(true);
            if (selectedLoan.getLoanStatus() !=DTOLoanStatus.NEW) {
                loansListController.leftPaymentsLabel.setText("Left payments : " + String.valueOf(selectedLoan.getCapital() - selectedLoan.getCapitalSumLeftTillActive()));
                loansListController.totalPaymentsLabel.setText("Total payments : " + String.valueOf(selectedLoan.getCapitalSumLeftTillActive()));
                loansListController.lendersTableView.setItems(FXCollections.observableArrayList(selectedLoan.getListOfInlays()));
                loansListController.activeStatusTableView.setItems(FXCollections.observableArrayList(selectedLoan));
                loansListController.inRiskStatusTableView.setItems(FXCollections.observableArrayList(selectedLoan.getPaymentsInfoList()));
                loansListController.delayedPaymentsColumnRI.setText("Delayed payments : " + String.valueOf(selectedLoan.getInRiskCounter()));
                loansListController.totalDelayedColumnRI.setText("Total delayed : " + String.valueOf(selectedLoan.getDebt()));
                if (selectedLoan.getLoanStatus() == DTOLoanStatus.FINISHED) {
                    loansListController.endYazColumnFI.setVisible(true);
                    loansListController.startYazLabelFI.setVisible(true);
                    loansListController.endYazColumnFI.setText("End Yaz : " + String.valueOf(selectedLoan.getEndedYaz()));
                    loansListController.startYazLabelFI.setText("Start Yaz : " + String.valueOf(selectedLoan.getStartedYazInActive()));
                }
            }
        }
        return selectedLoan;
    }

    private void clearComponents(LoansListController loansListController){
        loansListController.activeStatusTableView.getItems().clear();
        loansListController.inRiskStatusTableView.getItems().clear();
        loansListController.loansAccordionInformation.setVisible(false);
        loansListController.endYazColumnFI.setVisible(false);
        loansListController.startYazLabelFI.setVisible(false);
        loansListController.leftPaymentsLabel.setText("");
        loansListController.totalPaymentsLabel.setText("");
        loansListController.lendersTableView.getItems().clear();
        loansListController.activeStatusTableView.getItems().clear();
        loansListController.inRiskStatusTableView.getItems().clear();
        loansListController.delayedPaymentsColumnRI.setText("");
        loansListController.totalDelayedColumnRI.setText("");
        loansListController.endYazColumnFI.setText("");
        loansListController.startYazLabelFI.setText("");
        loansListController.endYazColumnFI.setText("");
        loansListController.startYazLabelFI.setText("");
    }
}

