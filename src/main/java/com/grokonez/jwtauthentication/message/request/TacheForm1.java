package com.grokonez.jwtauthentication.message.request;

import java.util.Date;

import javax.validation.constraints.NotBlank;

public class TacheForm1 {
    
	@NotBlank
    private String nom;
	 
    private Long phase;
     
    @NotBlank
    private String description;
 
    private Date dd;    
    private Date df;
     

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
  
	public Long getPhase() {
		return phase;
	}

	public void setPhase(Long phase) {
		this.phase = phase;
	}

	public Date getDd() {
		return dd;
	}

	public void setDd(Date dd) {
		this.dd = dd;
	}

	public Date getDf() {
		return df;
	}

	public void setDf(Date df) {
		this.df = df;
	} 
}