package com.grokonez.jwtauthentication.message.request;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

public class GoupeForm {
    
	@NotBlank
    private String nom;
	private Long coordinateur; 
    private List<Long> users=new ArrayList<>();
	
    public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Long getCoordinateur() {
		return coordinateur;
	}
	public void setCoordinateur(Long coordinateur) {
		this.coordinateur = coordinateur;
	}
	public List<Long> getUsers() {
		return users;
	}
	public void setUsers(List<Long> users) {
		this.users = users;
	}
      
}