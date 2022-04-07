package application.console;

import application.UiType;
import dataObjects.dtoBank.dtoAccount.*;
import dataObjects.dtoCustomer.DTOCustomer;
import logic.UIInterfaceLogic;
import logic.YazLogic;
import logic.bank.Bank;
import logic.bank.account.Inlay;
import logic.bank.account.Loan;
import logic.bank.account.Movement;
import logic.customer.Customer;
import logic.bank.XmlSerialization;
import menuBuilder.MainMenu;
import menuBuilder.MenuItem;
import menuBuilder.MenuType;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


public class Console implements UiType {
    private UIInterfaceLogic bank;
    MainMenu menu;

    public Console() {

    }


    public void Run() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        menu = new MainMenu("Main", MenuType.PRIMARY_MENU);
        createMenu(menu);
        menu.show();
    }


    private void createMenu(MainMenu menu) {
        /*MainMenu first =
                new MainMenu("Reading the system information file.", MenuType.SECONDARY_MENU);*/
        MenuItem first = new MenuItem("Reading the system information file.");
        first.AddListener(()->bank = XmlSerialization.buildBank("api/src/resources/ex1-small.xml"));
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
        MenuItem seventh =
                new MenuItem("Promoting the timeline and providing payments.");
        seventh.AddListener(()->
                {
                    System.out.println("The Application was in YAZ: "+ YazLogic.currentYazUnit+"\n"+"And now the Application in YAZ: "+(YazLogic.currentYazUnit+1));
                    bank.yazProgressLogic();
                });
        menu.insertToMenu(seventh);
        MainMenu eighth =
                new MainMenu("Exiting the system.", MenuType.SECONDARY_MENU);
        menu.insertToMenu(eighth);
    }

    @Override
    public void showDataLoans(){
        showLoansList(bank.getLoansList(), 2);
    }


    @Override
    public void showLoansList(List<DTOLoan> loans, int indexOperation){
        int index=1;
        if(loans.size()==0){
            System.out.println("None\n---------------------------------");
        }
        else
            for (DTOLoan dtoLoan : loans) {
                System.out.println("\n----------Loan number " + index +"----------\n" + dtoLoan+dtoLoan.invokeStatusOperation(indexOperation)+"\n");
                index++;
            }
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
            amountOfCustomer = bank.getAmountOfCustomer(chosenCustomerIndex-1);
            DTOMovement dtoMovement=bank.movementBuildToCustomer(bank.getCustomer(chosenCustomerIndex-1),amountTOAdd, "+", amountOfCustomer, amountOfCustomer + amountTOAdd);
            if (ensureMassageToCustomer(dtoMovement)) {
                bank.cashDeposit(chosenCustomerIndex-1, amountTOAdd);
                System.out.println("-------- The operation was performed successfully --------\n");
            } else {
                System.out.println("-------- Operation canceled --------\n");
                menu.show();
            }
        } catch (NumberFormatException exception) {
            System.out.println("Incorrect input,please note that you entered an integer number!!\n");
            insertSumToAccount();
        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
            insertSumToAccount();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public <T> Boolean ensureMassageToCustomer(T data) {
        int chosenNumber = 0;
        try {
            System.out.println(data.toString());
            System.out.println("Are you sure you want to do this operation?\n1.Yes\n2.No\nPlease choose between 1 or 2 : ");
            Scanner sc = new Scanner(System.in);
            String number = sc.nextLine();
            chosenNumber = Integer.parseInt(number);
            if (chosenNumber != 1 && chosenNumber != 2)
                throw new NumberFormatException();
        } catch (NumberFormatException exception) {
            System.out.println("-------- Incorrect choice ---------");
            ensureMassageToCustomer(data);
        }
        return chosenNumber != 2;
    }

    @Override
    public void inlayActivation() {
        showCustomerList(true);
        try {
            System.out.println("Please select the customer number you would like to run the placement on:");
            int chosenCustomerIndex = getChosenCustomerIndex()-1;
            System.out.println("Please select the amount that you willing to invest. between 0 - " + bank.getAmountOfCustomer(chosenCustomerIndex) + ". (Mandatory selection)");
            int chosenInvestAmount = getChosenInvestAmount(bank.getAmountOfCustomer(chosenCustomerIndex)) ;
            bank.checksInvestAmount(bank.getCustomer(chosenCustomerIndex),chosenInvestAmount);
            showCategoriesList();
            System.out.println("Please select the appropriate category number. If you are interested in proceeding to the following figure, please click Enter.");
            int choseCategoryIndex = getChosenCategoryIndex();
            System.out.println("Please select the minimum interest yaz you are willing to receive, a decimal number greater than 0.\nIf you are interested in proceeding to the following figure, please click Enter.");
            double minInterestYaz = getChosenMinInterestYaz();//check in the logic sfare if the interest yaz is greater than 100
            System.out.println("Please select the minimum total yaz time for the loan. If you are interested in proceeding to the following figure, please click Enter.");
            int minYazTime = getChosenMinYazTime();

            String category = null;
            if (choseCategoryIndex > 0 && choseCategoryIndex< bank.getCategoriesGroup().size())
                category = bank.getCategory(choseCategoryIndex);
            DTOInlay dtoInlay=bank.inlayBuild(bank.getCustomer(chosenCustomerIndex), chosenInvestAmount, category, minInterestYaz, minYazTime);
            ArrayList<DTOLoan> loansSupportInlay = bank.loansSustainInlay(dtoInlay);
            showLoansList(loansSupportInlay,3);
            System.out.println("Please select the number loan that you are interested in participating in. You can select more than one by separating the numbers by space or Enter for cancel. The loan number is in the loan title and the format is: 2 3 4 ...");
            ArrayList<DTOLoan> loansCustomerChosen = loansCustomerChosenParticipate(loansSupportInlay);
            //DTOMovement dtoMovement=bank.movementBuild(bank.getCustomer(chosenCustomerIndex),chosenInvestAmount, "-", bank.getAmountOfCustomer(chosenCustomerIndex), bank.getAmountOfCustomer(chosenCustomerIndex) - chosenInvestAmount);
            /*Movement movement = Movement.build(chosenInvestAmount, "-", bank.getAmountOfCustomer(chosenCustomerIndex), bank.getAmountOfCustomer(chosenCustomerIndex) - chosenInvestAmount);
            DTOMovement cloneMovement = bank.addMovementToClient(chosenCustomerIndex, movement);*/
             System.out.println("Are you sure you want to this operation? 1.yes 2.no ");
            if (Integer.parseInt(new Scanner(System.in).nextLine())== 1) {
                bank.addMovementPerLoanFromInlay(dtoInlay,loansCustomerChosen,chosenInvestAmount, chosenCustomerIndex);
                System.out.println("-------- The operation was performed successfully --------\n");
                } else {
                    System.out.println("-------- Operation canceled --------\n");
                    menu.show();
                }

        }catch(NumberFormatException exception){
                System.out.println("Incorrect input!!!\n");
                inlayActivation();

        }catch(RuntimeException exception) {
            System.out.println(exception.getMessage());
            inlayActivation();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<DTOLoan> loansCustomerChosenParticipate(ArrayList<DTOLoan> loansSupportInlay) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        ArrayList<DTOLoan> loansChosenCustomer = new ArrayList<>();
        String[] arrayStringsScanner;
        Scanner sc = new Scanner(System.in);
        String idLoanCustomerChosen = sc.nextLine();
        arrayStringsScanner = idLoanCustomerChosen.split(" ");
        if(idLoanCustomerChosen.equals("")) {
            menu.show();
        }
        else {
            try {
                loansChosenCustomer = bank.loansSustainInlayAndClientChoose(loansSupportInlay, arrayStringsScanner);
            } catch (NumberFormatException exception) {
                System.out.println(exception.getMessage());
                loansCustomerChosenParticipate(loansSupportInlay);
            }
        }
        return loansChosenCustomer;
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
            amountOfCustomer = bank.getAmountOfCustomer(chosenCustomerIndex);
            DTOMovement dtoMovement=bank.movementBuildToCustomer(bank.getCustomer(chosenCustomerIndex),amountTODraw, "+", amountOfCustomer, amountOfCustomer + amountTODraw);
            /*Movement movement = Movement.build(amountTODraw, "-", amountOfCustomer, amountOfCustomer - amountTODraw);
            DTOMovement cloneMovement = bank.addMovementToClient(chosenCustomerIndex, movement);*/
            if (ensureMassageToCustomer(dtoMovement)) {
                bank.cashWithdrawal(chosenCustomerIndex, amountTODraw);
                System.out.println("-------- The operation was performed successfully --------\n");
            } else {
                System.out.println("-------- Operation canceled --------\n");
                menu.show();
            }
        } catch (NumberFormatException exception) {
            System.out.println("Incorrect input,please note that you entered an integer number!!\n");
            drawSumFromAccount();
        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
            drawSumFromAccount();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showCustomerList(Boolean printAmount) {
        int index = 1;
        System.out.println("------------ Customer list ------------");
        for (DTOAccount account : bank.getCustomers()) {
            System.out.println((index++) + "." + account.getCustomerName() + (printAmount ? " , " + account.getAmount() : " "));/////////
        }
    }

    @Override
    public void showCategoriesList() {
        int index =1;
        System.out.println("------------ Categories list ------------");
        for (String category : bank.getCategoriesGroup()) {
            System.out.println((index++) + "." + category);
        }
    }

    @Override
    public void showCustomerInformation() {
        List<DTOLoan> dtoCustomerLoanerList;
        List<DTOLoan> dtoCustomerBorrowersList;
        for (int i = 0; i < bank.getCustomers().size(); i++) {
            System.out.println(bank.getCustomer(i)+"\n---------------------------------");
            dtoCustomerLoanerList = bank.getCustomerLoanersList(bank.getCustomerName(i));//===bank1.methode();
            dtoCustomerBorrowersList = bank.getCustomerBorrowersList(bank.getCustomerName(i));
            System.out.println("\n-------- Loans as loaner --------");
            showLoansList(dtoCustomerLoanerList, 3);////////// send the number operation that loanStatus will differentiate between operationTwo and operationThree
            System.out.println("\n-------- Loans as borrower --------");
            showLoansList(dtoCustomerBorrowersList,3 );/////////// same.

        }

    }

    @Override
    public int getChosenCustomerIndex() {
        Scanner sc = new Scanner(System.in);
        String number = sc.nextLine();
        int chosenCustomerIndex = Integer.parseInt(number);
        if (chosenCustomerIndex <= 0 || chosenCustomerIndex > bank.getCustomers().size())
            throw new NumberFormatException();

        return chosenCustomerIndex;
    }

    @Override
    public int getAmountFromUser() {
        Scanner sc = new Scanner(System.in);
        String amount = sc.nextLine();
        int amountFromUser = Integer.parseInt(amount);
        if (amountFromUser < 0)
            throw new NumberFormatException();
        return amountFromUser;
    }

    @Override
    public int getChosenInvestAmount(int amountOfCustomer) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        int chosenInvestAmount;
        Scanner sc = new Scanner(System.in);
        String number = sc.nextLine();
        chosenInvestAmount = Integer.parseInt(number);
        if(chosenInvestAmount==0)
            menu.show();
        return chosenInvestAmount;
    }

    @Override
    public int getChosenCategoryIndex() {
        int chosenCategoryIndex=0;
        Scanner sc = new Scanner(System.in);
        String number = sc.nextLine();
        if (Objects.equals(number, ""))
            return 0;
        else {
            try {
                chosenCategoryIndex = Integer.parseInt(number);
                if (chosenCategoryIndex < 0 || chosenCategoryIndex > bank.getCategoriesGroup().size())
                    throw new NumberFormatException();
            }
            catch (NumberFormatException exception){
                System.out.println("Your choice is incorrect. Press Enter if you want to skip to the next item or press the category that you want.");
                getChosenCategoryIndex();
            }
            return chosenCategoryIndex;
        }
    }

    @Override
    public double getChosenMinInterestYaz(){
        double chosenMinInterestYaz=0;
        Scanner sc = new Scanner(System.in);
        String number = sc.nextLine();
        if (Objects.equals(number, ""))
            return 0;
        else{
            try {
                chosenMinInterestYaz = Double.parseDouble((number));
                if (chosenMinInterestYaz < 0 || chosenMinInterestYaz > 100)
                    throw new NumberFormatException();
            }
            catch (NumberFormatException exception){
                System.out.println("Your choice is incorrect. Press Enter if you want to skip to the next item or select the minimum interest yaz you are willing to receive, a decimal number greater than 0 and smaller than 100.");
                getChosenMinInterestYaz();
            }
            return chosenMinInterestYaz;
        }
    }

    @Override
    public int getChosenMinYazTime(){
        int chosenMinYazTime =0 ;
        Scanner sc = new Scanner(System.in);
        String number = sc.nextLine();
        if (Objects.equals(number, ""))
            return 0;
        else{
            try {
                chosenMinYazTime = Integer.parseInt((number));
                if (chosenMinYazTime < 0)
                    throw new NumberFormatException();
            }
            catch (NumberFormatException exception){
                System.out.println("Your choice is incorrect. Press Enter if you want to skip to the next item or select the minimum total yaz time for the loan.\n");
                getChosenMinYazTime();
            }
            return chosenMinYazTime;
        }
    }
}
