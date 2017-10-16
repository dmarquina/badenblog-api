package com.badenblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.badenblog.common.AbstractBadenblogController;
import com.badenblog.common.BadenblogResource;
import com.badenblog.json.request.NewPostRequest;
import com.badenblog.json.request.PostByCategoryRequest;
import com.badenblog.json.response.NewPostResponse;
import com.badenblog.json.response.PostDetailResponse;
import com.badenblog.json.response.PostFeedResponse;
import com.badenblog.service.PostService;

@RestController
@RequestMapping(value = "/post")
public class PostController extends AbstractBadenblogController {

	@Autowired
	private PostService postService;

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public BadenblogResource<NewPostResponse> create(@RequestBody NewPostRequest newPostRequest) throws Exception {
		return handleSuccessResponse(postService.create(newPostRequest));
	}

	@RequestMapping(value = "/{idPost}", method = RequestMethod.GET)
	PostDetailResponse findById(@PathVariable int idPost) {
		PostDetailResponse post = postService.findById(idPost);
		return post;
	}

	@RequestMapping(value = "/actives", method = RequestMethod.GET)
	Page<PostFeedResponse> listActive(Pageable pageable) {
		Page<PostFeedResponse> posts = postService.findAllActives(pageable);
		return posts;
	}

	@RequestMapping(value = "/byCategory", method = RequestMethod.POST)
	Page<PostFeedResponse> findByIdsCategory(Pageable pageable,
			@RequestBody PostByCategoryRequest postByCategoryRequest) throws Exception {
		return postService.findByCategory(pageable, postByCategoryRequest);
	}
	
	@RequestMapping(value = "/search/{searchField}", method = RequestMethod.GET)
	Page<PostFeedResponse> searchPosts(Pageable pageable,@PathVariable String searchField) throws Exception {
		return postService.searchPosts(pageable, searchField);
	}
	
}
