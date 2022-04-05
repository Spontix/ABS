package logic.bank.account;

//Eliran123


import dataObjects.dtoBank.dtoAccount.DTOLoan;
import dataObjects.dtoBank.dtoAccount.DTOLoanStatus;

import java.util.ArrayList;
import java.util.Comparator;

public class Loan extends DTOLoan {


    public Loan() {

    }



    public int setTotalYaz(int number) {
        return totalYazTime + number;
    }

    private void setPendingDetails() {
        //int sum=listOfAccompanied.stream().mapToInt(a -> a.get)
    }

    public static Loan build(String idLoan, String ownerLoan, String categoryLoan, int capitalLoan, int totalYazTimeLoan, int paysEveryYazLoan, int interestPerPaymentLoan) {
        Loan loan = new Loan();
        loan.id = idLoan;
        loan.owner = ownerLoan;
        loan.category = categoryLoan;
        loan.capital = capitalLoan;
        loan.totalYazTime = totalYazTimeLoan;
        loan.paysEveryYaz = paysEveryYazLoan;
        loan.interestPerPayment = interestPerPaymentLoan;
        loan.listOfAccompanied = new ArrayList<>();
        loan.listOfInlays=new ArrayList<>();
        /*loan.yazNumberTillEnd = 0;
        loan.totalInterestPayTillNow = 0;
        loan.totalInterestPayTillEnd = 0;
        loan.totalCapitalPayTillNow = 0;
        loan.totalCapitalPayTillEnd = 0;*/
        return loan;
    }

    public void decCapitalSumLeftTillActive(int paymentPerPulse){
        this.capitalSumLeftTillActive=this.capitalSumLeftTillActive-paymentPerPulse;

    }

    public void setStartedYazInActive(int yaz){
        if(startedYazInActive==0)
            startedYazInActive=yaz;
    }

    public void setLoanStatus(DTOLoanStatus loanStatus){
        this.loanStatus=loanStatus;

    }

    public void incrInRiskCounter(){
        this.inRiskCounter++;
    }

    public void decInRiskCounter(){
        this.inRiskCounter--;
    }

    public void incrCapitalSumLeftTillActive(int paymentPerPulse){
        this.capitalSumLeftTillActive=this.capitalSumLeftTillActive+paymentPerPulse;
    }




}




