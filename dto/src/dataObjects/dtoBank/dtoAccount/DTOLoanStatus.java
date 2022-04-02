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
            for(int i=0; i<dtoLoan.listOfInlays.size(); i++){
                sum += dtoLoan.listOfInlays.get(i).investAmount;
            }
            return "The missing amount until the loan will become active is - " +  dtoLoan.capital+"\n";
        }
    },
    ACTIVE{
        @Override
        public String operationFife(DTOLoan dtoLoan) {
            return null;
        }

        @Override
        public String operationThree(DTOLoan dtoLoan) {
            return null;
        }
    }
    ,RISK{
        @Override
        public String operationFife(DTOLoan dtoLoan) {
            return null;
        }

        @Override
        public  String operationThree(DTOLoan dtoLoan) {
            return null;
        }
    }
    ,FINISHED{
        @Override
        public String operationFife(DTOLoan dtoLoan) {
            return null;
        }

        @Override
        public String operationThree(DTOLoan dtoLoan) {
            return null;
        }
    }
    ,NEW {
        @Override
        public String operationFife(DTOLoan dtoLoan) {
            return null;
        }

        @Override
        public String operationThree(DTOLoan dtoLoan) {
            return null;
        }
    };

    public abstract String operationFife(DTOLoan dtoLoan);
    public abstract String operationThree(DTOLoan dtoLoan);
}
