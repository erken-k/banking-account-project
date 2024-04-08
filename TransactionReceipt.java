import java.util.ArrayList;
import java.util.Calendar;

public class TransactionReceipt {
    private TransactionTicket ticket;
    private Boolean successIndicatorFlag;
    private String reasonForFailure;
    private double preTransactionBalance;
    private double postTransactionBalance;
    private Calendar postTransactionMaturityDate;
    private String accInfo;

    //default no arg constructor, sets success indicator flag to false
    public TransactionReceipt() {
        successIndicatorFlag = false;
    }

    //copy constructor, sets all data fields from passed transaction receipt, to current.
    public TransactionReceipt(TransactionReceipt receipt) {
        this.ticket = receipt.getTransactionTicket();
        this.successIndicatorFlag = receipt.getSuccessIndicatorFlag();
        this.reasonForFailure = receipt.getReasonForFailure();
        this.preTransactionBalance = receipt.getPreTransactionBalance();
        this.postTransactionBalance = receipt.getPostTransactionBalance();

    }

    //Constructor used in account.getBalance()
    //takes in Ticket, boolean(success flag), double(pre transaction balance), double(post transaciton balance
    //assigns these variables to data fields in object
    public TransactionReceipt(TransactionTicket ticket, boolean successIndicatorFlag, double preTransactionBalance,
                              double postTransactionBalance) {
        this.ticket = new TransactionTicket(ticket);
        this.successIndicatorFlag = successIndicatorFlag;
        this.preTransactionBalance = preTransactionBalance;
        this.postTransactionBalance = postTransactionBalance;

    }
//constructor for failed transactions
    //takes in Ticket, boolean(success flag), String containing reason for failure
    // double(pre transaction balance), double(post transaction balance

    public TransactionReceipt(TransactionTicket ticket, boolean successIndicatorFlag, String reasonForFailure,
                              double preTransactionBalance, double postTransactionBalance) {
        this.ticket = new TransactionTicket(ticket);
        this.successIndicatorFlag = successIndicatorFlag;
        this.reasonForFailure = reasonForFailure;
        this.preTransactionBalance = preTransactionBalance;
        this.postTransactionBalance = postTransactionBalance;

    }

    //returns ticket as a string
    public String toStringAsLine() {
        String toReturn=String.format("%-10s %20s %10d %20.2f %20.2f %20.2f ",ticket.getStringDate(),
                ticket.getTypeOfTransaction(),ticket.getAccountNumber(),preTransactionBalance,ticket.getAmountOfTransaction(),
                postTransactionBalance);
        String success="";
        if(successIndicatorFlag)
            success+="Successful";
        else
            success+="Unsuccessful";
        toReturn+=String.format("%20s",success);
        return toReturn;
    }

    public String toStringAsColumn() {
        String receipt = "";
        receipt += ("\n***********************************");
        /**/receipt += ("\n\t***Transaction Receipt***\t");
        TransactionTicket ticket = getTransactionTicket();
        receipt += ("\nTransaction Type: " + ticket.getTypeOfTransaction());
        if(ticket.getTypeOfTransaction().equals("Account Info")||ticket.getTypeOfTransaction().equals("Account Info With History")){
            receipt+=("\nSSN Searched for: "+ticket.getSSN());
            receipt+=accInfo;
            return receipt;
        }
        if(ticket.getAccountNumber()!=0)
            receipt += ("\nAccount number: " + ticket.getAccountNumber());
        if (getSuccessIndicatorFlag()) {
            if (!ticket.getTypeOfTransaction().equals("Balance Inquiry")) {
                receipt += String.format("\nPre Transaction balance: %.2f\n", getPreTransactionBalance());
                receipt += String.format("Transaction amount: %.2f\n", ticket.getAmountOfTransaction());
                receipt += String.format("Post Transaction balance: %.2f\n", getPostTransactionBalance());
                if (!getTransactionTicket().getCDExpirationDateForNewAccount().equals("n"))
                    receipt += String.format("New Maturity Date: %s\n",
                            getTransactionTicket().getCDExpirationDateForNewAccount());
            } else {
                receipt += String.format("\nCurrent Balance: %.2f\n", getPreTransactionBalance());
            }
        } else {
            receipt += String.format("\nError: " + getReasonForFailure());
        }/**/
        return receipt;
    }

    //gets copy ofthe transaction ticket (TransactionTicket)
    public TransactionTicket getTransactionTicket() {
        return new TransactionTicket(ticket);
    }

    // gets success indicator flag(boolean)
    public Boolean getSuccessIndicatorFlag() {
        return successIndicatorFlag;
    }

    // gets reson for failure of transaction(String)
    public String getReasonForFailure() {
        return reasonForFailure;
    }

    //gets pre transaction balance(double)
    public double getPreTransactionBalance() {
        return preTransactionBalance;
    }

    //gets post transaction balance(double)
    public double getPostTransactionBalance() {
        return postTransactionBalance;
    }

    //gets a copy of post transaction maturity date(Calendar object)
    public Calendar getPostTransactionMaturityDate() {
        return (Calendar) postTransactionMaturityDate.clone();
    }

    //sets TransactionTicket data field of this object, to a copy of passed transactionTicket object
    public void setTransactionTicket(TransactionTicket ticket) {
        this.ticket = new TransactionTicket(ticket);
    }

    //sets successIndicatorFlag data field value to a passed boolean
    public void setSuccessIndicatorFlag(Boolean indicatorFlag) {
        this.successIndicatorFlag = indicatorFlag;
    }

    //sets reasonForFailure data field to a passed String
    public void setReasonForFailure(String reasonForFailure) {
        this.reasonForFailure = reasonForFailure;
    }

    //sets  preTransactionBalance to a passed double
    public void setPreTransactionBalance(double preTransactionBalance) {
        this.preTransactionBalance = preTransactionBalance;
    }

    //sets postTransactionBalance to a passed double
    public void setPostTransactionBalance(double postTransactionBalance) {
        this.postTransactionBalance = postTransactionBalance;
    }

    //sets postTransactionMaturity date to a copy of passed calendar
    public void setPostTransactionMaturityDate(Calendar postTransactionMaturityDate) {
        this.postTransactionMaturityDate = (Calendar) postTransactionMaturityDate.clone();
    }
    // sets message that is returned for account info transactions
    public void setAccInfo(String s){
        accInfo=s;
    }

}
