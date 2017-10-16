package com.badenblog.persistence.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Table(name = "category")
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class Category implements Serializable{

	private static final long serialVersionUID = 1L;

	public Category() {}
	
	public Category(final int idCategory) {
		this.idCategory = idCategory;
	}

	@Id
	@Column(name = "id_category")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idCategory;

	private String name;

	private String description;

	@OneToMany(mappedBy = "category",fetch=FetchType.LAZY)
	private Set<PostCategory> postcategories;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateInsert;

	@JsonIgnore
	private String userInsert;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

	public Date getDateInsert() {
		return dateInsert;
	}

	public void setDateInsert(Date dateInsert) {
		this.dateInsert = dateInsert;
	}

	public String getUserInsert() {
		return userInsert;
	}

	public void setUserInsert(String userInsert) {
		this.userInsert = userInsert;
	}

	public void setPostcategories(Set<PostCategory> postcategories) {
		this.postcategories = postcategories;
	}
}
