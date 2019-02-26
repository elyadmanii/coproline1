package com.grokonez.jwtauthentication.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grokonez.jwtauthentication.model.DBFile;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {

}