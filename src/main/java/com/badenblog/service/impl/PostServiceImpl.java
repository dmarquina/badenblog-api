package com.badenblog.service.impl;

import com.badenblog.common.BadenblogException;
import com.badenblog.common.constants.Constants;
import com.badenblog.dao.PostDao;
import com.badenblog.json.request.NewPostRequest;
import com.badenblog.json.request.PostByCategoryRequest;
import com.badenblog.json.response.NewPostResponse;
import com.badenblog.json.response.PostDetailResponse;
import com.badenblog.json.response.PostFeedResponse;
import com.badenblog.persistence.domain.*;
import com.badenblog.repository.MaterialRepository;
import com.badenblog.repository.PostCategoryRepository;
import com.badenblog.repository.PostMaterialRepository;
import com.badenblog.repository.PostRepository;
import com.badenblog.service.PostService;
import com.badenblog.util.queryresult.PostDetailResult;
import com.badenblog.util.queryresult.PostFeedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
            Post newPost = new Post();
            newPost.setTittle(newPostRequest.getTittle());
            newPost.setDescription(newPostRequest.getDescription());
            newPost.setMinAge(newPostRequest.getMinAge());
            newPost.setState(Constants.ACTIVE_STATE);
            newPost.setLikes(Constants.CERO_ENTERO);
            newPost.setDateInsert(new Date());
            postRepository.save(newPost);

            if (!newPostRequest.getMaterials().isEmpty()) {
                newPostRequest.getMaterials().stream()
                        .map(m -> Optional.ofNullable(materialRepository.findByName(m))
                                .map(Material::getIdMaterial)
                                .orElse(materialRepository.save(new Material(m, new Date())).getIdMaterial()))
                        .forEach(m -> postMaterialRepository
                                .save(new PostMaterial(new Post(newPost.getIdPost()), new Material(m))));
            }

            newPostRequest.getCategories().stream()
                    .forEach(c -> postCategoryRepository
                            .save(new PostCategory(new Post(newPost.getIdPost()), new Category(c.getIdCategory()))));

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
        List<Integer> idPosts = postDao.findIdActivePosts(offset, pageable.getPageSize());
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
        if (result.isEmpty()) {
            throw new BadenblogException("No existe la actividad");
        }
        return postDetailResultToResponse(result);
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
        List<Integer> idPosts = postDao.searchPosts(offset, pageable.getPageSize(), searchField);
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

    public List<PostFeedResponse> postFeedResultToResponse(List<PostFeedResult> postFeedResult) {
        Map<Integer, PostFeedResponse> postFeedCategories = new HashMap<Integer, PostFeedResponse>();

        postFeedResult.stream()
                .forEach(psr -> {
                    Optional<PostFeedResponse> opt = Optional.ofNullable(postFeedCategories.get(psr.getIdPost()));
                    opt.map(postFeedResponse -> {
                        Optional.ofNullable(psr.getCategory())
                                .ifPresent(c -> postFeedResponse.getCategories().add(c));
                        postFeedCategories.put(psr.getIdPost(), postFeedResponse);
                        return postFeedCategories;
                    }).orElseGet(() -> {
                        PostFeedResponse resp = new PostFeedResponse();
                        resp.setIdPost(psr.getIdPost());
                        resp.setPostName(psr.getPostName());
                        resp.setMinAge(psr.getMinAge());
                        resp.setMaxAge(psr.getMaxAge());
                        resp.setDescription(psr.getDescription());
                        resp.setOwner(psr.getOwner());
                        Optional.ofNullable(psr.getCategory())
                                .ifPresent(c -> resp.setCategories(new HashSet<>(Arrays.asList(c))));
                        postFeedCategories.put(psr.getIdPost(), resp);
                        return postFeedCategories;
                    });
                });
        List<PostFeedResponse> postFeed = new ArrayList<PostFeedResponse>(postFeedCategories.values());
        return postFeed;
    }

    public PostDetailResponse postDetailResultToResponse(List<PostDetailResult> result) {
        Optional<List<PostDetailResult>> optResult = Optional.ofNullable(result);
        return optResult.map(res -> {
                    PostDetailResponse postDetailreponse = new PostDetailResponse();
                    res.stream()
                            .forEach(postDetailResult -> {
                                Optional.ofNullable(postDetailResult.getCategory())
                                        .ifPresent(cat -> postDetailreponse.getCategories().add(cat));
                                Optional.ofNullable(postDetailResult.getMaterial())
                                        .ifPresent(mat -> postDetailreponse.getMaterials().add(mat));
                            });
                    PostDetailResult postDetailResult = res.stream().findAny().get();
                    postDetailreponse.setIdPost(postDetailResult.getIdPost());
                    postDetailreponse.setPostName(postDetailResult.getPostName());
                    postDetailreponse.setMinAge(postDetailResult.getMinAge());
                    postDetailreponse.setMaxAge(postDetailResult.getMaxAge());
                    postDetailreponse.setDescription(postDetailResult.getDescription());
                    postDetailreponse.setDateInsert(postDetailResult.getDateInsert());
                    postDetailreponse.setOwner(postDetailResult.getOwner());
                    return postDetailreponse;
                }
        ).orElseThrow(() -> new BadenblogException("No existe la actividad"));
    }
}