package dataObjects.dtoCustomer;


import dataObjects.dtoBank.dtoAccount.*;
import logic.YazLogic;
import logic.bank.account.Account;
import logic.bank.account.Movement;
import logic.customer.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DTOCustomer implements DTOAccount {

    protected String name;
    protected int amount;
    protected List<DTOMovement> movements;
    protected List<DTOInlay> inlays;
    protected List<DTOLoan> loaner;
    protected List<DTOLoan> borrower;


    public DTOCustomer(){
        inlays = new ArrayList<>();
        movements=new ArrayList<>();
        loaner=new ArrayList<>();
        borrower=new ArrayList<>();

    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public List<DTOMovement> getMovements(){
        return movements;
    }

    @Override
    public List<DTOInlay> getInlays(){return inlays;}

    @Override
    public String getCustomerName(){
        return name;
    }

    @Override
    public int getAllOpenLoansToBorrower(){
        return (int) borrower.stream().filter(l -> l.getLoanStatus() != DTOLoanStatus.FINISHED).count();
    }

    @Override
    public String toString()
    {
       // return "Name : "+name+"\n"+"Current amount : "+amount+"\n"+"-----------Movements-----------"+"\n"+movements.toString();
        return "Name : "+name+"\n"+"Current amount : "+amount;
    }
    
    public static DTOCustomer build(DTOCustomer customer){
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
