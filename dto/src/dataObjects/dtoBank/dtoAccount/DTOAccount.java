package dataObjects.dtoBank.dtoAccount;

import java.util.ArrayList;
import java.util.List;

public interface DTOAccount {

    List<DTOInlay> getInlays();
    List<DTOMovement> getMovements();
    String getCustomerName();
    int getAmount();
    String toString();
}
