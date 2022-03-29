package dataObjects.bank.dtoAccount;

public class DTOMovement {
    protected int toDoYazTime;
    protected int sum;
    protected String operation;
    protected int sumBeforeOperation;
    protected int sumAfterOperation;

    public int getSum() {
        return sum;
    }

    public int getSumAfterOperation() {
        return sumAfterOperation;
    }

    public int getSumBeforeOperation() {
        return sumBeforeOperation;
    }

    public int getToDoYazTime() {
        return toDoYazTime;
    }

    public String getOperation() {
        return operation;
    }
}
