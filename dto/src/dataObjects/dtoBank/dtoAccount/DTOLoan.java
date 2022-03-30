package dataObjects.dtoBank.dtoAccount;


import dataObjects.dtoBank.dtoLoanStatus.DTOLoanStatus;

import java.util.ArrayList;

public class DTOLoan {

    protected String id;
    protected String owner;
    protected String category;
    protected int capital;
    protected int totalYazTime;
    protected int paysEveryYaz;
    protected int interestPerPayment;
    protected final DTOLoanStatus loanStatus;
    protected ArrayList<DTOAccount> listOfAccompanied;
    protected int yazNumberTillEnd;
    protected int totalInterestPayTillNow;
    protected int totalInterestPayTillEnd;
    protected int totalCapitalPayTillNow;
    protected int totalCapitalPayTillEnd;


    public DTOLoan(DTOLoanStatus loanStatusLoan,String idLoan,String ownerLoan,String categoryLoan,int capitalLoan,int totalYazTimeLoan,int paysEveryYazLoan,int interestPerPaymentLoan) {
        loanStatus = loanStatusLoan;
        id=idLoan;
        owner=ownerLoan;
        category=categoryLoan;
        capital=capitalLoan;
        totalYazTime=totalYazTimeLoan;
        paysEveryYaz=paysEveryYazLoan;
        interestPerPayment=interestPerPaymentLoan;
        listOfAccompanied=null;
        yazNumberTillEnd=0;
        totalInterestPayTillNow=0;
        totalInterestPayTillEnd=0;
        totalCapitalPayTillNow=0;
        totalCapitalPayTillEnd=0;

    }

    public int getCapital() {
        return capital;
    }

    public ArrayList<DTOAccount> getListOfAccompanied() {
        return listOfAccompanied;
    }

    public int getInterestPerPayment() {
        return interestPerPayment;
    }

    public int getPaysEveryYaz() {
        return paysEveryYaz;
    }

    public int getTotalYazTime() {
        return totalYazTime;
    }

    public DTOLoanStatus getLoanStatus() {
        return loanStatus;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public int getTotalCapitalPayTillEnd() {
        return totalCapitalPayTillEnd;
    }

    public int getTotalCapitalPayTillNow() {
        return totalCapitalPayTillNow;
    }

    public int getTotalInterestPayTillEnd() {
        return totalInterestPayTillEnd;
    }

    public int getTotalInterestPayTillNow() {
        return totalInterestPayTillNow;
    }

    public int getYazNumberTillEnd() {
        return yazNumberTillEnd;
    }
}
