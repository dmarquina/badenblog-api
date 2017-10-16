package com.badenblog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.badenblog.common.RestDataMessageResponse;
import com.badenblog.persistence.domain.Material;

public interface MaterialService {
	RestDataMessageResponse create(Material material);

	RestDataMessageResponse update(Material material);

	RestDataMessageResponse delete(int id);

	Page<Material> findAll(Pageable pageable);

	Material findOne(int id);
}
