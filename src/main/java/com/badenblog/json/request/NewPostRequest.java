package com.badenblog.json.request;

import com.badenblog.persistence.domain.Post;
import com.badenblog.util.dto.CategoryDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewPostRequest {
	Post post;
	Set<String> materials = new HashSet<>();
	List<CategoryDTO> categories = new ArrayList<>();
	
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public Set<String> getMaterials() {
		return materials;
	}
	public void setMaterials(Set<String> materials) {
		this.materials = materials;
	}
	public List<CategoryDTO> getCategories() {
		return categories;
	}
	public void CategoryDTO(List<CategoryDTO> categories) {
		this.categories = categories;
	}
	
	
}
