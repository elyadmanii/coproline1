package com.grokonez.jwtauthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grokonez.jwtauthentication.model.DocumentProjet;

@Repository
public interface DocumentProjetRepository extends JpaRepository<DocumentProjet, Long> {
      
}