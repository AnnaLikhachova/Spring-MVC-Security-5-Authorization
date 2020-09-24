package guide.tour.controller;

import guide.tour.dto.PasswordDto;
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
    public CustomResponse resendRegistrationToken(HttpServletRequest request, @RequestParam("token") String existingToken) {
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        User user = userService.getUser(newToken.getToken());
        mailSender.send(emailConstructor.constructResendVerificationTokenEmail(emailConstructor.getAppUrl(request), request.getLocale(), newToken, user));
        return new CustomResponse(messages.getMessage("message.resendToken", null, request.getLocale()));
    }

    /**
     * Reset password
     */
    @RequestMapping(method = RequestMethod.POST, path="/user/resetPassword")
    public CustomResponse resetPassword(HttpServletRequest request, @RequestParam("email")String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        if (user != null) {
            final String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            mailSender.send(emailConstructor.constructResetTokenEmail(emailConstructor.getAppUrl(request), request.getLocale(), token, user));
        }
        return new CustomResponse(messages.getMessage("message.resetPasswordEmail", null, request.getLocale()));
    }

    /**
     * Change user password
     */
    @RequestMapping(method = RequestMethod.POST, path="/user/updatePassword")
    public CustomResponse changeUserPassword(Locale locale, @Valid PasswordDto passwordDto, @RequestParam("token") String existingToken) {
        User user = userService.getUserByPasswordResetToken(existingToken);
        userService.changeUserPassword(user, passwordDto.getNewPassword());
        return new CustomResponse(messages.getMessage("message.updatePasswordSuc", null, locale));
    }


}
