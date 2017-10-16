package com.badenblog.dao;

import java.util.List;

import com.badenblog.persistence.dao.ICrudDAO;
import com.badenblog.util.dto.CategoryDTO;

public interface CategoryDao extends ICrudDAO<CategoryDTO> {

	List<CategoryDTO> findCategories();
}
