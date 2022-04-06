package application;

import dataObjects.dtoBank.dtoAccount.DTOInlay;
import dataObjects.dtoBank.dtoAccount.DTOLoan;
import logic.bank.account.Inlay;
import logic.bank.account.Loan;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public interface UiType {
    //void showDataLoans();
    void Run() throws InvocationTargetException, InstantiationException, IllegalAccessException;
    /*void sumToInvest(Loan x);
    void category(Loan x);
    void minimumInterestPerUnitTime(Loan x);
    void minimumTimePerUnitTime(Loan x);
    void maximumPercentageOfOwnership(Loan x);
    void maximumLoansOpenToTheBorrower(Loan x);
    void userCustomerSelection(Inlay inlay);
    void userSumSelection(Inlay inlay);*/
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
    void showLoansList(List<DTOLoan> loans);
    ArrayList<DTOLoan> loansCustomerChosenParticipate(ArrayList<DTOLoan> loansSupportInlay) throws InvocationTargetException, InstantiationException, IllegalAccessException;
   // void addMovementPerLoanFromInlay(ArrayList<DTOLoan> loansCustomerChosen, int chosenInvestAmount ,int customerIndexGiveMoney);
}
