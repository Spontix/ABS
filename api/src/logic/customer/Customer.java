package logic.customer;

import logic.bank.account.Account;
//Eliran
public abstract class Customer implements Account {
        String name;
        private int amount;

        @Override
        public void setAmount(int startAmount)
        {
            amount=startAmount;
        }

       @Override
       public String cashDeposit(int sum){
               amount+=sum;
               return "The deposit of funds was successful!.\n "+"The current amount is: "+amount+"\n";
       }

        @Override
        public String cashWithdrawal(int sum){
                if(amount>=sum) {
                        amount -= sum;
                        return "The withdrawal of funds was successful!.\n ";
                }
                else{
                        return "Operation failed! There is not enough money in the account!.\n";
                }
        }
}
