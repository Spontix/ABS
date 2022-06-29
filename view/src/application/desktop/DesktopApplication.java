package application.desktop;

import absController.ABSController;
import application.UiType;
import dataObjects.dtoBank.dtoAccount.DTOLoan;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//
public class DesktopApplication extends Application implements UiType {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("Players Manager");
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("MyGeneralView.fxml");
        fxmlLoader.setLocation(url);
        Parent banksRoot = fxmlLoader.load(url.openStream());

        ABSController absController=fxmlLoader.getController();
        Scene scene = new Scene(banksRoot);
        absController.getSkinMenuButton().setOnAction(e->{
         scene.getStylesheets().setAll(getClass().getResource("FullPackStyling.css").toExternalForm());
         absController.getSkinsMenuButton().setText(absController.getSkinMenuButton().getText());
        });
        absController.getDefaultSkinMenuButton().setOnAction(e->{
            scene.getStylesheets().setAll(getClass().getResource("Default.css").toExternalForm());
            absController.getSkinsMenuButton().setText(absController.getDefaultSkinMenuButton().getText());
        });
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    @Override
    public void Run(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        main(args);
    }























    @Override
    public void insertSumToAccount() {

    }

    @Override
    public void inlayActivation() {

    }

    @Override
    public void drawSumFromAccount() {

    }

    @Override
    public void showCustomerList(Boolean printAmount) {

    }

    @Override
    public void showCustomerInformation() {

    }

    @Override
    public int getChosenCustomerIndex() {
        return 0;
    }

    @Override
    public int getAmountFromUser() {
        return 0;
    }

    @Override
    public void showCategoriesList() {

    }

    @Override
    public String[] getChosenCategoryIndex() {
        return new String[0];
    }

    @Override
    public double getChosenMinInterestYaz() {
        return 0;
    }

    @Override
    public int getChosenMinYazTime() {
        return 0;
    }

    @Override
    public int getChosenInvestAmount(int amountOfCustomer) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return 0;
    }

    @Override
    public void showLoansList(List<DTOLoan> loans, int indexOperation) {

    }

    @Override
    public <T> Boolean ensureMassageToCustomer(T data) {
        return null;
    }

    @Override
    public void showDataLoans() {

    }

    @Override
    public ArrayList<DTOLoan> loansCustomerChosenParticipate(ArrayList<DTOLoan> loansSupportInlay) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return null;
    }

    @Override
    public void showCurrentYAZ() {

    }
}
