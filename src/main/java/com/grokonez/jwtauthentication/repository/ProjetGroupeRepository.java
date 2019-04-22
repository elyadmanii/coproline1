package com.grokonez.jwtauthentication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grokonez.jwtauthentication.model.Projet;
import com.grokonez.jwtauthentication.model.ProjetGroupe;

@Repository
public interface ProjetGroupeRepository extends JpaRepository<ProjetGroupe, Long> {
     List<ProjetGroupe> findAllByProjet(Projet projet);
}