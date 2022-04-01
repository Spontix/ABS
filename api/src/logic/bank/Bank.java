package logic.bank;

import dataObjects.dtoBank.DTOBank;
import dataObjects.dtoBank.dtoAccount.DTOMovement;
import dataObjects.dtoCustomer.DTOCustomer;
import logic.UIInterfaceLogic;
import logic.bank.account.Account;
import logic.bank.account.Movement;
import logic.customer.Customer;

import java.util.ArrayList;

public class Bank extends DTOBank implements UIInterfaceLogic {

    protected Bank(){

    }


    @Override
    public DTOMovement addMovementToClient(int index, Movement movement) {
        this.getAccounts().get(index).getMovements().add(movement);
        return DTOMovement.build(movement);
    }

    @Override
    public int getAmountOfCustomer(int index) {
        return this.getCustomer(index).getAmount();
    }

    @Override
    public DTOCustomer getCustomer(int index) {
        return DTOCustomer.build((Customer) this.getAccounts().get(index));
    }

    @Override
    public int getTotalCustomersSize() {
        return this.getAccounts().size();
    }

    @Override
    public void cashDeposit(int index, int sum) {
        this.getAccounts().get(index).cashDeposit(sum);
    }

    @Override
    public void cashWithdrawal(int index, int sum) {
        this.getAccounts().get(index).cashWithdrawal(sum);
    }

    @Override
    public void addCustomer(Customer customer) {
        this.getAccounts().add(customer);
    }

    @Override
    public ArrayList<DTOCustomer> getCustomers() {
        ArrayList<DTOCustomer> dtoCustomers=new ArrayList<>();
        for (Account account : this.getAccounts()) {
            dtoCustomers.add(DTOCustomer.build((Customer)account));
        }

        return dtoCustomers;
    }
}
