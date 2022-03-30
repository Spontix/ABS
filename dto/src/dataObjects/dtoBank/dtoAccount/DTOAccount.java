package dataObjects.dtoBank.dtoAccount;

import java.util.ArrayList;

public interface DTOAccount {

    ArrayList<DTOMovement> getMovements();
    String getCustomerName();
    int getAmount();
    String toString();
}
