package com.grokonez.jwtauthentication.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "groupes_users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class GroupeUser {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private Boolean coordinateur;
	
	@ManyToOne
	@JoinColumn(name="groupe_id")
	private Groupe groupe1;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user1;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getCoordinateur() {
		return coordinateur;
	}

	public void setCoordinateur(Boolean coordinateur) {
		this.coordinateur = coordinateur;
	}

	public Groupe getGroupe1() {
		return groupe1;
	}

	public void setGroupe1(Groupe groupe1) {
		this.groupe1 = groupe1;
	}

	public User getUser1() {
		return user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}
	
	 
}
