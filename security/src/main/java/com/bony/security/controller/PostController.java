package com.bony.security.controller;


import com.bony.security.model.Post;
import com.bony.security.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<Post> createPost(@PathVariable Integer userId, @RequestBody Post post) {
        return ResponseEntity.ok(postService.createPost(post, userId));
    }


    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Integer postId) {
        Post post = postService.getPostById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        return ResponseEntity.ok(post);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        System.out.println("Posts returned: " + posts); // Log or debug here
        return ResponseEntity.ok(posts);
    }


    @PutMapping("/update/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Integer postId, @RequestBody Post postDetails) {
        return ResponseEntity.ok(postService.updatePost(postId, postDetails));
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}


