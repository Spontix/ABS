package dataObjects.dtoCustomer;


import dataObjects.dtoBank.dtoAccount.DTOAccount;
import dataObjects.dtoBank.dtoAccount.DTOMovement;

import java.util.ArrayList;

public class DTOCustomer implements DTOAccount {

    protected String name;
    protected int amount;
    protected ArrayList<DTOMovement> movements=new ArrayList<>();

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public ArrayList<DTOMovement> getMovements(){
        return movements;
    }

    @Override
    public String getCustomerName(){
        return name;
    }

    @Override
    public String toString()
    {
        return "Name : "+name+"\n"+"Current amount : "+amount+"\n"+"-----------Movements-----------"+"\n"+movements.toString();
    }
}
