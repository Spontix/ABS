package absController;

import dataObjects.dtoBank.dtoAccount.DTOInlay;
import dataObjects.dtoBank.dtoAccount.DTOLoan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import logic.UIInterfaceLogic;
import logic.bank.Bank;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.*;

public class CustomerController {
    private UIInterfaceLogic bank = new Bank();

    public CustomerController() {
        bank = ABSController.bank;
    }


    @FXML
    TabPane customerTablePane;

    @FXML
    private ComboBox<String> categoriesList;

    @FXML
    private Button enableInlayButton;

    @FXML
    private TextField investmentAmount;

    @FXML
    private TextArea textErrorInvestmentAmount;

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


    @FXML
    void AccordingAddLoanTitleTableActionLisener(MouseEvent event) {

    }


    @FXML
    void MaximumLoanOwnershipPercentageActionLisener(ActionEvent event) {

    }

    @FXML
    void MaximumLoansOpenToTheBorrowerActionLisener(ActionEvent event) {

    }

    @FXML
    double MinimumInterestYazActionLisener() {
        double chosenMinInterestYaz = 0;
        String number = minimumInterestYaz.getText();
        if (Objects.equals(number, ""))
            return 0;
        else {
            chosenMinInterestYaz = Double.parseDouble((number));
            if (chosenMinInterestYaz < 0 || chosenMinInterestYaz > 100)
                throw new NumberFormatException();
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
            if (chosenMinYazTime < 0)
                throw new NumberFormatException();
        }
        return chosenMinYazTime;
    }

    @FXML
    boolean investmentAmountActionListener() {
        String amount = investmentAmount.getText();
        int amountFromUser = 0;
        try {
            amountFromUser = Integer.parseInt(amount);
            ///////////////////////// Missing - what customer is it to take the amount of the balance from him ////////////////////////
            if (amountFromUser < 0 || amountFromUser > bank.getAmountOfCustomer(0))
                throw new NumberFormatException();
        } catch (NumberFormatException exception) {
            return false;
        }
        //return amountFromUser;
        return true;
    }

    private boolean validateEnteredData() {
        return investmentAmountActionListener();
    }

    @FXML
    void ClickEnableInlayButtonActionLisener(ActionEvent event) {
        if (validateEnteredData()) {
            try {
                //int chosenInvestAmount = investmentAmountActionListener();
                double minInterestYaz = MinimumInterestYazActionLisener();
                int minYazTime = MinimumTotalYazActionLisener();
                //DTOInlay dtoInlay=bank.inlayBuild(bank.getCustomer(0), chosenInvestAmount, categoriesList.toString(), minInterestYaz, minYazTime);
                //ArrayList<DTOLoan> loansSupportInlay = bank.loansSustainInlay(dtoInlay);
            } catch (NumberFormatException exception) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Input field is incorrect!");
                alert.showAndWait();
            } catch (RuntimeException exception) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Input field is incorrect!");
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        } else {
                        /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Input field is incorrect!");
            alert.setContentText("Enter a number greater than 0 and less than" + bank.getAmountOfCustomer(0));
            alert.showAndWait();*/
            textErrorInvestmentAmount.isVisible();
        }
    }

    @FXML
    void ShowCategoriesListActionLisener(ActionEvent event) {
        for (String category : bank.getCategoriesGroup()) {
            categoriesList.getItems().addAll(category);
        }
    }

}
