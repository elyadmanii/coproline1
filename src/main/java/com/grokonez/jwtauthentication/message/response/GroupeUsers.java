package com.grokonez.jwtauthentication.message.response;

import java.util.ArrayList;
import java.util.Collection;

import com.grokonez.jwtauthentication.model.Groupe;
import com.grokonez.jwtauthentication.model.GroupeUser;
import com.grokonez.jwtauthentication.model.User;

public class GroupeUsers {
	private Groupe groupe;
	private Collection<GroupeUser> groupeUsers=new ArrayList<>();
	 
	public GroupeUsers(Groupe groupe) {
		super();
		this.groupe = groupe;
	}
	
	public Groupe getGroupe() {
		return groupe;
	}
	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}
	public Collection<GroupeUser> getUsers() {
		return groupeUsers;
	}
	public void setUsers(Collection<GroupeUser> users) {
		this.groupeUsers = users;
	}
	
}
