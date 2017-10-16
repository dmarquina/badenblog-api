package com.badenblog.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.badenblog.common.constants.QueryConstants;
import com.badenblog.dao.CategoryDao;
import com.badenblog.persistence.dao.impl.HibernateCrudDAO;
import com.badenblog.util.dto.CategoryDTO;

@Repository("categoryDao")
public class CategoryDaoImpl extends HibernateCrudDAO<CategoryDTO> implements CategoryDao {

	@Autowired
	private Properties properties;

	@Override
	public List<CategoryDTO> findCategories() {
		final String query = properties.getProperty(QueryConstants.FIND_CATEGORIES);

		final Map<String, Object> parametros = new HashMap<String, Object>();

		return findAllWithNativeQuery(CategoryDTO.class, query, parametros,
				new AliasToBeanResultTransformer(CategoryDTO.class));

	}
}
