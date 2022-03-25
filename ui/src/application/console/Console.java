package application.console;

import application.UiType;
import logic.bank.account.Loan;
import logic.fileUpload.XmlSerialization;
import menuBuilder.MainMenu;
import menuBuilder.MenuItem;
import menuBuilder.MenuType;
import menuBuilder.OptionInvoker;

import javax.xml.bind.annotation.XmlAccessOrder;
import java.util.Scanner;


public class Console implements UiType {
    Loan x=new Loan();
    static XmlSerialization xml=new XmlSerialization();

   public void Run() {
       MainMenu menu = new MainMenu("Main", MenuType.PRIMARY_MENU);
       createMenu(menu);
       menu.show();
   }

    private static void createMenu(MainMenu menu) {
        /*MainMenu first =
                new MainMenu("Reading the system information file.", MenuType.SECONDARY_MENU);*/
        MenuItem first=new MenuItem("Reading the system information file.");
        first.AddListener(new OptionInvoker() {
            @Override
            public void reportAction() {
                System.out.println("Please Enter a Path of xml file!");
                String fileName=new Scanner(System.in).nextLine();
                System.out.println("Thanks!");
                xml.doSomething(fileName);
            }
        });
        menu.insertToMenu(first);
        MainMenu second =
                new MainMenu("Presentation of information on existing loans and their status.", MenuType.SECONDARY_MENU);
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
    public String sumToInvest(Loan x) {
        return null;
    }

    @Override
    public String category(Loan x) {
        return null;
    }

    @Override
    public String minimumInterestPerUnitTime(Loan x) {
        return null;
    }

    @Override
    public String minimumTimePerUnitTime(Loan x) {
        return null;
    }

    @Override
    public String maximumPercentageOfOwnership(Loan x) {
        return null;
    }

    @Override
    public String maximumLoansOpenToTheBorrower(Loan x) {
        return null;
    }



}
