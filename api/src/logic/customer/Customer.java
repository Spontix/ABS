package logic.customer;

import dataObjects.bank.dtoAccount.DTOMovement;
import dataObjects.dtoCustomer.DTOCustomer;
import logic.bank.account.Account;
import logic.bank.account.Movement;

import java.util.ArrayList;

//Eliran
public class Customer extends DTOCustomer implements Account {

        @Override
        public void setAmount(int startAmount)
        {
            amount=startAmount;
        }

       @Override
       public void cashDeposit(int sum){
               amount+=sum;
       }

        @Override
        public void cashWithdrawal(int sum){
                if(amount>=sum) {
                        amount -= sum;
                }
        }

}
