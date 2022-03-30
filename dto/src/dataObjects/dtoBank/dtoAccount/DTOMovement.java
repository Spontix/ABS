package dataObjects.dtoBank.dtoAccount;

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

    @Override
    public String toString(){
        return "<To do yaz time : "+toDoYazTime+"\n"+"Sum : "+sum+"\n"+"Operation : "+ operation+"\n"+ "Sum before operation :"+sumBeforeOperation+"\n"+"Sum after operation : "+sumAfterOperation+">\n";
    }
}
