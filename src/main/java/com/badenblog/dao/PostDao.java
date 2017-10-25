package com.badenblog.dao;

import java.math.BigInteger;
import java.util.List;

import com.badenblog.persistence.dao.ICrudDAO;
import com.badenblog.util.queryresult.PostDetailResult;
import com.badenblog.util.queryresult.PostFeedResult;

@SuppressWarnings("rawtypes")
public interface PostDao extends ICrudDAO {

	List<PostDetailResult> findById(Integer idPost);

	List<PostFeedResult> findAllActives(List<Integer> idPosts);

	BigInteger findTotalIdActivePosts();

	List<Integer> findIdActivePosts(int offset, int size);

	BigInteger findTotalPostByCategory(List<Integer> idCategories);
	
	List<Integer> findByCategory(int offset, int size, List<Integer> idCategories);
	
	BigInteger totalSearchPosts(String searchField);

	List<Integer> searchPosts(int offset, int size, String searchField);
}
