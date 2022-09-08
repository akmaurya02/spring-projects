package com.onetomany.controllers;

import com.onetomany.entity.Comment;
import com.onetomany.entity.Post;
import com.onetomany.repos.CommentRepository;
import com.onetomany.repos.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class CommentController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    public CommentRepository commentRepository;

    @GetMapping("/posts/{postId}/comments")
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @PostMapping("/posts/{postId}/comments")
    public Comment createComment(@PathVariable Integer postId) {
        Post post = postRepository.getById(postId);
        Comment comment = new Comment("My first comment");
        comment.setPost(post);
        return commentRepository.save(comment);
    }
}
