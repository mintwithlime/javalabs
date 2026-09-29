package exception;

public class InvalidStoneDataException extends RuntimeException {
    public InvalidStoneDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
