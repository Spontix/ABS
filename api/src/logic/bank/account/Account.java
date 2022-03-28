package logic.bank.account;

public interface Account {

    void setAmount(int amount);

    void cashDeposit(int sum);

    void cashWithdrawal(int sum);
}
