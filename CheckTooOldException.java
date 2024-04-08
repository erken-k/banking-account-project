package MyExceptions;

public class CheckTooOldException extends RuntimeException {
    //no arg constructor which calls super constuctor passing message
    public CheckTooOldException() {
        super("Check is older than 6 month");
    }

}
