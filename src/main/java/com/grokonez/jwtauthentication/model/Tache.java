package com.grokonez.jwtauthentication.model;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "taches")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tache {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String nom;
	private String description;
	private Date dateDebut;
	private Date dateFin;
	private Integer dureEstime;
	private String document;
	private Boolean complete;
	
	@ManyToOne
	@JoinColumn(name="phase_id")
 	private Phase phase;
	
	@OneToMany(mappedBy="tache",fetch=FetchType.LAZY)
	private Collection<SousTache> sousTaches;
	
	@OneToMany(mappedBy="tache1",fetch=FetchType.LAZY)
	@JsonIgnore
	private Collection<ProductionTache> productionTaches;
	
	@OneToMany(mappedBy="tache_eleve",fetch=FetchType.LAZY)
    @JsonIgnore
	private Collection<TacheEleve> tacheEleves;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Integer getDureEstime() {
		return dureEstime;
	}

	public void setDureEstime(Integer dureEstime) {
		this.dureEstime = dureEstime;
	}

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public Collection<SousTache> getSousTaches() {
		return sousTaches;
	}

	public void setSousTaches(Collection<SousTache> sousTaches) {
		this.sousTaches = sousTaches;
	}

	public Collection<ProductionTache> getProductionTaches() {
		return productionTaches;
	}

	public void setProductionTaches(Collection<ProductionTache> productionTaches) {
		this.productionTaches = productionTaches;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public Boolean getComplete() {
		return complete;
	}

	public void setComplete(Boolean complete) {
		this.complete = complete;
	}

	public Collection<TacheEleve> getTacheEleves() {
		return tacheEleves;
	}

	public void setTacheEleves(Collection<TacheEleve> tacheEleves) {
		this.tacheEleves = tacheEleves;
	}
	  
}
