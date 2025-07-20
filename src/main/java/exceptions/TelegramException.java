package exceptions;

public final class TelegramException extends ControllerException {
    public TelegramException(String message, Throwable e) {
        super(message, e);
    }
}