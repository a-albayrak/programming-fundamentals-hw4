package exceptionlayer;

public class InsufficientStaminaException extends Exception{

    public InsufficientStaminaException() {
        super("Insufficient stamina!");
    }

    public InsufficientStaminaException(String message) {
        super(message);
    }
}