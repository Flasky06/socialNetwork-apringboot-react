package com.bony.security.controller;

import com.bony.security.model.User;
import com.bony.security.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts/{postId}/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<Void> likePost(@PathVariable Long postId) {
        Long currentUserId = getCurrentUserId();
        likeService.likePost(postId, currentUserId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countLikes(@PathVariable Long postId) {
        long count = likeService.countLikes(postId);
        return ResponseEntity.ok(count);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }
}
