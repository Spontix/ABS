package application;

import dataObjects.dtoBank.dtoAccount.DTOInlay;
import dataObjects.dtoBank.dtoAccount.DTOLoan;
import logic.bank.account.Inlay;
import logic.bank.account.Loan;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public interface UiType {
    void Run() throws InvocationTargetException, InstantiationException, IllegalAccessException;
    void insertSumToAccount();
    void inlayActivation();
    void drawSumFromAccount();
    void showCustomerList(Boolean printAmount);
    void showCustomerInformation();
    int getChosenCustomerIndex();
    int getAmountFromUser();
    void showCategoriesList();
    int getChosenCategoryIndex();
    double getChosenMinInterestYaz();
    int getChosenMinYazTime();
    int getChosenInvestAmount(int amountOfCustomer) throws InvocationTargetException, InstantiationException, IllegalAccessException;
    //ArrayList<DTOLoan> loansSustainInlay(DTOInlay inlay);
    void showLoansList(List<DTOLoan> loans, int indexOperation);
    <T> Boolean ensureMassageToCustomer(T data);
    void showDataLoans();
    ArrayList<DTOLoan> loansCustomerChosenParticipate(ArrayList<DTOLoan> loansSupportInlay) throws InvocationTargetException, InstantiationException, IllegalAccessException;
   // void addMovementPerLoanFromInlay(ArrayList<DTOLoan> loansCustomerChosen, int chosenInvestAmount ,int customerIndexGiveMoney);
}
