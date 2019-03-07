package com.microservice.authserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.authserver.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {	

	Role findByName(String name);
}
