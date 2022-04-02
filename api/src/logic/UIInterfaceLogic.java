package logic;

import dataObjects.dtoBank.dtoAccount.DTOLoan;
import dataObjects.dtoBank.dtoAccount.DTOMovement;
import dataObjects.dtoCustomer.DTOCustomer;
import logic.bank.account.Loan;
import logic.bank.account.Movement;
import logic.customer.Customer;

import java.util.ArrayList;
import java.util.List;

public interface UIInterfaceLogic {
    DTOMovement addMovementToClient(int customerIndex,Movement movement);
    int getAmountOfCustomer(int customerIndex);
    DTOCustomer getCustomer(int customerIndex);
    String getCustomerName(int customerIndex);
    int getTotalCustomersSize();
    void cashDeposit(int customerIndex,int sum);
    void cashWithdrawal(int customerIndex,int sum);
    void addCustomer(Customer customer);
    ArrayList<DTOCustomer> getCustomers();
    List<DTOLoan> getCustomerLoanersList(String customerName);
    List<DTOLoan> getCustomerBorrowersList(String customerName);
    void addLoanToBank(Loan loan);
    void addBorrowerTOLoan();

}