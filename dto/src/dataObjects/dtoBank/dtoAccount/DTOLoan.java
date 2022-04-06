package dataObjects.dtoBank.dtoAccount;


import dataObjects.dtoCustomer.DTOCustomer;
import logic.YazLogic;
import logic.customer.Customer;

import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DTOLoan {

    protected String id;
    protected String owner;
    protected String category;
    protected int capital;
    protected int capitalSumLeftTillActive;
    protected int totalYazTime;
    protected int paysEveryYaz;
    protected int interestPerPayment;
    protected DTOLoanStatus loanStatus = DTOLoanStatus.NEW;
    protected List<DTOAccount> listOfAccompanied;
    protected List<DTOInlay> listOfInlays;
    /*protected int yazNumberTillEnd;
    protected int totalInterestPayTillNow;
    protected int totalInterestPayTillEnd;
    protected int totalCapitalPayTillNow;
    protected int totalCapitalPayTillEnd;*/
    protected int pulseCounterThatHappened;
    protected int startedYazInActive;
    protected int endedYaz;
    protected int inRiskCounter;
    public DTOLoan() {


    }

    public static DTOLoan build(DTOLoan loan) {
        DTOLoan dtoLoan = new DTOLoan();
        dtoLoan.capital = loan.capital;
        dtoLoan.loanStatus = loan.loanStatus;
        dtoLoan.paysEveryYaz = loan.paysEveryYaz;
        dtoLoan.id = loan.id;
        dtoLoan.owner = loan.owner;
        dtoLoan.totalYazTime = loan.totalYazTime;
        dtoLoan.interestPerPayment = loan.interestPerPayment;
        dtoLoan.category = loan.category;
        List<DTOAccount> accompaniedList = new ArrayList<>();
        List<DTOInlay> inlaysList = new ArrayList<>();
        for (DTOAccount dtoAccount : loan.listOfAccompanied) {
            accompaniedList.add(DTOCustomer.build((DTOCustomer) dtoAccount));
        }
        for (DTOInlay dtoInlay : loan.listOfInlays) {
            inlaysList.add(DTOInlay.build(dtoInlay));
        }
        dtoLoan.listOfInlays=inlaysList;
        dtoLoan.listOfAccompanied=accompaniedList;
        return dtoLoan;
    }

    @Override
    public String toString() {

        return (
                "Loan ID - " + id + "\n" +
                "Loan owner - " + owner + "\n" +
                "Loan category - " + category + "\n" +
                "Loan capital - "+ capital + "\n" +
                "The total original time of the loan - " + totalYazTime + "\n" +
                "Pays every yaz - " + paysEveryYaz + "\n" +
                "Loan interest - " + interestPerPayment + "\n" +
                "Loan status - " + loanStatus+"\n");
    }

    public String invokeStatusOperation(){
        return loanStatus.operationThree(this);
    }

    public DTOLoanStatus getStatusOperation(){
        return loanStatus;
    }

    public int getCapital() {
        return capital;
    }

    public List<DTOAccount> getListOfAccompanied() {
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

    public List<DTOInlay> getListOfInlays() {
        return listOfInlays;
    }

    public int pulseNumber() {
        return totalYazTime / paysEveryYaz;
        //it's the total payments that we will have
    }

    public int paymentPerPulse() {
        return capital / pulseNumber() + (int)(capital / pulseNumber() * ( (double)(interestPerPayment / 100.0)));
        //it's the amount per payment capital+interest
    }

    public int theNextYazToBePaid(){
        return YazLogic.currentYazUnit+ numberOfYazTillNextPulse();
    }

    public int numberOfYazTillNextPulse(){
        return (paysEveryYaz-( (YazLogic.currentYazUnit-1)% paysEveryYaz));
    }


    public int calculatePaymentToLoaner(Customer customer) {
        return (paymentPerPulse() * (listOfInlays.stream().filter(i-> Objects.equals(i.dtoAccount.getCustomerName(), customer.getCustomerName())).collect(Collectors.toList()).get(0).investAmount * 100) / capital) / 100;
    }

    public int getCurrentYaz() {
        return YazLogic.currentYazUnit;
    }

    public int getStartedYazInActive() {
        return startedYazInActive;
    }

    public int getEndedYaz() {
        return endedYaz;
    }

    public int getInRiskCounter() {
        return inRiskCounter;
    }

    public int totalAmountThatWasNotPayed(){
        return inRiskCounter*paymentPerPulse();
    }

    public int getCapitalSumLeftTillActive() {
        return capitalSumLeftTillActive;
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
