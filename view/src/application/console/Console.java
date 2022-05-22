package application.console;
import application.UiType;
import dataObjects.dtoBank.dtoAccount.*;
import logic.UIInterfaceLogic;
import logic.YazLogic;
import logic.bank.XmlSerialization;
import menuBuilder.MainMenu;
import menuBuilder.MenuItem;
import menuBuilder.MenuType;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


public class Console implements UiType {
    private UIInterfaceLogic bank;
    MainMenu menu;

    public Console() {

    }


    public void Run(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        menu = new MainMenu("Main", MenuType.PRIMARY_MENU);
        createMenu(menu);
        menu.show();
    }

    public void getFileNameFromUser() throws InvocationTargetException, InstantiationException, IllegalAccessException {
            YazLogic.currentYazUnit=1;
            System.out.println("Please insert a full path of a XML file that you desire to load!");
            String userInput;
            userInput=new Scanner(System.in).nextLine();
            try {
                bank= XmlSerialization.buildBank(userInput.trim());
                System.out.println("The file was uploaded successfully\n");
            }
            catch (FileNotFoundException fileNotFoundException){
                System.out.println("The system could not find the file, please check the file path again");
                checksIfUserInsertZero(0);
            }
            catch (Exception ex){
                if(ex.getMessage().equals(" ")) {
                    System.out.println("The file is not an xml file");
                    checksIfUserInsertZero(0);
                }
                else
                    System.out.println(ex.getMessage());
            }
    }


    private void createMenu(MainMenu menu) {
        System.out.println("!!!Be aware if you insert the number 0 in any operation that required an input of amount it's like you chose to exit this operation!!!\n");
        MenuItem first = new MenuItem("Reading the system information file.");
        first.AddListener(this::getFileNameFromUser);
        menu.insertToMenu(first);
        MenuItem second = new MenuItem("Presentation of information on existing loans and their status.");
        second.AddListener(()->{if(bank!=null)showDataLoans();else System.out.println("File is not read");});
        menu.insertToMenu(second);
        MenuItem third =
                new MenuItem("Display information about system customers.");
        third.AddListener(()->{if(bank!=null)showCustomerInformation();else System.out.println("File is not read");});
        menu.insertToMenu(third);
        MenuItem fourth =
                new MenuItem("Loading funds into the account.");
        fourth.AddListener(()->{if(bank!=null)insertSumToAccount();else System.out.println("File is not read");});
        menu.insertToMenu(fourth);
        MenuItem fifth =
                new MenuItem("Withdrawal of funds from the account.");
        fifth.AddListener(()->{if(bank!=null)drawSumFromAccount();else System.out.println("File is not read");});
        menu.insertToMenu(fifth);
        MenuItem sixth =
                new MenuItem("Inlay activation.");
        sixth.AddListener(()->{if(bank!=null)inlayActivation();else System.out.println("File is not read");});
        menu.insertToMenu(sixth);
        MenuItem seventh =
                new MenuItem("Promoting the timeline and providing payments.");
        seventh.AddListener(()->
                {
                    if(bank!=null) {
                        System.out.println("The Application was in YAZ: " + YazLogic.currentYazUnit + "\n" + "And now the Application in YAZ: " + (YazLogic.currentYazUnit + 1));
                        bank.yazProgressLogic(true);
                    }
                    else
                        System.out.println("File is not read");
                });
        menu.insertToMenu(seventh);
        MenuItem eighth =
                new MenuItem("Exiting the system.");
        menu.insertToMenu(eighth);
    }

    @Override
    public void showDataLoans(){
        showCurrentYAZ();
        showLoansList(bank.getLoansList(), 2);
    }


    @Override
    public void showLoansList(List<DTOLoan> loans, int indexOperation){
        int index=1;
        if(loans.size()==0){
            System.out.println("None\n---------------------------------\n");
        }
        else
            for (DTOLoan dtoLoan : loans) {
                showLoanData(dtoLoan,index,indexOperation);
                index++;
            }
    }



    @Override
    public void insertSumToAccount() {
        showCurrentYAZ();
        showCustomerList(true);
        int chosenCustomerIndex;
        int amountTOAdd;
        int amountOfCustomer;
        try {
            System.out.println("Please select the customer number to add him the amount of money: ");
            chosenCustomerIndex = getChosenCustomerIndex();
            System.out.println("Please write the amount to be add: ");
            amountTOAdd = getAmountFromUser();
            checksIfUserInsertZero(amountTOAdd);
            amountOfCustomer = bank.getAmountOfCustomer(chosenCustomerIndex-1);
            DTOMovement dtoMovement=bank.movementBuildToCustomer(bank.getCustomer(chosenCustomerIndex-1),amountTOAdd, "+", amountOfCustomer, amountOfCustomer + amountTOAdd);
            if (ensureMassageToCustomer(dtoMovement)) {
                bank.cashDeposit(chosenCustomerIndex-1, amountTOAdd);
                System.out.println("-------- The operation was performed successfully --------\n");
            } else {
                System.out.println("-------- Operation canceled --------\n");
                System.out.println("Enter any keyword to continue...\n");
                new Scanner(System.in).nextLine();
                menu.show();
            }
        } catch (NumberFormatException exception) {
            System.out.println("Incorrect input,please note that you entered an integer number!!\n");
            insertSumToAccount();
        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
            insertSumToAccount();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
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
        showCurrentYAZ();
        showCustomerList(true);
        try {
            System.out.println("Please select the customer number you would like to run the placement on:");
            int chosenCustomerIndex = getChosenCustomerIndex()-1;
            System.out.println("Please select the amount that you willing to invest. between 0 - " + bank.getAmountOfCustomer(chosenCustomerIndex) + ". (Mandatory selection)");
            int chosenInvestAmount = getChosenInvestAmount(bank.getAmountOfCustomer(chosenCustomerIndex)) ;
            checksIfUserInsertZero(chosenInvestAmount);
            bank.checksInvestAmount(bank.getCustomer(chosenCustomerIndex),chosenInvestAmount);
            showCategoriesList();
            System.out.println("Please select the appropriate categories number that you want in the format 1 2 3...(without duplication!) \nIf you are interested in proceeding to the following figure, please click Enter.(chose all)");
            String[] choseCategoryIndex = getChosenCategoryIndex();
            System.out.println("Please select the minimum interest yaz you are willing to receive, a decimal number greater than 0.\nIf you are interested in proceeding to the following figure, please click Enter.");
            double minInterestYaz = getChosenMinInterestYaz();//check in the logic sfare if the interest yaz is greater than 100
            System.out.println("Please select the minimum total yaz time for the loan. If you are interested in proceeding to the following figure, please click Enter.");
            int minYazTime = getChosenMinYazTime();
            StringBuilder categories = new StringBuilder();
            if (choseCategoryIndex.length > 0 && choseCategoryIndex.length<= bank.getCategoriesGroup().size())
                for (String category:choseCategoryIndex) {
                    categories.append(bank.getCategory(Integer.parseInt(category))).append(" ");
                }
            ///////// add another field in the end, its not import in here its just for errors/////////////
            DTOInlay dtoInlay=bank.inlayBuild(bank.getCustomer(chosenCustomerIndex), chosenInvestAmount, categories.toString(), minInterestYaz, minYazTime);
            ArrayList<DTOLoan> loansSupportInlay = bank.loansSustainInlay(dtoInlay);
            showLoansList(loansSupportInlay,3);
            if(loansSupportInlay.size()==0){
                System.out.println("There is no loans that you can invest your money in!\nEnter any keyword to continue...");
                new Scanner(System.in).nextLine();
                menu.show();
            }
            System.out.println("Please select the number loan that you are interested in participating in. You can select more than one by separating the numbers by space or Enter for cancel. The loan number is in the loan title and the format is: 2 3 4 ...");
            ArrayList<DTOLoan> loansCustomerChosen = loansCustomerChosenParticipate(loansSupportInlay);
             System.out.println("Are you sure you want to this operation? 1.yes 2.no ");
            if (Integer.parseInt(new Scanner(System.in).nextLine())== 1) {
                List<DTOMovement> dtoMovementsOfInlayOperation=bank.addMovementPerLoanFromInlay(dtoInlay,loansCustomerChosen,chosenInvestAmount, chosenCustomerIndex);
                for(int i=0;i<loansCustomerChosen.size();i++){
                    showLoanData(bank.getLoanById(loansCustomerChosen.get(i).getId()),i+1,3);
                    System.out.println(dtoMovementsOfInlayOperation.get(i));
                }
                System.out.println("-------- The operation was performed successfully --------\n");
                } else {
                    System.out.println("-------- Operation canceled --------\n");
                    System.out.println("Enter any keyword to continue...\n");
                    new Scanner(System.in).nextLine();
                    menu.show();
                }

        }catch(NumberFormatException exception){
                System.out.println("Incorrect input!!!\n");
                inlayActivation();

        }catch(RuntimeException exception) {
            System.out.println(exception.getMessage());
            inlayActivation();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public void showLoanData(DTOLoan dtoLoan,int index, int indexOperation){
        System.out.println("\n----------Loan number " + index +"----------\n" + dtoLoan+dtoLoan.invokeStatusOperation(indexOperation));
    }

    @Override
    public ArrayList<DTOLoan> loansCustomerChosenParticipate(ArrayList<DTOLoan> loansSupportInlay) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        ArrayList<DTOLoan> loansChosenCustomer = new ArrayList<>();
        String[] arrayStringsScanner;
        Scanner sc = new Scanner(System.in);
        String idLoanCustomerChosen = sc.nextLine();
        arrayStringsScanner = idLoanCustomerChosen.split(" ");
        if(idLoanCustomerChosen.equals("")) {
            System.out.println("Enter any keyword to continue...\n");
            new Scanner(System.in).nextLine();
            menu.show();
        }
        else {
            try {
                loansChosenCustomer = bank.loansSustainInlayAndClientChoose(loansSupportInlay, arrayStringsScanner);
            } catch (NumberFormatException exception) {
                System.out.println(exception.getMessage());
                loansChosenCustomer=loansCustomerChosenParticipate(loansSupportInlay);
            }
        }
        return loansChosenCustomer;
    }

    @Override
    public void showCurrentYAZ() {
        System.out.println("The current YAZ that's the system stand on is : "+YazLogic.currentYazUnit+"\nPlease press any key to move forward to the operation!");
        new Scanner(System.in).nextLine();
    }


    @Override
    public void drawSumFromAccount() {
        showCurrentYAZ();
        showCustomerList(true);
        int chosenCustomerIndex;
        int amountTODraw;
        int amountOfCustomer;
        try {
            System.out.println("Please select the customer number to draw from him the amount of money: ");
            chosenCustomerIndex = getChosenCustomerIndex();
            System.out.println("Please write the amount to be draw: ");
            amountTODraw = getAmountFromUser();
            checksIfUserInsertZero(amountTODraw);
            amountOfCustomer = bank.getAmountOfCustomer(chosenCustomerIndex-1);
            DTOMovement dtoMovement=bank.movementBuildToCustomer(bank.getCustomer(chosenCustomerIndex),amountTODraw, "-", amountOfCustomer, amountOfCustomer - amountTODraw);
            if (ensureMassageToCustomer(dtoMovement)) {
                bank.cashWithdrawal(chosenCustomerIndex-1, amountTODraw);
                System.out.println("-------- The operation was performed successfully --------\n");
            } else {
                System.out.println("-------- Operation canceled --------\n");
                System.out.println("Enter any keyword to continue...\n");
                new Scanner(System.in).nextLine();
                menu.show();
            }
        } catch (NumberFormatException exception) {
            System.out.println("Incorrect input,please note that you entered an integer number!!\n");
            drawSumFromAccount();
        } catch (RuntimeException exception) {
            System.out.println(exception.getMessage());
            drawSumFromAccount();
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private void checksIfUserInsertZero(int userAmountInput) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if(userAmountInput==0){
            System.out.println("-------- Operation canceled --------\n");
            System.out.println("Enter any keyword to continue...\n");
            new Scanner(System.in).nextLine();
            menu.show();
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
        showCurrentYAZ();
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
        if(chosenInvestAmount==0){
            System.out.println("Enter any keyword to continue...\n");
            new Scanner(System.in).nextLine();
            menu.show();
        }
        return chosenInvestAmount;
    }

    @Override
    public String[] getChosenCategoryIndex() {
        String[] arrayStringsScanner;
        Scanner sc = new Scanner(System.in);
        String categoriesCustomerChosen = sc.nextLine();
        arrayStringsScanner = categoriesCustomerChosen.split(" ");
        int chosenCategoryIndex = 0;
        if (Objects.equals(arrayStringsScanner[0], ""))
                return new String[0];
        for (String number : arrayStringsScanner) {

            try {
                chosenCategoryIndex = Integer.parseInt(number);
                if (chosenCategoryIndex < 0 || chosenCategoryIndex > bank.getCategoriesGroup().size() || Arrays.stream(arrayStringsScanner).filter(s->s.equals(number)).count()>1)
                    throw new NumberFormatException();
            } catch (NumberFormatException exception) {
                System.out.println("Your choice is incorrect. Press Enter if you want to skip to the next item or press the categories that you want in the format 1 2 3...(without duplication!");
                arrayStringsScanner=getChosenCategoryIndex();
            }
        }
        return arrayStringsScanner;
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
