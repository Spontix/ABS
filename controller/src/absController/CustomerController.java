package absController;

import dataObjects.dtoBank.dtoAccount.DTOInlay;
import dataObjects.dtoBank.dtoAccount.DTOLoan;
import dataObjects.dtoBank.dtoAccount.DTOLoanStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import logic.UIInterfaceLogic;
import logic.bank.Bank;
import logic.bank.account.Loan;
import org.controlsfx.control.CheckComboBox;

import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;

public class CustomerController extends HelperFunction implements Initializable{
    private UIInterfaceLogic bank;
    final ObservableList<String> categories = FXCollections.observableArrayList();
    private LoansListController loansListController;

    @FXML
    protected TabPane customerTablePane;

    @FXML
    protected CheckComboBox<String> categoriesList;

    @FXML
    private BorderPane inlayLoansBorderPane;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loansListController=myFXMLLoader("/application/desktop/LoansListViewer.fxml");

    }

    public void setBankInCustomerController(UIInterfaceLogic bank){
        this.bank=bank;
    }

    @FXML
    private void MaximumLoanOwnershipPercentageActionLisener() {
    }

    @FXML
    private int MaximumLoansOpenToTheBorrowerActionLisener() {
        int maxLoansOpen = 0;
        String number = maximumLoansOpenToTheBorrower.getText();
        if (Objects.equals(number, ""))
            return 0;
        else {
            maxLoansOpen = Integer.parseInt((number));
            if (maxLoansOpen < 0)
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
        ///////////////////////// Missing - what customer is it to take the amount of the balance from him ////////////////////////
        if (amountFromUser <= 0 || amountFromUser > bank.getAmountOfCustomer(0))
            throw new NumberFormatException("In 'Amount to investment' - Invalid input!! Please enter a number greater than 0 and less than \" + bank.getAmountOfCustomer(0)");

        return amountFromUser;
    }




    @FXML
    private void ClickEnableInlayButtonActionLisener(ActionEvent event) {
        try {
            ObservableList<String> list = categoriesList.getCheckModel().getCheckedItems();
            int investAmount = investmentAmountActionListener();
            int minimumTotalYaz = MinimumTotalYazActionLisener();
            double minimumInterestYaz = MinimumInterestYazActionLisener();
            int maximumLoansOpenToTheBorrower = MaximumLoansOpenToTheBorrowerActionLisener();
            ///////////////////////// Missing - what customer is it to take the amount of the balance from him ////////////////////////
            DTOInlay dtoInlay = bank.inlayBuildForDK(bank.getCustomer(0), investAmount,list.toString().substring(1,list.toString().length()-1), minimumInterestYaz, minimumTotalYaz, maximumLoansOpenToTheBorrower);

            inlayLoansBorderPane.setCenter(loansListController.LoansMainGridPane);
            showLoanInformationInAdminView(loansListController.LoansListView,bank.loansSustainInlayDK(dtoInlay));
            DTOLoan localLoan = loansListController.LoansListView.getSelectionModel().getSelectedItem();
            loansListController.loansAccordionInformation.setVisible(false);
            if(localLoan!=null)
                loansListController.lendersTableView.setItems(FXCollections.observableArrayList(localLoan.getListOfInlays()));



        } catch (Exception e) {
            String message = e.getMessage();
            if(message.startsWith("For input string:", 0))
                message = "Invalid Input!! " + message;
            errorTextArea.setVisible(true);
            errorTextArea.setText(message);
        }

    }

    /*void setDisableFields(boolean choice){
        enableInlayButton.setDisable(choice);
        categoriesList.setDisable(choice);
        minimumInterestYaz.setDisable(choice);
        minimumTotalYaz.setDisable(choice);
        maximumLoansOpenToTheBorrower.setDisable(choice);
        maximumLoanOwnershipPercentage.setDisable(choice);
    }*/


    private void sleepForSomeTime() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {}
    }




}
