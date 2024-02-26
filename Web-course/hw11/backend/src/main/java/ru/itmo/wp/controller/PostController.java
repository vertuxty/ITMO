package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.form.validator.PostCredentialsWriteValidator;
import ru.itmo.wp.service.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class PostController {
    private final PostService postService;
    private final PostCredentialsWriteValidator postCredentialsWriteValidator;
    public PostController(PostService postService, PostCredentialsWriteValidator postCredentialsWriteValidator) {
        this.postService = postService;
        this.postCredentialsWriteValidator = postCredentialsWriteValidator;
    }

    @GetMapping("posts")
    public List<Post> findPosts() {
        return postService.findAll();
    }


    @InitBinder("postCredentials")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators();
    }
    @PostMapping("posts")
    public List<Post> createPost(@RequestBody @Valid PostCredentials postCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        postService.create(postCredentials);
        return postService.findAll();
    }
}
