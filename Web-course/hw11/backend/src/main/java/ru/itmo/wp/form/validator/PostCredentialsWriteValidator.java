package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.service.UserService;

@Component
public class PostCredentialsWriteValidator {

    private final PostService postService;

    public PostCredentialsWriteValidator(PostService postService) {
        this.postService = postService;
    }

    public boolean supports(Class<?> clazz) {
        return PostCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            PostCredentials writeForm = (PostCredentials) target;
//            if (postService.findByLoginAndPassword(writeForm.getText(), writeForm.getTitle()) == null) {
//                errors.reject("invalid-login-or-password", "Invalid login or password");
//            }
        }
    }
}
