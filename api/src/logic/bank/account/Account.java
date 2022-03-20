package logic.bank.account;

public interface Account {

    void setAmount(int amount);

    String cashDeposit(int sum);

    public String cashWithdrawal(int sum);
}
