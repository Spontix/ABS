package absController;

import dataObjects.dtoBank.dtoAccount.DTOInlay;
import dataObjects.dtoBank.dtoAccount.DTOLoan;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoansListController implements Initializable{

    @FXML
    protected TableView<DTOInlay> lendersTableView;

    @FXML
    protected GridPane LoansMainGridPane;

    @FXML
    private TableColumn<DTOInlay, String> nameColumn;

    @FXML
    private TableColumn<DTOInlay, Integer> investmentColumn;

    @FXML
    protected Accordion loansAccordionInformation;


    @FXML
    protected ListView<DTOLoan> LoansListView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
