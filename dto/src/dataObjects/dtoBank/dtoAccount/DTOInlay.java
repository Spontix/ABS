package dataObjects.dtoBank.dtoAccount;

public class DTOInlay {
    protected int investAmount;
    protected String category;
    protected double minInterestYaz;
    protected int minYazTime;

    public String getCategory() {
        return category;
    }

    public int getInvestAmount() {
        return investAmount;
    }

    public double getMinInterestYaz() {
        return minInterestYaz;
    }

    public int getMinYazTime() {
        return minYazTime;
    }
}
