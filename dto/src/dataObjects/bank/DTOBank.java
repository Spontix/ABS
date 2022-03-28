package dataObjects.bank;

import dataObjects.bank.dtoAccount.DTOAccount;
import dataObjects.bank.dtoAccount.DTOLoan;

import java.util.ArrayList;

public class DTOBank {

    private ArrayList<DTOAccount> accounts;
    private ArrayList<DTOLoan> loans;
    private ArrayList<String> categories;

    public DTOBank(){
        accounts=new ArrayList<>();
        loans=new ArrayList<>();
        categories=new ArrayList<>();
    }

    public ArrayList<DTOAccount> getAccounts(){
        return accounts;
    }

    public ArrayList<DTOLoan> getLoans() {
        return loans;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }
}
