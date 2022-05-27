package absController;

import dataObjects.dtoBank.dtoAccount.DTOInlay;
import dataObjects.dtoBank.dtoAccount.DTOLoan;
import dataObjects.dtoBank.dtoAccount.DTOLoanStatus;
import dataObjects.dtoCustomer.DTOCustomer;
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
import org.controlsfx.control.GridView;

import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;

public class CustomerController extends HelperFunction implements Initializable{
    private UIInterfaceLogic bank;
    protected DTOCustomer dtoCustomer;
    final ObservableList<String> categories = FXCollections.observableArrayList();
    private ListView<DTOLoan> allInlayListView;
    private ListView<DTOLoan> chosenInlayListView;


    @FXML
    private BorderPane chosenInlayLoansBorderPane;

    @FXML
    private BorderPane allInlayLoansBorderPane;

    @FXML
    private Button addLoanButton;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chosenInlayListView=new ListView<>();
        allInlayListView=new ListView<>();


    }

    public void setBankInCustomerController(UIInterfaceLogic bank){this.bank= bank;}

    @FXML
    private void ClickOnAddLoanButtonActionLisener(ActionEvent event) {

    }

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
        ///////////////////////// Missing - what customer is it to take the amount of the balance from him ////////////////////////
        if (amountFromUser <= 0 || amountFromUser > bank.getAmountOfCustomer(0))
            throw new NumberFormatException("In 'Amount to investment' - Invalid input!! Please enter a number greater than 0 and less than \" + bank.getAmountOfCustomer(0)");

        return amountFromUser;
    }




    @FXML
    private void ClickEnableInlayButtonActionLisener(ActionEvent event) {
        allInlayListView.setVisible(false);
        addLoanButton.setVisible(false);
        List<DTOLoan> loansCustomerChosen = new ArrayList<>();

        try {
            ObservableList<String> list = categoriesList.getCheckModel().getCheckedItems();
            int investAmount = investmentAmountActionListener();
            int minimumTotalYaz = MinimumTotalYazActionLisener();
            double minimumInterestYaz = MinimumInterestYazActionLisener();
            int maximumLoansOpenToTheBorrower = MaximumLoansOpenToTheBorrowerActionLisener();
            int maximumLoanOwnershipPercentage = MaximumLoanOwnershipPercentageActionLisener();
            ///////////////////////// Missing - what customer is it to take the amount of the balance from him ////////////////////////
            DTOInlay dtoInlay = bank.inlayBuildForDK(bank.getCustomer(0), investAmount,list.toString().substring(1,list.toString().length()-1), minimumInterestYaz, minimumTotalYaz, maximumLoansOpenToTheBorrower);
            ArrayList<DTOLoan> loansSupportInlay = bank.loansSustainInlayDK(dtoInlay);
            if(loansSupportInlay.isEmpty()){
                popupMessage("Failed!!!", "No loans were found for this inlay.");
            }
            else {
                allInlayListView.setVisible(true);
                allInlayLoansBorderPane.setCenter(allInlayListView);
                chosenInlayLoansBorderPane.setCenter(chosenInlayListView);
                showLoanInformationInAdminView(allInlayListView, loansSupportInlay);
                addLoanButton.setVisible(true);
                popupMessage("Success!!!", "Please click on the loan you want and then click on the 'Add loan' button.");
                //DTOLoan localLoan = loansListController.LoansListView.getSelectionModel().getSelectedItem();
                //allInlayListView.setVisible(false);
                addLoanButton.setOnAction(e->{
                    DTOLoan localLoan = allInlayListView.getSelectionModel().getSelectedItem();
                    chosenInlayListView.getItems().add(localLoan);
                    loansCustomerChosen.add(localLoan);});
                //if (localLoan != null)
                    //loansListController.lendersTableView.setItems(FXCollections.observableArrayList(localLoan.getListOfInlays()));

            }

        } catch (Exception e) {
            String message = e.getMessage();
            if(message.startsWith("For input string:", 0))
                message = "Invalid Input!! " + message;
            errorTextArea.setVisible(true);
            errorTextArea.setText(message);
        }

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
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {}
    }


    protected void setCurrentCustomer(DTOCustomer dtoCustomer){
        this.dtoCustomer=dtoCustomer;
    }


}
