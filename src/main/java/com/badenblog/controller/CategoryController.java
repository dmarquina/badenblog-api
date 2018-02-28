package com.badenblog.controller;

import com.badenblog.service.CategoryService;
import com.badenblog.util.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<CategoryDTO> list() {
		return categoryService.findAll();
	}
}
