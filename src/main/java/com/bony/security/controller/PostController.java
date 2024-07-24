package com.bony.security.controller;

import com.bony.security.entity.Post;
import com.bony.security.services.LikeService;
import com.bony.security.services.PostService;
import com.bony.security.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final LikeService likeService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(postService.createPost(post, currentUserId));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody Post postDetails) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(postService.updatePost(postId, postDetails, currentUserId));
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        postService.deletePost(postId, currentUserId);
        return ResponseEntity.noContent().build();
    }
}
