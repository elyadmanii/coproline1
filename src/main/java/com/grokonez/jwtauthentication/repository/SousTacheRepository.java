package com.grokonez.jwtauthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grokonez.jwtauthentication.model.SousTache;

@Repository
public interface SousTacheRepository extends JpaRepository<SousTache, Long> {
}