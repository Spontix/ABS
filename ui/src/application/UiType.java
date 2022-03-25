package application;

import logic.bank.account.Loan;

public interface UiType {

    public void Run();
    public String sumToInvest(Loan x);
    public String category(Loan x);
    public String minimumInterestPerUnitTime(Loan x);
    public String minimumTimePerUnitTime(Loan x);
    public String maximumPercentageOfOwnership(Loan x);
    public String maximumLoansOpenToTheBorrower(Loan x);
}
