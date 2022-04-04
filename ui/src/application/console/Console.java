package application.console;

import application.UiType;
import dataObjects.dtoBank.dtoAccount.*;
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
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


public class Console implements UiType {
    Bank bank;
    UIInterfaceLogic bank1;
    private final XmlSerialization xml = new XmlSerialization();
    MainMenu menu;

    public Console() {
        //bank=new Bank();
        bank1 = XmlSerialization.buildBank("fileName");
        addDataForCheck();
    }


    public void Run() {
        menu = new MainMenu("Main", MenuType.PRIMARY_MENU);
        createMenu(menu);
        menu.show();
    }

    ///////////////////////////////////////////Don't Forget To Delete it/////////////////////////////////////////////////////////////
    public void addDataForCheck(){
        bank1.addCategories();

        bank1.addCustomer(new Customer("Moshe", 10000));
        bank1.addCustomer(new Customer("Avi", 5000));
        bank1.addCustomer(new Customer("Shlomo", 20000));
        bank1.addCustomer(new Customer("Dani", 3000));
        bank1.addCustomer(new Customer("Eden", 500000));
        /*bank1.addCustomer(new Customer("Koko", 789));
        bank1.addCustomer(new Customer("Lili", 456));*/

        Loan loan=Loan.build("1","Menash","Car",10000,12,85,10);
        //Loan loan1=Loan.build("2","MoMo","Home",5000,24,100,50);
        //Loan loan2=Loan.build("3","Gil","Bar Mitzvah",100,10,20,20);
        //Loan loan3=Loan.build("4","Noa","Mortgage",300,30,10,5);
        //Loan loan4=Loan.build("5","Koko","Car",800,50,60,6);
        //Loan loan5=Loan.build("6","Avi","Home",20,17,5,3);
        //Loan loan6=Loan.build("7","Lili","Bar Mitzvah",50,7,3,2);

        bank1.addLoanToBank(loan);
        //bank1.addLoanToBank(loan1);
        //bank1.addLoanToBank(loan2);
        //bank1.addLoanToBank(loan3);
        //bank1.addLoanToBank(loan4);
        //bank1.addLoanToBank(loan5);
        //bank1.addLoanToBank(loan6);
        bank1.addBorrowerTOLoan();
        bank1.addBorrowerTOLoan();
    }

    private void createMenu(MainMenu menu) {
        /*MainMenu first =
                new MainMenu("Reading the system information file.", MenuType.SECONDARY_MENU);*/
        MenuItem first = new MenuItem("Reading the system information file.");
        //first.AddListener(()->sumToInvest(new Loan()));
        menu.insertToMenu(first);
        MenuItem second = new MenuItem("Presentation of information on existing loans and their status.");
        //second.AddListener(this::showDataLoans);
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

   /* @Override
    public void sumToInvest(Loan x) {}
    @Override
    public void category(Loan x) {}
    @Override
    public void minimumInterestPerUnitTime(Loan x) {}
    @Override
    public void minimumTimePerUnitTime(Loan x) {}
    @Override
    public void maximumPercentageOfOwnership(Loan x) {}
    @Override
    public void maximumLoansOpenToTheBorrower(Loan x) {
    }*/

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
            if (ensureMassageToCustomer(cloneMovement)) {
                bank1.cashDeposit(chosenCustomerIndex, amountTOAdd);
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
            System.out.println("Please select the amount that you willing to invest. between 0 - " + bank1.getAmountOfCustomer(chosenCustomerIndex) + ". (Mandatory selection)");
            int chosenInvestAmount = getChosenInvestAmount(bank1.getAmountOfCustomer(chosenCustomerIndex)) + 1;
            showCategoriesList();
            System.out.println("Please select the appropriate category number. If you are interested in proceeding to the following figure, please click Enter.");
            int choseCategoryIndex = getChosenCategoryIndex();
            System.out.println("Please select the minimum interest yaz you are willing to receive, a decimal number greater than 0.\nIf you are interested in proceeding to the following figure, please click Enter.");
            double minInterestYaz = getChosenMinInterestYaz();
            System.out.println("Please select the minimum total yaz time for the loan. If you are interested in proceeding to the following figure, please click Enter.");
            int minYazTime = getChosenMinYazTime();

            String category = null;
            if (choseCategoryIndex > 0 && choseCategoryIndex< bank1.getCategoriesGroup().size())
                category = bank1.getCategory(choseCategoryIndex);
            Inlay inlay = Inlay.build(bank1.getCustomer(chosenCustomerIndex), chosenInvestAmount, category, minInterestYaz, minYazTime);
            DTOInlay cloneInlay = bank1.addInlayToClient(chosenCustomerIndex, inlay);
            ArrayList<DTOLoan> loansSupportInlay = bank1.loansSustainInlay(cloneInlay);
            showLoansList(loansSupportInlay);
            System.out.println("Please select the id loans you are interested in participating in. You can select more than one by separating the numbers by space or Enter for cancel. The format is: 2 3 4 ...");
            ArrayList<DTOLoan> loansCustomerChosen = loansCustomerChosenParticipate(loansSupportInlay);

            Movement movement = Movement.build(chosenInvestAmount, "-", bank1.getAmountOfCustomer(chosenCustomerIndex), bank1.getAmountOfCustomer(chosenCustomerIndex) - chosenInvestAmount, 0);
            DTOMovement cloneMovement = bank1.addMovementToClient(chosenCustomerIndex, movement);
            if (ensureMassageToCustomer(cloneMovement)) {
                bank1.cashWithdrawal(chosenCustomerIndex, chosenInvestAmount);
                addMovementPerLoanFromInlay(loansCustomerChosen,chosenInvestAmount, chosenCustomerIndex);
                System.out.println("-------- The operation was performed successfully --------\n");
                } else {
                    System.out.println("-------- Operation canceled --------\n");
                    menu.show();
                }

        }catch(NumberFormatException exception){
                System.out.println("Incorrect input,please note that you entered an integer number!!\n");
                inlayActivation();

        }catch(RuntimeException exception) {
            System.out.println(exception.getMessage());
            inlayActivation();
        }
    }

    @Override
    public void addMovementPerLoanFromInlay(ArrayList<DTOLoan> loansCustomerChosen , int chosenInvestAmount, int customerIndexGiveMoney){
        int sumPerLoan =0;
        if(loansCustomerChosen != null){
            sumPerLoan = (chosenInvestAmount / loansCustomerChosen.size());
            for (DTOLoan dtoLoan : loansCustomerChosen) {
                for (int j = 0; j < bank1.getCustomers().size(); j++) {
                    DTOCustomer customer = bank1.getCustomers().get(j);
                    if (Objects.equals(customer.getCustomerName(), dtoLoan.getId())) {
                        Movement movementLoan = Movement.build(sumPerLoan, "+", customer.getAmount(), customer.getAmount() + sumPerLoan, 0);
                        DTOMovement cloneMovementLoan = bank1.addMovementToClient(j, movementLoan);
                        dtoLoan.getListOfAccompanied().add(bank1.getCustomer(customerIndexGiveMoney));
                        if (ensureMassageToCustomer(cloneMovementLoan)) {
                            bank1.cashDeposit(j, sumPerLoan);
                        }
                    }
                }

            }
        }
        else
            System.out.println("You didnt choose any loans.");
    }

    @Override
    public ArrayList<DTOLoan> loansCustomerChosenParticipate(ArrayList<DTOLoan> loansSupportInlay){
        ArrayList<DTOLoan> loansChosenCustomer = new ArrayList<>();
        String[] arrayStringsScanner;
        Scanner sc = new Scanner(System.in);
        String idLoanCustomerChosen = sc.nextLine();
        arrayStringsScanner = idLoanCustomerChosen.split(" ");
        try {
            loansChosenCustomer= bank1.loansSustainInlayAndClientChoose(loansSupportInlay,arrayStringsScanner);
        }
           catch (NumberFormatException exception) {
                System.out.println("Please select the id loans you are interested in participating in. You can select more than one by separating the numbers by space or Enter for cancel. The format is: 2 3 4 ...");
               loansCustomerChosenParticipate( loansSupportInlay);
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
            amountOfCustomer = bank1.getAmountOfCustomer(chosenCustomerIndex);
            Movement movement = Movement.build(amountTODraw, "-", amountOfCustomer, amountOfCustomer - amountTODraw, 0);
            DTOMovement cloneMovement = bank1.addMovementToClient(chosenCustomerIndex, movement);
            if (ensureMassageToCustomer(cloneMovement)) {
                bank1.cashWithdrawal(chosenCustomerIndex, amountTODraw);
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
        }
    }

    @Override
    public void showCustomerList(Boolean printAmount) {
        int index = 1;
        System.out.println("------------ Customer list ------------");
        for (DTOAccount account : bank1.getCustomers()) {
            System.out.println((index++) + "." + account.getCustomerName() + (printAmount ? " , " + account.getAmount() : " "));/////////
        }
    }

    @Override
    public void showCategoriesList() {
        int index =1;
        System.out.println("------------ Categories list ------------");
        for (String category : bank1.getCategoriesGroup()) {
            System.out.println((index++) + "." + category);
        }
    }

    @Override
    public void showCustomerInformation() {
        List<DTOLoan> dtoCustomerLoanerList;
        List<DTOLoan> dtoCustomerBorrowersList;
        for (int i = 0; i < bank1.getCustomers().size(); i++) {
            System.out.println(bank1.getCustomer(i));
            dtoCustomerLoanerList = bank1.getCustomerLoanersList(bank1.getCustomerName(i));//===bank1.methode();
            dtoCustomerBorrowersList = bank1.getCustomerBorrowersList(bank1.getCustomerName(i));
            System.out.println("\n-------- Loans as loaner --------\n");
            showLoansList(dtoCustomerLoanerList);
            System.out.println("\n-------- Loans as borrower --------\n");
            showLoansList(dtoCustomerBorrowersList);

        }

    }

    @Override
    public void showLoansList(List<DTOLoan> loans){
        for (DTOLoan dtoLoan : loans) {
            System.out.println(dtoLoan+ "\n" + dtoLoan.getStatusOperation());
        }
    }

    /*@Override
    public void showDataLoans() {
        System.out.println("This information for all existing loans in the system:\n");
        ArrayList<Loan> loans = bank.getLoans();
        //loans.add(new Loan(DTOLoanStatus.PENDING,"4","Moshe","Car",2400,12,1,5));
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

    }*/

    @Override
    public int getChosenCustomerIndex() {
        Scanner sc = new Scanner(System.in);
        String number = sc.nextLine();
        int chosenCustomerIndex = Integer.parseInt(number);
        if (chosenCustomerIndex < 0 || chosenCustomerIndex > bank1.getCustomers().size())
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
    public int getChosenInvestAmount(int amountOfCustomer){
        int chosenInvestAmount;
        Scanner sc = new Scanner(System.in);
        String number = sc.nextLine();
        chosenInvestAmount = Integer.parseInt(number) - 1;
        /*if(chosenInvestAmount > amountOfCustomer || chosenInvestAmount<0){
            throw new RuntimeException("The investment amount is above the amount balance,please try again!");
        }*/
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
                if (chosenCategoryIndex < 0 || chosenCategoryIndex > bank1.getCategoriesGroup().size())
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
                if (chosenMinInterestYaz < 0)
                    throw new NumberFormatException();
            }
            catch (NumberFormatException exception){
                System.out.println("Your choice is incorrect. Press Enter if you want to skip to the next item or select the minimum interest yaz you are willing to receive, a decimal number greater than 0.");
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
