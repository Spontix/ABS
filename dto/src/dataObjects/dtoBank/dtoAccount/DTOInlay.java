package dataObjects.dtoBank.dtoAccount;

import dataObjects.dtoCustomer.DTOCustomer;
import logic.customer.Customer;

public class DTOInlay {
    protected DTOAccount dtoAccount=new DTOCustomer();
    protected int investAmount;
    protected String category;
    protected double minInterestYaz;
    protected int minYazTime;

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
}
