package com.grokonez.jwtauthentication.message.request;
import java.util.Date;
import javax.validation.constraints.NotBlank;

public class PhaseForm1 {
    
	@NotBlank
    private String nom;
	 
    private Long projet;
     
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
  
	public Long getProjet() {
		return projet;
	}

	public void setProjet(Long projet) {
		this.projet = projet;
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