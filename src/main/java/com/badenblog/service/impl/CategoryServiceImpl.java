package com.badenblog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.badenblog.dao.CategoryDao;
import com.badenblog.service.CategoryService;
import com.badenblog.util.dto.CategoryDTO;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<CategoryDTO> findAll() {
		return categoryDao.findCategories();
	}
	
	
}
