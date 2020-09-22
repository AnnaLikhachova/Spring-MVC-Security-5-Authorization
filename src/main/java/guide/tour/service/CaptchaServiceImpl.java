package guide.tour.service;

import guide.tour.exception.handler.CaptchaInvalidException;
import guide.tour.exception.handler.CaptchaUnavailableException;
import guide.tour.google.api.GoogleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.net.URI;


@Service("captchaService")
public class CaptchaServiceImpl extends AbstractCaptchaService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CaptchaService.class);

    @Value("${google.recaptcha.key.secret}")
    private  String siteSecret;

    @Override
    public void processResponse(final String response) {
        securityCheck(response);

        final URI verifyUri = URI.create(String.format(RECAPTCHA_URL_TEMPLATE, siteSecret, response, getClientIP()));
        try {
            final GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);
            LOGGER.debug("Google's response: {} ", googleResponse.toString());

            if (!googleResponse.isSuccess()) {
                if (googleResponse.hasClientError()) {
                    reCaptchaAttemptService.reCaptchaFailed(getClientIP());
                }
                throw new CaptchaInvalidException("reCaptcha was not successfully validated");
            }
        } catch (RestClientException rce) {
            throw new CaptchaUnavailableException("Registration unavailable at this time.  Please try again later.", rce);
        }
        reCaptchaAttemptService.reCaptchaSucceeded(getClientIP());
    }
}
