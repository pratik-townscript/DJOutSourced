package com.townscript.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.townscript.demo.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByName(String name);
}
