package logic.bank.account;

//Eliran123


import dataObjects.dtoBank.dtoAccount.DTOAccount;
import dataObjects.dtoBank.dtoAccount.DTOInlay;
import dataObjects.dtoBank.dtoAccount.DTOLoan;
import dataObjects.dtoBank.dtoAccount.DTOLoanStatus;

import java.util.ArrayList;
import java.util.Comparator;

public class Loan extends DTOLoan {


    private Loan() {

    }



    public void setTotalYaz(int totalYazTime) {
        this.totalYazTime= totalYazTime;
    }

    private void setPendingDetails() {
        //int sum=listOfAccompanied.stream().mapToInt(a -> a.get)
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
        this.capitalSumLeftTillActive+=paymentPerPulse;
    }


    public void setId(String idLoan) {
        this.id=idLoan;
    }

    public void setOwner(String ownerLoan) {
        this.owner=ownerLoan;
    }

    public void setCategory(String categoryLoan) {
        this.category=categoryLoan;
    }

    public void setCapital(int capitalLoan) {
        this.capital=capitalLoan;
    }

    public void serPaysEveryYaz(int paysEveryYazLoan) {
        this.paysEveryYaz=paysEveryYazLoan;
    }

    public void setInterestPerPayment(int interestPerPaymentLoan) {
        this.interestPerPayment=interestPerPaymentLoan;
    }

    public void setListOfAccompanied(ArrayList<DTOAccount> accompanied) {
        this.listOfAccompanied=accompanied;
    }

    public void setListOfInlays(ArrayList<DTOInlay> inlays) {
        this.listOfInlays=inlays;
    }

    public void setCapitalSumLeftTillActive(int capitalSumLeftTillActive) {
        this.capitalSumLeftTillActive=capitalSumLeftTillActive;
    }

    public void incrPulseCounterThatHappenedByOne(){
        pulseCounterThatHappened++;
    }
}




