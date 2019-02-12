package com.grokonez.jwtauthentication.message.response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.grokonez.jwtauthentication.model.DocumentProjet;
import com.grokonez.jwtauthentication.model.GroupeUser;
import com.grokonez.jwtauthentication.model.Phase;
import com.grokonez.jwtauthentication.model.ProductionProjet;
import com.grokonez.jwtauthentication.model.Projet;
import com.grokonez.jwtauthentication.model.ProjetGroupe;

public class InfosProjet {
	
	private Projet projet;
	private Collection<ProductionProjet> productionProjets;
	private Collection<InfosPhase> phases=new ArrayList<>();
	private Collection<DocumentProjet> documentProjets;
	private Collection<GroupeUsers> groupes=new ArrayList<>();
	
	
	
	public InfosProjet(Projet projet) {
		super();
		this.projet = projet;
		this.productionProjets = this.projet.getProductionProjets();
		//this.phases = ;
		for (Iterator ip = this.projet.getPhases().iterator(); ip.hasNext();) {
			Phase phase=(Phase)ip.next();
			phases.add(new InfosPhase(phase));
		}
		
		this.documentProjets = this.projet.getDocumentProjets();
		
		GroupeUsers groupeUsers;
		for (Iterator iterator = this.projet.getProjetGroupes().iterator(); iterator.hasNext();) {
			ProjetGroupe pg = (ProjetGroupe) iterator.next();
			groupeUsers=new GroupeUsers(pg.getGroupe());
			 for (Iterator it = pg.getGroupe().getGroupeUsers().iterator(); it.hasNext();) {
				 GroupeUser gu = (GroupeUser) it.next();
				 groupeUsers.getUsers().add(gu);
			 }
			 groupes.add(groupeUsers);
		}
	}
	public Projet getProjet() {
		return projet;
	}
	public void setProjet(Projet projet) {
		this.projet = projet;
	}
	public Collection<ProductionProjet> getProductionProjets() {
		return productionProjets;
	}
	public void setProductionProjets(Collection<ProductionProjet> productionProjets) {
		this.productionProjets = productionProjets;
	}
	public Collection<InfosPhase> getPhases() {
		return phases;
	}
	public void setPhases(Collection<InfosPhase> phases) {
		this.phases = phases;
	}
	public Collection<DocumentProjet> getDocumentProjets() {
		return documentProjets;
	}
	public void setDocumentProjets(Collection<DocumentProjet> documentProjets) {
		this.documentProjets = documentProjets;
	}
	public Collection<GroupeUsers> getGroupes() {
		return groupes;
	}
	public void setGroupes(Collection<GroupeUsers> groupes) {
		this.groupes = groupes;
	}
	 
	
	
}
