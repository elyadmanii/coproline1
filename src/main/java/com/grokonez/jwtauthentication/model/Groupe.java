package com.grokonez.jwtauthentication.model;

import java.util.Collection;

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
@Table(name = "groupes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Groupe {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String nom;
	
	@ManyToOne
	@JoinColumn(name="professeur_id")
	@JsonIgnore
	private User professeur;
	
	@OneToMany(mappedBy="groupe",fetch=FetchType.LAZY)
	@JsonIgnore
	private Collection<ProjetGroupe> projetGroupes;
	
	@OneToMany(mappedBy="groupe1",fetch=FetchType.LAZY)
	@JsonIgnore
	private Collection<GroupeUser> groupeUsers;
	
	@OneToMany(mappedBy="groupe2",fetch=FetchType.LAZY)
	@JsonIgnore
	private Collection<ProductionProjet> productionProjets;
	
	@OneToMany(mappedBy="groupe3",fetch=FetchType.LAZY)
	@JsonIgnore
	private Collection<ProductionTache> productionTaches;

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

	public Collection<ProjetGroupe> getProjetGroupes() {
		return projetGroupes;
	}

	public void setProjetGroupes(Collection<ProjetGroupe> projetGroupes) {
		this.projetGroupes = projetGroupes;
	}

	public Collection<GroupeUser> getGroupeUsers() {
		return groupeUsers;
	}

	public void setGroupeUsers(Collection<GroupeUser> groupeUsers) {
		this.groupeUsers = groupeUsers;
	}

	public Collection<ProductionProjet> getProductionProjets() {
		return productionProjets;
	}

	public void setProductionProjets(Collection<ProductionProjet> productionProjets) {
		this.productionProjets = productionProjets;
	}

	public Collection<ProductionTache> getProductionTaches() {
		return productionTaches;
	}

	public void setProductionTaches(Collection<ProductionTache> productionTaches) {
		this.productionTaches = productionTaches;
	}

	public User getProfesseur() {
		return professeur;
	}

	public void setProfesseur(User professeur) {
		this.professeur = professeur;
	}
	 
}
