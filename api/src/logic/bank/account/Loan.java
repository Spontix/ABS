package logic.bank.account;

//Eliran123


import dataObjects.dtoBank.dtoAccount.DTOLoan;
import dataObjects.dtoBank.dtoLoanStatus.DTOLoanStatus;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Loan extends DTOLoan {


    public Loan(DTOLoanStatus loanStatusLoan,String idLoan,String ownerLoan,String categoryLoan,int capitalLoan,int totalYazTimeLoan,int paysEveryYazLoan,int interestPerPaymentLoan) {
        super(loanStatusLoan,idLoan,ownerLoan,categoryLoan,capitalLoan,totalYazTimeLoan,paysEveryYazLoan,interestPerPaymentLoan);
    }

    public int setTotalYaz(int number)
    {
        return totalYazTime+number;
    }

    private void setPendingDetails(){
        //int sum=listOfAccompanied.stream().mapToInt(a -> a.get)
    }

    public DTOLoan exeStatus(DTOLoanStatus loanStatus){
        switch (loanStatus){
            case PENDING:
                setPendingDetails();
                break;
            case ACTIVE:

                break;
            case RISK:

                break;

            case FINISHED:

                break;

        }

        return this;

    }


}

