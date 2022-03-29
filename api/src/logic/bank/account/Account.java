package logic.bank.account;

import dataObjects.bank.dtoAccount.DTOAccount;
import dataObjects.bank.dtoAccount.DTOMovement;

import java.util.ArrayList;

public interface Account extends DTOAccount {

    void setAmount(int amount);

    void cashDeposit(int sum);

    void cashWithdrawal(int sum);

}
