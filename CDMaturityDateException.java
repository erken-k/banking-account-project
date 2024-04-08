package MyExceptions;

public class CDMaturityDateException extends RuntimeException {
    public CDMaturityDateException() {
        super("Maturity date has not been reached");
    }
}
