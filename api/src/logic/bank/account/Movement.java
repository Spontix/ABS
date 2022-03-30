package logic.bank.account;

import dataObjects.dtoBank.dtoAccount.DTOMovement;

public class Movement extends DTOMovement {

    private void setSum(int movementSum) {
        sum=movementSum;
    }

    private void setSumAfterOperation(int movementSumAfterOperation) {
        sumAfterOperation=movementSumAfterOperation;
    }

    private void setSumBeforeOperation(int movementSumBeforeOperation) {
        sumBeforeOperation=movementSumBeforeOperation;
    }

    private void setToDoYazTime(int movementToDoYazTime) {
        toDoYazTime=movementToDoYazTime;

    }

    private void setOperation(String movementOperation) {
        operation=movementOperation;
    }

    public static DTOMovement build(int movementSum,String movementOperation,int movementSumBeforeOperation,int movementSumAfterOperation,int movementToDoYaz){
        Movement movement=new Movement();
        movement.setSum(movementSum);
        movement.setOperation(movementOperation);
        movement.setSumAfterOperation(movementSumAfterOperation);
        movement.setSumBeforeOperation(movementSumBeforeOperation);
        movement.setToDoYazTime(movementToDoYaz);
        return movement;
    }
}
