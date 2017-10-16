package com.badenblog.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.badenblog.common.constants.Constants;
import com.badenblog.common.constants.QueryConstants;
import com.badenblog.dao.PostDao;
import com.badenblog.persistence.dao.impl.HibernateCrudDAO;
import com.badenblog.util.queryresult.PostDetailResult;
import com.badenblog.util.queryresult.PostFeedResult;

@SuppressWarnings("rawtypes")
@Repository("postDao")
public class PostDaoImpl extends HibernateCrudDAO implements PostDao {

	@Autowired
	private Properties properties;

	@SuppressWarnings("unchecked")
	@Override
	public List<PostFeedResult> findAllActives(final List<Integer> idPosts) {
		final String query = properties.getProperty(QueryConstants.FIND_ALL_POSTS);

		final Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codState", Constants.ACTIVE_STATE);
		parametros.put("idPosts", idPosts);
		return findAllWithNativeQuery(PostFeedResult.class, query, parametros,
				new AliasToBeanResultTransformer(PostFeedResult.class));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostDetailResult> findById(final Integer idPost) {
		final String query = properties.getProperty(QueryConstants.FIND_POST_BY_ID);

		final Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codState", Constants.ACTIVE_STATE);
		parametros.put("idPost", idPost);
		return findAllWithNativeQuery(PostDetailResult.class, query, parametros,
				new AliasToBeanResultTransformer(PostDetailResult.class));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> findIdActivePosts(final int rowStart, final int offset) {
		final String query = properties.getProperty(QueryConstants.FIND_ID_POSTS);

		final Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codState", Constants.ACTIVE_STATE);
		parametros.put("rowStart", rowStart);
		parametros.put("offset", offset);
		return findAllWithNativeQuery(Integer.class, query, parametros);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> searchPosts(final int rowStart, final int offset, final String searchField) {
		final String query = properties.getProperty(QueryConstants.SEARCH_POSTS);

		final Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codState", Constants.ACTIVE_STATE);
		parametros.put("tittleLike", likeQueryPattern(searchField));
		parametros.put("descriptionLike", likeQueryPattern(searchField));
		parametros.put("materialLike", likeQueryPattern(searchField));
		parametros.put("categoryLike", likeQueryPattern(searchField));
		parametros.put("rowStart", rowStart);
		parametros.put("offset", offset);
		return findAllWithNativeQuery(Integer.class, query, parametros);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> findByCategory(final int rowStart, final int offset, final List<Integer> idCategories) {
		final String query = properties.getProperty(QueryConstants.FIND_POSTS_BY_CATEGORY);

		final Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codState", Constants.ACTIVE_STATE);
		parametros.put("rowStart", rowStart);
		parametros.put("offset", offset);
		parametros.put("idCategories", idCategories);
		return findAllWithNativeQuery(Integer.class, query, parametros);
	}
}
