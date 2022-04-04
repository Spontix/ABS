package dataObjects.dtoBank.dtoAccount;


import dataObjects.dtoCustomer.DTOCustomer;
import logic.customer.Customer;

import java.util.ArrayList;
import java.util.List;

public class DTOLoan {

    protected String id;
    protected String owner;
    protected String category;
    protected int capital;
    protected int totalYazTime;
    protected int paysEveryYaz;
    protected int interestPerPayment;
    protected DTOLoanStatus loanStatus=DTOLoanStatus.NEW;
    protected ArrayList<DTOAccount> listOfAccompanied;
    protected ArrayList<DTOInlay> listOfInlays;
    /*protected int yazNumberTillEnd;
    protected int totalInterestPayTillNow;
    protected int totalInterestPayTillEnd;
    protected int totalCapitalPayTillNow;
    protected int totalCapitalPayTillEnd;*/

    public DTOLoan() {


    }

    public static DTOLoan build(DTOLoan loan){
        DTOLoan dtoLoan=new DTOLoan();
        dtoLoan.capital=loan.capital;
        dtoLoan.loanStatus=loan.loanStatus;
        dtoLoan.paysEveryYaz=loan.paysEveryYaz;
        dtoLoan.id=loan.id;
        dtoLoan.owner=loan.owner;
        dtoLoan.totalYazTime=loan.totalYazTime;
        dtoLoan.interestPerPayment=loan.interestPerPayment;
        dtoLoan.category=loan.category;
        List<DTOAccount> accompaniedList=new ArrayList<>();
        List<DTOInlay> inlaysList=new ArrayList<>();
        for (DTOAccount dtoAccount:loan.listOfAccompanied) {
            accompaniedList.add(DTOCustomer.build((DTOCustomer)dtoAccount));
        }
        for (DTOInlay dtoInlay:loan.listOfInlays) {
            inlaysList.add(DTOInlay.build(dtoInlay));
        }
        return dtoLoan;
    }

    @Override
    public String toString(){

        return ("---------------------------------\n" +
                "Loan ID - " + id + "\n" +
                "Loan owner - " + owner + "\n" +
                "Loan category - " + category + "\n" +
                "The total original time of the loan - " + totalYazTime + "\n" +
                "Pays every yaz - " + paysEveryYaz + "\n" +
                "Loan interest - " + interestPerPayment + "\n" +
                "Loan status - " + loanStatus);
    }

    public String getStatusOperation(){
        return loanStatus.operationThree(this);
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

    public ArrayList<DTOInlay> getListOfInlays() {
        return listOfInlays;
    }

    public int foundComponentPerYaz(){ //מרכיב הקרן עבור י"ז בודד
        return capital/ totalYazTime;
    }

    public int singlePayment(){ //תשלום בודד
        return foundComponentPerYaz()*paysEveryYaz;
    }

    /*public int getTotalCapitalPayTillEnd() {
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
    }*/
}
