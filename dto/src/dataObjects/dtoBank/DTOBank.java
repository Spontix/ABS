package dataObjects.dtoBank;

import logic.bank.account.Account;
import logic.bank.account.Loan;
import logic.YazLogic;

import java.util.ArrayList;
import java.util.List;

public class DTOBank {

    protected List<Account> accounts;
    protected List<Loan> loans;
    protected List<String> categories;



    public DTOBank(){
        accounts=new ArrayList<>();
        loans=new ArrayList<>();
        categories=new ArrayList<>();
    }

    public List<Account> getAccounts(){
        return accounts;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public List<String> getCategories() {
        return categories;
    }

}
