package guide.tour.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


/**
 * The controller class for informational pages.
 */
@Controller
public class CustomController {
	
	static final Logger logger = LoggerFactory.getLogger(CustomController.class);

    @Qualifier("messageSource")
    @Autowired
    MessageSource messages;

    /**
     * This method returns welcoming page.
     */
	@RequestMapping(value = { "/information"}, method = RequestMethod.GET)
	public String information(ModelMap model, @RequestParam("message") String message) {
		model.addAttribute("message", message);
		return "/information";
	}

    @RequestMapping(value = { "/logout"}, method = RequestMethod.GET)
    public String logout(ModelMap model, HttpServletRequest request) {
        model.addAttribute("message", messages.getMessage("message.logout", null, request.getLocale()));
        return "/logout";
    }

}
