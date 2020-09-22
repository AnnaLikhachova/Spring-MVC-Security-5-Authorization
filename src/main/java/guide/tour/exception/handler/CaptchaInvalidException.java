package guide.tour.exception.handler;

public final class CaptchaInvalidException extends RuntimeException {

    public CaptchaInvalidException() {
        super();
    }

    public CaptchaInvalidException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CaptchaInvalidException(final String message) {
        super(message);
    }

    public CaptchaInvalidException(final Throwable cause) {
        super(cause);
    }

}