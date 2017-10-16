package com.badenblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.badenblog.persistence.domain.PostMaterial;

public interface PostMaterialRepository extends JpaRepository<PostMaterial, Integer>{

}
