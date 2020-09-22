package guide.tour.controller;

import guide.tour.dto.PasswordDto;
import guide.tour.exception.handler.InvalidPasswordException;
import guide.tour.model.User;
import guide.tour.model.VerificationToken;
import guide.tour.service.SecurityUserService;
import guide.tour.service.UserService;
import guide.tour.util.CustomResponse;
import guide.tour.util.EmailConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.UUID;

@RestController
@SessionAttributes(types = VerificationToken.class)
public class CustomRestController {

    @Autowired
    private UserService userService;

    @Qualifier("messageSource")
    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private EmailConstructor emailConstructor;

    @Autowired
    private Environment env;

    public CustomRestController() {
        super();
    }

    /**
     * User activation - verification
     */
    @RequestMapping(method = RequestMethod.GET, path="/user/resendRegistrationToken")
    public CustomResponse resendRegistrationToken(final HttpServletRequest request, @RequestParam("token") final String existingToken) {
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        User user = userService.getUser(newToken.getToken());
        mailSender.send(emailConstructor.constructResendVerificationTokenEmail(emailConstructor.getAppUrl(request), request.getLocale(), newToken, user));
        return new CustomResponse(messages.getMessage("message.resendToken", null, request.getLocale()));
    }

    /**
     * Reset password
     */
    @RequestMapping(method = RequestMethod.POST, path="/user/resetPassword")
    public CustomResponse resetPassword(final HttpServletRequest request, @RequestParam("email") final String userEmail) {
        final User user = userService.findUserByEmail(userEmail);
        if (user != null) {
            final String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            mailSender.send(emailConstructor.constructResetTokenEmail(emailConstructor.getAppUrl(request), request.getLocale(), token, user));
        }
        return new CustomResponse(messages.getMessage("message.resetPasswordEmail", null, request.getLocale()));
    }

    /**
     * Save password
     */
    @RequestMapping(method = RequestMethod.POST, path="/user/savePassword")
    public CustomResponse savePassword(final Locale locale, @Valid PasswordDto passwordDto) {

        final String result = securityUserService.validatePasswordResetToken(passwordDto.getToken());

        if(result != null) {
            return new CustomResponse(messages.getMessage("auth.message." + result, null, locale));
        }

        User user = userService.getUserByPasswordResetToken(passwordDto.getToken());
        if(user != null) {
            userService.changeUserPassword(user, passwordDto.getNewPassword());
            return new CustomResponse(messages.getMessage("message.resetPasswordSuc", null, locale));
        } else {
            return new CustomResponse(messages.getMessage("auth.message.invalid", null, locale));
        }
    }

    /**
     * Change user password
     */
    @RequestMapping(method = RequestMethod.POST, path="/user/updatePassword")
    public CustomResponse changeUserPassword(Locale locale, @Valid PasswordDto passwordDto, @RequestParam("token") String existingToken) {
        User user = userService.getUserByPasswordResetToken(existingToken);
        if (!userService.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changeUserPassword(user, passwordDto.getNewPassword());
        return new CustomResponse(messages.getMessage("message.updatePasswordSuc", null, locale));
    }

    /**
     * Change user 2 factor authentication
     */
    @RequestMapping(method = RequestMethod.POST, path="/user/update/2fa")
    public CustomResponse modifyUser2FA(@RequestParam("use2FA") final boolean use2FA) throws UnsupportedEncodingException {
        final User user = userService.updateUser2FA(use2FA);
        if (use2FA) {
            return new CustomResponse(userService.generateQRUrl(user));
        }
        return null;
    }

}
