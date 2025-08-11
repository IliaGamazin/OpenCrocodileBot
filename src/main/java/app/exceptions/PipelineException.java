package app.exceptions;

public class PipelineException extends Exception {
    public PipelineException(String message) {
        super(message);
    }
    public PipelineException(String message, Throwable cause) { super(message, cause); }
}
