package com.microservice.authserver.service;

import java.net.URISyntaxException;
import java.util.List;

import com.microservice.authserver.entity.User;

public interface UserService {
	
	User addUser(User user) throws URISyntaxException;
	List<User> getAllUsers();
	User getUserById(Long id);
	String deleteUser(Long id);
	User editUser(User user) throws URISyntaxException;
	List<User> searchByName(String name, String nic);
	
	
}
