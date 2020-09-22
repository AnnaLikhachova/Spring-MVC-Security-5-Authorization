package guide.tour.exception.handler;

public final class InvalidPasswordException extends RuntimeException{

	public InvalidPasswordException() {
        super();
    }

    public InvalidPasswordException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidPasswordException(final String message) {
        super(message);
    }

    public InvalidPasswordException(final Throwable cause) {
        super(cause);
    }
}
