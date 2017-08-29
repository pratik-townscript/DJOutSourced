package com.townscript.demo.repository;

import com.townscript.demo.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsername(String username);
	
/*	@Query("SELECT distinct u.username from user u")
	List<String> findUsernames();*/
}
