package com.onetomany.controllers;

import com.onetomany.entity.Comment;
import com.onetomany.entity.Post;
import com.onetomany.repos.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts")
    public Iterable<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @PostMapping("/posts")
    public Post createPost() {
        Post post = new Post("First post");

        /*post.getComments().add(
                new Comment("My first comment")
        );
        post.getComments().add(
                new Comment("My second comment")
        );
        post.getComments().add(
                new Comment("My third comment")
        );*/
        return postRepository.save(post);
    }

}
