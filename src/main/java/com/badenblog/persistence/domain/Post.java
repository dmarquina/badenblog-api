package com.badenblog.persistence.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the post database table.
 * 
 */
@Entity
@Table(name = "post")
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_post")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idPost;

	private String tittle;

	private String description;
	
	private int state;

	private String place;
	
	private int likes;
	
	private String suggestions;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateInsert;

	@ManyToOne
	@JoinColumn(name = "id_user")
	private Userprofile owner;
	
	@OneToMany(mappedBy = "post")
	private Set<PostCategory> postcategories;

	// bi-directional many-to-one association to PostMaterial
	@OneToMany(mappedBy = "post")
	private Set<PostMaterial> postmaterials ;
	
	public Post() {
	}

	public Post(final int idPost) {
		this.idPost = idPost;
	}
	
	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(String suggestions) {
		this.suggestions = suggestions;
	}

	public Userprofile getOwner() {
		return owner;
	}

	public void setOwner(Userprofile owner) {
		this.owner = owner;
	}
	
	public Set<PostMaterial> getPostmaterials() {
		return postmaterials;
	}
	
	public void setPostmaterials(Set<PostMaterial> postmaterials) {
		this.postmaterials = postmaterials;
	}

	public Set<PostCategory> getPostcategories() {
		return postcategories;
	}
	
	public void setPostcategories(Set<PostCategory> postcategories) {
		this.postcategories = postcategories;
	}
	
	public int getIdPost() {
		return idPost;
	}

	public void setIdPost(int idPost) {
		this.idPost = idPost;
	}

	public Date getDateInsert() {
		return dateInsert;
	}

	public void setDateInsert(Date dateInsert) {
		this.dateInsert = dateInsert;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}