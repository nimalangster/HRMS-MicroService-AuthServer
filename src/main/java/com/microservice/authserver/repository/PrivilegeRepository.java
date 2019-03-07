package com.microservice.authserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.authserver.entity.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

	public Privilege findByName(String name);
}
