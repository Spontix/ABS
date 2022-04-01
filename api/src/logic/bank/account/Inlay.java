package logic.bank.account;

import dataObjects.dtoBank.dtoAccount.DTOAccount;
import dataObjects.dtoBank.dtoAccount.DTOInlay;
import dataObjects.dtoCustomer.DTOCustomer;
import logic.customer.Customer;

import java.util.Scanner;

public class Inlay extends DTOInlay {

    public Inlay(){

    }

    public void setInvestAmount(DTOAccount inlayChosenCustomer,int inlayInvestAmount){
        if(inlayChosenCustomer.getAmount()<inlayInvestAmount){
            throw new RuntimeException("The investment amount is above the amount balance,please try again!");
        }
        investAmount=inlayInvestAmount;
    }

    public void setCategory(String inlayCategory){
        category = inlayCategory;
    }

    public void setMinInterestYaz(double inlayMinInterestYaz){
        if(inlayMinInterestYaz<0){
            throw new RuntimeException("The investment amount is above the amount balance,please try again!");
        }
        minInterestYaz = inlayMinInterestYaz;
    }

    public void setMinYazTime(int inlayMinYazTime){
        minYazTime = inlayMinYazTime;
    }

    public void setInlayCustomer(DTOAccount customer){
        dtoAccount=customer;
    }

}
