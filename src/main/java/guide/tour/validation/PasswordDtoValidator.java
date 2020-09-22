package guide.tour.validation;

import guide.tour.dto.PasswordDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Anna Likhachova
 */

@Component
public class PasswordDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return PasswordDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        PasswordDto dto = (PasswordDto) o;

        if (dto.getNewPassword().length() < 8 || dto.getNewPassword().length() > 32) {
            errors.rejectValue("newPassword", "Size.userForm.password");
        }

        if (!dto.getMatchingPassword().equals(dto.getNewPassword())) {
            errors.rejectValue("matchingPassword", "Diff.userForm.passwordConfirm");
        }
    }
}