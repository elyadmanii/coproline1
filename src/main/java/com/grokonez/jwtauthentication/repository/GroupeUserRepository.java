package com.grokonez.jwtauthentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grokonez.jwtauthentication.model.GroupeUser;

@Repository
public interface GroupeUserRepository extends JpaRepository<GroupeUser, Long> {
     
}