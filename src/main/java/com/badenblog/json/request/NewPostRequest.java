package com.badenblog.json.request;

import java.util.List;

import com.badenblog.persistence.domain.Post;
import com.badenblog.util.dto.CategoryDTO;

public class NewPostRequest {
	Post post;
	List<String> materials;
	List<CategoryDTO> categories;
	
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public List<String> getMaterials() {
		return materials;
	}
	public void setMaterials(List<String> materials) {
		this.materials = materials;
	}
	public List<CategoryDTO> getCategories() {
		return categories;
	}
	public void CategoryDTO(List<CategoryDTO> categories) {
		this.categories = categories;
	}
	
	
}
