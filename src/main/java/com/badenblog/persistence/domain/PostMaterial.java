package com.badenblog.persistence.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the PostxMaterial database table.
 * 
 */
@Entity
@Table(name = "postmaterial")
@NamedQuery(name = "PostMaterial.findAll", query = "SELECT a FROM PostMaterial a")
public class PostMaterial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idPostmaterial;

	// bi-directional many-to-one association to Post
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_post")
	private Post post;

	// bi-directional many-to-one association to Material
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_material")
	private Material material;

	public PostMaterial(){
	}
	
	public PostMaterial(Post post, Material material){
		this.post = post;
		this.material = material;
	}
	
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public int getIdPostmaterial() {
		return idPostmaterial;
	}

	public void setIdPostmaterial(int idPostmaterial) {
		this.idPostmaterial = idPostmaterial;
	}

}
