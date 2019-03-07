package com.microservice.authserver.service;

import java.net.URISyntaxException;
import java.util.List;

import com.microservice.authserver.entity.Privilege;

public interface PrivilegeService {

	Privilege addPrivilege(Privilege Privilege) throws URISyntaxException;
	List<Privilege> getAllPrivileges();
	Privilege getPrivilegeById(Long id);
	String deletePrivilege(Long id);
	Privilege editPrivilege(Privilege Privilege) throws URISyntaxException;
	
	Privilege searchByName(String name);
}
