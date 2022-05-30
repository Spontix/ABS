package dataObjects.dtoBank.dtoAccount;


import dataObjects.dtoCustomer.DTOCustomer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import logic.YazLogic;
import logic.YazLogicDesktop;
import logic.customer.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    protected List<DTOMovement> listOfMovements;
    protected List<Integer> listOfYazPayments;
    protected List<Integer> listOfInRiskYazPayments;
    protected int pulseCounterThatHappened;
    protected int startedYazInActive;
    protected int endedYaz;
    protected int inRiskCounter;
    protected int windowOfPaymentCounter=1;
    protected StringProperty massagesProperty=new SimpleStringProperty();

    public DTOLoan() {


    }

    public static DTOLoan build(DTOLoan loan) {
        DTOLoan dtoLoan = new DTOLoan();
        dtoLoan.windowOfPaymentCounter=loan.windowOfPaymentCounter;
        dtoLoan.capital = loan.capital;
        dtoLoan.loanStatus = loan.loanStatus;
        dtoLoan.paysEveryYaz = loan.paysEveryYaz;
        dtoLoan.id = loan.id;
        dtoLoan.owner = loan.owner;
        dtoLoan.totalYazTime = loan.totalYazTime;
        dtoLoan.interestPerPayment = loan.interestPerPayment;
        dtoLoan.category = loan.category;
        dtoLoan.startedYazInActive=loan.startedYazInActive;
        dtoLoan.endedYaz=loan.endedYaz;
        dtoLoan.inRiskCounter=loan.inRiskCounter;
        dtoLoan.capitalSumLeftTillActive=loan.capitalSumLeftTillActive;
        List<DTOAccount> accompaniedList = new ArrayList<>();
        List<DTOInlay> inlaysList = new ArrayList<>();
        List<DTOMovement> movementsList=new ArrayList<>();
        List<Integer> listOfYazPayments=new ArrayList<>();
        List<Integer> listOfInRiskYazPayments=new ArrayList<>();
        for (DTOAccount dtoAccount : loan.listOfAccompanied) {
            accompaniedList.add(DTOCustomer.build((DTOCustomer) dtoAccount));
        }
        for (DTOInlay dtoInlay : loan.listOfInlays) {
            inlaysList.add(DTOInlay.build(dtoInlay));
        }
        for (DTOMovement dtoMovement : loan.listOfMovements) {
            movementsList.add(DTOMovement.build(dtoMovement));
        }
        listOfYazPayments.addAll(loan.listOfYazPayments);
        listOfInRiskYazPayments.addAll(loan.listOfInRiskYazPayments);
        dtoLoan.listOfInRiskYazPayments=listOfInRiskYazPayments;
        dtoLoan.listOfYazPayments=listOfYazPayments;
        dtoLoan.listOfInlays=inlaysList;
        dtoLoan.listOfAccompanied=accompaniedList;
        dtoLoan.listOfMovements=movementsList;
        dtoLoan.massagesProperty=loan.massagesProperty;
        dtoLoan.pulseCounterThatHappened=loan.pulseCounterThatHappened;
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
                "Loan status - " + loanStatus+"\n") +
                "Final loan amount - "+ (this.capital+this.capital*this.getInterestPerPayment()/100) +"\n" +
                invokeStatusOperation(3);
    }

////////////////// I decided that I don't want to copy this CODE, so we will sand the number of the operation: //////////////////////////////
    public String invokeStatusOperation(int indexOperation){
        if(indexOperation ==3)
            return loanStatus.operationThree(this);
        return loanStatus.operationTwo(this);

    }

    public DTOLoanStatus getStatusOperation(){
        return loanStatus;
    }

    public List<Integer> getListOfInRiskYazPayments() {
        return listOfInRiskYazPayments;
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
        //it's the total pulses that we will have
    }

    public int paymentPerPulse() {
        return capital / pulseNumber() + (int)(capital / pulseNumber() * ( (double)(interestPerPayment / 100.0)));
        //it's the amount per payment capital+interest
    }

    public int theNextYazToBePaid(){
        return windowOfPaymentCounter*paysEveryYaz+startedYazInActive-1;
    }

    public int numberOfYazTillNextPulse(){
        if((YazLogic.currentYazUnit-(this.startedYazInActive-1))%this.paysEveryYaz==0) {
            windowOfPaymentCounter++;
        }
        return (YazLogic.currentYazUnit-(this.startedYazInActive-1))%this.paysEveryYaz;
    }

    public int numberOfYazTillNextPulseDK(){
        if((YazLogicDesktop.currentYazUnitProperty.getValue()-(this.startedYazInActive-1))%this.paysEveryYaz==0) {
            windowOfPaymentCounter++;
        }
        return (YazLogicDesktop.currentYazUnitProperty.getValue()-(this.startedYazInActive-1))%this.paysEveryYaz;
    }

    public int numberOfYazTillNextPulseWithoutTheIncOfWindowOfPaymentCounterDK(){
        return (YazLogicDesktop.currentYazUnitProperty.getValue()-(this.startedYazInActive-1))%this.paysEveryYaz;
    }


    public int calculatePaymentToLoaner(Customer customer) {

        return (paymentPerPulse() * (listOfInlays.stream().filter(i-> Objects.equals(i.dtoAccount.getCustomerName(), customer.getCustomerName())).collect(Collectors.toList()).get(0).investAmount * 100) / capital) / 100;
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

    public List<DTOMovement> getListOfMovements() {
        return listOfMovements;
    }

    public int getPulseCounterThatHappened() {
        return pulseCounterThatHappened;
    }

    public List<Integer> getListOfYazPayments() {
        return this.listOfYazPayments;
    }

    public int getTotalCapitalPayTillNow() {
        return this.listOfYazPayments.size()*this.paymentPerPulse();
    }

    public int getTotalCapitalPayTillEnd() {
        return this.pulseNumber()*this.paymentPerPulse()-this.getTotalCapitalPayTillNow();
    }

    public int getTotalInterestPayTillNow() {
        return this.listOfYazPayments.size()*(int)((this.capital) / this.pulseNumber() * ( (double)(this.interestPerPayment / 100.0)));
    }

    public int getTotalInterestPayTillEnd() {
        return this.pulseNumber()*(int)((this.capital) / this.pulseNumber() * ( (double)(this.interestPerPayment / 100.0)))-this.getTotalInterestPayTillNow();
    }

    public int getTotalAmountOfInRiskCapitalThatDidNotPayed(){
        return this.inRiskCounter*this.paymentPerPulse();
    }

    public int getWindowOfPaymentCounter(){
        return windowOfPaymentCounter;
    }

}
