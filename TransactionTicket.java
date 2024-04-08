import MyExceptions.CDMaturityDateException;

import java.util.Calendar;

public class TransactionTicket {
    private Calendar dateOfTransaction;
    private String typeOfTransaction;
    private double amountOfTransaction;
    private int accountNumber;
    private int termOfCDC;
    private Check check;
    private int SSN;
    private Depositor depositor;
    private String accountType;
    private String CDExpirationDateForNewAccount;

    //no arg constructor assigns date by getting current time from Calendar.getInstance
    public TransactionTicket() {
        accountNumber=0;

        dateOfTransaction = Calendar.getInstance();
        CDExpirationDateForNewAccount = "n";
        termOfCDC = 0;
    }

    //takes in type of transaction, and amount,assigns date by getting current time from Calendar.getInstance
    //assigns terms of cdc to 0 by default
    public TransactionTicket(String typeOfTransaction, double amountOfTransaction, int accountNumber) {
        dateOfTransaction = Calendar.getInstance();
        this.typeOfTransaction = typeOfTransaction;
        this.amountOfTransaction = amountOfTransaction;
        this.accountNumber = accountNumber;
        CDExpirationDateForNewAccount = "n";
        termOfCDC = 0;
    }

    //takes in type of transaction, and amount,assigns date by getting current time from Calendar.getInstance
    //assigns terms of cdc  as well
    public TransactionTicket(String typeOfTransaction, double amountOfTransaction, int accountNumber, int termOfCDC) {
        dateOfTransaction = Calendar.getInstance();
        this.typeOfTransaction = typeOfTransaction;
        this.amountOfTransaction = amountOfTransaction;
        this.accountNumber = accountNumber;
        CDExpirationDateForNewAccount = "n";
        this.termOfCDC = termOfCDC;
    }

    //copy constructor
    public TransactionTicket(TransactionTicket ticket) {
        this.dateOfTransaction = ticket.getDateOfTransaction();
        this.typeOfTransaction = ticket.getTypeOfTransaction();
        this.amountOfTransaction = ticket.getAmountOfTransaction();
        this.accountNumber = ticket.getAccountNumber();
        this.CDExpirationDateForNewAccount = ticket.CDExpirationDateForNewAccount;
        this.termOfCDC = ticket.getTermOfCDC();
        this.SSN=ticket.SSN;
    }

    //returns String containing date int the form of mm/dd/year
    public String getStringDate() {

        String date = (dateOfTransaction.get(Calendar.MONTH) + 1) + "/" + dateOfTransaction.get(Calendar.DAY_OF_MONTH) +
                "/" + dateOfTransaction.get(Calendar.YEAR);
        return date;

    }


    //returns copy of the date of transaction
    public Calendar getDateOfTransaction() {
        return (Calendar) dateOfTransaction.clone();
    }

    //returns type of transaction
    public String getTypeOfTransaction() {
        return typeOfTransaction;
    }

    //returns amount of transaction
    public double getAmountOfTransaction() {
        return amountOfTransaction;
    }

    //returns terms of cdc as integer value
    public int getTermOfCDC() {
        return termOfCDC;
    }

    public String getCDExpirationDateForNewAccount() {
        return CDExpirationDateForNewAccount;
    }

    public String getNewMaturityDate() {
        String stringDate;
        Calendar maturity = Calendar.getInstance();
        maturity.add(Calendar.MONTH, termOfCDC);
        int month = maturity.get(Calendar.MONTH) + 1;
        int day = maturity.get(Calendar.DAY_OF_MONTH);
        int year = maturity.get(Calendar.YEAR);
        String date = month + "/" + day + "/" + year;
        return date;
    }

    //returns account number of transaction ticket
    public int getAccountNumber() {
        return accountNumber;
    }

    //sets date of transaction from passed calendar object
    public void setDateOfTransaction(Calendar dateOfTransaction) {
        this.dateOfTransaction = (Calendar) dateOfTransaction.clone();
    }

    //sets type of transaction from passed String
    public void setTypeOfTransaction(String typeOfTransaction) {
        this.typeOfTransaction = typeOfTransaction;
    }

    //sets amount of transaction to passed double
    public void setAmountOfTransaction(double amountOfTransaction) {
        this.amountOfTransaction = amountOfTransaction;
    }

    //sets term of cdc to passed int
    public void setTermOfCDC(int termOfCDC) {
        this.termOfCDC = termOfCDC;
    }

    public void setCDExpirationDateForNewAccount(String date) {
        this.CDExpirationDateForNewAccount = date;
    }
    //sets check to check variable

    public void setCheck(Check check){
        this.check= new Check(check);
    }
    //returns Check object representing check variable
    public Check getCheck(){
        return new Check(this.check);

    }
    //sets ssn
    public void setSSN(int ssn){
        this.SSN=ssn;
    }
    //returns int representing ssn
    public int getSSN(){
        return SSN;
    }
    //receives depositor, dets depositor variable to a copy of received depositor
    public void setDepositor(Depositor dep){
        this.depositor=new Depositor(dep);
    }
    //returns a copy of depositor
    public Depositor getDepositor(){
        return new Depositor(depositor);
    }
    //sets type of account for transactions with account opening
    public void setAccountType(String type){
        accountType=type;
    }
    //returns type of account on which transaction is made
    public String getAccountType(){
        return accountType;
    }
}
