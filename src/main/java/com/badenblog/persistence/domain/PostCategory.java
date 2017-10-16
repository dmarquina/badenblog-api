package com.badenblog.persistence.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the PostxMaterial database table.
 * 
 */
@Entity
@Table(name = "postcategory")
public class PostCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idPostcategory;

	// bi-directional many-to-one association to Post
	@ManyToOne
	@JoinColumn(name = "id_post")
	private Post post;

	// bi-directional many-to-one association to Material
	@ManyToOne
	@JsonIgnoreProperties({"idCategory","description","PostCategory"})
	@JoinColumn(name = "id_category")
	private Category category;

	public PostCategory(){
	}
	
	public PostCategory(final Post post,final Category category){
		this.post = post;
		this.category = category;
	}
	
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getIdPostcategory() {
		return idPostcategory;
	}

	public void setIdPostcategory(int idPostcategory) {
		this.idPostcategory = idPostcategory;
	}

}
