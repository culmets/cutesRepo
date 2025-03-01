package Domain.Exceptions;

public class InvalidPositionFormatException extends RuntimeException{
    public InvalidPositionFormatException(String message) {
        super(message);
    }

}
