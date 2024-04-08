package MyExceptions;

public class InsufficientFundsException extends RuntimeException {
    private int accNum;
    private double invalidAmount;
    private double currentAccountBalance;
    private String transType;
    private String issue;

    /*
    takes in String transaction type, in account number double invalid amount, double current balance, and string issue
    assigns it to corresponding data fields of this object
     */
    public InsufficientFundsException(String transType, int accNum, double invalidAmount, double currentAccountBalance,
                                      String issue) {
        this.transType = transType;
        this.accNum = accNum;
        this.invalidAmount = invalidAmount;
        this.currentAccountBalance = currentAccountBalance;
        this.issue = issue;
    }

    // returns formated string representing error receipt
    public String getMessage() {
        String message = ("\n***********************************");
        message += ("\n\n\t***Error Receipt***");
        message += ("\nTransaction type: " + transType);
        message += ("\nAccount number: " + accNum);
        message += String.format("\nCurrent balance: %.2f\n", currentAccountBalance);
        message += String.format("Amount of transaction: %.2f\n", invalidAmount);
        message += ("Error: " + issue);

        return message;
    }
}
