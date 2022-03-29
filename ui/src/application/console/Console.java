package application.console;

import application.UiType;
import dataObjects.bank.dtoAccount.DTOLoan;
import dataObjects.bank.dtoLoanStatus.DTOLoanStatus;
import logic.bank.Bank;
import logic.bank.account.Loan;
import logic.customer.Customer;
import logic.fileUpload.XmlSerialization;
import menuBuilder.MainMenu;
import menuBuilder.MenuItem;
import menuBuilder.MenuType;
import menuBuilder.OptionInvoker;

import javax.xml.bind.annotation.XmlAccessOrder;
import java.util.ArrayList;
import java.util.Scanner;


public class Console implements UiType {
    Bank bank;
    private XmlSerialization xml=new XmlSerialization();

    public Console(){
        bank=new Bank();
    }


    public void Run() {
       MainMenu menu = new MainMenu("Main", MenuType.PRIMARY_MENU);
       createMenu(menu);
       menu.show();
   }

    private void createMenu(MainMenu menu) {
        /*MainMenu first =
                new MainMenu("Reading the system information file.", MenuType.SECONDARY_MENU);*/
        MenuItem first=new MenuItem("Reading the system information file.");
        //first.AddListener(()->sumToInvest(new Loan()));
        menu.insertToMenu(first);
        MenuItem second = new MenuItem("Presentation of information on existing loans and their status.");
        second.AddListener(()->showDataLoans());
        menu.insertToMenu(second);
        MainMenu third =
                new MainMenu("Display information about system customers.", MenuType.SECONDARY_MENU);
        menu.insertToMenu(third);
        MainMenu fourth =
                new MainMenu("Loading funds into the account.", MenuType.SECONDARY_MENU);
        menu.insertToMenu(fourth);
        MainMenu fifth =
                new MainMenu("Withdrawal of funds from the account.", MenuType.SECONDARY_MENU);
        menu.insertToMenu(fifth);
        MainMenu sixth =
                new MainMenu("Inlay activation.", MenuType.SECONDARY_MENU);
        menu.insertToMenu(sixth);
        MainMenu seventh =
                new MainMenu("Promoting the timeline and providing payments.", MenuType.SECONDARY_MENU);
        menu.insertToMenu(seventh);
        MainMenu eighth =
                new MainMenu("Exiting the system.", MenuType.SECONDARY_MENU);
        menu.insertToMenu(eighth);
    }

    @Override
    public void sumToInvest(Loan x) {
    }

    @Override
    public void category(Loan x) {
    }

    @Override
    public void minimumInterestPerUnitTime(Loan x) {
    }

    @Override
    public void minimumTimePerUnitTime(Loan x) {
    }

    @Override
    public void maximumPercentageOfOwnership(Loan x) {
    }

    @Override
    public void maximumLoansOpenToTheBorrower(Loan x) {
    }

    @Override
    public void showDataLoans() {
        System.out.println("This information for all existing loans in the system:\n");
        ArrayList<DTOLoan> loans = bank.getLoans();
        loans.add(new Loan(DTOLoanStatus.PENDING,"4","Moshe","Car",2400,12,1,5));
        for (int i = 0; i < loans.size(); i++) {
            System.out.println("------------ Loan number " + i + " ------------\n" +
                    "Loan ID - " + loans.get(i).getId() + "\n" +
                    "Loan owner - " + loans.get(i).getOwner() + "\n" +
                    "Loan category - " + loans.get(i).getCategory() + "\n" +
                    "The total original time of the loan - " + loans.get(i).getTotalYazTime() + "\n" +
                    "Loan interest - " + loans.get(i).getInterestPerPayment() + "\n");
        }


        //loans.get(0).setTotalYaz(4);
        Customer d=new Customer();

    }
}
