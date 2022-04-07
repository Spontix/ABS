package dataObjects.dtoBank.dtoAccount;

import dataObjects.dtoBank.dtoAccount.DTOLoan;

import java.util.ArrayList;

public enum DTOLoanStatus {
    PENDING{
        @Override
        public String operationTwo(DTOLoan dtoLoan) {
            return printLoansList(dtoLoan) + operationThree(dtoLoan);
        }

        public String operationThree(DTOLoan dtoLoan){
            int sum =0;
            for (DTOInlay dtoInlay:dtoLoan.listOfInlays) {
                sum += dtoInlay.investAmount;
            }
            return "The missing amount until the loan will become active is - " +  (dtoLoan.capital-sum)+"\n";
        }
    },
    ACTIVE{
        @Override
        public String operationTwo(DTOLoan dtoLoan) {
            return printLoansList(dtoLoan) +"The Yaz when the status loan become ACTIVE is: "+dtoLoan.startedYazInActive +"\n"+ operationThree(dtoLoan);///+ כל החלק החל ממידע על כל התשלומים שבוצעו עד כה. על כל תשלום יש לפרט:
        }

        @Override
        public String operationThree(DTOLoan dtoLoan) {
            return "The next Yaz to be paid is: "+dtoLoan.theNextYazToBePaid()+"\n"+"The expected payment is: "+dtoLoan.paymentPerPulse();
        }
    }
    ,RISK{
        @Override
        public String operationTwo(DTOLoan dtoLoan) {
            return null;
        }

        @Override
        public  String operationThree(DTOLoan dtoLoan) {
            return "The total payments number that was not paid are: "+dtoLoan.inRiskCounter+"/"+dtoLoan.pulseNumber()+"\n"+"And the dept is:  "+ dtoLoan.totalAmountThatWasNotPayed();
        }
    }
    ,FINISHED{
        @Override
        public String operationTwo(DTOLoan dtoLoan) {
            return printLoansList(dtoLoan) + operationThree(dtoLoan); //+ •	מידע על כל התשלומים ששולמו בפועל (כפי שמוגדר ב active)
        }

        @Override
        public String operationThree(DTOLoan dtoLoan) {
            return "Started YAZ is: "+dtoLoan.startedYazInActive+"\n"+"Ended YAZ is: "+dtoLoan.endedYaz;
        }
    }
    ,NEW {
        @Override
        public String operationTwo(DTOLoan dtoLoan) {
            return new String("The loan standing on NEW status there for the capital stand still");
        }

        @Override
        public String operationThree(DTOLoan dtoLoan) {

            return new String("The loan standing on NEW status there for the capital stand still");
        }
    };

    public abstract String operationTwo(DTOLoan dtoLoan);
    public abstract String operationThree(DTOLoan dtoLoan);

    public String printLoansList(DTOLoan dtoLoan){
        String stringPrint = new String();
        int index=1;
        if(dtoLoan.listOfAccompanied == null)
            stringPrint+="The loaners list is currently empty.";
        else {
            stringPrint+="------------ Loaners list ------------\n";
            for (DTOAccount accompanied : dtoLoan.listOfAccompanied) {
                stringPrint += index + ". Name: " + accompanied.getCustomerName() + ", The invest amount: " + accompanied.getAmount() + "\n";
            }
        }
        return stringPrint;
    }
}
