package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.DisabledCredentials;
import ru.itmo.wp.service.UserService;

@Component
public class DisabledCredentialsValidator implements Validator {
    private final UserService userService;

    public DisabledCredentialsValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return DisabledCredentials.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            DisabledCredentials disabledForm = (DisabledCredentials) target;
            if (userService.findById(disabledForm.getUserId()) == null) {
                errors.rejectValue(
                        "user-status", "user.not.found", "User not found!");
            }
            if (!disabledForm.getDisabled().equals("disable") && !disabledForm.getDisabled().equals("enable")) {
                errors.rejectValue(
                        "status", "incorrect.status", "Unexpected status!");
            }
        }
    }
}
