package com.badenblog.service;

import java.util.List;

import com.badenblog.util.dto.CategoryDTO;

public interface CategoryService {
	List<CategoryDTO> findAll();
}
