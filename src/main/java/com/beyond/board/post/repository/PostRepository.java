package com.beyond.board.post.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beyond.board.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
