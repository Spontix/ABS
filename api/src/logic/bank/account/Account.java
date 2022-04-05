package logic.bank.account;

import dataObjects.dtoBank.dtoAccount.DTOAccount;
import dataObjects.dtoBank.dtoAccount.DTOMovement;

import java.util.List;


public interface Account extends DTOAccount {

    void setAmount(int amount);


    void cashDeposit(int sum);

    void cashWithdrawal(int sum);


}
