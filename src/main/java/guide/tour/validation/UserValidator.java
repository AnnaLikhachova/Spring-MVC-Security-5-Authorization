package guide.tour.validation;

import guide.tour.model.User;
import guide.tour.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator for {@link guide.tour.model.User} class,
 * implements {@link Validator} interface.
 *
 * @author Anna Likhachova
 * @version 1.0
 */

@Component
public class UserValidator implements Validator {

	@Autowired
	private UserService userService;

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		User user = (User) o;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userProfiles", "Required");

		if (user.getName().length() < 7 || user.getName().length() > 16) {
			errors.rejectValue("name", "Size.userForm.username");
		}

		if (!user.getName().matches("^[a-zA-Z]*$")) {
			errors.rejectValue("name", "Size.userForm.username.regex");
		}

		if (userService.findByUserName(user.getName()) != null) {
			errors.rejectValue("name", "Duplicate.userForm.username");
		}

		if (user.getPassword().length() < 7 || user.getPassword().length() > 16) {
			errors.rejectValue("password", "Size.userForm.password");
		}

		if(user.getConfirmPassword()!=null && user.getPassword()!=null) {
            if(!user.getConfirmPassword().equals(user.getPassword())) {
                errors.rejectValue("confirmPassword", "Different.userForm.password");
            }
        }

		if (!validate(user.getEmail())) {
			errors.rejectValue("email", "Regex.userForm.email");
		}

        if (userService.findUserByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.userForm.email");
        }


    }

    private static boolean validate(String emailStr) {
        Matcher matcher = EMAIL_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
