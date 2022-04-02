package dataObjects.dtoBank;

import dataObjects.dtoBank.dtoAccount.DTOAccount;
import dataObjects.dtoBank.dtoAccount.DTOLoan;
import logic.bank.account.Account;
import logic.bank.account.Loan;
import logic.customer.Customer;

import java.util.ArrayList;

public class DTOBank {

    protected final ArrayList<Account> accounts;
    protected final ArrayList<Loan> loans;
    protected final ArrayList<String> categories;



    public DTOBank(){
        accounts=new ArrayList<>();
        loans=new ArrayList<>();
        categories=new ArrayList<>();
    }

    public ArrayList<Account> getAccounts(){
        return accounts;
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }
}
