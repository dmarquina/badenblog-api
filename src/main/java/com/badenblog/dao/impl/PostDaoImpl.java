package com.badenblog.dao.impl;

import java.math.BigInteger;
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

		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codState", Constants.ACTIVE_STATE);
		parameters.put("idPosts", idPosts);
		return findAllWithNativeQuery(PostFeedResult.class, query, parameters,
				new AliasToBeanResultTransformer(PostFeedResult.class));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PostDetailResult> findById(final Integer idPost) {
		final String query = properties.getProperty(QueryConstants.FIND_POST_BY_ID);

		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codState", Constants.ACTIVE_STATE);
		parameters.put("idPost", idPost);
		return findAllWithNativeQuery(PostDetailResult.class, query, parameters,
				new AliasToBeanResultTransformer(PostDetailResult.class));
	}

	@SuppressWarnings("unchecked")
	@Override
	public BigInteger findTotalIdActivePosts(){
		final String query = properties.getProperty(QueryConstants.FIND_TOTAL_ID_POSTS);

		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codState", Constants.ACTIVE_STATE);
		return (BigInteger)findSingleResultWithNativeQuery(BigInteger.class, query, parameters);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> findIdActivePosts(final int offset, final int size) {
		final String query = properties.getProperty(QueryConstants.FIND_ID_POSTS);

		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codState", Constants.ACTIVE_STATE);
		parameters.put("offset", offset);
		parameters.put("size", size);
		return findAllWithNativeQuery(Integer.class, query, parameters);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public BigInteger totalSearchPosts(final String searchField) {
		final String query = properties.getProperty(QueryConstants.TOTAL_SEARCH_POSTS);

		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codState", Constants.ACTIVE_STATE);
		parameters.put("tittleLike", likeQueryPattern(searchField));
		parameters.put("descriptionLike", likeQueryPattern(searchField));
		parameters.put("materialLike", likeQueryPattern(searchField));
		parameters.put("categoryLike", likeQueryPattern(searchField));
		return (BigInteger)findSingleResultWithNativeQuery(BigInteger.class, query, parameters);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> searchPosts(final int offset, final int size, final String searchField) {
		final String query = properties.getProperty(QueryConstants.SEARCH_POSTS);

		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codState", Constants.ACTIVE_STATE);
		parameters.put("tittleLike", likeQueryPattern(searchField));
		parameters.put("descriptionLike", likeQueryPattern(searchField));
		parameters.put("materialLike", likeQueryPattern(searchField));
		parameters.put("categoryLike", likeQueryPattern(searchField));
		parameters.put("offset", offset);
		parameters.put("size", size);
		return findAllWithNativeQuery(Integer.class, query, parameters);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BigInteger findTotalPostByCategory(final List<Integer> idCategories) {
		final String query = properties.getProperty(QueryConstants.FIND_TOTAL_POSTS_BY_CATEGORY);

		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codState", Constants.ACTIVE_STATE);
		parameters.put("idCategories", idCategories);
		return (BigInteger)findSingleResultWithNativeQuery(BigInteger.class, query, parameters);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> findByCategory(final int offset, final int size, final List<Integer> idCategories) {
		final String query = properties.getProperty(QueryConstants.FIND_POSTS_BY_CATEGORY);

		final Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codState", Constants.ACTIVE_STATE);
		parameters.put("offset", offset);
		parameters.put("size", size);
		parameters.put("idCategories", idCategories);
		return findAllWithNativeQuery(Integer.class, query, parameters);
	}
}
