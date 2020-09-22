package guide.tour.listener;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import guide.tour.event.OnRegistrationCompleteEvent;
import guide.tour.model.User;
import guide.tour.service.UserService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
	
	private final Logger LOGGER = LoggerFactory.getLogger(RegistrationListener.class);
	
    @Autowired
    private UserService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Override
    public void onApplicationEvent( OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration( OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        LOGGER.info("/user onregistration _______________________", user);
        String token = UUID.randomUUID().toString();
        LOGGER.info("/token _______________________", token);
        service.createVerificationTokenForUser(user, token);

        SimpleMailMessage email = constructEmailMessage(event, user, token);
        mailSender.send(email);
    }

    private SimpleMailMessage constructEmailMessage(OnRegistrationCompleteEvent event, User user, String token) {
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
        String message = messages.getMessage("message.regSuccLink", null, "You registered successfully. To confirm your registration, please click on the link below:", event.getLocale());
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
    
}