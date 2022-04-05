package logic.customer;

import dataObjects.dtoBank.dtoAccount.DTOInlay;
import dataObjects.dtoBank.dtoAccount.DTOLoan;
import dataObjects.dtoBank.dtoAccount.DTOMovement;
import dataObjects.dtoCustomer.DTOCustomer;
import logic.bank.account.Account;

import java.util.ArrayList;
import java.util.List;

//Eliran
public class Customer extends DTOCustomer implements Account {

        @Override
        public void setAmount(int startAmount)
        {
            amount=startAmount;
        }


    public Customer(){/////////////////

    }

    public static Customer build(String name,int amount){
            Customer customer=new Customer();
            customer.name=name;
            customer.amount=amount;
            customer.movements=new ArrayList<>();
            customer.borrower=new ArrayList<>();
            customer.inlays=new ArrayList<>();
            customer.loaner=new ArrayList<>();
            return customer;
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
