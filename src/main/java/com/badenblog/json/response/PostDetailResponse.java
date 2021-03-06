package com.badenblog.json.response;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PostDetailResponse {
	private Integer idPost;
	private String postName;
	private Integer minAge;
	private Integer maxAge;
	private String description;
	private Date dateInsert;
	private String owner;
	private Set<String> categories;
	private Set<String> materials;

	public PostDetailResponse(){
		this.categories = new HashSet();
		this.materials = new HashSet();
	}

	public Integer getIdPost() {
		return idPost;
	}

	public void setIdPost(Integer idPost) {
		this.idPost = idPost;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Set<String> getCategories() {
		return this.categories;
	}

	public void setCategories(Set<String> categories) {
		this.categories = categories;
	}

	public Date getDateInsert() {
		return dateInsert;
	}

	public void setDateInsert(Date dateInsert) {
		this.dateInsert = dateInsert;
	}

	public Set<String> getMaterials() {
		return this.materials;
	}

	public void setMaterials(Set<String> materials) {
		this.materials = materials;
	}

	public Integer getMinAge() {
		return minAge;
	}

	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}

	public Integer getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}

}
