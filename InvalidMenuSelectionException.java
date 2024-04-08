package MyExceptions;

public class InvalidMenuSelectionException extends RuntimeException {
    //no arg constructor with default message
    public InvalidMenuSelectionException() {
        super("Entered value is not an option");
    }

    // constructor wwhich takes in string and assigns it to message by calling parents cosnstructor
    public InvalidMenuSelectionException(String mes) {
        super(mes);
    }
}
