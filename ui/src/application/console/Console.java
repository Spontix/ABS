package application.console;

import application.UiType;
import dataObjects.dtoBank.dtoAccount.DTOAccount;
import dataObjects.dtoBank.dtoAccount.DTOInlay;
import dataObjects.dtoBank.dtoAccount.DTOMovement;
import dataObjects.dtoBank.dtoLoanStatus.DTOLoanStatus;
import logic.bank.Bank;
import logic.bank.account.Inlay;
import logic.bank.account.Loan;
import logic.bank.account.Movement;
import logic.customer.Customer;
import logic.fileUpload.XmlSerialization;
import menuBuilder.MainMenu;
import menuBuilder.MenuItem;
import menuBuilder.MenuType;

import java.util.ArrayList;
import java.util.Scanner;


public class Console implements UiType {
    Bank bank;
    private XmlSerialization xml=new XmlSerialization();

    public Console(){
        bank=new Bank();
        bank.getAccounts().add(new Customer());
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
        second.AddListener(this::showDataLoans);
        menu.insertToMenu(second);
        MenuItem third =
                new MenuItem("Display information about system customers.");
        third.AddListener(this::showCustomerInformation);
        menu.insertToMenu(third);
        MenuItem fourth =
                new MenuItem("Loading funds into the account.");
        fourth.AddListener(this::insertSumToAccount);
        menu.insertToMenu(fourth);
        MenuItem fifth =
                new MenuItem("Withdrawal of funds from the account.");
        fifth.AddListener(this::drawSumFromAccount);
        menu.insertToMenu(fifth);
        MenuItem sixth =
                new MenuItem("Inlay activation.");
        sixth.AddListener(this::inlayActivation);
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
    public void insertSumToAccount() {
        showCustomerList(false);
        System.out.println("Please select the customer number to add him the amount of money: ");
        Scanner sc= new Scanner(System.in);
        String number= sc.nextLine();
        System.out.println("Please write the amount to be add: ");
        String amount=sc.nextLine();
        DTOMovement movement=Movement.build(Integer.parseInt(amount),"+",bank.getAccounts().get(Integer.parseInt(number)-1).getAmount(),bank.getAccounts().get(Integer.parseInt(number)-1).getAmount()+Integer.parseInt(amount),0);
        bank.getAccounts().get(Integer.parseInt(number)-1).getMovements().add(movement);
        bank.getAccounts().get(Integer.parseInt(number)-1).cashDeposit(Integer.parseInt(amount));

    }

    @Override
    public void inlayActivation(){
        Inlay inlay = new Inlay();
        showCustomerList(true);
        try{
        System.out.println("Please select the customer number you would like to run the placement on: ");
        Scanner sc= new Scanner(System.in);
        String number= sc.nextLine();
        if(Integer.parseInt(number)<=0 || bank.getAccounts().size()<Integer.parseInt(number))
         throw new RuntimeException();
        }
        catch(RuntimeException exception) {
            System.out.println("The number that you choose incorrect,please select valid number from the list");
        }

    }

    @Override
    public void drawSumFromAccount() {
        showCustomerList(false);
        System.out.println("Please select the customer number to draw from him the amount of money: ");
        Scanner sc= new Scanner(System.in);
        String number= sc.nextLine();
        System.out.println("Please write the amount to be draw: ");
        String amount=sc.nextLine();
        DTOMovement movement=Movement.build(Integer.parseInt(amount),"-",bank.getAccounts().get(Integer.parseInt(number)-1).getAmount(),bank.getAccounts().get(Integer.parseInt(number)-1).getAmount()-Integer.parseInt(amount),0);
        bank.getAccounts().get(Integer.parseInt(number)-1).getMovements().add(movement);
        bank.getAccounts().get(Integer.parseInt(number)-1).cashWithdrawal(Integer.parseInt(amount));
    }

    @Override
    public void showCustomerList(Boolean printAmount) {
        int index=1;
        System.out.println("------------ Customer list ------------");
        for (DTOAccount account:bank.getAccounts()) {
            System.out.println((index++)+"."+account.getCustomerName() + (printAmount ? " , " + account.getAmount():" "));
        }
    }

    @Override
    public void showCustomerInformation() {
        bank.getAccounts().stream().forEach(a->System.out.println(a.toString()+"\n"));
    }


    @Override
    public void showDataLoans() {
        System.out.println("This information for all existing loans in the system:\n");
        ArrayList<Loan> loans = bank.getLoans();
        loans.add(new Loan(DTOLoanStatus.PENDING,"4","Moshe","Car",2400,12,1,5));
        for (int i = 0; i < loans.size(); i++) {
            System.out.println("------------ Loan number " + i + " ------------\n" +
                    "Loan ID - " + loans.get(i).getId() + "\n" +
                    "Loan owner - " + loans.get(i).getOwner() + "\n" +
                    "Loan category - " + loans.get(i).getCategory() + "\n" +
                    "The total original time of the loan - " + loans.get(i).getTotalYazTime() + "\n" +
                    "Pays every yaz - " + loans.get(i).getPaysEveryYaz() + "\n" +
                    "Loan interest - " + loans.get(i).getInterestPerPayment() + "\n");

        }

        //loans.get(0).setTotalYaz(4);

    }
}
