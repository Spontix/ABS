package application.console;

import application.UiType;
import logic.bank.account.Loan;
import logic.bank.enumsType.LoanStatus;
import logic.customer.Customer;
import logic.fileUpload.XmlSerialization;
import menuBuilder.MainMenu;
import menuBuilder.MenuItem;
import menuBuilder.MenuType;
import menuBuilder.OptionInvoker;

import javax.xml.bind.annotation.XmlAccessOrder;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Console implements UiType {
    static Loan[] allLoans = new Loan[]{};      //מערך ההלוואות, אותה מאתחלים לפי קובץ הXML -- מערך כי צריך לבחור הלוואה לפי מספר
    static Customer[] allCustomer = new Customer[]{};  //מערך הלקוחות, אותה נאתחל לפי קובץ הXML-- מערך כי צריך לבחור לקוח לפי מספר
    static XmlSerialization xml = new XmlSerialization();
    public static boolean uploadedFile;

    public void Run() {
        MainMenu menu = new MainMenu("Main", MenuType.PRIMARY_MENU);
        createMenu(menu);
        uploadedFile = false;
        menu.show();
    }

    private static <uploadedFile> void createMenu(MainMenu menu) {
        //MainMenu first = new MainMenu("Reading the system information file.", MenuType.SECONDARY_MENU);
        MenuItem first = new MenuItem("Reading the system information file.");
        first.AddListener(new OptionInvoker() {
            @Override
            public void reportAction() {
                System.out.println("Please Enter a Path of xml file!");
                String fileName = new Scanner(System.in).nextLine();
                System.out.println("Thanks!");
                xml.doSomething(fileName, uploadedFile, allLoans);
            }
        });
        menu.insertToMenu(first);
        MenuItem second =
                new MenuItem("Presentation of information on existing loans and their status.");
        second.AddListener(new OptionInvoker() {
            @Override
            public void reportAction() {
                if (uploadedFile == true) {
                    System.out.println("This information for all existing loans in the system:\n");
                    for (int i = 0; i < allLoans.length; i++) {
                        System.out.println("------------ Loan number " + i + " ------------\n" +
                                "Loan ID - " + allLoans[i].getId() + "\n" +
                                "Loan owner - " + allLoans[i].getOwner() + "\n" +
                                "Loan category - " + allLoans[i].getCategory() + "\n" +
                                "The total original time of the loan - " + allLoans[i].getTotalYazTime() + "\n" +
                                "Loan interest - " + allLoans[i].getInterestPerPayment() + "\n");
                        LoanStatus status = allLoans[i].getLoanStatus();
                        switch (status) {
                            case PENDING:
                                pendingStatus(i, status);
                                break;
                            case ACTIVE:
                                activeStatus(i, status);
                                break;
                            case RISK:
                                riskStatus(i, status);
                                break;
                            case FINISHED:
                                finishedStatus(i, status);
                                break;
                        }
                    }
                } else {
                    System.out.println("There was a problem loading the file so this operation could not be performed.\n");
                }
            }
        });
        menu.insertToMenu(second);
        MainMenu third =
                new MainMenu("Display information about system customers.", MenuType.SECONDARY_MENU);
        menu.insertToMenu(third);
        MainMenu fourth =
                new MainMenu("Loading funds into the account.", MenuType.SECONDARY_MENU);
        /*fourth.AddListener(new OptionInvoker() {
            @Override
            public void reportAction() {
                printAllCustomersName();
                System.out.println("Please select the customer number to which you would like to enter a sum of money:\n");


            }
        });*/
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


    public static void pendingStatus(int index, LoanStatus status) {
        System.out.println("The status of the Loan is: " + status + "\n");
        System.out.println("------------ list of accompanied are: ------------\n");
        for (int j = 0; j < allLoans[index].getlistOfAccompanied().size(); j++) {
            System.out.println("Name of accompanied: " + allLoans[index].getlistOfAccompanied().get(j).getName() + "\n" +
                    "His private investment as part of the loan amount: " + allLoans[index].getlistOfAccompanied().get(j).getPrivateInvestment() + "\n");
        }
        //TO DO: The total amount already raised by the lenders and what amount is left to make this loan active should be presented.
    }

    public static void activeStatus(int index, LoanStatus status) {
        pendingStatus(index, status);
        //TO DO
    }

    public static void riskStatus(int index, LoanStatus status) {
        activeStatus(index, status);
        //TO DO
    }

    public static void finishedStatus(int index, LoanStatus status) {
        pendingStatus(index, status);
        //TO DO
    }

    public static void printAllCustomersName() {
        System.out.println("The customers are: \n");
        for (int i = 0; i < allCustomer.length; i++) {
            System.out.println(i+ ". " + allCustomer[i].getName() + "\n");
        }

    }

}


