package com.grokonez.jwtauthentication.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tache_eleve")
public class TacheEleve {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
	
	@ManyToOne
	@JoinColumn(name="tache_id")
	@JsonIgnore
 	private Tache tache_eleve;
	
	@ManyToOne
	@JoinColumn(name="eleve_id")
 	private User eleve_tache;

	public TacheEleve() {
		super(); 
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tache getTache_eleve() {
		return tache_eleve;
	}

	public void setTache_eleve(Tache tache_eleve) {
		this.tache_eleve = tache_eleve;
	}

	public User getEleve_tache() {
		return eleve_tache;
	}

	public void setEleve_tache(User eleve_tache) {
		this.eleve_tache = eleve_tache;
	}

}
