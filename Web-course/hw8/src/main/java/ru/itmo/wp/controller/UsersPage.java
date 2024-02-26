package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.DisabledCredentials;
import ru.itmo.wp.form.validator.DisabledCredentialsValidator;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.bind.ValidationException;

@Controller
public class UsersPage extends Page {
    private final UserService userService;
    private final DisabledCredentialsValidator disabledCredentialsValidator;

    public UsersPage(UserService userService, DisabledCredentialsValidator disabledCredentialsValidator) {
        this.userService = userService;
        this.disabledCredentialsValidator = disabledCredentialsValidator;
    }

    @InitBinder("disabledForm")
    public void initBinder(WebDataBinder bind) {
        bind.addValidators(disabledCredentialsValidator);
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @PostMapping(value = "/users/all")
    public String setStatus(@Valid @ModelAttribute("disabledForm") DisabledCredentials disabledForm,
                            BindingResult bindingResult,
                            HttpSession httpSession) throws ValidationException {
        if (bindingResult.hasErrors()) {
            return "UsersPage";
        }
        User user = userService.findById(disabledForm.getUserId());
//        if (getUser(httpSession) == null) {
//            setMessage(httpSession, "If you want to change state of users, you must be authorized");
//        } else if (!user.equals(getUser(httpSession))) {
//            userService.setDisabled(disabledForm.getUserId(), disabledForm.getDisabled());
//            setMessage(httpSession, "Mode of " + user.getLogin() + " has been changed!");
//        } else {
//            setMessage(httpSession, "You cant change your Mode!");
//        }
        setMessage(httpSession, userService.makeAction(user, getUser(httpSession), disabledForm));
        return "redirect:/users/all";
    }
}
