package com.grokonez.jwtauthentication.message.response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.grokonez.jwtauthentication.model.GroupeUser;
import com.grokonez.jwtauthentication.model.ProductionTache;
import com.grokonez.jwtauthentication.model.Projet;
import com.grokonez.jwtauthentication.model.User;

public class InfosUser {
	
	private User superieur;
	private Collection<Projet> projets;
	private Collection<InfosProjet> projets2=new ArrayList<>();
	private Collection<User> eleves;
	private Collection<ProductionTache> productionTaches;
	private Collection<GroupeUser> groupeUsers;
	
	 

	public InfosUser(User superieur, Collection<Projet> projets, Collection<User> eleves,
			Collection<ProductionTache> productionTaches, Collection<GroupeUser> groupeUsers) {
		super();
		this.superieur = superieur;
		this.projets = projets;
		for (Iterator iterator = projets.iterator(); iterator.hasNext();) {
			Projet p = (Projet) iterator.next();
			projets2.add(new InfosProjet(p));
		}
		this.eleves = eleves;
		this.productionTaches = productionTaches;
		this.groupeUsers = groupeUsers;
	}

	public User getSuperieur() {
		return superieur;
	}

	public void setSuperieur(User superieur) {
		this.superieur = superieur;
	}

	public Collection<Projet> getProjets() {
		return projets;
	}

	public void setProjets(Collection<Projet> projets) {
		this.projets = projets;
	}

	public Collection<User> getEleves() {
		return eleves;
	}

	public void setEleves(Collection<User> eleves) {
		this.eleves = eleves;
	}

	public Collection<ProductionTache> getProductionTaches() {
		return productionTaches;
	}

	public void setProductionTaches(Collection<ProductionTache> productionTaches) {
		this.productionTaches = productionTaches;
	}

	public Collection<GroupeUser> getGroupeUsers() {
		return groupeUsers;
	}

	public void setGroupeUsers(Collection<GroupeUser> groupeUsers) {
		this.groupeUsers = groupeUsers;
	}

	public Collection<InfosProjet> getProjets2() {
		return projets2;
	}

	public void setProjets2(Collection<InfosProjet> projets2) {
		this.projets2 = projets2;
	}
	 
}
