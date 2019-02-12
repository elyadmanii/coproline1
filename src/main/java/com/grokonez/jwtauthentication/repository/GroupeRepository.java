package com.grokonez.jwtauthentication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grokonez.jwtauthentication.model.Groupe;
import com.grokonez.jwtauthentication.model.User;

@Repository
public interface GroupeRepository extends JpaRepository<Groupe, Long> {
     
	List<Groupe> findByProfesseur(User user);
}