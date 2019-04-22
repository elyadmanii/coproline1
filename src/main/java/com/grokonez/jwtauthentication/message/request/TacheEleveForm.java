package com.grokonez.jwtauthentication.message.request;

import java.util.ArrayList;
import java.util.List;

public class TacheEleveForm {
    
	private Long tache;	 
    private List<Long> eleves =new ArrayList<>();
	public Long getTache() {
		return tache;
	}
	public void setTache(Long tache) {
		this.tache = tache;
	}
	public List<Long> getEleves() {
		return eleves;
	}
	public void setEleves(List<Long> eleves) {
		this.eleves = eleves;
	}
    
}