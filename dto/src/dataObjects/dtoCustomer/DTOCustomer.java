package dataObjects.dtoCustomer;


import dataObjects.dtoBank.dtoAccount.DTOAccount;
import dataObjects.dtoBank.dtoAccount.DTOLoan;
import dataObjects.dtoBank.dtoAccount.DTOMovement;
import logic.bank.account.Account;
import logic.bank.account.Movement;
import logic.customer.Customer;

import java.util.ArrayList;

public class DTOCustomer implements DTOAccount {

    protected String name;
    protected int amount;
    protected ArrayList<DTOMovement> movements;
    protected final ArrayList<DTOLoan> loaner;
    protected final ArrayList<DTOLoan> borrower;


    public DTOCustomer(){
        movements=new ArrayList<>();
        loaner=new ArrayList<>();
        borrower=new ArrayList<>();

    }

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
    
    public static DTOCustomer build(Customer customer){
        DTOCustomer dtoCustomer=new DTOCustomer();
        dtoCustomer.name=customer.name;
        dtoCustomer.amount=customer.amount;
        ArrayList<DTOMovement> dtoMovements=new ArrayList<>();
        for (DTOMovement movement : customer.movements) {
            dtoMovements.add(DTOMovement.build(movement));
        }
        dtoCustomer.movements=dtoMovements;
        return dtoCustomer;
    }
}
