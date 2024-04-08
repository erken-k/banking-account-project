import MyExceptions.InsufficientFundsException;
import MyExceptions.InvalidAccountException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public class Account implements Comparator<Account>,Comparable<Account> {
    protected Depositor depositor;
    protected int accountNumber;
    protected String accountType;
    protected String accountStatus;
    protected double accountBalance;
    protected ArrayList<TransactionReceipt> transactionHistory;

    // no arg constructor
    public Account() {
    }

    /*
    takes in Depositor, int account number, String type of the account, String account status, double account balance
    assigns these values to corresponding data fields of this object
     */
    public Account(Depositor depositor, int accountNumber, String accountType, String accountStatus,
                   double accountBalance) {
        this.depositor = new Depositor(depositor);
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.accountBalance = accountBalance;
        this.transactionHistory = new ArrayList<TransactionReceipt>();
    }

    //constructor used in children classes takes in String account type, and assigns it to accType data field
    public Account(String accountType) {
        this.accountType = accountType;
    }
    //used for testin in hw 18 b
    public Account(int accountNumber){
        this.accountNumber=accountNumber;
    }

    //copy constructor which takes in Account object and assigns all data fields of current object
    //to the values of passed object
    public Account(Account account) {
        this.depositor = account.getDepositor();
        this.accountNumber = account.getAccountNumber();
        this.accountType = account.getAccountType();
        this.accountStatus = account.getAccountStatus();
        this.accountBalance = account.getAccountBalance();
        this.transactionHistory = account.getTransactionHistory();

    }

    /*
   method is evoked if account type is not specified. Account cant be just account. it has to be declared using polymorphism
     */
    public TransactionReceipt getBalance(TransactionTicket ticket) {
        TransactionReceipt currentReceipt=new TransactionReceipt();
        currentReceipt.setReasonForFailure("Unspecified type of account");
        return currentReceipt;
    }
    /*
  method is evoked if account type is not specified. Account cant be just account. it has to be declared using polymorphism
   */
    public TransactionReceipt makeDeposit(TransactionTicket ticket) {
        TransactionReceipt currentReceipt=new TransactionReceipt();
        currentReceipt.setReasonForFailure("Unspecified type of account");
        return currentReceipt;

    }

    /*
    method is evoked if account type is not specified. Account cant be just account. it has to be declared using polymorphism
     */
    public TransactionReceipt makeWithdrawal(TransactionTicket ticket) {

        TransactionReceipt currentReceipt=new TransactionReceipt();
        currentReceipt.setReasonForFailure("Unspecified type of account");
        return currentReceipt;
    }

    //returns String representing account. String is pre formated using String.format method
    public String toString() {

        String toReturn=String.format("\n%10s %10d %10s %10.2f",depositor.toString(),accountNumber,
                accountType,accountBalance);
        if (accountStatus.equals("Closed"))
            toReturn += "\tclosed";
        return toReturn;
    }

    //getters
//returns a copy of depositor
    public Depositor getDepositor() {
        return new Depositor(this.depositor);
    }

    // returns int representing acocunt number
    public int getAccountNumber() {
        return accountNumber;
    }

    // returns String representing account type
    public String getAccountType() {
        return accountType;
    }

    // returns a string representing account status
    public String getAccountStatus() {
        return accountStatus;
    }

    // returns a double representing account balance
    public double getAccountBalance() {
        return accountBalance;
    }

    // returns arraylist of TransactionReceipts, which represent transaction history
    public ArrayList<TransactionReceipt> getTransactionHistory() {
        return deepCopy(transactionHistory);
    }

    //setters
    //takes in depositor object, assigns a copy of it to a Depositor datafield of this object
    public void setDepositor() {
        this.depositor = new Depositor(depositor);
    }

    // takes in int, assigns it to accountNumber data field of this object
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    //takes in String and assigns it to accountType data field of this object
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
// takes in String accountStatus and assigns it to accountStatus datafield of this object

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    //takes in double, and assigns its value to accountBalance datafield of this object
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    // takes in ArrayList of transaction receipts assigns a coopy of it, received by calling deepcopy method,
    // to the value of TransactionHistory datfield of object
    public void setTransactionHistory(ArrayList<TransactionReceipt> transactionHistory) {
        this.transactionHistory = deepCopy(transactionHistory);
    }

    //adds passed receiped to transactionhistory
    public void addToTransactionHistory(TransactionReceipt reciept) {
        transactionHistory.add(new TransactionReceipt(reciept));
    }

    //helper method to copy arraylist
    private ArrayList<TransactionReceipt> deepCopy(ArrayList<TransactionReceipt> original) {
        ArrayList<TransactionReceipt> copy = new ArrayList<>();
        for (int i = 0; i < original.size(); i++) {
            copy.add(new TransactionReceipt(original.get(i)));
        }
        return copy;
    }

    // takes in TransactionTicket and Calendar representing date of check
    // this method is invoked inly when Account type is not checking.
    // it returns receipt with failed tranaction due to account not beeing checking
    public TransactionReceipt clearCheck(TransactionTicket ticket, Calendar dateOfCheck) {
        TransactionReceipt receipt = new TransactionReceipt(ticket, false,
                "Not checking account", accountBalance, accountBalance);
        return receipt;
    }
    //hw18B
    //compares accounts based on their account number returns negative, 0 or positive number
    public int compareTo(Account account){
        return accountNumber-account.getAccountNumber();
    }
    //compares accounts based on their account number returns negative 0 or positive int
    public int compare(Account one, Account two){
        return accountNumber- two.getAccountNumber();
    }
    //this program uses account number as a hashcode, because it is the most unique data in account.
    //method returns hashcode for given account
    public int hashCode(){
        return accountNumber;
    }
    //returns true if object passed is equal to object from which's instance it is called
    public boolean equals(Object two){
        if(two== null|| !(two instanceof Account))
            return false;
        Account acc=(Account)two;
        return accountNumber==acc.getAccountNumber();
    }

}

