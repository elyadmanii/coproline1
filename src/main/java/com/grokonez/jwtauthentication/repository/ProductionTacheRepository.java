package com.grokonez.jwtauthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grokonez.jwtauthentication.model.ProductionTache;

@Repository
public interface ProductionTacheRepository extends JpaRepository<ProductionTache, Long> {
}