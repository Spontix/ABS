package logic.bank.account;

import logic.bank.enumsType.LoanStatus;
import logic.customer.Accompanied;

import java.util.List;

public class Loan {
    private String id;
    private String owner;
    private String category;
    private int capital;
    private int totalYazTime;
    private int paysEveryYaz;
    private int interestPerPayment;
    private final LoanStatus loanStatus;
    private List<Accompanied> listOfAccompanied;
//יש להציג את סך הסכום שכבר גויס ע"י המלווים ואיזה סכום עוד נותר כדי להפוך הלוואה זו לפעילה.


    public Loan(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public int getCapital() {
        return capital;
    }

    public void setTotalYazTime(int totalYazTime) {
        this.totalYazTime = totalYazTime;
    }

    public int getTotalYazTime() {
        return totalYazTime;
    }

    public void setInterestPerPayment(int interestPerPayment) {
        this.interestPerPayment = interestPerPayment;
    }

    public int getInterestPerPayment() {
        return interestPerPayment;
    }

    public void setPaysEveryYaz(int paysEveryYaz) {
        this.paysEveryYaz = paysEveryYaz;
    }

    public int getPaysEveryYaz() {
        return paysEveryYaz;
    }

    public List<Accompanied> getlistOfAccompanied() {
        return listOfAccompanied;
    }


}
