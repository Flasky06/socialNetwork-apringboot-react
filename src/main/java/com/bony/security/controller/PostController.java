package com.bony.security.controller;

import com.bony.security.model.Comment;
import com.bony.security.model.Post;
import com.bony.security.model.User;
import com.bony.security.services.CommentService;
import com.bony.security.services.LikeService;
import com.bony.security.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final LikeService likeService;
    private final CommentService commentService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<Post> createPost(@PathVariable Long userId, @RequestBody Post post) {
        return ResponseEntity.ok(postService.createPost(post, userId));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        Post post = postService.getPostById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return ResponseEntity.ok(post);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody Post postDetails) {
        Long currentUserId = getCurrentUserId();
        return ResponseEntity.ok(postService.updatePost(postId, postDetails, currentUserId));
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        Long currentUserId = getCurrentUserId();
        postService.deletePost(postId, currentUserId);
        return ResponseEntity.noContent().build();
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }
}
