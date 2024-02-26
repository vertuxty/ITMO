package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.form.validator.NoticeCredentialsCreateValidator;
import ru.itmo.wp.form.validator.UserCredentialsEnterValidator;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;
//import javax.validation.constraints.NotEmpty;

@Controller
public class NoticesPage extends Page {
    private final NoticeService noticeService;
    private final NoticeCredentialsCreateValidator noticeCredentialsCreateValidator;

    public NoticesPage(NoticeService noticeService, NoticeCredentialsCreateValidator noticeCredentialsCreateValidator, UserCredentialsEnterValidator userCredentialsEnterValidator) {
        this.noticeService = noticeService;
        this.noticeCredentialsCreateValidator = noticeCredentialsCreateValidator;
    }

    @InitBinder("noticeContent")
    public void initBinder(WebDataBinder bind) {
        bind.addValidators(noticeCredentialsCreateValidator);
    }

    @GetMapping("/notices/create")
    public String noticesGet(Model model) {
        model.addAttribute("noticeContent", new NoticeCredentials());
        return "NoticesPage";
    }

    @PostMapping("/notices/create")
    public String noticesPost(@Valid @ModelAttribute("noticeContent") NoticeCredentials noticeContent,
                             BindingResult bindingResult,
                             HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticesPage";
        }
        System.out.println(noticeContent.getContent());
        noticeService.saveNotice(noticeContent);
        setMessage(httpSession, "New notice has been created!");
        return "redirect:/";
    }
}
