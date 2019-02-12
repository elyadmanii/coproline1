package com.grokonez.jwtauthentication.message.response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.grokonez.jwtauthentication.model.Phase;
import com.grokonez.jwtauthentication.model.Tache;

public class InfosPhase {
	
	private Phase phase;
	private Collection<InfosTache> taches=new ArrayList<>();
	 
	public InfosPhase(Phase phase) {
		super();
		this.phase = phase;
		for (Iterator ip = this.phase.getTaches().iterator(); ip.hasNext();) {
			Tache tache=(Tache)ip.next();
			this.taches.add(new InfosTache(tache));
		}
	}

 	public Phase getPhase() {
		return phase;
	}
 
	public void setPhase(Phase phase) {
		this.phase = phase;
	}
 
	public Collection<InfosTache> getTaches() {
		return taches;
	}

	public void setTaches(Collection<InfosTache> taches) {
		this.taches = taches;
	}
		 
}
