package gamelogic;

public class InvalidStepException extends Exception{
    public InvalidStepException() {
    }

    public InvalidStepException(String message) {
        super(message);
    }
}
