package com.badenblog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.badenblog.json.request.NewPostRequest;
import com.badenblog.json.request.PostByCategoryRequest;
import com.badenblog.json.response.NewPostResponse;
import com.badenblog.json.response.PostDetailResponse;
import com.badenblog.json.response.PostFeedResponse;

public interface PostService {

	NewPostResponse create(NewPostRequest newPostRequest);

	Page<PostFeedResponse> findAllActives(Pageable pageable);

	PostDetailResponse findById(int idPost);

	Page<PostFeedResponse> findByCategory(Pageable pageable, PostByCategoryRequest postByIdsCategoryRequest);

	Page<PostFeedResponse> searchPosts(Pageable pageable, String searchField);
}
