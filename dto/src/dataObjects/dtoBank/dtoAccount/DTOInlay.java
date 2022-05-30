package dataObjects.dtoBank.dtoAccount;

import dataObjects.dtoCustomer.DTOCustomer;
import logic.YazLogic;
import logic.customer.Customer;

public class DTOInlay {
    protected DTOAccount dtoAccount = new DTOCustomer();
    protected String name;
    protected int investAmount;
    protected String category;
    protected double minInterestYaz;
    protected int minYazTime;
    protected int maximumLoansOpenToTheBorrower;


    public int getMaximumLoansOpenToTheBorrower(){return maximumLoansOpenToTheBorrower;}

    public String getCategory() {
        return category;
    }

    public int getInvestAmount() {
        return investAmount;
    }

    public double getMinInterestYaz() {
        return minInterestYaz;
    }

    public int getMinYazTime() {
        return minYazTime;
    }

    public DTOAccount getDtoAccount() {
        return dtoAccount;
    }

    public static DTOInlay build(DTOInlay inlay){
        DTOInlay dtoInlay=new DTOInlay();
        dtoInlay.investAmount=inlay.investAmount;
        dtoInlay.maximumLoansOpenToTheBorrower = inlay.maximumLoansOpenToTheBorrower;
        dtoInlay.category=inlay.category;
        dtoInlay.minInterestYaz=inlay.minInterestYaz;
        dtoInlay.minYazTime=inlay.minYazTime;
        dtoInlay.name=inlay.dtoAccount.getCustomerName();
        dtoInlay.dtoAccount=DTOCustomer.build((DTOCustomer)inlay.dtoAccount);
        return dtoInlay;
    }

}
