package logic;

import dataObjects.dtoBank.dtoAccount.DTOMovement;
import dataObjects.dtoCustomer.DTOCustomer;
import logic.bank.account.Movement;
import logic.customer.Customer;

import java.util.ArrayList;

public interface UIInterfaceLogic {
    DTOMovement addMovementToClient(int index,Movement movement);
    int getAmountOfCustomer(int index);
    DTOCustomer getCustomer(int index);
    int getTotalCustomersSize();
    void cashDeposit(int index,int sum);
    void cashWithdrawal(int index,int sum);
    void addCustomer(Customer customer);
    ArrayList<DTOCustomer> getCustomers();
}
