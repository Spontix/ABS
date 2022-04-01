package application.console;

import application.UiType;
import dataObjects.dtoBank.dtoAccount.DTOAccount;
import dataObjects.dtoBank.dtoAccount.DTOMovement;
import dataObjects.dtoBank.dtoLoanStatus.DTOLoanStatus;
import dataObjects.dtoCustomer.DTOCustomer;
import logic.UIInterfaceLogic;
import logic.bank.Bank;
import logic.bank.account.Inlay;
import logic.bank.account.Loan;
import logic.bank.account.Movement;
import logic.customer.Customer;
import logic.bank.XmlSerialization;
import menuBuilder.MainMenu;
import menuBuilder.MenuItem;
import menuBuilder.MenuType;

import java.util.ArrayList;
import java.util.Scanner;


public class Console implements UiType {
    Bank bank;
    UIInterfaceLogic bank1;
    private final XmlSerialization xml=new XmlSerialization();
    MainMenu menu;

    public Console(){
        //bank=new Bank();
        bank1=XmlSerialization.buildBank("fileName");
        bank1.addCustomer(new Customer());
    }


    public void Run() {
       menu = new MainMenu("Main", MenuType.PRIMARY_MENU);
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
        int chosenCustomerIndex;
        int amountTOAdd;
        int amountOfCustomer;
        try {
            System.out.println("Please select the customer number to add him the amount of money: ");
            chosenCustomerIndex = getChosenCustomerIndex();
            System.out.println("Please write the amount to be add: ");
            amountTOAdd = getAmountFromUser();
            amountOfCustomer = bank1.getAmountOfCustomer(chosenCustomerIndex);
            Movement movement = Movement.build(amountTOAdd, "+", amountOfCustomer, amountOfCustomer + amountTOAdd, 0);
            DTOMovement cloneMovement = bank1.addMovementToClient(chosenCustomerIndex, movement);
            if(ensureMassageToCustomer(cloneMovement)) {
                bank1.cashDeposit(chosenCustomerIndex, amountTOAdd);
                System.out.println("-------- The operation was performed successfully --------\n");
            }

            else {
                System.out.println("-------- Operation canceled --------\n");
                menu.show();
            }
        }
        catch (NumberFormatException exception){
            System.out.println("Incorrect input,please note that you entered an integer number!!\n");
            insertSumToAccount();
        }
       catch (RuntimeException exception){
            System.out.println(exception.getMessage());
            insertSumToAccount();
       }
    }

    public <T> Boolean ensureMassageToCustomer(T data){
        int chosenNumber=0;
        try {
            System.out.println(data.toString());
            System.out.println("Are you sure you want to do this operation?\n1.Yes\n2.No\nPlease choose between 1 or 2 : ");
            Scanner sc = new Scanner(System.in);
            String number = sc.nextLine();
            chosenNumber = Integer.parseInt(number);
            if(chosenNumber!=1 && chosenNumber!=2)
                throw new NumberFormatException();
        }
        catch(NumberFormatException exception) {
            System.out.println("-------- Incorrect choice ---------");
            ensureMassageToCustomer(data);
        }
        return chosenNumber!=2;
    }

    @Override
    public void inlayActivation(){
        Inlay inlay = new Inlay();
        showCustomerList(true);
        userCustomerSelection(inlay);
        userSumSelection(inlay);


    }

    @Override
    public void userCustomerSelection(Inlay inlay){
        try{
            System.out.println("Please select the customer number you would like to run the placement on: ");
            Scanner sc= new Scanner(System.in);
            String number= sc.nextLine();
            if(Integer.parseInt(number)<=0 || bank.getAccounts().size()<Integer.parseInt(number))////////////////
                throw new RuntimeException();
            inlay.setInlayCustomer(bank.getAccounts().get(Integer.parseInt(number)));//////////////////
        }
        catch(RuntimeException exception) {
            System.out.println("The number that you choose incorrect,please select valid number from the list");
            showCustomerList(true);
            userCustomerSelection(inlay);
        }
    }

    @Override
    public void userSumSelection(Inlay inlay){
        try{
            System.out.println("Please insert amount between 0 - "+ inlay.getDtoAccount().getAmount()+"(Mandatory selection");//////////
            Scanner sc= new Scanner(System.in);
            String number= sc.nextLine();
            if(Integer.parseInt(number)<0 || inlay.getDtoAccount().getAmount()<Integer.parseInt(number))//////////////
                throw new RuntimeException();
        }
        catch(RuntimeException exception) {
            System.out.println("The amount that you inserted is incorrect,please try again");
            showCustomerList(true);
            userSumSelection(inlay);
        }
    }


    @Override
    public void drawSumFromAccount() {
        showCustomerList(false);
        int chosenCustomerIndex;
        int amountTODraw;
        int amountOfCustomer;
        try {
            System.out.println("Please select the customer number to draw from him the amount of money: ");
            chosenCustomerIndex = getChosenCustomerIndex();
            System.out.println("Please write the amount to be draw: ");
            amountTODraw = getAmountFromUser();
            amountOfCustomer = bank1.getAmountOfCustomer(chosenCustomerIndex);
            Movement movement = Movement.build(amountTODraw, "-", amountOfCustomer, amountOfCustomer - amountTODraw, 0);
            DTOMovement cloneMovement = bank1.addMovementToClient(chosenCustomerIndex, movement);
            if(ensureMassageToCustomer(cloneMovement)) {
                bank1.cashWithdrawal(chosenCustomerIndex, amountTODraw);
                System.out.println("-------- The operation was performed successfully --------\n");
            }
            else {
                System.out.println("-------- Operation canceled --------\n");
                menu.show();
            }
        }
         catch (NumberFormatException exception){
            System.out.println("Incorrect input,please note that you entered an integer number!!\n");
             drawSumFromAccount();
        }
       catch (RuntimeException exception){
            System.out.println(exception.getMessage());
           drawSumFromAccount();
        }
    }

    @Override
    public void showCustomerList(Boolean printAmount) {
        int index=1;
        System.out.println("------------ Customer list ------------");
        for (DTOAccount account:bank1.getCustomers()) {
            System.out.println((index++)+"."+account.getCustomerName() + (printAmount ? " , " + account.getAmount():" "));/////////
        }
    }

    @Override
    public void showCustomerInformation() {
        bank1.getCustomers().forEach(a->System.out.println(a.toString()+"\n"));

        bank1.addCustomer(new Customer());
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

        }////////////////

        //loans.get(0).setTotalYaz(4);

    }

    @Override
    public int getChosenCustomerIndex() {
        Scanner sc = new Scanner(System.in);
        String number = sc.nextLine();
        int chosenCustomerIndex = Integer.parseInt(number) - 1;
        if (chosenCustomerIndex < 0 || chosenCustomerIndex > bank1.getCustomers().size())
            throw new NumberFormatException();

        return chosenCustomerIndex;
    }

    @Override
    public int getAmountFromUser(){
        Scanner sc = new Scanner(System.in);
        String amount=sc.nextLine();
        int amountFromUser = Integer.parseInt(amount);
        if(amountFromUser < 0)
            throw new NumberFormatException();
        return amountFromUser;
    }
}
