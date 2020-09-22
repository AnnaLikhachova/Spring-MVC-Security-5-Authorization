package guide.tour.controller;

import guide.tour.event.OnRegistrationCompleteEvent;
import guide.tour.model.User;
import guide.tour.model.UserProfile;
import guide.tour.model.VerificationToken;
import guide.tour.service.CaptchaService;
import guide.tour.service.UserProfileService;
import guide.tour.service.UserService;
import guide.tour.util.EmailConstructor;
import guide.tour.validation.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


/**
 * The controller class for registration page.
 */
@Controller
@SessionAttributes(types = VerificationToken.class)
public class RegistrationController {

	static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

	@Value("${google.recaptcha.key.site}")
	private String siteV2;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private UserService userService;

	@Autowired
	UserProfileService userProfileService;

	@Qualifier("messageSource")
	@Autowired
	MessageSource messages;

	@Autowired
	private Environment env;

	@Autowired
	private EmailConstructor emailConstructor;

	@Autowired
	ApplicationEventPublisher eventPublisher;

	@Autowired
	private CaptchaService captchaService;

	@Autowired
	private JavaMailSender mailSender;

	/**
	 * This method returns registration page.
	 */
	@RequestMapping(value = {"/registration"}, method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("captchaSettings", siteV2);
		return "registration";
	}

	/**
	 * This is POST method for user registration.
	 */
	@RequestMapping(value = {"/registration"}, method = RequestMethod.POST)
	public String registrationForm(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,
								   HttpServletRequest req, Model model) {
		model.addAttribute("captchaSettings", siteV2);
		userValidator.validate(userForm, bindingResult);
		if(bindingResult.hasErrors()) {
			return "registration";
		}
		String response = req.getParameter("g-recaptcha-response");
		captchaService.processResponse(response);
		userService.saveUser(userForm);
		try {
			String appUrl = "http://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent
					(userForm, req.getLocale(), appUrl));

		}
		catch(Exception me) {
			return "redirect:/error";
		}

		return "/successRegister";
	}

	@RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
	public String confirmRegistration(HttpServletRequest request, Model model, HttpSession session, @RequestParam(name = "token") String token) {
		session.setAttribute("token", token);
		Locale locale = request.getLocale();
		String result = userService.validateVerificationToken(token);
		if(result.equals("valid")) {
			User user = userService.getUser(token);
			authWithoutPassword(user);
			model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));
			return "registrationConfirm";
		}

		model.addAttribute("message", messages.getMessage("auth.message." + result, null, locale));
		model.addAttribute("expired", "expired".equals(result));
		model.addAttribute("token", token);
		return "registrationConfirm";
	}

	@RequestMapping(value = "/registrationConfirm", method = RequestMethod.POST)
	public String resendRegistrationToken(HttpServletRequest request, @RequestParam(name = "token") String token) {
		VerificationToken newToken = userService.generateNewVerificationToken(token);
		User user = userService.getUser(newToken.getToken());
		mailSender.send(emailConstructor.constructResendVerificationTokenEmail(emailConstructor.getAppUrl(request), request.getLocale(), newToken, user));
		return "redirect:/successRegister";
	}

	@RequestMapping(value = "/unvalidUser", method = RequestMethod.GET)
	public String unvalidUser
			(WebRequest request, Model model, @ModelAttribute("verificationToken") VerificationToken verificationToken) {
		Locale locale = request.getLocale();
		Calendar cal = Calendar.getInstance();
		if(verificationToken == null) {
			String message = messages.getMessage("auth.message.invalidToken", null, locale);
			model.addAttribute("message", message);
		}

		if((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			String messageValue = messages.getMessage("auth.message.expired", null, locale);
			String result = userService.validateVerificationToken(verificationToken.getToken());
			model.addAttribute("expired", "expired".equals(result));
			model.addAttribute("token", verificationToken.getToken());
			model.addAttribute("message", messageValue);
		}
		return "unvalidUser";
	}

	/**
	 * This method will provide UserProfile list to views
	 */
	@ModelAttribute("roles")
	public List<UserProfile> initializeProfiles() {
		return userProfileService.findAll();
	}

	public void authWithoutPassword(User user) {

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for(UserProfile role : user.getUserProfiles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getType()));
		}

		Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
}

