package exceptions;

public final class ValidationException extends ControllerException {
    public ValidationException(String message) {
        super(message);
    }
}
