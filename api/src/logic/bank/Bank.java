package logic.bank;

import dataObjects.dtoBank.DTOBank;
import dataObjects.dtoBank.dtoAccount.*;
import dataObjects.dtoCustomer.DTOCustomer;
import logic.UIInterfaceLogic;
import logic.bank.account.Account;
import logic.bank.account.Inlay;
import logic.bank.account.Loan;
import logic.bank.account.Movement;
import logic.customer.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Bank extends DTOBank implements UIInterfaceLogic {

    protected Bank() {

    }

    @Override
    public DTOInlay addInlayToClient(int customerIndex, Inlay inlay) {
        this.getAccounts().get(customerIndex).getInlays().add(inlay);
        return DTOInlay.build(inlay);
    }

    @Override
    public DTOMovement addMovementToClient(int customerIndex, Movement movement) {
        this.getAccounts().get(customerIndex).getMovements().add(movement);
        return DTOMovement.build(movement);
    }

    @Override
    public int getAmountOfCustomer(int customerIndex) {
        return this.getCustomer(customerIndex).getAmount();
    }

    @Override
    public DTOCustomer getCustomer(int customerIndex) {
        return DTOCustomer.build((Customer) this.getAccounts().get(customerIndex));
    }

    @Override
    public String getCustomerName(int customerIndex) {
        return getCustomer(customerIndex).getCustomerName();
    }

    @Override
    public int getTotalCustomersSize() {
        return this.getAccounts().size();
    }

    @Override
    public void cashDeposit(int customerIndex, int sum) {
        this.getAccounts().get(customerIndex).cashDeposit(sum);
    }

    @Override
    public void cashWithdrawal(int customerIndex, int sum) {
        this.getAccounts().get(customerIndex).cashWithdrawal(sum);
    }

    @Override
    public void addCustomer(Customer customer) {
        this.getAccounts().add(customer);
    }

    @Override
    public ArrayList<DTOCustomer> getCustomers() {
        ArrayList<DTOCustomer> dtoCustomers = new ArrayList<>();
        for (Account account : this.getAccounts()) {
            dtoCustomers.add(DTOCustomer.build((Customer) account));
        }

        return dtoCustomers;
    }

    @Override
    public ArrayList<Loan> getLoansList() {
        ArrayList<Loan> loans = new ArrayList<>();
        for (Loan loan : this.getLoans()) {
            loans.add(loan);
        }
        return loans;
        //return this.getLoans();
    }

    @Override
    public List<DTOLoan> getCustomerLoanersList(String customerName) {
        List<DTOLoan> dtoLoans = loans.stream().filter(l -> l.getOwner().equals(customerName)).collect(Collectors.toList());
        return dtoLoans;
    }

    @Override
    public List<DTOLoan> getCustomerBorrowersList(String customerName) {
        /*List<DTOLoan> dtoLoans = new ArrayList<>();
        for (Loan loan : loans) {
            for (int j = 0; j < loan.getListOfAccompanied().size(); j++) {
                if (Objects.equals(loan.getListOfAccompanied().get(j).getCustomerName(), customerName)) {
                    dtoLoans.add(loan);
                    break;
                }
            }
        }
        return dtoLoans;*/
        List<DTOLoan> dtoLoans =
                loans.stream()
                        .filter(l -> l.getListOfAccompanied().stream().anyMatch(a -> a.getCustomerName().equals(customerName)))
                        .collect(Collectors.toList());

        return dtoLoans;
    }

    @Override
    public void addLoanToBank(Loan loan) {///////////////
        loans.add(loan);
    }

    @Override
    public void addBorrowerTOLoan() {///////////
        loans.get(0).getListOfAccompanied().add(getCustomer(0));
        loans.get(0).getListOfAccompanied().add(getCustomer(1));
        loans.get(0).getListOfAccompanied().add(getCustomer(2));
        loans.get(0).getListOfAccompanied().add(getCustomer(3));

    }

    @Override
    public ArrayList<String> getCategoriesGroup() {
        ArrayList<String> categories = new ArrayList<>();
        for (String category : this.getCategories()) {
            categories.add(category);
        }
        return categories;
    }

    @Override
    public String getCategory(int categoryIndex) {
        return this.getCategories().get(categoryIndex - 1);
    }

    @Override
    public void addCategories() {
        categories.add("car");
        categories.add("Home");
        categories.add("Mortgage");
        categories.add("Bar Mitzvah");
    }

    @Override
    public ArrayList<DTOLoan> loansSustainInlay(DTOInlay inlay) {
        ArrayList<DTOLoan> loansSustainInlay = new ArrayList<>();
        ArrayList<Loan> loans = this.loans;
        for (Loan loan : loans) {
            if (loan.getLoanStatus() == DTOLoanStatus.PENDING || loan.getLoanStatus() == DTOLoanStatus.NEW) {
                if (!Objects.equals(inlay.getDtoAccount().getCustomerName(), loan.getOwner())) {
                    if (inlay.getInvestAmount() > loan.getCapital() &&
                            (inlay.getMinInterestYaz() > loan.getInterestPerPayment() || inlay.getMinInterestYaz() == 0) &&
                            (inlay.getMinYazTime() > loan.getTotalYazTime() || inlay.getMinYazTime() == 0) &&
                            (inlay.getCategory() == null || Objects.equals(inlay.getCategory(), loan.getCategory())))

                        loansSustainInlay.add(DTOLoan.build(loan));
                }
            }
        }
        return loansSustainInlay;
    }

    @Override
    public ArrayList<DTOLoan> loansSustainInlayAndClientChoose(ArrayList<DTOLoan> loansSupportInlay,String[] arrayStringsScanner) {
        ArrayList<DTOLoan> loansChosenCustomer = new ArrayList<>();
        boolean checkIdLoan = false;
        int index = 0;
        int indexForWhileLoop = 0;
        while (indexForWhileLoop < arrayStringsScanner.length) {
            if (Objects.equals(arrayStringsScanner[index], "")) {
                loansChosenCustomer = null;
                break;
            }
            for (DTOLoan dtoLoan : loansSupportInlay) {
                if (Objects.equals(dtoLoan.getId(), arrayStringsScanner[index])) {
                    checkIdLoan = true;
                    break;
                }
                if(checkIdLoan)
                    loansChosenCustomer.add(dtoLoan);
                else
                    throw new NumberFormatException("Please select the id loans you are interested in participating in. You can select more than one by separating the numbers by space or Enter for cancel. The format is: 2 3 4 ...");
            }
            indexForWhileLoop++;
            checkIdLoan=false;
            index++;
        }
        return loansChosenCustomer;
    }

    @Override
    public void addMovementPerLoanFromInlay(Inlay inlay, ArrayList<DTOLoan> loansCustomerChosen , int chosenInvestAmount, int customerIndexGiveMoney){
        int sumPerLoan =0;
        if(loansCustomerChosen != null){
            sumPerLoan = (chosenInvestAmount / loansCustomerChosen.size());
            for (DTOLoan dtoLoan : loansCustomerChosen) {
                Inlay customerInlay = Inlay.build(inlay.getDtoAccount(),sumPerLoan,inlay.getCategory(),inlay.getMinInterestYaz(), inlay.getMinYazTime());
                for (int j = 0; j < getCustomers().size(); j++) {
                    DTOCustomer customer = getCustomers().get(j);
                    if (Objects.equals(customer.getCustomerName(), dtoLoan.getId())) {
                        Movement movementLoan = Movement.build(sumPerLoan, "+", customer.getAmount(), customer.getAmount() + sumPerLoan, 0);
                        addMovementToClient(j, movementLoan);
                        dtoLoan.getListOfAccompanied().add(getCustomer(customerIndexGiveMoney));
                        dtoLoan.getListOfInlays().add(customerInlay);
                        cashDeposit(j, sumPerLoan);///////////////////////////////////////////////////
                        }
                    }
                }

            }
        else
            throw new RuntimeException ("You didnt choose any loans.");
    }

    @Override
    public void yazProgress() {

    }
}
