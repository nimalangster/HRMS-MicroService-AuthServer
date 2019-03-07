package com.microservice.authserver.service;

import java.net.URISyntaxException;
import java.util.List;

import com.microservice.authserver.entity.Role;

public interface RoleService {

	Role addRole(Role Role) throws URISyntaxException;
	List<Role> getAllRoles();
	Role getRoleById(Long id);
	String deleteRole(Long id);
	Role editRole(Role Role) throws URISyntaxException;
	
	Role searchByName(String name, String nic);
}
