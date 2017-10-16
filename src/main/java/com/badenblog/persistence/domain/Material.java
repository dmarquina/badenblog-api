package com.badenblog.persistence.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the material database table.
 * 
 */
@Entity
@Table(name = "material")
@NamedQuery(name = "Material.findAll", query = "SELECT a FROM Material a")

public class Material implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public Material(){}

	public Material(final int idMaterial){
		this.idMaterial = idMaterial;
	}
	
	public Material(final String name){
		this.name = name;
	}
	
	@Id
	@Column(name = "id_material")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idMaterial;

	private String name;

	private double price;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateInsert;

	// bi-directional many-to-one association to AdmTrPermisoxperfil
	@OneToMany(mappedBy = "material")
	private Set<PostMaterial> postmaterials;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(int idMaterial) {
		this.idMaterial = idMaterial;
	}

	public Date getDateInsert() {
		return dateInsert;
	}

	public void setDateInsert(Date dateInsert) {
		this.dateInsert = dateInsert;
	}

	public void setPostmaterials(Set<PostMaterial> postmaterials) {
		this.postmaterials = postmaterials;
	}

}
