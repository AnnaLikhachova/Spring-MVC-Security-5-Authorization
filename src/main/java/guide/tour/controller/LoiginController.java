package guide.tour.controller;

import guide.tour.model.User;
import guide.tour.service.SecurityService;
import guide.tour.service.UserService;
import guide.tour.validation.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;


/**
 * The controller class for welcoming and login page.
 */
@Controller
public class LoiginController {
	
	static final Logger logger = LoggerFactory.getLogger(LoiginController.class);

	@Autowired
	private SecurityService securityService;
	
    @Autowired
    private UserService userService;

	@Autowired
	private UserValidator userValidator;

    /**
     * This method returns welcoming page.
     */
	@RequestMapping(value = { "/", "/index"}, method = RequestMethod.GET)
	public String prepare(ModelMap model) {
		return "/index";
	}
		
	 /**
     * This method returns login page.
     */
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login(ModelMap model) {
		model.addAttribute("user", new User());
		return "/login";
	}

	 /**
	 * This method for user authorization.
	 */
	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public String submit(@Valid @ModelAttribute("user") User user) {
		if (user != null)
			securityService.autologin(user.getName(), user.getPassword());
			return "redirect:/welcome";
	}
}
