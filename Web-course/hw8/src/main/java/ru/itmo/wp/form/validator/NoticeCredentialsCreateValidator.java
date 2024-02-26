package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.service.NoticeService;


@Component
public class NoticeCredentialsCreateValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return NoticeCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            String createNoticeForm = ((NoticeCredentials) target).getContent();
            if (createNoticeForm.isBlank()) {
                errors.rejectValue(
                        "content", "password.invalid-login-or-password", "Content should contains any symbols, not only whitespaces.");
            }
        }
    }

//    public boolean supports(Class<?> clazz) {
//        return NoticeCredentials.class.equals(clazz);
//    }
//
//    public void validate(Object target, Errors errors) {
//        if (!errors.hasErrors()) {
//            NoticeCredentials createNoticeForm = (NoticeCredentials) target;
//            if (createNoticeForm.getContent().trim().isEmpty()) {
//                errors.rejectValue(
//                        "content", "password.invalid-login-or-password", "Content should contains any symbols, not only whitespaces.");
//            }
//        }
//    }
}
