package com.grokonez.jwtauthentication.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "phases")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Phase {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String nom;
	private String description;
	private Date dateDebut;
	private Date dateFin;
	
	private String uuid;
	
	@ManyToOne
	@JoinColumn(name="projet_id")
	private Projet projet2;
	
	@OneToMany(mappedBy="phase",fetch=FetchType.LAZY, cascade = {javax.persistence.CascadeType.ALL})
	@Cascade({org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	@JsonIgnore
	private Collection<Tache> taches=new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Projet getProjet2() {
		return projet2;
	}

	public void setProjet2(Projet projet2) {
		this.projet2 = projet2;
	}
    
	public Collection<Tache> getTaches() {
		return taches;
	}

	public void setTaches(Collection<Tache> taches) {
		this.taches = taches;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
}
