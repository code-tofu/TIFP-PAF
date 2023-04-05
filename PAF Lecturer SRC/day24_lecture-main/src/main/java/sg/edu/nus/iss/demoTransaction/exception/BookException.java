package sg.edu.nus.iss.demoTransaction.exception;

public class BookException extends RuntimeException {

    public BookException() {
        super();
    }

    public BookException(String message) {
        super(message);
    }

    public BookException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
