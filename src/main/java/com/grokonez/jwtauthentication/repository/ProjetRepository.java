package com.grokonez.jwtauthentication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grokonez.jwtauthentication.model.Projet;
import com.grokonez.jwtauthentication.model.User;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
    List<Projet> findByProfesseur(User user);
}