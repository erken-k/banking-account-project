package MyExceptions;

public class InvalidAccountException extends RuntimeException {
    private int accountNum;
    private String transactionType;
    private String issue;

    // no arg constructor
    public InvalidAccountException() {
        super("Account format is invalid");
    }

    /*
    takes in type of transaction, int account number and string issue
    assigns them to corresponding data fields of this object
     */
    public InvalidAccountException(String type, int num, String issue) {
        this.transactionType = type;
        this.accountNum = num;
        this.issue = issue;
    }

    // returns formated error message with info
    public String getMessage() {
        String message = ("\n***********************************");
        message += ("\n\n\t***Error Receipt***");
        message += ("\nTransaction type: " + transactionType);
        message += ("\nAccount number: " + accountNum);
        message += "\nError: Account " + accountNum + " " + issue;
        return message;
    }
}
