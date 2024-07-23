package com.bony.security.services;


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

    public Post createPost(Post post, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        post.setUser(user);
        return postRepository.save(post);
    }




    // In PostService
    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        System.out.println("Retrieved posts: " + posts); // Log or debug here
        return posts;
    }


    public Optional<Post> getPostById(Integer postId) {
        return postRepository.findById(postId);
    }

    public Post updatePost(Integer postId, Post postDetails) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        return postRepository.save(post);
    }

    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.delete(post);
    }
}
