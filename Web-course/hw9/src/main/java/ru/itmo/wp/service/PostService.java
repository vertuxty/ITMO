package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.PostCredentials;
import ru.itmo.wp.repository.PostRepository;
import ru.itmo.wp.repository.TagRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;

    private final TagService tagService;

    public PostService(PostRepository postRepository, TagService tagService) {
        this.postRepository = postRepository;
        this.tagService = tagService;
    }

    public List<Post> findAll() {
        return postRepository.findAllByOrderByCreationTimeDesc();
    }

    public Post findById(Long id) {
        return id == null ? null : postRepository.findById(id).orElse(null);
    }

    public Long parseLong(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void writeComment(Post post, User user, Comment comment) {
        comment.setUser(user);
        post.getComments().add(comment);
        comment.setPost(post);
        postRepository.save(post);
    }

    public Post createPost(PostCredentials postCredentials) {
        Post post = new Post();
        post.setTitle(postCredentials.getTitle());
        post.setText(postCredentials.getText());
        if (!postCredentials.getTags().isEmpty()) {
            Set<Tag> tagSet = new LinkedHashSet<>();
            String[] tags = postCredentials.getTags().trim().split("\\s+");
            Set<String> stringSet = new HashSet<>(Arrays.asList(tags));
            for (String tag : stringSet) {
                Tag tag1 = new Tag();
                tag1.setName(tag);
                tagSet.add(tag1);
            }
            tagService.save(tagSet);
            post.setTags(tagSet);
        }
        //TagsService...
        return post;
    }
}
