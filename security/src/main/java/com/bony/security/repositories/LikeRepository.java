package com.bony.security.repositories;

import com.bony.security.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    long countByPostId(Long postId);
    boolean existsByUserIdAndPostId(Long userId, Long postId);
}
