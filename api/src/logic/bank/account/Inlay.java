package logic.bank.account;

import dataObjects.dtoBank.dtoAccount.DTOAccount;
import dataObjects.dtoBank.dtoAccount.DTOInlay;
import dataObjects.dtoCustomer.DTOCustomer;
import logic.customer.Customer;

import java.util.Scanner;

public class Inlay extends DTOInlay {

    public Inlay(){

    }

    public void setInvestAmount(int inlayInvestAmount){
        investAmount=inlayInvestAmount;
    }

    public void setCategory(String inlayCategory){
        category = inlayCategory;
    }

    public void setMinInterestYaz(double inlayMinInterestYaz){
        minInterestYaz = inlayMinInterestYaz;
    }

    public void setMinYazTime(int inlayMinYazTime){
        minYazTime = inlayMinYazTime;
    }

    public void setInlayCustomer(DTOAccount customer){
        dtoAccount=customer;
    }


    public static Inlay build(DTOAccount customer, int investAmount,String category,double minInterestYaz,int minYazTime){
        Inlay inlay=new Inlay();
        if(investAmount > customer.getAmount() || investAmount<0){
            throw new RuntimeException("The investment amount is above the amount balance,please try again!");
        }
        inlay.setInvestAmount(investAmount);
        inlay.setCategory(category);
        inlay.setMinInterestYaz(minInterestYaz);
        inlay.setMinYazTime(minYazTime);
        inlay.setInlayCustomer(customer);
        return inlay;
    }

}
