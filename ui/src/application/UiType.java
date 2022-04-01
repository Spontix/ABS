package application;

import logic.bank.account.Inlay;
import logic.bank.account.Loan;

public interface UiType {
    void showDataLoans();
    void Run();
    void sumToInvest(Loan x);
    void category(Loan x);
    void minimumInterestPerUnitTime(Loan x);
    void minimumTimePerUnitTime(Loan x);
    void maximumPercentageOfOwnership(Loan x);
    void maximumLoansOpenToTheBorrower(Loan x);
    void insertSumToAccount();
    void inlayActivation();
    void userCustomerSelection(Inlay inlay);
    void userSumSelection(Inlay inlay);
    void drawSumFromAccount();
    void showCustomerList(Boolean printAmount);
    void showCustomerInformation();
    int getChosenCustomerIndex();
    int getAmountFromUser();
}
