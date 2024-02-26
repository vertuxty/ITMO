package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.PostCredentials;

@Component
public class PostCredentialsWriteValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PostCredentials.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            PostCredentials postCredentials = (PostCredentials) target;
            String[] tagsArray = postCredentials.getTags().trim().split("\\s+");
            if (!postCredentials.getTags().trim().isEmpty()) {
                for (String currTag : tagsArray) {
                    if (currTag != null && !currTag.matches("[a-zA-Z]+")) {
                        errors.rejectValue("tags", "tags.is-not-matching-pattern", "Only have latin letters!");
                    }
                }
                if (postCredentials.getTitle().isBlank()) {
                    errors.rejectValue("title", "title.is-empty", "Title is empty!");
                } else if (postCredentials.getText().isBlank()) {
                    errors.rejectValue("text", "text.is-empty", "Text is empty!");
                }
                //            } else if (postCredentials.getTags().isBlank()){
//                errors.rejectValue("tags", "login.is-in-us", "Tags is empty!");
//            }
            } else {
                postCredentials.setTags(postCredentials.getTags().trim());
            }
        }
    }
}
