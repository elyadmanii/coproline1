package com.grokonez.jwtauthentication.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "production_taches")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductionTache {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String document;
	private Date date;
	 
    private String fileName;
     
    private String fileType;
    
    private String uuid;
    @Lob
    @JsonIgnore
    private byte[] data;
	
	@ManyToOne
	@JoinColumn(name="eleve_id")
	private User eleve;
	
	@ManyToOne
	@JoinColumn(name="tache_id")
	private Tache tache1;
	
	@ManyToOne
	@JoinColumn(name="groupe_id")
	private Groupe groupe3;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getEleve() {
		return eleve;
	}

	public void setEleve(User eleve) {
		this.eleve = eleve;
	}

	public Tache getTache1() {
		return tache1;
	}

	public void setTache1(Tache tache1) {
		this.tache1 = tache1;
	}

	public Groupe getGroupe3() {
		return groupe3;
	}

	public void setGroupe3(Groupe groupe3) {
		this.groupe3 = groupe3;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
	
}
