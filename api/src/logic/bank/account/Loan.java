package logic.bank.account;

import logic.customer.Accompanied;
//Eliran123
import java.util.List;

public class Loan {
    private String customerName;
    private int originalLoanAmount;
    private String uniqueLoanName;
    private int timeUnits;
    private int rateOfPaymentsPerUnitOfTime;
    private double interestRate;
    private List<Accompanied> listOfAccompanied;
    private int numberOfUnitsOfTimeToCompleteTheLoan;
    private double currentInterestPaid;
    private  int currentPaidFoundation;

}
