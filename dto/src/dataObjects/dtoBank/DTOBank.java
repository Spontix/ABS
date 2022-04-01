package dataObjects.dtoBank;

import dataObjects.dtoBank.dtoAccount.DTOAccount;
import dataObjects.dtoBank.dtoAccount.DTOLoan;
import logic.bank.account.Account;
import logic.bank.account.Loan;
import logic.customer.Customer;

import java.util.ArrayList;

public class DTOBank {

    private final ArrayList<Account> accounts;
    private final ArrayList<Loan> loans;
    private final ArrayList<String> categories;

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
