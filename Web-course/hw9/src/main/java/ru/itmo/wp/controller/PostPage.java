package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.form.validator.PostCredentialsWriteValidator;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.security.Guest;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import ru.itmo.wp.domain.Post;
import ru.itmo.wp.service.PostService;

@Controller
public class PostPage extends Page {

    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }


    @Guest
    @GetMapping("/post/{id}")
    public String getPost(@PathVariable String id, Model model, HttpSession httpSession) {
//        model.addAttribute();
        Post post = postService.findById(postService.parseLong(id));
        model.addAttribute("postById", post);
//        httpSession.setAttribute("postById", post);
        model.addAttribute("comment", new Comment());
        return "PostPage";
    }


    @AnyRole({Role.Name.ADMIN, Role.Name.WRITER})
    @PostMapping("/post/{id}")
    public String writePost(@PathVariable String id, @Valid @ModelAttribute("comment") Comment comment,
                            BindingResult bindingResult, Model model,
                            HttpSession httpSession) {
        Post post = postService.findById(postService.parseLong(id));
        if (bindingResult.hasErrors()) {
            model.addAttribute("postById", post);
            return "PostPage";
        }

//        model.addAttribute("post", post);
//        if (post == null) {
//            return "PostPage";
//        }
        postService.writeComment(post, getUser(httpSession), comment);
        putMessage(httpSession, "New comment success added!");
        return "redirect:/post/{id}";
    }
}
