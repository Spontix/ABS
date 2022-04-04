package dataObjects.dtoBank.dtoAccount;

import java.util.ArrayList;

public interface DTOAccount {

    ArrayList<DTOInlay> getInlays();
    ArrayList<DTOMovement> getMovements();
    String getCustomerName();
    int getAmount();
    String toString();
}
