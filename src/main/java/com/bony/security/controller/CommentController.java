package com.bony.security.controller;

import com.bony.security.entity.Comment;
import com.bony.security.entity.Post;
import com.bony.security.services.CommentService;
import com.bony.security.services.PostService;
import com.bony.security.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts-comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @PostMapping("/{postId}/comment")
    public ResponseEntity<Post> addComment(@PathVariable Long postId, @RequestBody Map<String, String> commentRequest) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        String content = commentRequest.get("comment"); // Extract comment from request body
        commentService.addComment(postId, currentUserId, content);
        Post updatedPost = postService.getPostWithComments(postId);
        return ResponseEntity.ok(updatedPost);
    }


    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(comments);
    }
}


