package com.bony.security.services;

import com.bony.security.entity.Comment;
import com.bony.security.entity.Post;
import com.bony.security.entity.User;
import com.bony.security.exception.ResourceNotFoundException;
import com.bony.security.repositories.CommentRepository;
import com.bony.security.repositories.PostRepository;
import com.bony.security.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void addComment(Long postId, Long userId, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Comment comment = Comment.builder()
                .comment(content)
                .post(post)
                .user(user)
                .build();

        commentRepository.save(comment);
    }



    public List<Comment> getCommentsByPost(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}

