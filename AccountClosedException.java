package MyExceptions;

public class AccountClosedException extends RuntimeException {
    public AccountClosedException() {
        super("Error: Account is closed");
    }

    public AccountClosedException(int num) {
        super("Error: " + num + " is closed");
    }
}
