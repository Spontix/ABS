package dataObjects.dtoBank.dtoAccount;

import logic.bank.account.PaymentsInfo;

public class DTOPaymentsInfo {
    protected int yazPayment;
    protected int capitalAmount;
    protected int interestAmount;
    protected int capitalAndInterest;
    protected String isPaid;

    static DTOPaymentsInfo build(PaymentsInfo paymentsInfo){
        DTOPaymentsInfo dtoPaymentsInfo=new DTOPaymentsInfo();
        dtoPaymentsInfo.interestAmount= paymentsInfo.interestAmount;;
        dtoPaymentsInfo.yazPayment= paymentsInfo.yazPayment;
        dtoPaymentsInfo.capitalAmount = paymentsInfo.capitalAmount;;
        dtoPaymentsInfo.isPaid = paymentsInfo.isPaid;
        dtoPaymentsInfo.capitalAndInterest = paymentsInfo.capitalAndInterest;
        return dtoPaymentsInfo;
    }

    public int getYazPayment(){
        return yazPayment;
    }

    public int getCapitalAmount(){
        return capitalAmount;
    }

    public int getCapitalAndInterest(){
        return capitalAndInterest;
    }

    public int getInterestAmount(){
        return interestAmount;
    }

    public String getIsPaid(){
        return isPaid;
    }


}
