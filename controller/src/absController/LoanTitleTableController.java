package absController;

        import dataObjects.dtoBank.dtoAccount.DTOLoan;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.TitledPane;
        import logic.UIInterfaceLogic;

        import java.net.URL;
        import java.util.ResourceBundle;

public class LoanTitleTableController implements Initializable {
    private UIInterfaceLogic bank;

    @FXML
    private TitledPane titledPaneLoan;

    @FXML
    private TableView<?> loanInformationDepandingStatus;

    @FXML
    private TableView<DTOLoan> loanDetails;

    @FXML
    private TableColumn<?, ?> informationDependingOnStatusColumn;

    @FXML
    private TableColumn<DTOLoan,String> idColumn;

    @FXML
    private TableColumn<DTOLoan,String> ownerColumn;

    @FXML
    private TableColumn<DTOLoan,String> categoryColumn;

    @FXML
    private TableColumn<DTOLoan,String> capitalColumn;

    @FXML
    private TableColumn<DTOLoan,String> totalTimeColumn;

    @FXML
    private TableColumn<DTOLoan,String> paysEveryYazColumn;

    @FXML
    private TableColumn<DTOLoan,String> interestColumn;

    @FXML
    private TableColumn<DTOLoan,String> statusColumn;


    void CreateRowLoanDetails(DTOLoan dtoLoan) {
        loanDetails.getItems().add(dtoLoan);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    } @FXML
    void CategoryColumnActionLisener(ActionEvent event) {

    }

    @FXML
    void IdColumnActionLisener(ActionEvent event) {

    }

    @FXML
    void InformationDependingOnStatusColumnActionLisener(ActionEvent event) {

    }

    @FXML
    void InterestColumnActionLisener(ActionEvent event) {

    }

    @FXML
    void OwnerColumnActionLisener(ActionEvent event) {

    }

    @FXML
    void PaysEveryYazColumnActionLisener(ActionEvent event) {

    }

    @FXML
    void StatusColumnActionLisener(ActionEvent event) {

    }

    @FXML
    void TotalTimeColumnActionLisener(ActionEvent event) {

    }

}
