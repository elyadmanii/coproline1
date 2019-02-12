package com.grokonez.jwtauthentication.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "production_projets")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductionProjet {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String document;
	private String description;
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="groupe_id")
	private Groupe groupe2;
	
	@ManyToOne
	@JoinColumn(name="projet_id")
	private Projet projet4;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Groupe getGroupe2() {
		return groupe2;
	}

	public void setGroupe2(Groupe groupe2) {
		this.groupe2 = groupe2;
	}

	public Projet getProjet4() {
		return projet4;
	}

	public void setProjet4(Projet projet4) {
		this.projet4 = projet4;
	}
	
}
