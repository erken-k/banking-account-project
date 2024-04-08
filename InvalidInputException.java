package MyExceptions;

public class InvalidInputException extends RuntimeException {
    private String dataField;       // data field of invalid value
    private String invalidValue;    // value which is invalid
    private String transType;      // type of transaction
    private int accNum;        // acount number
    private String issue;        // issue message

    public InvalidInputException() {
        super("Input entered is not valid");
    }

    //constructor which takes in string representing message and calls super constructor, passing string toit
    public InvalidInputException(String mes) {
        super(mes);
    }

    /*
    constuctor which takes in string representing datafield, string representing invalid value,
    string representing transaction type, int representing account number, string representing issue.
    and assigns their values to the corresponding datafield of this exception object
     */
    public InvalidInputException(String dataField, String invalidValue, String transType, int accNum, String issue) {
        this.dataField = dataField;
        this.invalidValue = invalidValue;
        this.transType = transType;
        this.accNum = accNum;
        this.issue = issue;
    }

    // creates Error receipt string which will be printed in the main
    public String getMessage() {
        String message = ("\n***********************************");
        message += ("\n\n\t***Error Receipt***");
        message += ("\nTransaction type: " + transType);
        if (accNum != 0)
            message += ("\nAccount number: " + accNum);
        message += ("\n" + dataField + ": " + invalidValue);
        message += "\nError: " + dataField + ": " + invalidValue + " " + issue;
        return message;
    }
}
