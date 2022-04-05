package logic.bank;


import generated.AbsDescriptor;
import logic.UIInterfaceLogic;
import logic.bank.Bank;
import logic.bank.account.Account;
import logic.bank.account.Loan;
import logic.customer.Customer;
import menuBuilder.OptionInvoker;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlSerialization{
    //Eliran
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "generated";

    public static Bank buildBank(String fileName) {
        Bank bank=null;
        try {
            InputStream inputStream = new FileInputStream(new File(fileName));
            bank= deserializeFrom(inputStream);
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return bank;
    }

    private static Bank deserializeFrom(InputStream in) throws JAXBException {
        Bank bank=new Bank();
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        AbsDescriptor absDescriptor= (AbsDescriptor) u.unmarshal(in);
        absDescriptor.getAbsCustomers().getAbsCustomer().forEach(customer->bank.getAccounts().add(Customer.build(customer.getName(),customer.getAbsBalance())));
        absDescriptor.getAbsCategories().getAbsCategory().forEach(category->bank.getCategories().add(category));
        absDescriptor.getAbsLoans().getAbsLoan().forEach(loan->bank.getLoans().add(Loan.build(loan.getId(),loan.getAbsOwner(),loan.getAbsCategory(),loan.getAbsCapital(),loan.getAbsTotalYazTime(),loan.getAbsPaysEveryYaz(),loan.getAbsIntristPerPayment())));
        return bank;
    }
}


