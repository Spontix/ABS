package logic.bank;

import dataObjects.dtoBank.DTOBank;
import dataObjects.dtoBank.dtoAccount.*;
import dataObjects.dtoCustomer.DTOCustomer;
import logic.UIInterfaceLogic;
import logic.YazLogic;
import logic.bank.account.Account;
import logic.bank.account.Inlay;
import logic.bank.account.Loan;
import logic.bank.account.Movement;
import logic.customer.Customer;

import java.lang.ref.SoftReference;
import java.util.*;
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
    public DTOMovement addMovementToClient(DTOCustomer customer, Movement movement) {
        accounts.stream().filter(a -> Objects.equals(a.getCustomerName(), customer.getCustomerName())).collect(Collectors.toList()).get(0).getMovements().add(Movement.build(movement));
        return (DTOMovement) movement;
    }

    @Override
    public DTOMovement addMovementToClient(int customerIndex, Movement movement) {
        this.getAccounts().get(customerIndex).getMovements().add(Movement.build(movement));
        return (DTOMovement) movement;
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
    public void cashDeposit(Customer customer, int sum) {
        accounts.stream().filter(a -> a.getCustomerName() == customer.getCustomerName()).collect(Collectors.toList()).get(0).cashDeposit(sum);

    }

    @Override
    public void cashWithdrawal(Customer customer, int sum) {
        accounts.stream().filter(a -> a.getCustomerName() == customer.getCustomerName()).collect(Collectors.toList()).get(0).cashWithdrawal(sum);

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
            loans.add(Loan.build(loan.getId(), loan.getOwner(), loan.getCategory(), loan.getCapital(), loan.getTotalYazTime(), loan.getPaysEveryYaz(), loan.getInterestPerPayment()));
        }
        return loans;
        //return this.getLoans();
    }

    @Override
    public List<DTOLoan> getCustomerLoanersList(String customerName) {
        return loans.stream().filter(l -> l.getOwner().equals(customerName)).collect(Collectors.toList());
    }

    @Override
    public List<DTOLoan> getCustomerBorrowersList(String customerName) {
        return loans.stream()
                .filter(l -> l.getListOfAccompanied().stream().anyMatch(a -> a.getCustomerName().equals(customerName)))
                .collect(Collectors.toList());
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
        return new ArrayList<>(this.getCategories());
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
        for (Loan loan : loans) {
            if (loan.getLoanStatus() == DTOLoanStatus.PENDING || loan.getLoanStatus() == DTOLoanStatus.NEW) {
                if (!Objects.equals(inlay.getDtoAccount().getCustomerName(), loan.getOwner())) {//if i am not the one who asking for the money
                    if (loan.getCapital() > inlay.getInvestAmount() &&
                            (inlay.getMinInterestYaz() > loan.getInterestPerPayment() || inlay.getMinInterestYaz() == 0) &&//i dont understand why this is not should be equal
                            (inlay.getMinYazTime() > loan.getTotalYazTime() || inlay.getMinYazTime() == 0) &&
                            (inlay.getCategory() == null || Objects.equals(inlay.getCategory(), loan.getCategory())))

                        loansSustainInlay.add(DTOLoan.build(loan));
                }
            }
        }
        return loansSustainInlay;
    }

    @Override
    public ArrayList<DTOLoan> loansSustainInlayAndClientChoose(ArrayList<DTOLoan> loansSupportInlay, String[] arrayStringsScanner) {
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
                if (Objects.equals(dtoLoan.getId(), arrayStringsScanner[index])) {////////////////////////////////////////////////////////Todo!!
                    checkIdLoan = true;
                }
                if (checkIdLoan)
                    loansChosenCustomer.add(dtoLoan);
                else
                    throw new NumberFormatException("Please select the id loans you are interested in participating in. You can select more than one by separating the numbers by space or Enter for cancel. The format is: 2 3 4 ...");
            }
            indexForWhileLoop++;
            checkIdLoan = false;
            index++;
        }
        return loansChosenCustomer;
    }

    @Override
    public void addMovementPerLoanFromInlay(Inlay inlay, ArrayList<DTOLoan> loansCustomerChosen, int chosenInvestAmount, int customerIndexGiveMoney) {
        int sumPerLoan = 0;
        if (loansCustomerChosen != null) {
            sumPerLoan = (chosenInvestAmount / loansCustomerChosen.size());
            for (DTOLoan dtoLoan : loansCustomerChosen) {
                Loan loan = loans.stream().filter(l -> l.getId().equals(dtoLoan.getId())).collect(Collectors.toList()).get(0);
                Inlay customerInlay = Inlay.build(inlay.getDtoAccount(), sumPerLoan, inlay.getCategory(), inlay.getMinInterestYaz(), inlay.getMinYazTime());
                loan.getListOfAccompanied().add(accounts.get(customerIndexGiveMoney));
                loan.getListOfInlays().add(customerInlay);
                loan.decCapitalSumLeftTillActive(chosenInvestAmount);
                if(loan.getStatusOperation().equals(DTOLoanStatus.NEW)){
                    loan.setLoanStatus(DTOLoanStatus.PENDING);
                }
                else if(loan.getCapitalSumLeftTillActive()==0){
                    loan.setLoanStatus(DTOLoanStatus.ACTIVE);
                }
                /*for (int j = 0; j < getCustomers().size(); j++) {
                    DTOCustomer customer = getCustomers().get(j);
                    if (Objects.equals(customer.getCustomerName(), dtoLoan.getId())) {
                        Movement movementLoan = Movement.build(sumPerLoan, "+", customer.getAmount(), customer.getAmount() + sumPerLoan, 0);
                        addMovementToClient(j, movementLoan);
                        dtoLoan.getListOfAccompanied().add(getCustomer(customerIndexGiveMoney));
                        dtoLoan.getListOfInlays().add(customerInlay);
                        cashDeposit(j, sumPerLoan);///////////////////////////////////////////////////*/
            }
        } else
            throw new RuntimeException("You didnt choose any loans.");
    }

    private Customer getRealCustomerByName(String customerName) {
        return (Customer) (accounts.stream().filter(c -> Objects.equals(c.getCustomerName(), customerName)).collect(Collectors.toList())).get(0);
    }

    @Override
    public void yazProgressLogic() {
        YazLogic.currentYazUnit++;
        /////////////1.if loan is getting active,set the started yaz----its should be in the loan logic
        (loans.stream().filter(l -> l.getLoanStatus() == DTOLoanStatus.ACTIVE).collect(Collectors.toList())).forEach(l -> l.setStartedYazInActive(YazLogic.currentYazUnit));
        /////////////2.Payment should be paid by the loaner+sorted the list because of the logic of the app
        List<Loan> loansThatShouldPay = loans.stream().filter(l -> l.numberOfYazTillNextPulse() == 0 && (l.getLoanStatus() == DTOLoanStatus.ACTIVE || l.getLoanStatus() == DTOLoanStatus.RISK)).sorted(new Comparator<Loan>() {
            @Override
            public int compare(Loan l1, Loan l2) {
                if (l1.getStartedYazInActive() - l2.getStartedYazInActive() == 0 && l1.paymentPerPulse() - l2.paymentPerPulse() == 0)//if the yaz equals and the payment per pulse is equal so sort by the number of pulse
                    return l1.pulseAmount() - l2.pulseAmount();
                else if (l1.getStartedYazInActive() - l2.getStartedYazInActive() == 0) {//if the yaz equals so sort by the payment per pulse
                    return l1.paymentPerPulse() - l2.paymentPerPulse();
                }
                return l1.getStartedYazInActive() - l2.getStartedYazInActive();//else sort by the activation time of the loan
            }
        }).collect(Collectors.toList());
        for (Loan loan : loansThatShouldPay) {
            ///2.1 the loaner can't afford to pay the amount in the right Yaz and that's why the inRisk counter is elevating
            if (loan.paymentPerPulse() > getRealCustomerByName(loan.getOwner()).getAmount()) {
                loan.setLoanStatus(DTOLoanStatus.RISK);
                loan.incrInRiskCounter();
            }
            ///2.2 the loaner can afford to pay the amount in the right Yaz and calculate all the amount that should be spread
            else if (loan.paymentPerPulse() <= getRealCustomerByName(loan.getOwner()).getAmount()) {
                if (loan.getStatusOperation().equals(DTOLoanStatus.RISK)) {
                    loan.decInRiskCounter();
                    if (loan.getInRiskCounter() == 0) {
                        loan.setLoanStatus(DTOLoanStatus.ACTIVE);
                    }
                }
                Movement movement = Movement.build(loan.paymentPerPulse(), "-", getRealCustomerByName(loan.getOwner()).getAmount(), getRealCustomerByName(loan.getOwner()).getAmount() - loan.paymentPerPulse());
                addMovementToClient(getRealCustomerByName(loan.getOwner()), movement);
                cashWithdrawal(getRealCustomerByName(loan.getOwner()), loan.paymentPerPulse());
                loan.incrCapitalSumLeftTillActive(loan.paymentPerPulse());
                for (DTOInlay dtoInlay : loan.getListOfInlays()) {
                    cashDeposit((Customer) dtoInlay.getDtoAccount(), loan.calculatePaymentToLoaner((Customer) dtoInlay.getDtoAccount()));
                }

                if (loan.getCapitalSumLeftTillActive() == loan.getCapital()) {
                    loan.setLoanStatus(DTOLoanStatus.FINISHED);

                }
            }

        }
        for (Loan loan : loansThatShouldPay) {/////run one more time on the loans if there is more money to the customer to close the risk loans that he have or just decrece the depth pulses
            if (loan.getLoanStatus().equals(DTOLoanStatus.RISK) && loan.paymentPerPulse() <= getRealCustomerByName(loan.getOwner()).getAmount()) {
                loan.decInRiskCounter();
                if (loan.getInRiskCounter() == 0) {
                    loan.setLoanStatus(DTOLoanStatus.ACTIVE);
                }
            }
            Movement movement = Movement.build(loan.paymentPerPulse(), "-", getRealCustomerByName(loan.getOwner()).getAmount(), getRealCustomerByName(loan.getOwner()).getAmount() - loan.paymentPerPulse());
            addMovementToClient(getRealCustomerByName(loan.getOwner()), movement);
            cashWithdrawal(getRealCustomerByName(loan.getOwner()), loan.paymentPerPulse());
            loan.incrCapitalSumLeftTillActive(loan.paymentPerPulse());
            for (DTOInlay dtoInlay : loan.getListOfInlays()) {
                cashDeposit((Customer) dtoInlay.getDtoAccount(), loan.calculatePaymentToLoaner((Customer) dtoInlay.getDtoAccount()));
            }

            if (loan.getCapitalSumLeftTillActive() == loan.getCapital()) {
                loan.setLoanStatus(DTOLoanStatus.FINISHED);

            }
        }
    }
}


