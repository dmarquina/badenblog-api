package com.badenblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.badenblog.persistence.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
