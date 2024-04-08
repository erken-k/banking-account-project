package MyExceptions;

public class PostDatedCheckException extends RuntimeException {
    //no arg constructor
    public PostDatedCheckException() {
        super("Check is post dated");
    }
}
