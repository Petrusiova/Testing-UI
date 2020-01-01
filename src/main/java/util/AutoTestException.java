package util;

public class AutoTestException extends RuntimeException {
    public AutoTestException(String message, Throwable cause) {
        super(message, cause);
    }

    public AutoTestException(String message) {
        super(message);
    }
}
