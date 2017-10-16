package com.badenblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.badenblog.persistence.domain.PostCategory;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Integer>{

}
