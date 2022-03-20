package logic.bank.account;

import logic.customer.Accompanied;
//Eliran
import java.util.List;

public class Loan {
    private String customerName;
    private int originalLoanAmount;
    private String uniqueLoanName;
    private int timeUnits;
    private int rateOfPaymentsPerUnitOfTime;
    private double interestRate;
    //קטגוריה: מידע המתאר את סוג ההלוואה: עסק, שיפוץ בית, סגירת מינוס, אירוע וכו'. זהו סט סופי ומוגדר של אפשרויות
    private List<Accompanied> listOfAccompanied;
    private int numberOfUnitsOfTimeToCompleteTheLoan;
    private double currentInterestPaid;
    //Enum of status

}
