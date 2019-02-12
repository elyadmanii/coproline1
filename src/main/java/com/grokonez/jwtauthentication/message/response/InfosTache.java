package com.grokonez.jwtauthentication.message.response;
import java.util.ArrayList;
import java.util.Collection;

import com.grokonez.jwtauthentication.model.ProductionTache;
import com.grokonez.jwtauthentication.model.SousTache;
import com.grokonez.jwtauthentication.model.Tache;
import com.grokonez.jwtauthentication.model.TacheEleve;

public class InfosTache {
	
	private Tache tache;

	private Collection<SousTache> sousTaches=new ArrayList<>();	 
	private Collection<ProductionTache> productionTaches=new ArrayList<>();	 
	private Collection<TacheEleve> tacheEleves=new ArrayList<>();
	
	public InfosTache(Tache tache) {
		super();
		this.tache=tache;
		this.sousTaches = tache.getSousTaches();
		this.productionTaches = tache.getProductionTaches();
		this.tacheEleves = tache.getTacheEleves();
		 
	}

	public Tache getTache() {
		return tache;
	}

	public void setTache(Tache tache) {
		this.tache = tache;
	}

	public Collection<SousTache> getSousTaches() {
		return sousTaches;
	}

	public void setSousTaches(Collection<SousTache> sousTaches) {
		this.sousTaches = sousTaches;
	}

	public Collection<ProductionTache> getProductionTaches() {
		return productionTaches;
	}

	public void setProductionTaches(Collection<ProductionTache> productionTaches) {
		this.productionTaches = productionTaches;
	}

	public Collection<TacheEleve> getTacheEleves() {
		return tacheEleves;
	}

	public void setTacheEleves(Collection<TacheEleve> tacheEleves) {
		this.tacheEleves = tacheEleves;
	}
	

}
