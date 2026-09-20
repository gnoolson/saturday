package gnoolson.saturday.script.model.exception;

public class TestDataException extends RuntimeException {

    public TestDataException() {
        super();
    }

    public TestDataException(String message) {
        super(message);
    }

    public TestDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public TestDataException(Throwable cause) {
        super(cause);
    }

    protected TestDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
