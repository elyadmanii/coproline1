package com.grokonez.jwtauthentication.message.request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

public class PhaseForm {
    
	@NotBlank
    private String nom;
     
    @NotBlank
    private String description;
 
    private Date date_debut;    
    private Date date_fin;
    
    private List<TacheForm> taches=new ArrayList<>();

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

	public Date getDate_debut() {
		return date_debut;
	}

	public List<TacheForm> getTaches() {
		return taches;
	}

	public void setTaches(List<TacheForm> taches) {
		this.taches = taches;
	}

	public void setDate_debut(Date date_debut) {
		this.date_debut = date_debut;
	}

	public Date getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(Date date_fin) {
		this.date_fin = date_fin;
	}
	
}