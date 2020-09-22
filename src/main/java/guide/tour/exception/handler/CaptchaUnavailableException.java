package guide.tour.exception.handler;

public final class CaptchaUnavailableException extends RuntimeException {

	public CaptchaUnavailableException() {
        super();
    }

    public CaptchaUnavailableException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CaptchaUnavailableException(final String message) {
        super(message);
    }

    public CaptchaUnavailableException(final Throwable cause) {
        super(cause);
    }

}
