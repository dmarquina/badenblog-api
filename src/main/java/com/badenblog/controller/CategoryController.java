package com.badenblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.badenblog.common.AbstractBadenblogController;
import com.badenblog.common.BadenblogResource;
import com.badenblog.service.CategoryService;
import com.badenblog.util.dto.CategoryDTO;

@RestController
@RequestMapping(value = "/category")
public class CategoryController extends AbstractBadenblogController{
	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public BadenblogResource<List<CategoryDTO>> list() {
		List<CategoryDTO> categories = categoryService.findAll();
		return handleSuccessResponse(categories);
	}
}
