package exceptionlayer;

public class SpecialAlreadyUsedException extends Exception{

    public SpecialAlreadyUsedException() {
        super("Special already used!");
    }

    public SpecialAlreadyUsedException(String message) {
        super(message);
    }
}