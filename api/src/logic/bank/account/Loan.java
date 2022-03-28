package logic.bank.account;

//Eliran123


import dataObjects.bank.dtoAccount.DTOLoan;
import dataObjects.bank.dtoLoanStatus.DTOLoanStatus;

import java.util.ArrayList;

public class Loan extends DTOLoan {


    public Loan(DTOLoanStatus loanStatusLoan,String idLoan,String ownerLoan,String categoryLoan,int capitalLoan,int totalYazTimeLoan,int paysEveryYazLoan,int interestPerPaymentLoan) {
        super(loanStatusLoan,idLoan,ownerLoan,categoryLoan,capitalLoan,totalYazTimeLoan,paysEveryYazLoan,interestPerPaymentLoan);
    }

    public int setTotalYaz(int number)
    {
        return totalYazTime+number;
    }


}

