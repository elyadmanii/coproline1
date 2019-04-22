package com.grokonez.jwtauthentication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.grokonez.jwtauthentication.model.TacheEleve;

@Repository
public interface TacheEleveRepository extends JpaRepository<TacheEleve, Long> {
	
	//List<TacheEleve> findAllByTache_eleve(Tache tache);
	@Query("SELECT t FROM TacheEleve t WHERE t.tache_eleve.id = ?1")
	List<TacheEleve> findAllByTache_eleve(Long tache);
}