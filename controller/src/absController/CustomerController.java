package absController;

import dataObjects.dtoBank.dtoAccount.DTOInlay;
import dataObjects.dtoBank.dtoAccount.DTOLoan;
import dataObjects.dtoBank.dtoAccount.DTOLoanStatus;
import dataObjects.dtoBank.dtoAccount.DTOMovement;
import dataObjects.dtoCustomer.DTOCustomer;
import javafx.animation.FillTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import logic.UIInterfaceLogic;
import logic.bank.Bank;
import logic.bank.account.Loan;
import logic.customer.Customer;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.GridView;

import java.awt.event.MouseEvent;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class CustomerController extends HelperFunction implements Initializable{
    private UIInterfaceLogic bank;
    protected DTOCustomer dtoCustomer;
    protected ListView<DTOLoan> allInlayListView;
    protected ListView<DTOLoan> chosenInlayListView;
    protected ABSController absControllerRef;

    @FXML
    protected ListView<DTOMovement> listViewMovments;

    @FXML
    private Tab informationTab;

    @FXML
    protected ListView<DTOLoan> loanerLoansListView;

    @FXML
    protected ListView<DTOLoan> LenderLoansTableListView;

    @FXML
    private TextField amountTextField;

    @FXML
    private Label errorAmountLabel;

    @FXML
    private Button chargeButton;

    @FXML
    private Button withdrawButton;

    @FXML
    protected Button doneChosenLoanButton;

    @FXML
    protected Button unChosenLoanButton;

    @FXML
    protected Button chooseLoanButton;

    @FXML
    private BorderPane chosenInlayLoansBorderPane;

    @FXML
    private BorderPane allInlayLoansBorderPane;

    @FXML
    protected TabPane customerTablePane;

    @FXML
    protected CheckComboBox<String> categoriesList;

    @FXML
    private ListView<DTOLoan> inlayLoansChosen;

    @FXML
    protected Button enableInlayButton;

    @FXML
    private TextField investmentAmount;

    @FXML
    private TextArea errorTextArea;

    @FXML
    private TextField minimumInterestYaz;

    @FXML
    private TextField minimumTotalYaz;

    @FXML
    private TextField maximumLoansOpenToTheBorrower;

    @FXML
    private TextField maximumLoanOwnershipPercentage;

    @FXML
    protected ListView<String> notificationAreaListView;

    @FXML
    protected ListView<DTOLoan> loansThatShouldBePaidListView;

    //ToDo: create property FinalBalance=amount in DTOCustomer that will listen for every change, that we will be possible to show current balance all the time.
    @FXML
    private Label currentFinalBalance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chosenInlayListView=new ListView<>();
        allInlayListView=new ListView<>();
        informationTab.setOnSelectionChanged(e-> {
            errorAmountLabel.setVisible(false);
            amountTextField.clear();
            if (bank != null) {

                setCurrentCustomer(bank.getCustomerByName(dtoCustomer.getCustomerName()));
                showLoanInformationInAdminView(loanerLoansListView, bank.getCustomerLoanersList(dtoCustomer.getCustomerName()));
                showLoanInformationInAdminView(LenderLoansTableListView, bank.getCustomerBorrowersList(dtoCustomer.getCustomerName()));
                listViewMovments.setItems(FXCollections.observableArrayList(dtoCustomer.getMovements()));

            }
        });
        chargeButton.setOnAction(c->{
            chargeOrWithdrawAction(1);
        });

        withdrawButton.setOnAction(w->{
            chargeOrWithdrawAction(2);
        });
    }

    private void chargeOrWithdrawAction(int indexOperation){
        errorAmountLabel.setVisible(false);
        String number = amountTextField.getText();

        if (Objects.equals(number, "")) {
            errorAmountLabel.setVisible(true);
        }
        try {
            int amount = Integer.parseInt(number);
            if (indexOperation == 1) {
                if (amount <= 0) {
                    throw new Exception();
                }
                DTOMovement dtoMovement = bank.movementBuildToCustomer(dtoCustomer, amount, "+", bank.getCustomerByName(dtoCustomer.getCustomerName()).getAmount(), bank.getCustomerByName(dtoCustomer.getCustomerName()).getAmount() + amount);
                bank.cashDeposit(bank.getRealCustomerByName(dtoCustomer.getCustomerName()), amount);
                listViewMovments.getItems().add(dtoMovement);

            } else if (indexOperation == 2) {
                if (amount <= 0 || amount > dtoCustomer.getAmount()) {
                    throw new Exception();
                }
                DTOMovement dtoMovement = bank.movementBuildToCustomer(dtoCustomer, amount, "-", bank.getCustomerByName(dtoCustomer.getCustomerName()).getAmount(), bank.getCustomerByName(dtoCustomer.getCustomerName()).getAmount() - amount);
                bank.cashWithdrawal(bank.getRealCustomerByName(dtoCustomer.getCustomerName()), amount);
                listViewMovments.getItems().add(dtoMovement);
            }
        }
        catch(Exception ex){
            errorAmountLabel.setVisible(true);
        }
    }

    public void setBankInCustomerController(UIInterfaceLogic bank){this.bank= bank;}



    @FXML
    private int MaximumLoanOwnershipPercentageActionLisener() {
        int maxLoanOwnershipPercentage=0;
        String number = maximumLoanOwnershipPercentage.getText();
        if (Objects.equals(number, ""))
            return 0;
        else {
            maxLoanOwnershipPercentage = Integer.parseInt((number));
            if (maxLoanOwnershipPercentage < 1 || maxLoanOwnershipPercentage > 100)
                throw new NumberFormatException("In 'Maximum loan ownership percentage' - Invalid input!! Please enter a number between 1 and 100, or leave this figure empty.");
        }
        return maxLoanOwnershipPercentage;
    }

    @FXML
    private int MaximumLoansOpenToTheBorrowerActionLisener() {
        int maxLoansOpen = 0;
        String number = maximumLoansOpenToTheBorrower.getText();
        if (Objects.equals(number, ""))
            return 0;
        else {
            maxLoansOpen = Integer.parseInt((number));
            if (maxLoansOpen <= 0)
                throw new NumberFormatException("In 'Maximum loans open to the borrower' - Invalid input!! Please enter a number greater than 0 or leave this figure empty.");
        }
        return maxLoansOpen;
    }

    @FXML
    private double MinimumInterestYazActionLisener() {
        double chosenMinInterestYaz = 0;
        String number = minimumInterestYaz.getText();
        if (Objects.equals(number, ""))
            return 0;
        else {
            chosenMinInterestYaz = Double.parseDouble((number));
            if (chosenMinInterestYaz <= 0 || chosenMinInterestYaz > 100)
                throw new NumberFormatException("In 'Minimum interest yaz' - Invalid input!! Please enter a number greater than 0 or leave this figure empty.");

        }
        return chosenMinInterestYaz;
    }

    @FXML
    private int MinimumTotalYazActionLisener() {
        int chosenMinYazTime = 0;
        String number = minimumTotalYaz.getText();
        if (Objects.equals(number, ""))
            return 0;
        else {
            chosenMinYazTime = Integer.parseInt((number));
            if (chosenMinYazTime <= 0)
                throw new NumberFormatException("In 'Minimum total yaz' - Invalid input!! Please enter a number greater than 0 or leave this figure empty.");
        }
        return chosenMinYazTime;
    }

    @FXML
    private int investmentAmountActionListener() throws Exception {
        String amount = investmentAmount.getText();
        int amountFromUser = 0;
        amountFromUser = Integer.parseInt(amount);
        if (amountFromUser <= 0 || amountFromUser > (bank.getCustomerByName(dtoCustomer.getCustomerName())).getAmount())
            throw new NumberFormatException("In 'Amount to investment' - Invalid input!! Please enter a number greater than 0 and less than " + (bank.getCustomerByName(dtoCustomer.getCustomerName())).getAmount());

        return amountFromUser;
    }




    @FXML
    private void ClickEnableInlayButtonActionLisener(ActionEvent event) {
        mySetVisible(false);
        try {
            ObservableList<String> list = categoriesList.getCheckModel().getCheckedItems();
            int investAmount = investmentAmountActionListener();
            int minimumTotalYaz = MinimumTotalYazActionLisener();
            double minimumInterestYaz = MinimumInterestYazActionLisener();
            int maximumLoansOpenToTheBorrower = MaximumLoansOpenToTheBorrowerActionLisener();
            int maximumLoanOwnershipPercentage = MaximumLoanOwnershipPercentageActionLisener();
            DTOInlay dtoInlay = bank.inlayBuildForDK(dtoCustomer, investAmount,list.toString().substring(1,list.toString().length()-1), minimumInterestYaz, minimumTotalYaz, maximumLoansOpenToTheBorrower);
            ArrayList<DTOLoan> loansSupportInlay = bank.loansSustainInlayDK(dtoInlay);
            if(loansSupportInlay.isEmpty()){
                popupMessage("Failed!!!", "No loans were found for this inlay.");
            }
            else {
                mySetVisible(true);
                allInlayLoansBorderPane.setCenter(allInlayListView);
                chosenInlayLoansBorderPane.setCenter(chosenInlayListView);
                showLoanInformationInAdminView(allInlayListView, loansSupportInlay);
                popupMessage("Success!!!", "Please click on the loan you want and then click on the 'Add loan' button.");

                chooseLoanButton.setOnAction(e->{
                    chooseLoanButtonSetOnAction();
                });
                unChosenLoanButton.setOnAction(e->{
                    unChosenLoanButtonSetOnAction();
                });
                doneChosenLoanButton.setOnAction(e->{
                    List<DTOMovement> dtoMovementList;
                    try {
                        dtoMovementList=bank.addMovementPerLoanFromInlayDK(dtoInlay, new ArrayList<>(chosenInlayListView.getItems()),investAmount,maximumLoanOwnershipPercentage);
                        List<DTOLoan> loansThatShouldPay=bank.yazProgressLogicDesktop();
                        absControllerRef.clearAllLoansPayListView();
                        absControllerRef.addTheLoansThatShouldPayToAllTheLoansPayListView(loansThatShouldPay);
                        allInlayListView.getItems().clear();
                        chosenInlayListView.getItems().clear();
                        allInlayListView.setVisible(false);
                        chosenInlayListView.setVisible(false);
                        chooseLoanButton.setVisible(false);
                        unChosenLoanButton.setVisible(false);
                        doneChosenLoanButton.setVisible(false);
                        popupMessage("Success!","The operation is done.");
                    } catch (InvocationTargetException | InstantiationException | IllegalAccessException ex) {
                        ex.printStackTrace();
                    }

    private void unChosenLoanButtonSetOnAction(){
        DTOLoan localLoan = chosenInlayListView.getSelectionModel().getSelectedItem();
        if (localLoan != null){
            allInlayListView.getItems().add(localLoan);
            chooseLoanButton.setDisable(false);
            chosenInlayListView.getItems().removeAll(localLoan);
            if (chosenInlayListView.getItems().isEmpty())
                unChosenLoanButton.setDisable(true);
        }
    }

    private void chooseLoanButtonSetOnAction(){
        DTOLoan localLoan = allInlayListView.getSelectionModel().getSelectedItem();
        if (localLoan != null) {
            chosenInlayListView.getItems().add(localLoan);
            unChosenLoanButton.setDisable(false);
            allInlayListView.getItems().removeAll(localLoan);
            if(allInlayListView.getItems().isEmpty())
                chooseLoanButton.setDisable(true);
        }
    }

    private void mySetVisible(boolean parameter){
        allInlayListView.setVisible(parameter);
        chosenInlayListView.setVisible(parameter);
        chooseLoanButton.setVisible(parameter);
        unChosenLoanButton.setVisible(parameter);
        doneChosenLoanButton.setVisible(parameter);
    }

    private void popupMessage(String title, String contentText)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


    private void sleepForSomeTime() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {}
    }


    protected void setCurrentCustomer(DTOCustomer dtoCustomer){
        this.dtoCustomer=dtoCustomer;
    }

    protected void setAbsControllerRef(ABSController absController){
        this.absControllerRef=absController;
    }


}
