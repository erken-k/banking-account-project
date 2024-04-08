package MyExceptions;

public class EmptyQueueException extends RuntimeException {
    public EmptyQueueException(){
        super("The Queue is Empty!!!");
    }
}
