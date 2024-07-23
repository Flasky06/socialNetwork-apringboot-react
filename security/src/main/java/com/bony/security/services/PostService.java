package com.bony.security.services;

import com.bony.security.exception.ResourceNotFoundException;
import com.bony.security.exception.UnauthorizedActionException;
import com.bony.security.model.Post;
import com.bony.security.model.User;
import com.bony.security.repositories.PostRepository;
import com.bony.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post createPost(Post post, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        post.setUser(user);
        return postRepository.save(post);
    }

    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post updatePost(Long postId, Post postDetails, Long currentUserId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        if (!post.getUser().getId().equals(currentUserId)) {
            throw new UnauthorizedActionException("You can only update your own posts");
        }

        if (postDetails.getTitle() != null) post.setTitle(postDetails.getTitle());
        if (postDetails.getContent() != null) post.setContent(postDetails.getContent());

        return postRepository.save(post);
    }

    public void deletePost(Long postId, Long currentUserId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        if (!post.getUser().getId().equals(currentUserId)) {
            throw new UnauthorizedActionException("You can only delete your own posts");
        }

        postRepository.deleteById(postId);
    }
}
