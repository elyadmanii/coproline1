package com.grokonez.jwtauthentication.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.OnDelete;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "username"
        }),
        @UniqueConstraint(columnNames = {
            "email"
        })
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min=3, max = 50)
    private String name;
    
    @NotBlank
    @Size(min=3, max = 50)
    private String lastName;

    @NotBlank
    @Size(min=3, max = 50)
    private String username;

    @NaturalId
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min=6, max = 100)
    @JsonIgnore
    private String password;
    
    private String profil;
    

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", 
    	joinColumns = @JoinColumn(name = "user_id"), 
    	inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    
    @ManyToOne(fetch=FetchType.LAZY)
	@OnDelete(action=org.hibernate.annotations.OnDeleteAction.CASCADE)
	@JoinColumn(name="userSup_id")
    @JsonIgnore
	private User userSuperieur;
    
    @OneToMany(mappedBy="professeur",fetch=FetchType.LAZY)
    @JsonIgnore
	private Collection<Projet> projets;
    
    @OneToMany(mappedBy="userSuperieur",fetch=FetchType.LAZY)
    @JsonIgnore
	private Collection<User> eleves;
    
    
    
    @OneToMany(mappedBy="user1",fetch=FetchType.LAZY)
    @JsonIgnore
	private Collection<GroupeUser> groupeUsers;
    
    @OneToMany(mappedBy="eleve",fetch=FetchType.LAZY)
    @JsonIgnore
	private Collection<ProductionTache> productionTaches;
    
    @OneToMany(mappedBy="eleve_tache",fetch=FetchType.LAZY)
    @JsonIgnore
	private Collection<TacheEleve> tacheEleves;

    public User() {}

    public User(String name,String lastName, String username, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
     
	public User getUserSuperieur() {
		return userSuperieur;
	}

	public void setUserSuperieur(User userSuperieur) {
		this.userSuperieur = userSuperieur;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Collection<Projet> getProjets() {
		return projets;
	}

	public void setProjets(Collection<Projet> projets) {
		this.projets = projets;
	}

	public Collection<GroupeUser> getGroupeUsers() {
		return groupeUsers;
	}

	public void setGroupeUsers(Collection<GroupeUser> groupeUsers) {
		this.groupeUsers = groupeUsers;
	}

	public Collection<ProductionTache> getProductionTaches() {
		return productionTaches;
	}

	public void setProductionTaches(Collection<ProductionTache> productionTaches) {
		this.productionTaches = productionTaches;
	}

	public String getProfil() {
		return profil;
	}

	public void setProfil(String profil) {
		this.profil = profil;
	}

	public Collection<User> getEleves() {
		return eleves;
	}

	public void setEleves(Collection<User> eleves) {
		this.eleves = eleves;
	}

	public Collection<TacheEleve> getTacheEleves() {
		return tacheEleves;
	}

	public void setTacheEleves(Collection<TacheEleve> tacheEleves) {
		this.tacheEleves = tacheEleves;
	}
	
}