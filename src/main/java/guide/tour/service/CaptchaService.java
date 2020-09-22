package guide.tour.service;

import guide.tour.exception.handler.CaptchaInvalidException;

public interface CaptchaService {
    
    default void processResponse(final String response) throws CaptchaInvalidException {}
    
    default void processResponse(final String response, String action) throws CaptchaInvalidException {}
    
    String getReCaptchaSite();

    String getReCaptchaSecret();
}
