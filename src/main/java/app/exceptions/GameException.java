package app.exceptions;

public final class GameException extends ControllerException {
    public GameException(String message) {
        super(message);
    }

    public GameException(String message, Throwable e) {
        super(message, e);
    }
}
