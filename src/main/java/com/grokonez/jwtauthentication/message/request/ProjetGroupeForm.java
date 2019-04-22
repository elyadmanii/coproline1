package com.grokonez.jwtauthentication.message.request;

import java.util.ArrayList;
import java.util.List;

public class ProjetGroupeForm {
     
    private Long projet;
     
    private List<Long> groupes_added=new ArrayList<>();
    private List<Long> groupes_deleted=new ArrayList<>();
	
    public Long getProjet() {
		return projet;
	}
	public void setProjet(Long projet) {
		this.projet = projet;
	}
	public List<Long> getGroupes_added() {
		return groupes_added;
	}
	public void setGroupes_added(List<Long> groupes_added) {
		this.groupes_added = groupes_added;
	}
	public List<Long> getGroupes_deleted() {
		return groupes_deleted;
	}
	public void setGroupes_deleted(List<Long> groupes_deleted) {
		this.groupes_deleted = groupes_deleted;
	}
    	
}