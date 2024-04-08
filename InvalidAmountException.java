package MyExceptions;

public class InvalidAmountException extends RuntimeException {
    private int accNum;
    private double invalidAmount;
    private double currentAccountBalance;
    private String transType;
    private String issue;

    public InvalidAmountException() {
        super("Error: Invalid amount entered");
    }

    public InvalidAmountException(String mes) {
        super(mes);
    }

    /*
    takes in string transaction type, int account number, double amount that is invalid, double surrent account balance,
    assigns their values to the corresponding data fields of this exception
     */
    public InvalidAmountException(String transType, int accNum, double invalidAmount, double currentAccountBalance,
                                  String issue) {
        this.transType = transType;
        this.accNum = accNum;
        this.invalidAmount = invalidAmount;
        this.currentAccountBalance = currentAccountBalance;
        this.issue = issue;
    }

    // returns alreade formated String message represennting error and its information
    public String getMessage() {
        String message = ("\n***********************************");
        message += ("\n\n\t***Error Receipt***");
        message += ("\nTransaction type: " + transType);
        message += ("\nAccount number: " + accNum);
        if (currentAccountBalance != -1)
            message += String.format("\nCurrent balance: %.2f\n", currentAccountBalance);
        if (invalidAmount != -1)
            message += String.format("Amount of Transaction %.2f\n", invalidAmount);
        message += ("Error: " + issue);

        return message;
    }
}
