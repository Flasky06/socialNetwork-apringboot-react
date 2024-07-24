package com.bony.security.controller;

import com.bony.security.services.LikeService;
import com.bony.security.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts/{postId}")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<String> likePost(@PathVariable Long postId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        likeService.likePost(postId, currentUserId);
        return ResponseEntity.ok("Post liked successfully");
    }

    @GetMapping("/likes")
    public ResponseEntity<Long> countLikes(@PathVariable Long postId) {
        long likesCount = likeService.countLikes(postId);
        return ResponseEntity.ok(likesCount);
    }
}
