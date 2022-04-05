package logic.bank.account;

import dataObjects.dtoBank.dtoAccount.DTOMovement;
import logic.YazLogic;

public class Movement extends DTOMovement implements Cloneable {

    private Movement() {

    }

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

    public static Movement build(int movementSum,String movementOperation,int movementSumBeforeOperation,int movementSumAfterOperation){
        Movement movement=new Movement();
        movement.setSum(movementSum);
        movement.setOperation(movementOperation);
        movement.setToDoYazTime(YazLogic.currentYazUnit);
        if(movementSumAfterOperation<0){
            throw new RuntimeException("The operation cant be preformed because there is not enough money in this account!!");
        }
        movement.setSumAfterOperation(movementSumAfterOperation);
        movement.setSumBeforeOperation(movementSumBeforeOperation);
        return movement;
    }


    @Override
    public Movement clone() {
        try {
            return (Movement) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
