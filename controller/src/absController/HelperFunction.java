package absController;

import dataObjects.dtoBank.dtoAccount.DTOLoan;
import dataObjects.dtoCustomer.DTOCustomer;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import logic.UIInterfaceLogic;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public class HelperFunction {

    protected void showLoanInformationInAdminView(ListView<DTOLoan> listView, List<DTOLoan> list) {
        listView.getItems().clear();
        listView.getItems().addAll(list);
    }

    protected void showCustomerInformationAdminView(ListView<DTOCustomer> listView,  List<DTOCustomer> list){
        listView.getItems().clear();
        listView.getItems().addAll(list);
    }

    /*protected void showCustomersNamesView(ListView<String> listView,  List<DTOCustomer> list){
        listView.getItems().clear();
        for (DTOCustomer customer:list) {
            listView.getItems().add(customer.getCustomerName());
        }
    }*/

    protected  <T> T myFXMLLoader(String resource){
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url =  getClass().getResource(resource);
        fxmlLoader.setLocation(url);
        try {
            fxmlLoader.load(fxmlLoader.getLocation().openStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return fxmlLoader.getController();
    }
}

