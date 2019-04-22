package com.grokonez.jwtauthentication.message.request;

import javax.validation.constraints.NotBlank;

public class SousTacheForm {
    
	@NotBlank
    private String nom;
	 
    private Long tache;
     
    @NotBlank
    private String description;
   
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
  
	public Long getTache() {
		return tache;
	}

	public void setTache(Long tache) {
		this.tache = tache;
	}
  
}