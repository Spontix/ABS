package logic.bank.account;

import dataObjects.dtoBank.dtoAccount.DTOAccount;

public interface Account extends DTOAccount {

    void setAmount(int amount);

    void cashDeposit(int sum);

    void cashWithdrawal(int sum);


}
