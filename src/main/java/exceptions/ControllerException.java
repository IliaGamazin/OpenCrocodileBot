package exceptions;

public class ControllerException extends PipelineException {
    public ControllerException(String message) { super(message); }
    public ControllerException(String message, Throwable cause) { super(message, cause); }
}
