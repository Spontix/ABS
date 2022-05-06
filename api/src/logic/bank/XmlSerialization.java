package logic.bank;


import generated.AbsCustomer;
import generated.AbsDescriptor;
import generated.AbsLoan;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;


public class XmlSerialization{
    //Eliran
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "generated";

    public static Bank buildBank(String fileName) throws Exception {
        Bank bank=null;
        File file=new File(fileName);
        if (!file.exists())
            throw new FileNotFoundException();
        else if(!fileName.endsWith(".xml"))
            throw new Exception(" ");
        else {
            try {
                InputStream inputStream = new FileInputStream(new File(fileName));
                bank = deserializeFrom(inputStream);
            }
            catch (JAXBException | FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        return bank;
    }

    private static Bank deserializeFrom(InputStream in) throws Exception {
        Bank bank = new Bank();
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        AbsDescriptor absDescriptor = (AbsDescriptor) u.unmarshal(in);
        checksIfTheInformationOfTheFileAreCorrect(absDescriptor);
        absDescriptor.getAbsCustomers().getAbsCustomer().forEach(customer -> {
            try {
                bank.customerBuild(customer.getName(), customer.getAbsBalance());
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        absDescriptor.getAbsLoans().getAbsLoan().forEach(loan -> {
            try {
                bank.loanBuilder(loan.getId(), loan.getAbsOwner(), loan.getAbsCategory(), loan.getAbsCapital(), loan.getAbsTotalYazTime(), loan.getAbsPaysEveryYaz(), loan.getAbsIntristPerPayment());
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        absDescriptor.getAbsCategories().getAbsCategory().forEach(category -> bank.getCategories().add(category));

        return bank;
    }

    private static void checksIfTheInformationOfTheFileAreCorrect(AbsDescriptor absDescriptor) throws Exception {
        List<Object> objects=absDescriptor.getAbsLoans().getAbsLoan().stream().filter(l -> absDescriptor.getAbsCategories().getAbsCategory().contains(l.getAbsCategory())).collect(Collectors.toList());
        if(objects.size()!=absDescriptor.getAbsLoans().getAbsLoan().size()){
            throw new Exception("This category does not exist : " + convertToNameOfCategories(objects));
        }
        objects=absDescriptor.getAbsLoans().getAbsLoan().stream().filter(l -> l.getAbsTotalYazTime() % l.getAbsPaysEveryYaz() > 0).collect(Collectors.toList());
        if(objects.size()>0){
            throw new Exception("The division between totalYazNumber to paysEveryYaz is not an integer in : " + convertToNameOfLoans(objects));
        }
        objects=absDescriptor.getAbsLoans().getAbsLoan().stream().filter(l->absDescriptor.getAbsCustomers().getAbsCustomer().stream().anyMatch(c->l.getAbsOwner().equals(c.getName()))).collect(Collectors.toList());
        if(objects.size()!=absDescriptor.getAbsLoans().getAbsLoan().size()){
            throw new Exception("There is an open loan that the owner does not exist in the customer list : " + convertToNameOfLoans(objects));
        }
        objects=absDescriptor.getAbsCustomers().getAbsCustomer().stream().filter(c1->absDescriptor.getAbsCustomers().getAbsCustomer().stream().filter(c2->c2.getName().equals(c1.getName())).count()>1).collect(Collectors.toList());
        if(objects.size()!=0){
            throw new Exception("This client already exist : " + convertToNameOfCustomers(objects));
        }
        objects=absDescriptor.getAbsLoans().getAbsLoan().stream().filter(l1->absDescriptor.getAbsLoans().getAbsLoan().stream().filter(l2->l2.getId().equals(l1.getId())).count()>1).collect(Collectors.toList());
        if(objects.size()!=0){
            throw new Exception("This client already exist : "+ convertToNameOfLoans(objects));
        }
    }

    private static String convertToNameOfLoans(List<Object> loans){
        StringBuilder string= new StringBuilder("[");
        for (Object loan:loans) {
            string.append(((AbsLoan) loan).getId()).append("-");
        }
        return string.deleteCharAt(string.lastIndexOf("-")).append("]").toString();
    }

    private static String convertToNameOfCustomers(List<Object> customers){
        StringBuilder string= new StringBuilder("[");
        for (Object customer:customers) {
            string.append(((AbsCustomer) customer).getName()).append("-");
        }
        return string.deleteCharAt(string.lastIndexOf("-")).append("]").toString();
    }

    private static String convertToNameOfCategories(List<Object> categories){
        StringBuilder string= new StringBuilder("[");
        for (Object category:categories) {
            string.append(((AbsLoan) category).getAbsCategory()).append("-");
        }
        return string.deleteCharAt(string.lastIndexOf("-")).append("]").toString();
    }
}


