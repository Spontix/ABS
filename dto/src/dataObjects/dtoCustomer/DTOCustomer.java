package dataObjects.dtoCustomer;


import dataObjects.bank.dtoAccount.DTOAccount;
import dataObjects.bank.dtoAccount.DTOMovement;

import java.util.ArrayList;

public class DTOCustomer implements DTOAccount {

    protected String name;
    protected int amount;
    protected ArrayList<DTOMovement> movements;

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    @Override
    public ArrayList<DTOMovement> getMovements(){
        return movements;
    }

    public DTOCustomer(){

    }
}
