package com.badenblog.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.badenblog.common.constants.Constants;
import com.badenblog.dao.PostDao;
import com.badenblog.json.request.NewPostRequest;
import com.badenblog.json.request.PostByCategoryRequest;
import com.badenblog.json.response.NewPostResponse;
import com.badenblog.json.response.PostDetailResponse;
import com.badenblog.json.response.PostFeedResponse;
import com.badenblog.persistence.domain.Category;
import com.badenblog.persistence.domain.Material;
import com.badenblog.persistence.domain.Post;
import com.badenblog.persistence.domain.PostCategory;
import com.badenblog.persistence.domain.PostMaterial;
import com.badenblog.repository.MaterialRepository;
import com.badenblog.repository.PostCategoryRepository;
import com.badenblog.repository.PostMaterialRepository;
import com.badenblog.repository.PostRepository;
import com.badenblog.service.PostService;
import com.badenblog.util.dto.CategoryDTO;
import com.badenblog.util.queryresult.PostDetailResult;
import com.badenblog.util.queryresult.PostFeedResult;

@Service("postService")
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDao postDao;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private MaterialRepository materialRepository;

	@Autowired
	private PostMaterialRepository postMaterialRepository;

	@Autowired
	private PostCategoryRepository postCategoryRepository;

	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Override
	public NewPostResponse create(final NewPostRequest newPostRequest) {
		try {
			List<String> materials = newPostRequest.getMaterials();
			List<Integer> materialsId = new ArrayList<Integer>();
			for (String material : materials) {
				Material materialObj = materialRepository.findByName(material);
				if (materialObj != null) {
					materialsId.add(materialObj.getIdMaterial());
				} else {
					Material newMaterial = new Material(material);
					newMaterial.setDateInsert(new Date());
					materialsId.add(materialRepository.save(newMaterial).getIdMaterial());
				}
			}
			Post newPost = newPostRequest.getPost();
			newPost.setState(Constants.ACTIVE_STATE);
			newPost.setLikes(Constants.CERO_ENTERO);
			newPost.setDateInsert(new Date());
			postRepository.save(newPost);

			for (Integer materialId : materialsId) {
				postMaterialRepository.save(new PostMaterial(new Post(newPost.getIdPost()), new Material(materialId)));
			}

			for (CategoryDTO category : newPostRequest.getCategories()) {
				postCategoryRepository
						.save(new PostCategory(new Post(newPost.getIdPost()), new Category(category.getIdCategory())));
			}
			return (new NewPostResponse(newPost.getTittle()));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Page<PostFeedResponse> findAllActives(Pageable pageable) {
		long totalIdPosts = postDao.findTotalIdActivePosts().longValue();
		int offset = pageable.getOffset();
		List<Integer> idPosts = postDao.findIdActivePosts(offset,pageable.getPageSize());
		if (!idPosts.isEmpty()) {
			List<PostFeedResult> result = postDao.findAllActives(idPosts);
			return postFeedResultToPage(result, pageable, totalIdPosts);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public PostDetailResponse findById(int idPost) {
		List<PostDetailResult> result = postDao.findById(idPost);
		if (result != null) {
			return postDetailResultToResponse(result);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public Page<PostFeedResponse> findByCategory(final Pageable pageable,
			final PostByCategoryRequest postByCategoryRequest) {
		long totalIdPosts = postDao.findTotalPostByCategory(postByCategoryRequest.getIdsCategories()).longValue();
		int offset = pageable.getOffset();
		List<Integer> idPosts = postDao.findByCategory(offset, pageable.getPageSize(), postByCategoryRequest.getIdsCategories());
		if (!idPosts.isEmpty()) {
			List<PostFeedResult> result = postDao.findAllActives(idPosts);
			return postFeedResultToPage(result, pageable, totalIdPosts);
		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public Page<PostFeedResponse> searchPosts(final Pageable pageable, final String searchField) {
		long totalIdPosts = postDao.totalSearchPosts(searchField).longValue();
		int offset = pageable.getOffset();
		List<Integer> idPosts = postDao.searchPosts(offset,pageable.getPageSize(), searchField);
		if (!idPosts.isEmpty()) {
			List<PostFeedResult> result = postDao.findAllActives(idPosts);
			return postFeedResultToPage(result, pageable, totalIdPosts);
		} else {
			return null;
		}
	}

	public Page<PostFeedResponse> postFeedResultToPage(List<PostFeedResult> result, Pageable pageable,
			long totalResult) {
		Page<PostFeedResponse> pageResponse = new PageImpl<>(postFeedResultToResponse(result), pageable, totalResult);
		return pageResponse;
	}

	public List<PostFeedResponse> postFeedResultToResponse(List<PostFeedResult> result) {
		Map<Integer, PostFeedResponse> postCategories = new HashMap<Integer, PostFeedResponse>();
		for (PostFeedResult rst : result) {
			if (postCategories.get(rst.getIdPost()) != null) {
				PostFeedResponse resp = postCategories.get(rst.getIdPost());
				resp.getCategories().add(rst.getCategory());
				postCategories.put(rst.getIdPost(), resp);
			} else {
				PostFeedResponse resp = new PostFeedResponse();
				resp.setIdPost(rst.getIdPost());
				resp.setPostName(rst.getPostName());
				resp.setMinAge(rst.getMinAge());
				resp.setMaxAge(rst.getMaxAge());
				resp.setDescription(rst.getDescription());
				resp.setOwner(rst.getOwner());
				List<String> categories = new ArrayList<>();
				categories.add(rst.getCategory());
				resp.setCategories(categories);
				postCategories.put(rst.getIdPost(), resp);
			}
		}
		List<PostFeedResponse> postFeed = new ArrayList<PostFeedResponse>(postCategories.values());
		return postFeed;
	}

	public PostDetailResponse postDetailResultToResponse(List<PostDetailResult> result) {
		PostDetailResponse response = new PostDetailResponse();
		List<String> categories = new ArrayList<>();
		List<String> materials = new ArrayList<>();
		for (int i = 0; i < result.size(); i++) {
			if (i == 0) {
				response.setIdPost(result.get(i).getIdPost());
				response.setPostName(result.get(i).getPostName());
				response.setMinAge(result.get(i).getMinAge());
				response.setMaxAge(result.get(i).getMaxAge());
				response.setDescription(result.get(i).getDescription());
				response.setDateInsert(result.get(i).getDateInsert());
				response.setOwner(result.get(i).getOwner());
			}
			if (!categories.contains(result.get(i).getCategory())) {
				categories.add(result.get(i).getCategory());
			}
			if (!materials.contains(result.get(i).getMaterial())) {
				materials.add(result.get(i).getMaterial());
			}
		}
		response.setCategories(categories);
		response.setMaterials(materials);

		return response;
	}
}
