package dataObjects.dtoBank.dtoAccount;

import dataObjects.dtoCustomer.DTOCustomer;
import logic.YazLogic;
import logic.customer.Customer;

public class DTOInlay {
    protected DTOAccount dtoAccount = new DTOCustomer();
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

    public static DTOInlay build(DTOInlay inlay){
        DTOInlay dtoInlay=new DTOInlay();
        dtoInlay.investAmount=inlay.investAmount;
        dtoInlay.category=inlay.category;
        dtoInlay.minInterestYaz=inlay.minInterestYaz;
        dtoInlay.minYazTime=inlay.minYazTime;
        dtoInlay.dtoAccount=DTOCustomer.build((DTOCustomer)inlay.dtoAccount);
        return dtoInlay;
    }

}
