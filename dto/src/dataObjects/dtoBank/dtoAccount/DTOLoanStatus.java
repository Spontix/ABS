package dataObjects.dtoBank.dtoAccount;

import dataObjects.dtoBank.dtoAccount.DTOLoan;

import java.util.ArrayList;

public enum DTOLoanStatus {
    PENDING{
        @Override
        public String operationFife(DTOLoan dtoLoan) {

            return null;
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
        public String operationFife(DTOLoan dtoLoan) {
            return null;
        }

        @Override
        public String operationThree(DTOLoan dtoLoan) {
            return "The next Yaz to be paid is: "+dtoLoan.theNextYazToBePaid()+"\n"+"The expected payment is: "+dtoLoan.paymentPerPulse();
        }
    }
    ,RISK{
        @Override
        public String operationFife(DTOLoan dtoLoan) {
            return null;
        }

        @Override
        public  String operationThree(DTOLoan dtoLoan) {
            return "The total payments number that was not paid are: "+dtoLoan.inRiskCounter+"/"+dtoLoan.pulseAmount()+"\n"+"And the dept is:  "+ dtoLoan.totalAmountThatWasNotPayed();
        }
    }
    ,FINISHED{
        @Override
        public String operationFife(DTOLoan dtoLoan) {
            return null;
        }

        @Override
        public String operationThree(DTOLoan dtoLoan) {
            return "Started YAZ is: "+dtoLoan.startedYazInActive+"\n"+"Ended YAZ is: "+dtoLoan.endedYaz;
        }
    }
    ,NEW {
        @Override
        public String operationFife(DTOLoan dtoLoan) {
            return null;
        }

        @Override
        public String operationThree(DTOLoan dtoLoan) {

            return new String("The loan standing on NEW status there for the capital stand still");
        }
    };

    public abstract String operationFife(DTOLoan dtoLoan);
    public abstract String operationThree(DTOLoan dtoLoan);
}
