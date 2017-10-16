package com.badenblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.badenblog.persistence.domain.Material;

public interface MaterialRepository extends JpaRepository<Material, Integer> {

	Material findByName(String name);
}
