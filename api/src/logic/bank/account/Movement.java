package logic.bank.account;

import dataObjects.bank.dtoAccount.DTOMovement;

public class Movement extends DTOMovement {

    public void setSum(int movementSum) {
        sum=movementSum;
    }

    public void setSumAfterOperation(int movementSumAfterOperation) {
        sumAfterOperation=movementSumAfterOperation;
    }

    public void setSumBeforeOperation(int movementSumBeforeOperation) {
        sumBeforeOperation=movementSumBeforeOperation;
    }

    public void setToDoYazTime(int movementToDoYazTime) {
        toDoYazTime=movementToDoYazTime;

    }

    public void setOperation(String movementOperation) {
        operation=movementOperation;
    }
}
