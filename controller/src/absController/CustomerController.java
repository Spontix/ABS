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
import logic.UIInterfaceLogic;
import logic.bank.Bank;
import logic.bank.account.Loan;
import org.controlsfx.control.CheckComboBox;

import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;

public class CustomerController implements Initializable{
    private UIInterfaceLogic bank;

    @FXML
    TabPane customerTablePane;

    @FXML
    private CheckComboBox<String> categoriesList;

    @FXML
    private Button enableInlayButton;

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
    private Accordion accordion;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       final ObservableList<String> categories = FXCollections.observableArrayList();
       ////////////This two lines are for example - don't forget to delete it!!////////////////////
        //categories.add("Renovate");
        //categories.add("OverdraftCover");

        categories.addAll(bank.getCategoriesGroup());
        categoriesList.getItems().addAll(categories);
    }

    public void setBankInCustomerController(UIInterfaceLogic bank){
        this.bank=bank;
    }

    @FXML
    void AccordingAddLoanTitleTableActionLisener(MouseEvent event) {

    }


    @FXML
    void MaximumLoanOwnershipPercentageActionLisener() {
    }

    @FXML
    int MaximumLoansOpenToTheBorrowerActionLisener() {
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
    double MinimumInterestYazActionLisener() {
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
    int MinimumTotalYazActionLisener() {
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
    int investmentAmountActionListener() throws Exception {
        String amount = investmentAmount.getText();
        int amountFromUser = 0;
        amountFromUser = Integer.parseInt(amount);
        ///////////////////////// Missing - what customer is it to take the amount of the balance from him ////////////////////////
        if (amountFromUser <= 0 || amountFromUser > bank.getAmountOfCustomer(0))
            throw new NumberFormatException("In 'Amount to investment' - Invalid input!! Please enter a number greater than 0 and less than \" + bank.getAmountOfCustomer(0)");

        return amountFromUser;
    }




    @FXML
    void ClickEnableInlayButtonActionLisener(ActionEvent event) {
        try {
            ObservableList<String> list = categoriesList.getCheckModel().getCheckedItems();
            int investAmount = investmentAmountActionListener();
            int minimumTotalYaz = MinimumTotalYazActionLisener();
            double minimumInterestYaz = MinimumInterestYazActionLisener();
            int maximumLoansOpenToTheBorrower = MaximumLoansOpenToTheBorrowerActionLisener();
            ///////////////////////// Missing - what customer is it to take the amount of the balance from him ////////////////////////
            DTOInlay dtoInlay = bank.inlayBuildForDK(bank.getCustomer(0), investAmount, list.toString(), minimumInterestYaz, minimumTotalYaz, maximumLoansOpenToTheBorrower);
            ArrayList<DTOLoan> loansSupportInlay = bank.loansSustainInlayDK(dtoInlay);
            //ToDo: Function- creates loans from loansSupportInlay and SHOW them


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
            Thread.sleep(300);
        } catch (InterruptedException ignored) {}
    }

}
