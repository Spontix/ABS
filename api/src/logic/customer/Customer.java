package logic.customer;

import dataObjects.dtoCustomer.DTOCustomer;
import logic.bank.account.Account;

//Eliran
public class Customer extends DTOCustomer implements Account {

        @Override
        public void setAmount(int startAmount)
        {
            amount=startAmount;
        }

    public Customer(){///should be private because the user should not have the power to creat a new customer without any information , that's why we have the static method Customer.build
            amount=10000;
            name="Menash";
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
