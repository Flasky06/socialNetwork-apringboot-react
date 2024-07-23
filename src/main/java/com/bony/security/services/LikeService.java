package com.bony.security.services;


import com.bony.security.model.Like;
import com.bony.security.model.Post;
import com.bony.security.model.User;
import com.bony.security.repositories.LikeRepository;
import com.bony.security.repositories.PostRepository;
import com.bony.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void likePost(Long postId, Long userId) {
        if (likeRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new RuntimeException("You have already liked this post");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();

        likeRepository.save(like);
    }

    public long countLikes(Long postId) {
        return likeRepository.countByPostId(postId);
    }
}
