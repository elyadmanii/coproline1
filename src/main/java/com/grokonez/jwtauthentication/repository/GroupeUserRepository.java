package com.grokonez.jwtauthentication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.grokonez.jwtauthentication.model.GroupeUser;

@Repository
public interface GroupeUserRepository extends JpaRepository<GroupeUser, Long> {
     
	@Query("SELECT g FROM GroupeUser g WHERE g.groupe1.id = ?1")
	List<GroupeUser> findAllByGroupe(Long groupe);
}