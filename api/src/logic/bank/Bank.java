package logic.bank;

import dataObjects.dtoBank.DTOBank;
import dataObjects.dtoBank.dtoAccount.DTOLoan;
import dataObjects.dtoBank.dtoAccount.DTOMovement;
import dataObjects.dtoCustomer.DTOCustomer;
import logic.UIInterfaceLogic;
import logic.bank.account.Account;
import logic.bank.account.Loan;
import logic.bank.account.Movement;
import logic.customer.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Bank extends DTOBank implements UIInterfaceLogic {

    protected Bank(){

    }


    @Override
    public DTOMovement addMovementToClient(int customerIndex, Movement movement) {
        this.getAccounts().get(customerIndex).getMovements().add(movement);
        return DTOMovement.build(movement);
    }

    @Override
    public int getAmountOfCustomer(int customerIndex) {
        return this.getCustomer(customerIndex).getAmount();
    }

    @Override
    public DTOCustomer getCustomer(int customerIndex) {
        return DTOCustomer.build((Customer) this.getAccounts().get(customerIndex));
    }

    @Override
    public String getCustomerName(int customerIndex) {
        return getCustomer(customerIndex).getCustomerName();
    }

    @Override
    public int getTotalCustomersSize() {
        return this.getAccounts().size();
    }

    @Override
    public void cashDeposit(int customerIndex, int sum) {
        this.getAccounts().get(customerIndex).cashDeposit(sum);
    }

    @Override
    public void cashWithdrawal(int customerIndex, int sum) {
        this.getAccounts().get(customerIndex).cashWithdrawal(sum);
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

    @Override
    public List<DTOLoan> getCustomerLoanersList(String customerName) {
        List<DTOLoan> dtoLoans=loans.stream().filter(l->l.getOwner().equals(customerName)).collect(Collectors.toList());
        return dtoLoans;
    }

    @Override
    public List<DTOLoan> getCustomerBorrowersList(String customerName) {
        /*List<DTOLoan> dtoLoans = new ArrayList<>();
        for (Loan loan : loans) {
            for (int j = 0; j < loan.getListOfAccompanied().size(); j++) {
                if (Objects.equals(loan.getListOfAccompanied().get(j).getCustomerName(), customerName)) {
                    dtoLoans.add(loan);
                    break;
                }
            }
        }
        return dtoLoans;*/
        List<DTOLoan> dtoLoans =
                loans.stream()
                        .filter(l -> l.getListOfAccompanied().stream().anyMatch(a -> a.getCustomerName().equals(customerName)))
                        .collect(Collectors.toList());

        return dtoLoans;
    }

    @Override
    public void addLoanToBank(Loan loan){///////////////
        loans.add(loan);
    }

    @Override
    public void addBorrowerTOLoan() {///////////
        loans.get(4).getListOfAccompanied().add(getCustomer(0));
        loans.get(5).getListOfAccompanied().add(getCustomer(0));

    }

}
