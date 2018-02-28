package com.badenblog.json.request;

import com.badenblog.util.dto.CategoryDTO;

import java.util.*;

public class NewPostRequest {
	private String tittle;
	private String description;
	private Integer minAge;
	Set<String> materials = new HashSet<>();
	List<CategoryDTO> categories = new ArrayList<>();

	public String getTittle() {return tittle;}
	public void setTittle(String tittle) {this.tittle = tittle;}
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	public Integer getMinAge() {return minAge;}
	public void setMinAge(Integer minAge) {this.minAge = minAge;}
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
