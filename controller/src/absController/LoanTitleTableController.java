package absController;

import dataObjects.dtoBank.dtoAccount.DTOInlay;
import dataObjects.dtoBank.dtoAccount.DTOLoan;
import dataObjects.dtoCustomer.DTOCustomer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.UIInterfaceLogic;
import logic.bank.account.Inlay;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.DoubleToIntFunction;

public class LoanTitleTableController implements Initializable {
    private UIInterfaceLogic bank;

    @FXML
    protected TableView<DTOInlay> lendersTableView;

    @FXML
    protected TableView<DTOLoan> loansTableView;

    @FXML
    protected TitledPane loanTitledPane;

    @FXML
    private TableColumn<DTOLoan, String> idColumn;

    @FXML
    private TableColumn<DTOLoan, String> ownerColumn;

    @FXML
    private TableColumn<DTOLoan, Integer> capitalColumn;

    @FXML
    private TableColumn<DTOLoan, Integer> yazTimeColumn;

    @FXML
    private TableColumn<DTOLoan, Integer> paysYazColumn;

    @FXML
    private TableColumn<DTOLoan, Integer> interestColumn;

    @FXML
    private TableColumn<DTOLoan, String> categoryColumn;

    @FXML
    private TableColumn<DTOLoan, Integer> statusColumn;

    @FXML
    private TableColumn<DTOInlay, String> nameColumn;

    @FXML
    private TableColumn<DTOInlay, Integer> investmentColumn;

    @FXML
    private Label totalPaymentsLabel;

    @FXML
    private Label leftPaymentsLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));
        capitalColumn.setCellValueFactory(new PropertyValueFactory<>("capital"));
        yazTimeColumn.setCellValueFactory(new PropertyValueFactory<>("totalYazTime"));
        paysYazColumn.setCellValueFactory(new PropertyValueFactory<>("paysEveryYaz"));
        interestColumn.setCellValueFactory(new PropertyValueFactory<>("interestPerPayment"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("loanStatus"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        investmentColumn.setCellValueFactory(new PropertyValueFactory<>("investAmount"));

    }
}
