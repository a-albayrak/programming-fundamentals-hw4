package exceptionlayer;

public class NotAUniqueNameException extends Exception{

    public NotAUniqueNameException() {
        super("Not a unique name!");
    }

    public NotAUniqueNameException(String message) {
        super(message);
    }
}