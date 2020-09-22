package guide.tour.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * The controller class for welcoming and login page.
 */
@Controller
public class CustomController {
	
	static final Logger logger = LoggerFactory.getLogger(CustomController.class);

    /**
     * This method returns welcoming page.
     */
	@RequestMapping(value = { "/information"}, method = RequestMethod.GET)
	public String prepare(ModelMap model, @RequestParam("message") String message) {
		model.addAttribute("message", message);
		return "/information";
	}
		

}
