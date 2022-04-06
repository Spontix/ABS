package logic;

import dataObjects.dtoBank.dtoAccount.DTOAccount;
import dataObjects.dtoBank.dtoAccount.DTOInlay;
import dataObjects.dtoBank.dtoAccount.DTOLoan;
import dataObjects.dtoBank.dtoAccount.DTOMovement;
import dataObjects.dtoCustomer.DTOCustomer;
import logic.bank.account.Inlay;
import logic.bank.account.Loan;
import logic.bank.account.Movement;
import logic.customer.Customer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public interface UIInterfaceLogic {
    DTOMovement addMovementToClient(int customerIndex, Movement movement);

    DTOMovement addMovementToClient(DTOCustomer customer, Movement movement);

    DTOInlay addInlayToClient(int customerIndex, Inlay inlay);

    int getAmountOfCustomer(int customerIndex);

    DTOCustomer getCustomer(int customerIndex);

    String getCustomerName(int customerIndex);

    int getTotalCustomersSize();

    void cashDeposit(int customerIndex, int sum);

    void cashDeposit(Customer customer, int sum);

    void cashWithdrawal(Customer customer, int sum);

    void cashWithdrawal(int customerIndex, int sum);

    void addCustomer(Customer customer);

    ArrayList<DTOCustomer> getCustomers();

    List<DTOLoan> getCustomerLoanersList(String customerName);

    List<DTOLoan> getCustomerBorrowersList(String customerName);

    void addLoanToBank(Loan loan);

    ArrayList<String> getCategoriesGroup();

    String getCategory(int categoryIndex);


    ArrayList<DTOLoan> getLoansList();

    ArrayList<DTOLoan> loansSustainInlay(DTOInlay inlay);

    ArrayList<DTOLoan> loansSustainInlayAndClientChoose(ArrayList<DTOLoan> loansSupportInlay, String[] arrayStringsScanner);

    void yazProgressLogic() throws InvocationTargetException, InstantiationException, IllegalAccessException;//ToDo!!

    void addMovementPerLoanFromInlay(DTOInlay inlay, ArrayList<DTOLoan> loansCustomerChosen, int chosenInvestAmount, int customerIndexGiveMoney) throws InvocationTargetException, InstantiationException, IllegalAccessException;

    void checksInvestAmount(DTOAccount customer,int investAmount);


    DTOLoan loanBuilder(String idLoan, String ownerLoan, String categoryLoan, int capitalLoan, int totalYazTimeLoan, int paysEveryYazLoan, int interestPerPaymentLoan) throws InvocationTargetException, InstantiationException, IllegalAccessException;

    Inlay inlayBuild(DTOAccount customer, int investAmount, String category, double minInterestYaz, int minYazTime) throws InvocationTargetException, InstantiationException, IllegalAccessException;

    DTOMovement movementBuild(DTOCustomer customer, int movementSum, String movementOperation, int movementSumBeforeOperation, int movementSumAfterOperation) throws InvocationTargetException, InstantiationException, IllegalAccessException;

    DTOCustomer customerBuild(String name, int amount) throws InvocationTargetException, InstantiationException, IllegalAccessException;
}

