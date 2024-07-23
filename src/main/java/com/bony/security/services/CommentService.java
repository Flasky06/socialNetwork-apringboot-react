package com.bony.security.services;

import com.bony.security.model.Comment;
import com.bony.security.model.Post;
import com.bony.security.model.User;
import com.bony.security.repositories.CommentRepository;
import com.bony.security.repositories.PostRepository;
import com.bony.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Comment addComment(Long postId, Long userId, String content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(content)
                .build();

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPost(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
