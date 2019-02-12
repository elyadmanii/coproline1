package com.grokonez.jwtauthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grokonez.jwtauthentication.model.Tache;

@Repository
public interface TacheRepository extends JpaRepository<Tache, Long> {
}