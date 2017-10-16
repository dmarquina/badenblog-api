package com.badenblog.dao;

import java.util.List;

import com.badenblog.persistence.dao.ICrudDAO;
import com.badenblog.util.queryresult.PostDetailResult;
import com.badenblog.util.queryresult.PostFeedResult;

@SuppressWarnings("rawtypes")
public interface PostDao extends ICrudDAO {

	List<PostDetailResult> findById(Integer idPost);

	List<PostFeedResult> findAllActives(List<Integer> idPosts);

	List<Integer> findIdActivePosts(int rowStart, int offset);

	List<Integer> findByCategory(int rowStart, int offset, List<Integer> idCategories);

	List<Integer> searchPosts(int rowStart, int offset, String searchField);
}
