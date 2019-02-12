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
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "projets", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "nom"
        })
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Projet {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String nom;
	private String description;
	private Date dateCreation;
	private Date dateModification;
	private Date dateDebut;
	private Date dateFin;
	
	@ManyToOne
	@JoinColumn(name="professeur_id")
	private User professeur;
	
	@OneToMany(mappedBy="projet",fetch=FetchType.LAZY)
	@JsonIgnore
	private Collection<ProjetGroupe> projetGroupes;
	
	@OneToMany(mappedBy="projet3",fetch=FetchType.LAZY)
	@JsonIgnore
	private Collection<DocumentProjet> documentProjets;
	
	@OneToMany(mappedBy="projet2",fetch=FetchType.LAZY)
	@JsonIgnore
	private Collection<Phase> phases;
	
	@OneToMany(mappedBy="projet4",fetch=FetchType.LAZY)
	@JsonIgnore
	private Collection<ProductionProjet> productionProjets;
	
	
    
	public Projet() {
		super();
	}

	public Projet(String nom, String description, Date dateCreation, Date dateModification) {
		super();
		this.nom = nom;
		this.description = description;
		this.dateCreation = dateCreation;
		this.dateModification = dateModification;
	}

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


	public Date getDateCreation() {
		return dateCreation;
	}


	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	public Date getDateModification() {
		return dateModification;
	}


	public void setDateModification(Date dateModification) {
		this.dateModification = dateModification;
	}


	public User getProfesseur() {
		return professeur;
	}

	@JsonIgnore
	public void setProfesseur(User professeur) {
		this.professeur = professeur;
	}

	public Collection<ProjetGroupe> getProjetGroupes() {
		return projetGroupes;
	}

	public void setProjetGroupes(Collection<ProjetGroupe> projetGroupes) {
		this.projetGroupes = projetGroupes;
	}

	public Collection<DocumentProjet> getDocumentProjets() {
		return documentProjets;
	}

	public void setDocumentProjets(Collection<DocumentProjet> documentProjets) {
		this.documentProjets = documentProjets;
	}

	public Collection<Phase> getPhases() {
		return phases;
	}

	public void setPhases(Collection<Phase> phases) {
		this.phases = phases;
	}

	public Collection<ProductionProjet> getProductionProjets() {
		return productionProjets;
	}

	public void setProductionProjets(Collection<ProductionProjet> productionProjets) {
		this.productionProjets = productionProjets;
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
}
