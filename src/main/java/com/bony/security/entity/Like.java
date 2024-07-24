package com.bony.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // The user who liked the post

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;  // The post that was liked

    @Column(nullable = false, updatable = false)
    private LocalDateTime likedAt;  // Timestamp of when the like was created

    @PrePersist
    protected void onCreate() {
        likedAt = LocalDateTime.now();
    }
}
